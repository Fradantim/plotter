package com.fradantim.plotter.core.processor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fradantim.plotter.core.renderizable.Renderizable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EulerPVIProcessor.class),
        @JsonSubTypes.Type(value = ImprovedEulerPVIProcessor.class),
        @JsonSubTypes.Type(value = RungeKuttaPVIProcessor.class)
})
public abstract class INProcessor {
	
	protected List<String> vars;
	protected String function;
	protected Float a,b,h;
	protected Integer N;
	
	protected String toString;
	
	public INProcessor() {	}
	
	@SuppressWarnings("deprecation")
	public INProcessor(List<String> vars, String function, Float a, Float b, Float h, Integer N) {
		this();
		if(h==null && N == null) {
			throw new IllegalArgumentException("h and N where both null, at least on of those needs to have a value.");
		}
		
		this.vars=vars;
		this.function=function;
		this.a=a;
		this.b=b;
		this.h=h;
		this.N=N;
		
		toString=getName()+": f("+String.join(",", vars)+")="+function+"; a="+a+"; b="+b;
		
		if(N==null) {
			toString+="; h="+h;
			this.N=new Float(Math.abs(Math.ceil((b-a)/h))).intValue();
		}else {
			toString+="; N="+N;
			this.h=new Float(Math.abs(Math.ceil((b-a)/N)));
		}
	}
	
	@JsonIgnore
	public abstract String getName();
	
	@JsonIgnore
	public abstract List<? extends Renderizable> getImage();
	
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

	public Float getH() {
		return h;
	}

	public Integer getN() {
		return N;
	}
	
	public Float getA() {
		return a;
	}

	public Float getB() {
		return b;
	}

	public abstract Double getArea();
}

