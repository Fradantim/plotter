package com.fradantim.plotter.core.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fradantim.plotter.core.Renderizable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EulerPVIProcessor.class),
        @JsonSubTypes.Type(value = ImprovedEulerPVIProcessor.class),
        @JsonSubTypes.Type(value = RungeKuttaPVIProcessor.class)
})
public abstract class PVIProcessor {
	
	protected List<String> vars;
	protected String function;
	protected Float t0, x0, T, h;
	protected Integer N;
	
	protected String toString;
	
	@JsonIgnore
	protected Map<Float, Float> memory;
	
	public PVIProcessor() {
		memory= new HashMap<>();
	}
	
	@SuppressWarnings("deprecation")
	public PVIProcessor(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N) {
		this();
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
	
	@JsonIgnore
	public abstract String getName();
	
	@JsonIgnore
	public abstract List<Renderizable> getImage();
	
	@Override
	public String toString() {
		return toString;
	}

	public List<String> getVars() {
		return vars;
	}

	public String getFunction() {
		return function;
	}

	public Float getT0() {
		return t0;
	}

	public Float getX0() {
		return x0;
	}

	public Float getT() {
		return T;
	}

	public Float getH() {
		return h;
	}

	public Integer getN() {
		return N;
	}
}

