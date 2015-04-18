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

		balance(anArrayOfServersWith(theServer), anArrayOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(100.0d));
		assertThat("the server contains vm", theServer.contains(theVm));
	}

	@Test
	public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent() {
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));

		balance(anArrayOfServersWith(theServer), anArrayOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(10.0d));
		assertThat("the server contains vm", theServer.contains(theVm));
	}

	@Test
	public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
		Server theServer = a(server().withCapacity(100));
		Vm theFirstVm = a(vm().ofSize(1));
		Vm theSecondVm = a(vm().ofSize(1));

		balance(anArrayOfServersWith(theServer), anArrayOfVmsWith(theFirstVm, theSecondVm));

		assertThat(theServer, hasVmsCountOf(2));
		assertThat("the server contains vms", theServer.contains(theFirstVm));
		assertThat("the server contains vms", theServer.contains(theSecondVm));
	}

	@Test
	public void aVm_shouldBeBalanced_onLessLoadedServerFirst() {
		Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(45.0d));
		Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(50.0d));
		Vm theVm = a(vm().ofSize(10));

		balance(anArrayOfServersWith(moreLoadedServer, lessLoadedServer), anArrayOfVmsWith(theVm));

		assertThat("the less loaded server contains vm", lessLoadedServer.contains(theVm));
	}

	@Test
	public void balanceAServerWithNotEnoughRoom_shouldNotBeFilledWithAVm() {
		Server theServer = a(server().withCapacity(10).withCurrentLoadOf(90.0d));
		Vm theVm = a(vm().ofSize(2));

		balance(anArrayOfServersWith(theServer), anArrayOfVmsWith(theVm));

		assertThat("the less loaded server should not contain vm", !theServer.contains(theVm));
	}

	@Test
	public void balance_serversAndVms() {
		Server server1 = a(server().withCapacity(4));
		Server server2 = a(server().withCapacity(6));

		Vm vm1 = a(vm().ofSize(1));
		Vm vm2 = a(vm().ofSize(4));
		Vm vm3 = a(vm().ofSize(2));

		balance(anArrayOfServersWith(server1, server2), anArrayOfVmsWith(vm1, vm2, vm3));

		assertThat("The server 1 contains the vm 1", server1.contains(vm1));
		assertThat("The server 2 contains the vm 2", server2.contains(vm2));
		assertThat("The server 1 contains the vm 3", server1.contains(vm3));
		assertThat(server1, hasLoadPercentageOf(75.0d));
		assertThat(server2, hasLoadPercentageOf(66.66d));
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

	private Vm[] anArrayOfVmsWith(Vm... vms) {
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
