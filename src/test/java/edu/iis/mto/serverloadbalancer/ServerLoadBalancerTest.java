package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.builders.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.builders.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

import edu.iis.mto.serverloadbalancer.builders.Builder;
import edu.iis.mto.serverloadbalancer.matchers.CurrentLoadPercentageMatcher;
import edu.iis.mto.serverloadbalancer.matchers.ServerVmsCountMatcher;

public class ServerLoadBalancerTest {

	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingServer_noVm_ServersStaysEmpty() {
		Server theServer = a(server().withCapacity(1));

		balance(anArrayOfServersWith(theServer), anEmptyArrayOfVms());

		assertThat(theServer, hasLoadPercentageOf(0.0d));
	}

	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));

		balance(anArrayOfServersWith(theServer), aArrayOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(100.0d));
		assertThat("the server contains vm", theServer.contains(theVm));
	}

	@Test
	public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent() {
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));
		balance(anArrayOfServersWith(theServer), aArrayOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(10.0d));
		assertThat("the server contains vm", theServer.contains(theVm));
	}

	@Test
	public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
		Server theServer = a(server().withCapacity(100));
		Vm theFirstVm = a(vm().ofSize(1));
		Vm theSecondVm = a(vm().ofSize(1));

		balance(anArrayOfServersWith(theServer), aArrayOfVmsWith(theFirstVm, theSecondVm));

		assertThat(theServer, hasVmsCountOf(2));
		assertThat("the server contains vms", theServer.contains(theFirstVm));
		assertThat("the server contains vms", theServer.contains(theSecondVm));
	}

	private <T> T a(Builder<T> builder) {
		return builder.build();
	}

	private Server[] anArrayOfServersWith(Server... servers) {
		return servers;
	}

	private Vm[] anEmptyArrayOfVms() {
		return new Vm[0];
	}

	private Vm[] aArrayOfVmsWith(Vm... vms) {
		return vms;
	}

	private void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	private Matcher<? super Server> hasLoadPercentageOf(double expectedPercentageLoad) {
		return new CurrentLoadPercentageMatcher(expectedPercentageLoad);
	}
	
	private Matcher<? super Server> hasVmsCountOf(int vmsCount) {
		return new ServerVmsCountMatcher(vmsCount);
	}
}
