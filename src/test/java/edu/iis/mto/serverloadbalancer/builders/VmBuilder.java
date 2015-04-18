package edu.iis.mto.serverloadbalancer.builders;

import edu.iis.mto.serverloadbalancer.Vm;

public class VmBuilder implements Builder<Vm> {

	int size;

	public VmBuilder() {
		ofSize(1);
	}

	public VmBuilder ofSize(int i) {
		this.size = i;
		return this;
	}

	public Vm build() {
		return new Vm(size);
	}

	public static VmBuilder vm() {
		return new VmBuilder();

	}

}
