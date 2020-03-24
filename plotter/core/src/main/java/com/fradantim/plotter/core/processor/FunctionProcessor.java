package com.fradantim.plotter.core.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.Line;
import com.fradantim.plotter.core.renderizable.Point;
import com.fradantim.plotter.core.renderizable.Renderizable;

import bsh.EvalError;
import bsh.Interpreter;


public class FunctionProcessor {
	
	public enum RenderType{ POINT, LINE, BOTH; }
	
	public static Interpreter getInterpreter() {
		try {
			InnerInterpreter in = new InnerInterpreter();
			in.source(Gdx.files.internal("advancedFunctions.bsh"));
			return in;
		} catch (IOException |EvalError e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/** Lines so that distant point which should be connected are. */
	public static List<? extends Renderizable> pointsToLines(List<Point> points, Integer pointsPerPoints){
		List<Renderizable> lines = new ArrayList<>();
		
		for(int i=0; i<points.size()-1;i++) {
			Point pointA = points.get(i);
			Point pointB = points.get(i+1);
		
			//if both points are truly "continuous"
			if(pointsAreContinuous(pointA, pointB,pointsPerPoints)) {
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
	
	public static Double getImageAtElement(List<String> vars, String function, Map<String,Float> elementByVar) {
		Interpreter interpreter= getInterpreter();
		return getImageAtElement(interpreter, vars, function, elementByVar);
	}
	
	@SuppressWarnings("deprecation")
	private static Double getImageAtElement(Interpreter interpreter, List<String> vars, String function, Map<String,Float> elementByVar) {
		if(vars.size()!=elementByVar.size()){
			throw new IllegalAccessError("Variable names and it's values must be same size and same order.");
		}
		
		try {
			for(String var : vars) {
				Float element = elementByVar.get(var);
				interpreter.set(var, element);
			}
			
			interpreter.eval("resultado = "+function);
			
			Object resObj = interpreter.get("resultado");
			if(resObj !=null) {
				Double d = new Double(resObj.toString());
				if (!d.isNaN() && !d.isInfinite()) {
					return d;
				}
			}
				
			return null;
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<? extends Renderizable> getImage(List<String> vars, String function, Map<String,List<Float>> domainByVar, Integer pointsPerPoint) {
		return getImage(vars, function, domainByVar, pointsPerPoint, 0);
	}
	
	public static List<? extends Renderizable> getImage(List<String> vars, String function, Map<String,List<Float>> domainByVar, Integer pointsPerPoint, Integer times) {
		return getImage(vars, function, domainByVar, pointsPerPoint, times, RenderType.LINE);
	}
	
	public static List<? extends Renderizable> getImage(List<String> vars, String function, Map<String,List<Float>> domainByVar, Integer pointsPerPoint, Integer times, RenderType renderType) {
		Interpreter interpreter= getInterpreter();
		return getImage(interpreter, vars, function, domainByVar, pointsPerPoint, times, renderType);
	}
	
	private static List<? extends Renderizable> getImage(Interpreter interpreter, List<String> vars, String function, Map<String,List<Float>> domainByVar, Integer pointsPerPoint, Integer times, RenderType renderType) {
		List<Point> derivatedPoints = new ArrayList<>();
		
		if(vars.size()!=domainByVar.size()){
			throw new IllegalAccessError("Variable names and it's values must be same size and same order.");
		}
		
		Map<String,Float> elementByVar = new HashMap<>();
		
		for(String var: vars) {
			elementByVar.put(var, 0F);
		}
		
		String var=vars.get(0);
		for(Float element: domainByVar.get(var)) {
			elementByVar.put(var, element);
			Double value=getImageAtElement(interpreter, vars, function, elementByVar, pointsPerPoint, times);
			
			if(value !=null) {
				derivatedPoints.add(new Point(new Vector2(element,value.floatValue())));
			}
		}
		
		switch(renderType) {
			case POINT: {
				return derivatedPoints;
			}
			case LINE: {
				return pointsToLines(derivatedPoints, pointsPerPoint);
			}
			case BOTH: {
				List<Renderizable> result = new ArrayList<>(); 
				result.addAll(derivatedPoints);
				result.addAll(pointsToLines(derivatedPoints, pointsPerPoint));
				return result;
			}
		}
		
		return derivatedPoints;
	}
	
	//TODO try multithreading?
	private static Double getImageAtElement(Interpreter interpreter, List<String> vars, String function, Map<String,Float> elementByVar, Integer pointsPerPoint, Integer derivationTimes) {
		if(derivationTimes==0)
			return getImageAtElement(interpreter, vars, function, elementByVar);
		else {
			Map<String,Float> nextElementsByVar= new HashMap<>();
			elementByVar.keySet().forEach(var -> nextElementsByVar.put(var, elementByVar.get(var)+1F/pointsPerPoint) );
			
			Double thisValue=getImageAtElement(interpreter, vars, function, elementByVar, pointsPerPoint, derivationTimes-1);
			if(thisValue == null) {
				return null;
			}
			Double nextValue=getImageAtElement(interpreter, vars, function, nextElementsByVar, pointsPerPoint, derivationTimes-1);
			if(nextValue == null) {
				return null;
			}
			
			return  (nextValue - thisValue)/(1D/pointsPerPoint);
		}
	}
	

	private static boolean pointsAreContinuous(Point pointA, Point pointB, Integer pointsPerPoints) {
		return Math.abs(pointA.getPoint().x-pointB.getPoint().x)<1.01F/pointsPerPoints;
	}
}

final class InnerInterpreter extends Interpreter{
	
	private static final long serialVersionUID = 1L;

	public Object source(FileHandle file) throws IOException, EvalError{
		if ( Interpreter.DEBUG ) debug("Sourcing file: "+file);
			
		Reader sourceIn = new BufferedReader(new InputStreamReader(file.read()));
		try {
			return eval( sourceIn, getNameSpace(), "nono" );
		} finally {
			sourceIn.close();
		}
	}
}