package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(final Server[] servers, final Vm[] vms) {
		for (Vm vm : vms) {
			servers[0].addVm(vm);
		}
	}

}
