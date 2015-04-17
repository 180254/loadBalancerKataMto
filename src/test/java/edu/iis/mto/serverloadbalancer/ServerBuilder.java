package edu.iis.mto.serverloadbalancer;

public class ServerBuilder {
	int capacity = 1;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		return new Server();
	}

}
