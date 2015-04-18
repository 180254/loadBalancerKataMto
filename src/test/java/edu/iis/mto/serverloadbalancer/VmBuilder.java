package edu.iis.mto.serverloadbalancer;

public class VmBuilder {
	int size;

	public VmBuilder ofSize(int i) {
		this.size = i;
		return this;
	}

	public Vm build() {
		return new Vm(size);
	}

}
