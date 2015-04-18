package edu.iis.mto.serverloadbalancer.builders;

import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.Vm;

public class ServerBuilder implements Builder<Server> {

	int capacity;
	double initialLoad;

	public ServerBuilder() {
		withCapacity(1);
		withCurrentLoadOf(0d);
	}

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public ServerBuilder withCurrentLoadOf(double initialLoad) {
		this.initialLoad = initialLoad;
		return this;
	}

	public Server build() {
		Server server = new Server(capacity);
		addInitialLoad(server);
		return server;
	}

	private void addInitialLoad(Server server) {
		if (initialLoad > 0) {
			int initialLoadVmSize = (int) (initialLoad / Server.MAXIMUM_LOAD * capacity);
			Vm initialLoadVm = new VmBuilder().ofSize(initialLoadVmSize).build();
			server.addVm(initialLoadVm);
		}
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

}
