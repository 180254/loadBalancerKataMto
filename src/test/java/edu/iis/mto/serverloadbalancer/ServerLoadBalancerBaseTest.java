package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Matcher;

public class ServerLoadBalancerBaseTest {
	Vm[] aListOfVmsWith(Vm... vms) {
		return vms;
	}

	Server[] aListOfServersWith(Server... servers) {
		return servers;
	}

	protected static <T> T a(Builder<T> builder) {
		return builder.build();
	}

	protected void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	protected Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	protected Matcher<? super Server> hasLoadPercentageOf(double expectedPercentageLoad) {
		return new CurrentLoadPercentageMatcher(expectedPercentageLoad);
	}
}
