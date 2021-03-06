package com.fradantim.plotter.core.renderizable;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class Triangle implements Surface{

	private Vector2 pointA;
	private Vector2 pointB;
	private Vector2 pointC;
	private Color color;
	private Boolean filled=true;
	
	public Triangle(Vector2 pointA, Vector2 pointB, Vector2 pointC, Color color) {
		this.pointA = new Vector2(pointA);
		this.pointB = new Vector2(pointB);
		this.pointC = new Vector2(pointC);
		this.color = color;
	}
	
	public Triangle(Vector2 pointA, Vector2 pointB, Vector2 pointC) {
		this(pointA, pointB, pointC, Colorizer.DEFAULT_COLOR);
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
		if(filled)
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		else
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
       	shapeRenderer.triangle(pointA.x, pointA.y, pointB.x, pointB.y, pointC.x, pointC.y);
        shapeRenderer.end();
	}
	
	@Override
	public void render(Pixmap pixmap, Color color) {
		pixmap.setColor(color);
		pixmap.fillTriangle((int) pointA.x, (int) pointA.y, (int) pointB.x, (int) pointB.y, (int) pointC.x, (int) pointC.y);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" A:"+pointA+", B:"+pointB+", C:"+pointC;
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

	@Override
	public Triangle getCopy() {
		return new Triangle(pointA.cpy(), pointB.cpy(), pointC.cpy());
	}

	@Override
	public boolean collidesWith(Rectangle rect) {
		return rect.contains(pointA)||rect.contains(pointB)||rect.contains(pointC);
	}

	@Override
	public Double getArea() {
		//Heron's Formula (thank you so much)
		Double sideAB = getSideLength(pointA, pointB);
		Double sideBC = getSideLength(pointB, pointC);
		Double sideCA = getSideLength(pointC, pointA);
		
		//the semi perimeter
		Double semiPer= (sideAB+sideBC+sideCA)/2;
		
		return Math.sqrt(semiPer*(semiPer-sideAB)*(semiPer-sideBC)*(semiPer-sideCA));
	}
	
	private Double getSideLength(Vector2 pointA, Vector2 pointB) {
		return Math.sqrt(Math.pow(pointB.x-pointA.x, 2)+Math.pow(pointB.y-pointA.y, 2));
	}

	@Override
	public void setFilled(Boolean filled) {
		this.filled=filled;		
	}

	@Override
	public Boolean getFilled() {
		return filled;
	}
}
