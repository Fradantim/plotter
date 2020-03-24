package com.fradantim.plotter.core.renderizable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class Circle implements Surface{

	private Vector2 point;
	private Float radius;
	
	private Color color;
	private Boolean filled=true;
	
	public Circle(Vector2 point, Float radius, Color color) {
		this.point = new Vector2(point);
		this.radius = radius;
		this.color = color;
	}
	
	public Circle(Vector2 point, Float radius) {
		this(point, radius, Colorizer.DEFAULT_COLOR);
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
		if(filled) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		} else {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		}
		shapeRenderer.setColor(color);
		shapeRenderer.circle(point.x, point.y, radius);
		shapeRenderer.end();
	}
	
	@Override
	public void render(Pixmap pixmap, Color color) {
		pixmap.setColor(color);
		pixmap.fillCircle((int)point.x, (int)point.y, radius.intValue());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" C:"+point+", R:"+radius;
	}
	
	@Override
	public void setColor(Color color) {
		this.color=color;		
	}
	
	@Override
	public void move(Vector2 vector) {
		point.add(vector);
	}

	@Override
	public void scale(Float scale) {
		point.scl(scale);
		radius*=scale;
	}

	@Override
	public Circle getCopy() {
		return new Circle(point.cpy(), radius);
	}

	@Override
	public boolean collidesWith(Rectangle rect) {
		return rect.contains(point);
	}

	@Override
	public Double getArea() {
		return Math.PI*Math.pow(radius, 2);
	}

	@Override
	public void setFilled(Boolean filled) {
		this.filled=filled;		
	}

	@Override
	public Boolean getFilled() {
		return filled;
	}

	public Vector2 getPoint() {
		return point;
	}

	public void setPoint(Vector2 point) {
		this.point = point;
	}

	public Float getRadius() {
		return radius;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}
}
