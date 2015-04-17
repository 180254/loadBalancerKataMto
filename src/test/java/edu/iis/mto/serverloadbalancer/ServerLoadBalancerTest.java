package edu.iis.mto.serverloadbalancer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ServerLoadBalancerTest {

	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingServer_noVm_ServersStaysEmpty() {
		Server theServer = a(server().withCapacity(1));
		balance(aListOfServersWith(theServer), anEmptyArrayOfVms());
		assertThat(theServer, hasLoadPercentageOf(0.0d));
	}

	private Vm[] anEmptyArrayOfVms() {
		return new Vm[] {};
	}

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

	private void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	private Matcher<? super Server> hasLoadPercentageOf(double expectedPercentageLoad) {
		return new CurrentLoadPercentageMatcher(expectedPercentageLoad);
	}

	private Server[] aListOfServersWith(Server... server) {
		return server;
	}

	private ServerBuilder server() {
		return new ServerBuilder();
	}

}
