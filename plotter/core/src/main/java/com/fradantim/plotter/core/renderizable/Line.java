package com.fradantim.plotter.core.renderizable;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class Line implements Renderizable{

	private Vector2 pointA;
	private Vector2 pointB;	
	private Color color;
	
	public Line(Vector2 pointA, Vector2 pointB, Color color) {
		this.pointA = new Vector2(pointA);
		this.pointB = new Vector2(pointB);
		this.color = color;
	}
	
	public Line(Vector2 pointA, Vector2 pointB) {
		this(pointA,pointB,Colorizer.DEFAULT_COLOR);
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
       	shapeRenderer.line(pointA, pointB);
        shapeRenderer.end();
	}
	
	@Override
	public void render(Pixmap pixmap, Color color) {
		pixmap.setColor(color);
		pixmap.drawLine((int) pointA.x, (int) pointA.y, (int) pointB.x, (int) pointB.y);
	}

	public Vector2 getPointA() {
		return pointA;
	}

	public Vector2 getPointB() {
		return pointB;
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
		for(Vector2 point: Arrays.asList(pointA,pointB))
			point.add(vector);
	}

	@Override
	public void scale(Float scale) {
		for(Vector2 point: Arrays.asList(pointA,pointB))
			point.scl(scale);
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+" A:"+pointA+", B:"+pointB;
	}

	@Override
	public Line getCopy() {
		return new Line(pointA.cpy(), pointB.cpy());
	}

	@Override
	public boolean collidesWith(Rectangle rect) {
		return rect.contains(pointA)||rect.contains(pointB);
	}
	
	
	public Double getImage(Double x) {
		return getM()*x+getB();
	}
	@SuppressWarnings("deprecation")
	public Double getM() {
		return new Double(pointA.y-pointB.y)/(pointA.x-pointB.x);
	}
	
	public Double getB() {
		return pointA.y/(getM()*pointA.x);
	}
}
