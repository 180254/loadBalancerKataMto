package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(final Server[] servers, final Vm[] vms) {
		for (Vm vm : vms) {
			addToCapableLessLoadedServer(servers, vm);
		}
	}

	private List<Server> findServersWithEnoughCapacity(final Server[] servers, Vm vm) {
		List<Server> serversWithEnoughCapacity = new ArrayList<Server>(servers.length);

		for (Server server : servers) {
			if (server.catFit(vm)) {
				serversWithEnoughCapacity.add(server);
			}
		}

		return serversWithEnoughCapacity;
	}

	private Server extractLessLoaderServer(final List<Server> servers) {
		Server lessLoaded = null;

		for (Server server : servers) {
			if (lessLoaded == null ||
					lessLoaded.getCurrentLoadPercentage() > server.getCurrentLoadPercentage()) {
				lessLoaded = server;
			}
		}

		return lessLoaded;
	}

	private void addToCapableLessLoadedServer(final Server[] servers, Vm vm) {
		List<Server> capableServers = findServersWithEnoughCapacity(servers, vm);
		Server capableLessLoadedServer = extractLessLoaderServer(capableServers);

		if (capableLessLoadedServer != null) {
			capableLessLoadedServer.addVm(vm);
		}
	}

}
