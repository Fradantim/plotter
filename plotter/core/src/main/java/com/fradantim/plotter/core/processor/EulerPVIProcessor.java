package com.fradantim.plotter.core.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.Line;
import com.fradantim.plotter.core.renderizable.Renderizable;

/**
 * Supposing <i>vars</i>[0]='x' and <i>vars</i>[1]='t' <br>
 * <br>
 * x(t)=?<br>
 * <br>
 * (dx/dt)=f(t,x)=<i>function</i> <br>
 * <br>
 * u(t)=~x(t) <br>
 * u<sub>0</sub>=<i>x0</i> <br>
 * u<sub>k</sub>=u<sub>k-1</sub> + h*f(t<sub>k-1</sub>,u<sub>k-1</sub>) <br>
 *
 */
public class EulerPVIProcessor extends PVIProcessor{

	/** empty constructor for serialization*/
	public EulerPVIProcessor() {
		super();
	}
	
	public EulerPVIProcessor(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N) {
		super(vars, function, t0, x0, T, h, N);
	}
	
	@Override
	public List<Renderizable> getImage() {
		List<Renderizable> result = new ArrayList<Renderizable>();
		
		List<Vector2> puntos= new ArrayList<>();
		
		memory.put(t0, x0);
			
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
			
			Map<String, Float> elementByVar= new HashMap<String, Float>();
			elementByVar.put(vars.get(0), t-h*(t0<T?1:-1));
			elementByVar.put(vars.get(1), previousValue.floatValue());
			
			Float newValue=previousValue+h*process(vars, function, elementByVar).floatValue();
			memory.put(t, newValue);
			return newValue;
		}
	}
	
	private Double process(List<String> vars, String function, Map<String, Float> elementByVar) {
		return FunctionProcessor.getImageAtElement(vars, function, elementByVar)*(t0<T?1:-1);
	}
	
	@Override
	public String getName() {
		return "E ";
	}
}
