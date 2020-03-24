package com.fradantim.plotter.core.renderizable.generator;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.renderizable.Line;
import com.fradantim.plotter.core.util.Direction;

public class AxisGenerator {
	
	public final static Color AXIS_COLOR=Color.WHITE;
	
	private final static Integer RULER_SIZE=20;

	public static List<Renderizable> getAxis(Vector2 ScreenSize, int pixelsPerPoint){
		return getAxis((int) ScreenSize.x, (int) ScreenSize.y, pixelsPerPoint, AXIS_COLOR);
	}
	
	public static List<Renderizable> getAxis(Vector2 ScreenSize, int pixelsPerPoint, Color color){
		return getAxis((int) ScreenSize.x, (int) ScreenSize.y, pixelsPerPoint, color);
	}
	
	public static List<Renderizable> getAxis(int xScreenSize, int yScreenSize, int pixelsPerPoint, Color color){
		List<Renderizable> result = new ArrayList<Renderizable>();
		
		//horizontal line 
		result.add(new Line(new Vector2(0,yScreenSize/2), new Vector2(xScreenSize,yScreenSize/2), color));
		
		//vertical line 
		result.add(new Line(new Vector2(xScreenSize/2,0), new Vector2(xScreenSize/2,yScreenSize), color));
		
		//horizontal line steps
		for(int i=(xScreenSize/2)%pixelsPerPoint; i<xScreenSize; i+=pixelsPerPoint) {
			result.add(new Line(new Vector2(i,yScreenSize/2), new Vector2(i,(yScreenSize/2)-RULER_SIZE), color));
		}
		
		//vertical line steps
		for(int i=(yScreenSize/2)%pixelsPerPoint; i<yScreenSize; i+=pixelsPerPoint) {
			result.add(new Line(new Vector2(xScreenSize/2,i), new Vector2((xScreenSize/2)-RULER_SIZE,i), color));
		}
		
		//upper arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(xScreenSize/2,yScreenSize),Direction.UP,xScreenSize/(pixelsPerPoint*2),color));
		
		//lower arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(xScreenSize/2,0),Direction.DOWN,xScreenSize/(pixelsPerPoint*2),color));
		
		//left arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(0,yScreenSize/2),Direction.LEFT,xScreenSize/(pixelsPerPoint*2),color));
				
		//right arrow
		result.add(TriangleGenerator.simpleTriangle(new Vector2(xScreenSize,yScreenSize/2),Direction.RIGHT,xScreenSize/(pixelsPerPoint*2),color));
		
		return result;
	}	
}
 