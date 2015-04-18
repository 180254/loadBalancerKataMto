package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	public static final double MAXIMUM_LOAD = 100d;
	private static final int TO_PERCENTAGE_MULTIPLIER = 100;

	private List<Vm> runningVms = new ArrayList<Vm>();
	private double currentLoadPercentage = 0;
	private int capacity;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

	public void addVm(final Vm vm) {
		currentLoadPercentage += vmCostAsLoad(vm);
		runningVms.add(vm);
	}

	public boolean contains(final Vm vm) {
		return runningVms.contains(vm);
	}

	public int getVmsCount() {
		return runningVms.size();
	}

	public boolean catFit(final Vm vm) {
		return (getCurrentLoadPercentage() + vmCostAsLoad(vm)) <= MAXIMUM_LOAD;
	}

	private double vmCostAsLoad(final Vm vm) {
		return vm.getSize() / (double) capacity * TO_PERCENTAGE_MULTIPLIER;
	}
}
