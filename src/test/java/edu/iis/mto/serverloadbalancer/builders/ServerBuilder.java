package edu.iis.mto.serverloadbalancer.builders;

import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.Vm;

public class ServerBuilder implements Builder<Server> {

	int capacity;
	double initialLoad;

	public ServerBuilder() {
		withCapacity(1);
	}

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server(capacity);
		if (initialLoad > 0) {
			Vm vm = new VmBuilder().ofSize((int) (initialLoad / 100.0d * (double) capacity)).build();
			server.addVm(vm);
		}
		return server;
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withCurrentLoadOf(double initialLoad) {
		this.initialLoad = initialLoad;
		return this;
	}

}
