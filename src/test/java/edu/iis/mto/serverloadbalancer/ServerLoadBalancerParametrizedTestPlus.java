package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

class TestParameter {

	int[] serversCapacities;
	int[] vmsSizes;
	BalanaceAssertion[] balanceAssertions;
	double[] serversLoads;

	public TestParameter withServersCapacities(int... serversCapacities) {
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
		List<Server> servers = new ArrayList<Server>(serversCapacities.length);

		for (int capacity : serversCapacities) {
			servers.add(server().withCapacity(capacity).build());
		}

		return servers.toArray(new Server[servers.size()]);
	}

	public Vm[] vms() {
		List<Vm> vms = new ArrayList<Vm>(vmsSizes.length);

		for (int vmSize : vmsSizes) {
			vms.add(vm().ofSize(vmSize).build());
		}

		return vms.toArray(new Vm[vms.size()]);
	}
}

class BalanaceAssertion {

	int serverNo;
	int vmNo;

	public BalanaceAssertion(int serverNo, int vmNo) {
		super();
		this.serverNo = serverNo;
		this.vmNo = vmNo;
	}
}

@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTestPlus extends ServerLoadBalancerBaseTest {

	@Parameters
	public static Collection<TestParameter[]> data() {
		return Arrays.asList(new TestParameter[][] { {
				new TestParameter()
						.withServersCapacities(4, 6)
						.withVmsSizes(1, 4, 2)
						.withBalancedAssertions(
								new BalanaceAssertion(1, 1),
								new BalanaceAssertion(2, 2),
								new BalanaceAssertion(1, 3))
						.withServersLoads(75d, 66.66d)
		} });
	}

	private TestParameter param;

	public ServerLoadBalancerParametrizedTestPlus(TestParameter param) {
		super();
		this.param = param;
	}

	@Test
	public void balance_serversAndVms() {
		Server[] servers = param.servers();
		Vm[] vms = param.vms();

		balance(servers, vms);

		for (int assetionNo = 0; assetionNo < param.balanceAssertions.length; assetionNo++) {
			int serverNo = param.balanceAssertions[assetionNo].serverNo - 1;
			int vmNo = param.balanceAssertions[assetionNo].vmNo - 1;

			assertThat("The server " + (serverNo + 1) + " contains the vm " + (vmNo + 1),
					servers[serverNo].contains(vms[vmNo]));
		}

		for (int serverNo = 0; serverNo < param.serversLoads.length; serverNo++) {
			double serverLoad = param.serversLoads[serverNo];

			assertThat(servers[serverNo], hasLoadPercentageOf(serverLoad));
		}

	}

}
