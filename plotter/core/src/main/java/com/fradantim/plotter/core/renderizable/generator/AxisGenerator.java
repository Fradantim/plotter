package com.fradantim.plotter.core.renderizable.generator;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.enumerators.Direction;
import com.fradantim.plotter.core.renderizable.Line;

public class AxisGenerator {
	
	private final static Integer RULER_SIZE=5;

	public static List<Renderizable> getAxis(Vector2 ScreenSize, int pixelsPerPoint){
		return getAxis((int) ScreenSize.x, (int) ScreenSize.y, pixelsPerPoint);
	}
	
	public static List<Renderizable> getAxis(int xScreenSize, int yScreenSize, int pixelsPerPoint){
		List<Renderizable> result = new ArrayList<Renderizable>();
		
		//horizontal line 
		result.add(new Line(new Vector2(0,yScreenSize/2), new Vector2(xScreenSize,yScreenSize/2), Color.WHITE));
		
		//vertical line 
		result.add(new Line(new Vector2(xScreenSize/2,0), new Vector2(xScreenSize/2,yScreenSize), Color.WHITE));
		
		//horizontal line steps
		for(int i=(xScreenSize/2)%pixelsPerPoint; i<xScreenSize; i+=pixelsPerPoint) {
			result.add(new Line(new Vector2(i,yScreenSize/2), new Vector2(i,(yScreenSize/2)-RULER_SIZE), Color.WHITE));
		}
		
		//vertical line steps
		for(int i=(yScreenSize/2)%pixelsPerPoint; i<yScreenSize; i+=pixelsPerPoint) {
			result.add(new Line(new Vector2(xScreenSize/2,i), new Vector2((xScreenSize/2)-RULER_SIZE,i), Color.WHITE));
		}
		
		//upper arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(xScreenSize/2,yScreenSize),Direction.UP,xScreenSize/(pixelsPerPoint*2),Color.WHITE));
		
		//lower arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(xScreenSize/2,0),Direction.DOWN,xScreenSize/(pixelsPerPoint*2),Color.WHITE));
		
		//left arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(0,yScreenSize/2),Direction.LEFT,xScreenSize/(pixelsPerPoint*2),Color.WHITE));
				
		//right arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(xScreenSize,yScreenSize/2),Direction.RIGHT,xScreenSize/(pixelsPerPoint*2),Color.WHITE));
		
		return result;
	}
	
	
	
}
 