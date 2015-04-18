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
		Server server = a(server().withCapacity(1));
		balance(servers(server), noVms());
		assertThat(server, hasLoadPercentageOf(0.0d));
	}

	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server server = a(server().withCapacity(1));
		Vm vm = a(vm().ofSize(1));

		balance(servers(server), vms(vm));

		assertThat(server, hasLoadPercentageOf(100.0d));
		assertThat("the server contains vm", server.contains(vm));
	}

	@Test
	public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent() {
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));

		balance(servers(theServer), vms(theVm));

		assertThat(theServer, hasLoadPercentageOf(10.0d));
		assertThat("the server contains vm", theServer.contains(theVm));
	}

	@Test
	public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
		Server server = a(server().withCapacity(100));
		Vm firstVm = a(vm().ofSize(1));
		Vm secondVm = a(vm().ofSize(1));

		balance(servers(server), vms(firstVm, secondVm));

		assertThat(server, hasVmsCountOf(2));
		assertThat("the server contains vm", server.contains(firstVm));
		assertThat("the server contains vm", server.contains(secondVm));
	}

	@Test
	public void aVm_shouldBeBalanced_onLessLoadedServerFirst() {
		Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(45.0d));
		Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(50.0d));
		Vm vm = a(vm().ofSize(10));

		balance(servers(moreLoadedServer, lessLoadedServer), vms(vm));

		assertThat("the less loaded server contains vm", lessLoadedServer.contains(vm));
	}

	@Test
	public void balanceAServerWithNotEnoughRoom_shouldNotBeFilledWithAVm() {
		Server server = a(server().withCapacity(10).withCurrentLoadOf(90.0d));
		Vm vm = a(vm().ofSize(2));

		balance(servers(server), vms(vm));

		assertThat("the less loaded server doesn't contain vm", !server.contains(vm));
	}

	@Test
	public void balance_serversAndVms() {
		Server serverNo1 = a(server().withCapacity(4));
		Server serverNo2 = a(server().withCapacity(6));

		Vm vmNo1 = a(vm().ofSize(1));
		Vm vmNo2 = a(vm().ofSize(4));
		Vm vmNo3 = a(vm().ofSize(2));

		balance(servers(serverNo1, serverNo2), vms(vmNo1, vmNo2, vmNo3));

		assertThat("The server 1 contains the vm 1", serverNo1.contains(vmNo1));
		assertThat("The server 2 contains the vm 2", serverNo2.contains(vmNo2));
		assertThat("The server 1 contains the vm 3", serverNo1.contains(vmNo3));
		assertThat(serverNo1, hasLoadPercentageOf(75.0d));
		assertThat(serverNo2, hasLoadPercentageOf(66.66d));
	}

	private <T> T a(Builder<T> builder) {
		return builder.build();
	}

	private Server[] servers(Server... servers) {
		return servers;
	}

	private Vm[] noVms() {
		return new Vm[0];
	}

	private Vm[] vms(Vm... vms) {
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
