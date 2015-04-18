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
		return runningVms.stream().mapToInt(vm -> vm.getSize()).sum() / (capacity*1d) * 100;
	}

	public void addVm(Vm vm) {
		runningVms.add(vm);
	}

	public boolean contains(Vm vm) {
		return true;
	}

}
