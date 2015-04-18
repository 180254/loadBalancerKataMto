package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(final Server[] servers, final Vm[] vms) {
		for (Vm vm : vms) {
			Server lessLoaded = null;
			for (Server server : servers) {
				if (lessLoaded == null || lessLoaded.getCurrentLoadPercentage() > server.getCurrentLoadPercentage()) {
					lessLoaded = server;
				}
			}
			lessLoaded.addVm(vm);
		}
	}
}
