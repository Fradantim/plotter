package com.fradantim.plotter.core.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.Circle;
import com.fradantim.plotter.core.renderizable.Cross;
import com.fradantim.plotter.core.renderizable.Quadrilateral;
import com.fradantim.plotter.core.renderizable.Renderizable;
import com.fradantim.plotter.core.renderizable.Surface;
import com.fradantim.plotter.core.util.FileSystemUtil.AppProperty;


public class RandomINProcesor extends INProcessor{
	
	private Float minValue,maxValue;
	
	/** empty constructor for serialization*/
	public RandomINProcesor() {
		super();
	}
	
	public RandomINProcesor(List<String> vars, String function, Float a, Float b, Float h, Integer N) {
		super(vars, function, a, b, h, N);
	}
	
	@Override
	public String getName() {
		return "R ";
	}

	private static Integer pixelsPerPoint = (Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue();
	private static Float delta= 125F/(pixelsPerPoint*8);
	
	@Override
	public List<? extends Renderizable> getImage() {
		findMinAndMaxValues();
		
		List<Renderizable> renderizables = new ArrayList<>();
		Quadrilateral box= new Quadrilateral(new Vector2(a, minValue), new Vector2(a, maxValue), new Vector2(b, maxValue), new Vector2(b, minValue));
		box.setFilled(false);
		
		renderizables.add(box);
		
		Random r = new Random();
		for(int i=0; i<this.N; i++) {
			Float randomX = a + r.nextFloat() * (b - a);
			Float randomY = minValue + r.nextFloat() * (maxValue - minValue);
			
			Float trueY= getValue(randomX);
			
			Surface s=null;
			if((0 <= randomY && randomY <= trueY) || (0 >= randomY && randomY >= trueY) ) {
				s= new Circle(new Vector2(randomX,randomY),delta);
				area+=1*(trueY>0?1:-1);
			} else {
				s= new Cross(new Vector2(randomX,randomY),delta);
			}
			
			s.setFilled(false);
			renderizables.add(s);
		}
		
		area = area/N;
		
		toString+="; area="+area;
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
	
	private void findMinAndMaxValues() {
		minValue=maxValue=getValue(a);

		for(float i=a; i<=b; i++) {
			for(float j=0; j<pixelsPerPoint; j++) {
				Float var= i+j/pixelsPerPoint;
				
				Float value= getValue(var);
				
				if(value!=null) {
					if(value < minValue)
						minValue=value;
					if(value > maxValue)
						maxValue=value;
				}
			}
		}
	}
}
