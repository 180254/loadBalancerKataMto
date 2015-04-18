package edu.iis.mto.serverloadbalancer.builders;

import edu.iis.mto.serverloadbalancer.Server;

public class ServerBuilder implements Builder<Server> {
	int capacity;

	public ServerBuilder() {
		withCapacity(1);
	}

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		return new Server(capacity);
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}
}
