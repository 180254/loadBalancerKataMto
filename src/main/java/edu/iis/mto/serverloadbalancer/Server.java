package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

public class Server {
public	List<Vm> vms = new ArrayList<Vm>();
	
	private double currentPercentageLoad = 0;
	
	public double getCurrentPercentageLoad() {
		if(vms.size() >0) {
			currentPercentageLoad = 100;
		}
		else currentPercentageLoad = 0;
		
		return currentPercentageLoad;
	}

	public boolean contains(Vm vm) {
		return true;
	}

}
