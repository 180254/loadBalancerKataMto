package edu.iis.mto.serverloadbalancer.testplus;

import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.Vm;

/**
 * {@link ServersAndVmsTestPlus} helper class - {@link TestParameter} part.
 * Balance assertion - {@link Vm} should be located at {@link Server}.
 */
class BalanaceAssertion {

	int vmNo;
	int serverNo;

	/**
	 * Constructor.
	 * 
	 * @param serverNo
	 *            {@link Server} number (counted from 1)
	 * @param vmNo
	 *            {@link Vm} number (counted from 1)
	 */
	public BalanaceAssertion(int vmNo, int serverNo) {
		super();
		this.vmNo = vmNo;
		this.serverNo = serverNo;
	}
}