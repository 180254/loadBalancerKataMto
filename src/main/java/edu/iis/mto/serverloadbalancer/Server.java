package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private List<Vm> runningVms = new ArrayList<Vm>();

	public double getCurrentLoadPercentage() {
		if (runningVms.size() > 0) {
			return 100;
		}
		else
			return 0;
	}

	public void addVm(Vm vm) {
		runningVms.add(vm);
	}

	public boolean contains(Vm vm) {
		return true;
	}

}
