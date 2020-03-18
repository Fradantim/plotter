package com.fradantim.plotter.core.renderizable;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class Triangle implements Renderizable{

	private Vector2 pointA;
	private Vector2 pointB;
	private Vector2 pointC;
	private Color color;
	
	public Triangle(Vector2 pointA, Vector2 pointB, Vector2 pointC, Color color) {
		this.pointA = new Vector2(pointA);
		this.pointB = new Vector2(pointB);
		this.pointC = new Vector2(pointC);
		this.color = color;
	}
	
	public Triangle(Vector2 pointA, Vector2 pointB, Vector2 pointC) {
		this(pointA,pointB,pointC,Colorizer.DEFAULT_COLOR);
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
       	shapeRenderer.triangle(pointA.x, pointA.y, pointB.x, pointB.y, pointC.x, pointC.y);
        shapeRenderer.end();
	}

	public Vector2 getPointA() {
		return pointA;
	}

	public Vector2 getPointB() {
		return pointB;
	}
	
	public Vector2 getPointC() {
		return pointC;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public void setColor(Color color) {
		this.color=color;		
	}
	
	@Override
	public void move(Vector2 vector) {
		for(Vector2 point: Arrays.asList(pointA,pointB,pointC))
			point.add(vector);
	}

	@Override
	public void scale(Float scale) {
		for(Vector2 point: Arrays.asList(pointA,pointB,pointC))
			point.scl(scale);
	}	
}
