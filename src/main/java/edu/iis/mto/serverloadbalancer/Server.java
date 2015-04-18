package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private List<Vm> runningVms = new ArrayList<Vm>();
	private int capacity;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public double getCurrentLoadPercentage() {
		final int TO_PERCENTAGE_MULTIPLIER = 100;

		int runningVmsSizeSum = runningVms.stream().mapToInt(vm -> vm.getSize()).sum();
		double serverLoad = runningVmsSizeSum / (double) capacity;

		return serverLoad * TO_PERCENTAGE_MULTIPLIER;
	}

	public void addVm(final Vm vm) {
		runningVms.add(vm);
	}

	public boolean contains(final Vm vm) {
		return runningVms.contains(vm);
	}

	public int getVmsCount() {
		return runningVms.size();
	}

}
