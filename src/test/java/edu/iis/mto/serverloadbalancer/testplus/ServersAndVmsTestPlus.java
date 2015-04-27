package edu.iis.mto.serverloadbalancer.testplus;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.ServerLoadBalancerBaseTest;
import edu.iis.mto.serverloadbalancer.Vm;

@RunWith(Parameterized.class)
public class ServersAndVmsTestPlus extends ServerLoadBalancerBaseTest {

	@Parameters
	public static Collection<TestParameter[]> data() {
		return Arrays.asList(new TestParameter[][] { {
				new TestParameter()
						.withServersCapacities(4, 6)
						.withVmsSizes(1, 4, 2)
						.withBalancedAssertions(
								new BalanaceAssertion(1, 1),
								new BalanaceAssertion(2, 2),
								new BalanaceAssertion(3, 1))
						.withServersLoads(75d, 66.66d)
		}, {
				new TestParameter()
						.withServersCapacities(3, 3, 6)
						.withVmsSizes(1, 4, 2, 2)
						.withBalancedAssertions(
								new BalanaceAssertion(1, 1),
								new BalanaceAssertion(2, 3),
								new BalanaceAssertion(3, 2),
								new BalanaceAssertion(4, 1))
						.withServersLoads(100d, 66.66d, 66.66d)
		} });
	}

	private TestParameter param;

	public ServersAndVmsTestPlus(TestParameter param) {
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
