package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(final Server[] servers, final Vm[] vms) {

		for (Vm vm : vms) {
			List<Server> serversWithEnoughCapacity = new ArrayList<Server>(servers.length);
			for (Server server : servers) {
				if (server.getCurrentLoadPercentage() + vm.getSize()/(double)server.getVmsCapacity()*100 <= Server.MAXIMUM_LOAD) {
					serversWithEnoughCapacity.add(server);
				}
			}
			Server lessLoaded = extractLessLoaderServer(serversWithEnoughCapacity
					.toArray(new Server[serversWithEnoughCapacity.size()]));
			if (lessLoaded != null)
				lessLoaded.addVm(vm);
		}
	}

	private Server extractLessLoaderServer(final Server[] servers) {
		Server lessLoaded = null;

		for (Server server : servers) {
			if (lessLoaded == null ||
					lessLoaded.getCurrentLoadPercentage() > server.getCurrentLoadPercentage()) {
				lessLoaded = server;
			}
		}

		return lessLoaded;
	}

}
