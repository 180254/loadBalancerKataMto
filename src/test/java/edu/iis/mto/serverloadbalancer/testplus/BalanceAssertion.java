package edu.iis.mto.serverloadbalancer.testplus;

import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.Vm;

/**
 * {@link ServersAndVmsTestPlus} helper class - {@link TestParameter} part.
 */
class BalanaceAssertion {

	int vmNo;
	int serverNo;

	/**
	 * * Balance assertion - {@link Vm} should be located at {@link Server}.
	 */
	public BalanaceAssertion() {
		super();
	}

	/**
	 * Balance assertion - {@link Vm} should be located at {@link Server}.
	 * 
	 * @param vmNo
	 *            {@link Vm} number (counted from 1)
	 * @param serverNo
	 *            {@link Server} number (counted from 1)
	 */
	public BalanaceAssertion(int vmNo, int serverNo) {
		super();
		this.vmNo = vmNo;
		this.serverNo = serverNo;
	}

	public BalanaceAssertion vm(int vmNo) {
		this.vmNo = vmNo;
		return this;
	}

	public BalanaceAssertion onserver(int serverNo) {
		this.serverNo = serverNo;
		return this;
	}

	public BalanaceAssertion server(int serverNo) {
		this.serverNo = serverNo;
		return this;
	}

	public BalanaceAssertion hasvm(int vmNo) {
		this.vmNo = vmNo;
		return this;
	}

	public static BalanaceAssertion balanceassertion() {
		return new BalanaceAssertion();
	}

}