package com.fradantim.plotter.core.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.renderizable.Line;

/**
 * Supposing <i>vars</i>[0]='x' and <i>vars</i>[1]='t' <br>
 * <br>
 * x(t)=?<br>
 * <br>
 * (dx/dt)=f(t,x)=<i>function</i> <br>
 * <br>
 * u(t)=~x(t) <br>
 * u<sub>0</sub>=<i>x0</i> <br>
 * u<sub>k</sub>=u<sub>k-1</sub> + (h/6)*(a<sub>1</sub> + 2*a<sub>2</sub> + 2*a<sub>3</sub> + a<sub>4</sub>) <br>
 * <br>
 * where...<br>
 * a<sub>1</sub>=f(t<sub>k-1</sub>, u<sub>k-1</sub>) <br>
 * a<sub>2</sub>=f(t<sub>k-1</sub> + h/2, u<sub>k-1</sub>*a<sub>1</sub>*h/2) <br>
 * a<sub>3</sub>=f(t<sub>k-1</sub> + h/2, u<sub>k-1</sub>*a<sub>2</sub>*h/2) <br>
 * a<sub>4</sub>=f(t<sub>k-1</sub> + h, u<sub>k-1</sub>*a<sub>3</sub>*h) <br>
 */
public class RungeKuttaPVIProcessor implements PVIProcessor{

	private List<String> vars;
	private String function;
	@SuppressWarnings("unused")
	private Float t0, x0, T, h;
	private Integer N;
	
	private Map<Float, Float> memory;
	
	private String toString="RK: ";
	
	@Override
	public List<Renderizable> getImage(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N) {
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
		
		toString+="f("+String.join(",", vars)+")="+function+"; t0="+t0+"; x0="+x0+"; T="+T;
		
		if(N==null) {
			toString+="; h="+h;
			this.N=new Float(Math.abs(Math.ceil((T-t0)/h))).intValue();
		}else {
			toString+="; N="+N;
			this.h=new Float(Math.abs(Math.ceil((T-t0)/N)));
		}
		
		memory= new HashMap<>();
		memory.put(t0, x0);
		List<Renderizable> result = new ArrayList<Renderizable>();

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
		if(t.equals(t0)) {
			return x0;
		}else {
			Float previousT=t-(h*(t0<T?1:-1));
			
			Float previousValue=memory.get(previousT);
			if(previousValue==null) {
				previousValue=getValue(previousT);
				memory.put(previousT, previousValue);
			}
			
			Float a1,a2,a3,a4;
			Map<String, Float> elementByVar= new HashMap<String, Float>();
			elementByVar.put(vars.get(0), t-h*(t0<T?1:-1));
			elementByVar.put(vars.get(1), previousValue.floatValue());
			
			a1=process(vars, function, elementByVar).floatValue();
			
			elementByVar.put(vars.get(0), t-h*(t0<T?1:-1) + (h/2)*(t0<T?1:-1));
			elementByVar.put(vars.get(1), previousValue + (1/2)*a1*h*(t0<T?1:-1));
			
			a2=process(vars, function, elementByVar).floatValue();
			
			elementByVar.put(vars.get(0), t-h*(t0<T?1:-1) + (h/2)*(t0<T?1:-1));
			elementByVar.put(vars.get(1), previousValue + (1/2)*a2*h*(t0<T?1:-1));
			
			a3=process(vars, function, elementByVar).floatValue();
			
			elementByVar.put(vars.get(0), t-h*(t0<T?1:-1) + (h/2)*(t0<T?1:-1));
			elementByVar.put(vars.get(1), previousValue + a3*h*(t0<T?1:-1));
			
			a4=process(vars, function, elementByVar).floatValue();
			
			Float newValue=previousValue + (h/6)*(a1+2*a2+2*a3+a4)*(t0<T?1:-1);
			memory.put(t, newValue);
			return newValue;
		}
	}
	
	private Double process(List<String> vars, String function, Map<String, Float> elementByVar) {
		return FunctionProcessor.getImageAtElement(vars, function, elementByVar)*(t0<T?1:-1);
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
