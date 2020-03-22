package com.fradantim.plotter.core.processor;

import java.util.List;

import com.fradantim.plotter.core.Renderizable;

public abstract class PVIProcessor {
	
	protected List<String> vars;
	protected String function;
	@SuppressWarnings("unused")
	protected Float t0, x0, T, h;
	protected Integer N;
	
	protected String toString;
	
	@SuppressWarnings("deprecation")
	public PVIProcessor(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N) {
		if(h==null && N == null) {
			throw new IllegalArgumentException("h and N where both null, at least on of those needs to have a value.");
		}
		
		this.vars=vars;
		this.function=function;
		this.t0=t0;
		this.x0=x0;
		this.T=T;
		this.h=h;
		this.N=N;
		
		toString=getName()+": f("+String.join(",", vars)+")="+function+"; t0="+t0+"; x0="+x0+"; T="+T;
		
		if(N==null) {
			toString+="; h="+h;
			this.N=new Float(Math.abs(Math.ceil((T-t0)/h))).intValue();
		}else {
			toString+="; N="+N;
			this.h=new Float(Math.abs(Math.ceil((T-t0)/N)));
		}
	}
	
	protected abstract String getName();
	
	public abstract List<Renderizable> getImage();
	
	@Override
	public String toString() {
		return toString;
	}
}

