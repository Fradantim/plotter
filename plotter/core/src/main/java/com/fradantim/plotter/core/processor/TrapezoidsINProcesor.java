package com.fradantim.plotter.core.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.Line;
import com.fradantim.plotter.core.renderizable.Quadrilateral;
import com.fradantim.plotter.core.renderizable.Renderizable;
import com.fradantim.plotter.core.renderizable.Surface;
import com.fradantim.plotter.core.util.FileSystemUtil.AppProperty;

public class TrapezoidsINProcesor extends INProcessor{
	
	/** empty constructor for serialization*/
	public TrapezoidsINProcesor() {
		super();
	}
	
	public TrapezoidsINProcesor(List<String> vars, String function, Float a, Float b, Float h, Integer N) {
		super(vars, function, a, b, h, N);
	}
	
	@Override
	public String getName() {
		return "T ";
	}

	private static Integer pixelsPerPoint = (Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue();
	private static Float delta= 125F/(pixelsPerPoint*2);
	
	@Override
	public List<? extends Renderizable> getImage() {
		List<Renderizable> renderizables = new ArrayList<>();
		
		List<Vector2> puntos= new ArrayList<>();
		for(int i=0; i<=this.N; i++) {
			Float x= a+i*h*((a<b)?1:-1);
			Float value=getValue(x);
			if(value!=null) {
				Vector2 punto =new Vector2(x, getValue(x)); 
				puntos.add(punto);
				Line line = new Line(new Vector2(punto.x, punto.y+delta*(punto.y>0?1:-1)), new Vector2(x, -delta*(punto.y>0?1:-1)));
				renderizables.add(line);
			}
		}
		
		for(int i=0; i<puntos.size()-1; i++) {
			Vector2 pointA = puntos.get(i);
			Vector2 pointB = puntos.get(i+1);
			
			Surface s=null;
			if(pointA.y*pointB.y>0) {
				//didn't cross the horizontal axis
				s = new Quadrilateral(pointA, pointB, new Vector2(pointB.x,0), new Vector2(pointA.x,0));
				if(pointA.y>0 && pointB.y >0) {
					area+=s.getArea();
				} else {
					area-=s.getArea();
				}
			} else {
				//did cross the horizontal axis
				s = new Quadrilateral(pointA, new Vector2(pointA.x,0), pointB, new Vector2(pointB.x,0));
				area+=h*(pointA.y+pointB.y)/2;
			}
								
			renderizables.add(s);
		}
		
		toString+="; area="+String.format("%."+2+"f",area);
		return renderizables;
	}

	private Float getValue(Float x) {
		Map<String, Float> elementByVar = new HashMap<>();
		elementByVar.put(vars.get(0), x);
		for(int i=1; i<vars.size();i++) {
			elementByVar.put(vars.get(i), 0F);
		}
		
		Double res=FunctionProcessor.getImageAtElement(vars, function, elementByVar);
		
		return res!=null?res.floatValue():null;
	}

	@Override
	public Double getArea() {
		return area;
	}
	
	
}
