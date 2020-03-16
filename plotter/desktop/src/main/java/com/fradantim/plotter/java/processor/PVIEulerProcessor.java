package com.fradantim.plotter.java.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.renderizable.Line;

import bsh.EvalError;

public class PVIEulerProcessor implements ProblemaValorInicialProcessor{

	@SuppressWarnings("unused")
	private Float t0, x0, T, h;
	
	private Integer N;
	
	private List<String> vars;
	
	private String function;
	

	@Override
	public void fillValues(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N) {
		this.vars=vars;
		this.function=function;
		this.t0=t0;
		this.x0=x0;
		this.T=T;
		
		if(h==null && N == null) {
			throw new IllegalArgumentException("h and N where both null, one of those needs to have a value.");
		}
		
		if((h!=null && N!=null)) {
			throw new IllegalArgumentException("h and N have a value, only one of those needs to have a value.");
		}
		
		if(N==null) {
			this.N=new Float(Math.ceil((T-t0)/h)).intValue();
			this.h=h;
		}else {
			this.h=new Float(Math.ceil((T-t0)/N));
			this.N=N;
		}
	}

	@Override
	public List<Renderizable> getImage(Integer pointsPerPoint, Color color) {
		List<Renderizable> result = new ArrayList<Renderizable>();
		try {
			List<Vector2> puntos= new ArrayList<>();
			
			for(int i=0; i<=N; i++) {
				Float t= t0+i*h;
				puntos.add(new Vector2(t, getValue(t)));
			}
			
			for(int i=0; i<puntos.size()-1; i++) {
				result.add(new Line(puntos.get(i), puntos.get(i+1), color));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Float getValue(Float t) throws FileNotFoundException, EvalError, IOException {
		if(t.equals(t0)) {
			return x0;
		}else {
			Float previousValue=getValue(t-h);
			if(previousValue==null)
				return null;
			
			Map<String, Float> elementByVar= new HashMap<String, Float>();
			elementByVar.put(vars.get(0), t-h);
			elementByVar.put(vars.get(1), previousValue);
			
			Float newValue=previousValue+h*FunctionProcessor.getImageAtElement(vars, function, elementByVar).floatValue();
			return newValue;
		}
	}
	
	
	
}
