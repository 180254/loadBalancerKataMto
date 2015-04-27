package edu.iis.mto.serverloadbalancer.testplus;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.Vm;

/**
 * {@link ServersAndVmsTestPlus} helper class - test parameter.
 */
class TestParameter {

	int[] serversCapacities;
	int[] vmsSizes;
	BalanaceAssertion[] balanceAssertions;
	double[] serversLoads;

	TestParameter withServersCapacities(int... serversCapacities) {
		this.serversCapacities = serversCapacities;
		return this;
	}

	public TestParameter withVmsSizes(int... vmsSizes) {
		this.vmsSizes = vmsSizes;
		return this;
	}

	public TestParameter withBalancedAssertions(BalanaceAssertion... balanceAssertions) {
		this.balanceAssertions = balanceAssertions;
		return this;
	}

	public TestParameter withServersLoads(double... serverLoads) {
		this.serversLoads = serverLoads;
		return this;
	}

	public Server[] servers() {
		Server[] servers = new Server[serversCapacities.length];

		for (int i = 0; i < serversCapacities.length; i++) {
			servers[i] = server().withCapacity(serversCapacities[i]).build();
		}

		return servers;
	}

	public Vm[] vms() {
		Vm[] vms = new Vm[vmsSizes.length];

		for (int i = 0; i < vmsSizes.length; i++) {
			vms[i] = vm().ofSize(vmsSizes[i]).build();
		}

		return vms;
	}
}
