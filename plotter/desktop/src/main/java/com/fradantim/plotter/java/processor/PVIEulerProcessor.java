package com.fradantim.plotter.java.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.renderizable.Line;

public class PVIEulerProcessor implements ProblemaValorInicialProcessor{

	private List<String> vars;
	private String function;
	@SuppressWarnings("unused")
	private Float t0, x0, T, h;
	private Integer N;
	
	private Map<Float, Float> memory;
	
	@Override
	public List<Renderizable<?>> getImage(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N) {
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
		
		if(N==null) {
			this.N=new Float(Math.abs(Math.ceil((T-t0)/h))).intValue();
		}else {
			this.h=new Float(Math.abs(Math.ceil((T-t0)/N)));
		}
		
		memory= new HashMap<>();
		memory.put(t0, x0);
		List<Renderizable<?>> result = new ArrayList<Renderizable<?>>();

		List<Vector2> puntos= new ArrayList<>();
			
		for(int i=0; i<=this.N; i++) {
			Float t= t0+i*this.h*((t0<T)?1:-1);
			puntos.add(new Vector2(t, getValue(t)));
		}
		
		for(int i=0; i<puntos.size()-1; i++) {
			result.add(new Line(puntos.get(i), puntos.get(i+1)));
		}
		
		return result;
	}
	
	public Float getValue(Float t) {
		try {
			if(t.equals(t0)) {
				return x0;
			}else {
				Float previousT=t-(h*(t0<T?1:-1));
				
				Float previousValue=memory.get(previousT);
				if(previousValue==null) {
					previousValue=getValue(t-(h*(t0<T?1:-1)));
					memory.put(previousT, previousValue);
				}
				
				Map<String, Float> elementByVar= new HashMap<String, Float>();
				elementByVar.put(vars.get(0), t-h*(t0<T?1:-1));
				elementByVar.put(vars.get(1), previousValue.floatValue());
				
				Float newValue=previousValue+h*FunctionProcessor.getImageAtElement(vars, function, elementByVar).floatValue()*(t0<T?1:-1);
				return newValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
