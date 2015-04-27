package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

class TestParameter {
	int[] serversCapacities;
	int[] vmsSizes;
	int[][] balancedAssertions;
	double[] serversLoads;

	public TestParameter withServersCapacities(int... serversCapacities) {
		this.serversCapacities = serversCapacities;
		return this;
	}

	public TestParameter withVmsSizes(int... vmsSizes) {
		this.vmsSizes = vmsSizes;
		return this;
	}

	public TestParameter withBalancedAssertions(int[][] balancedAssertions) {
		this.balancedAssertions = balancedAssertions;
		return this;
	}

	public TestParameter withServersLoads(double... serverLoads) {
		this.serversLoads = serverLoads;
		return this;
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
								new int[][] {
								{ 1, 1 },
								{ 2, 2 },
								{ 1, 3 }
								})
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
		Server[] servers = servers();
		Vm[] vms = vms();

		balance(servers, vms);

		for (int assetionNo = 0; assetionNo < param.balancedAssertions.length; assetionNo++) {
			int serverNo = param.balancedAssertions[assetionNo][0] - 1;
			int vmNo = param.balancedAssertions[assetionNo][1] - 1;

			assertThat("The server " + (serverNo + 1) + " contains the vm " + (vmNo + 1),
					servers[serverNo].contains(vms[vmNo]));
		}

		for (int serverNo = 0; serverNo < param.serversLoads.length; serverNo++) {
			 double serverLoad = param.serversLoads[serverNo];
			 assertThat(servers[serverNo], hasLoadPercentageOf(serverLoad));
		}

	}

	private Server[] servers() {
		List<Server> servers = new ArrayList<Server>(param.serversCapacities.length);

		for (int capacity : param.serversCapacities) {
			servers.add(a(server().withCapacity(capacity)));
		}

		return servers.toArray(new Server[servers.size()]);
	}

	private Vm[] vms() {
		List<Vm> vms = new ArrayList<Vm>(param.vmsSizes.length);

		for (int vmSize : param.vmsSizes) {
			vms.add(a(vm().ofSize(vmSize)));
		}

		return vms.toArray(new Vm[vms.size()]);
	}

	private Matcher<? super Server> hasLoadPercentageOf(double expectedPercentageLoad) {
		return new CurrentLoadPercentageMatcher(expectedPercentageLoad);
	}
}
