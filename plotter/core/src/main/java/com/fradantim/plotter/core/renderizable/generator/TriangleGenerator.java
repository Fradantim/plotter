package com.fradantim.plotter.core.renderizable.generator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.Triangle;
import com.fradantim.plotter.core.util.Direction;

public class TriangleGenerator {
	
	public static Triangle simpleTriangle(Vector2 head, Direction direction, int triangleSide) {
		return simpleTriangle(head, direction, triangleSide, Colorizer.DEFAULT_COLOR);
	}
	
	public static Triangle simpleTriangle(Vector2 head, Direction direction, int triangleSide, Color color) {
		switch (direction){
			case UP: return new Triangle(head,
					new Vector2(head.x-triangleSide/2,head.y-triangleSide),
					new Vector2(head.x+triangleSide/2,head.y-triangleSide),
					color);
			
			case DOWN: return new Triangle(head,
					new Vector2(head.x-triangleSide/2,head.y+triangleSide),
					new Vector2(head.x+triangleSide/2,head.y+triangleSide),
					color);
			case LEFT: return new Triangle(head,
					new Vector2(head.x+triangleSide,head.y+triangleSide/2),
					new Vector2(head.x+triangleSide,head.y-triangleSide/2),
					color);
			case RIGHT: return new Triangle(head,
					new Vector2(head.x-triangleSide,head.y+triangleSide/2),
					new Vector2(head.x-triangleSide,head.y-triangleSide/2),
					color);
			default: return null;
		}
	}
	
	public static Triangle simpleTriangle(Vector2 pointA, Vector2 pointB, Vector2 pointC) {
		return simpleTriangle(pointA, pointB, pointC, Colorizer.DEFAULT_COLOR);
	}
	
	public static Triangle simpleTriangle(Vector2 pointA, Vector2 pointB, Vector2 pointC, Color color) {
		return new Triangle(pointA, pointB, pointC, color);
	}
}
