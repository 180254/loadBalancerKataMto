package edu.iis.mto.serverloadbalancer.testplus;

import edu.iis.mto.serverloadbalancer.Builder;
import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.Vm;

/**
 * {@link BalanceAssertionBuilder} builder.
 *
 */
public class BalanceAssertionBuilder implements Builder<BalanaceAssertion> {
	private int vmNo;
	private int serverNo;

	/**
	 * Part of balance assertion notation no 1: {@link Server} contains
	 * {@link Vm}.
	 * 
	 * @param serverNo
	 *            {@link Server} number (counted from 1)
	 * @return self
	 */
	public BalanceAssertionBuilder serverNo(int serverNo) {
		this.serverNo = serverNo;
		return this;
	}

	/**
	 * 
	 * Part of balance assertion notation no 1: {@link Server} contains
	 * {@link Vm}.
	 * 
	 * @param vmNo
	 *            {@link Vm} number (counted from 1)
	 * @return self
	 */
	public BalanceAssertionBuilder containsVmNo(int vmNo) {
		this.vmNo = vmNo;
		return this;
	}

	/**
	 * Part of balance assertion notation no 2: {@link Vm} is on {@link Server}.
	 * 
	 * @param vmNo
	 *            {@link Vm} number (counted from 1)
	 * @return self
	 */
	public BalanceAssertionBuilder vmNo(int vmNo) {
		this.vmNo = vmNo;
		return this;
	}

	/**
	 * Part of balance assertion notation no 2: {@link Vm} is on {@link Server}.
	 * 
	 * @param serverNo
	 *            {@link Server} number (counted from 1)
	 * @return self
	 */
	public BalanceAssertionBuilder isOnServerNo(int serverNo) {
		this.serverNo = serverNo;
		return this;
	}

	public static BalanceAssertionBuilder balanceassertion() {
		return new BalanceAssertionBuilder();
	}

	public BalanaceAssertion build() {
		return new BalanaceAssertion(vmNo, serverNo);
	}
}
