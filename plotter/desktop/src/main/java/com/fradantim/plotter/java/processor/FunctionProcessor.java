package com.fradantim.plotter.java.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.renderizable.Line;
import com.fradantim.plotter.core.renderizable.Point;

import bsh.EvalError;
import bsh.Interpreter;

public class FunctionProcessor {
	
	public enum RenderType{ POINT, LINE, BOTH; }
	
	private Float pointsDeltaConnection;
	private Float maxHeight;
	
	public FunctionProcessor(Integer pointsPerPoints, Float maxHeight) throws FileNotFoundException, IOException, EvalError {
    	this.pointsDeltaConnection=1.01F/pointsPerPoints;
    	this.maxHeight=maxHeight;
	}
	
	private Interpreter getInterpreter() throws FileNotFoundException, IOException, EvalError {
		Interpreter interpreter = new Interpreter();
		interpreter.source("advancedFunctions.bsh");
		return interpreter;
	}
	
	/** Lines so that distant point which should be connected are. */
	public List<? extends Renderizable> pointsToLines(List<Point> points){
		List<Renderizable> lines = new ArrayList<>();
		
		for(int i=0; i<points.size()-1;i++) {
			Point pointA = points.get(i);
			Point pointB = points.get(i+1);
		
			//if both points are truly "continuous"
			if(pointsAreContinuous(pointA, pointB)) {
				//if both images are truly appart
				if(Math.abs(Math.abs(pointA.getPoint().y)-Math.abs(pointB.getPoint().y))<0.9) {
					Line l=new Line(new Vector2(pointA.getPoint()), new Vector2(pointB.getPoint()), pointA.getColor());
					//System.out.println(l);
					//System.out.println(Math.abs(pointA.getPoint().y)-Math.abs(pointB.getPoint().y));
					lines.add(l);
				}
			}
		}
		
		return lines;
	}
	
	public Double getImageAtElement(String var, String function, Float element) throws EvalError, FileNotFoundException, IOException{
		Interpreter interpreter= getInterpreter();
		return getImageAtElement(interpreter, var, function, element);
	}
	
	private Double getImageAtElement(Interpreter interpreter, String var, String function, Float element) throws EvalError{
		interpreter.set(var, element);
		interpreter.eval("resultado = "+function);
		Object resObj = interpreter.get("resultado");
		if(resObj !=null)
			return new Double(resObj.toString());
		return null;
	}
	
	
	public List<? extends Renderizable> getImage(String var, String function, List<Float> domain, Integer pointsPerPoint, Integer times, Color color) throws EvalError, FileNotFoundException, IOException{
		return getImage(var, function, domain, pointsPerPoint, times, color, RenderType.LINE);
	}
	
	public List<? extends Renderizable> getImage(String var, String function, List<Float> domain, Integer pointsPerPoint, Integer times, Color color, RenderType renderType) throws EvalError, FileNotFoundException, IOException{
		Interpreter interpreter= getInterpreter();
		return getImage(interpreter, var, function, domain, pointsPerPoint, times, color, renderType);
	}
	
	private List<? extends Renderizable> getImage(Interpreter interpreter, String var, String function, List<Float> domain, Integer pointsPerPoint, Integer times, Color color, RenderType renderType) throws EvalError {
		List<Point> derivatedPoints = new ArrayList<>();
		
		for(int i=0; i<domain.size();i++) {
			Float element=domain.get(i);
			Double value=getImageAtElement(interpreter, var, function, element, pointsPerPoint, times);
			
			if(value !=null) {
				derivatedPoints.add(new Point(new Vector2(element,value.floatValue()), color));
			}
		}
		
		switch(renderType) {
			case POINT: {
				return derivatedPoints;
			}
			case LINE: {
				return pointsToLines(derivatedPoints);
			}
			case BOTH: {
				List<Renderizable> result = new ArrayList<>(); 
				result.addAll(derivatedPoints);
				result.addAll(pointsToLines(derivatedPoints));
				return result;
			}
		}
		
		return derivatedPoints;
	}
	
	private Double getImageAtElement(Interpreter interpreter, String var, String function, Float element, Integer pointsPerPoint, Integer derivationTimes) throws EvalError {
		if(derivationTimes==0)
			return getImageAtElement(interpreter, var, function, element);
		else {
			return  (
						getImageAtElement(interpreter, var, function, element+1F/pointsPerPoint, pointsPerPoint, derivationTimes-1)
						-
						getImageAtElement(interpreter, var, function, element, pointsPerPoint, derivationTimes-1)
					)/ (1D/pointsPerPoint);
		}
	}
	

	private boolean pointsAreContinuous(Point pointA, Point pointB) {
		return Math.abs(pointA.getPoint().x-pointB.getPoint().x)<pointsDeltaConnection;
	}
}