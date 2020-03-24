package com.fradantim.plotter.core.renderizable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class Cross implements Surface{

	private Vector2 point;
	private Float radius;
	
	private Color color;
	
	public Cross(Vector2 point, Float radius, Color color) {
		this.point = new Vector2(point);
		this.radius = radius;
		this.color = color;
	}
	
	public Cross(Vector2 point, Float radius) {
		this(point, radius, Colorizer.DEFAULT_COLOR);
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(color);
		Float ady= getAdyacent().floatValue();
		Float opo= getOpposite().floatValue();
		//I know, those are the same value
		
		shapeRenderer.line(new Vector2(point.x-ady, point.y+opo), new Vector2(point.x+ady, point.y-opo));
		shapeRenderer.line(new Vector2(point.x-ady, point.y-opo), new Vector2(point.x+ady, point.y+opo));
		shapeRenderer.end();
	}
	
	private Double getAdyacent() {
		return Math.sin(Math.toRadians(45))*radius;
	}
	
	private Double getOpposite() {
		return Math.cos(Math.toRadians(45))*radius;
	}
	
	@Override
	public void render(Pixmap pixmap, Color color) {
		pixmap.setColor(color);
		pixmap.drawLine((int) (point.x-radius), (int) (point.y+radius), (int) (point.x+radius), (int) (point.y-radius));
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
	public Cross getCopy() {
		return new Cross(point.cpy(), radius);
	}

	@Override
	public boolean collidesWith(Rectangle rect) {
		return rect.contains(point);
	}

	@Override
	public Double getArea() {
		return 4*Math.pow(radius, 2);
	}

	@Override
	public void setFilled(Boolean filled) {}

	@Override
	public Boolean getFilled() {
		return null;
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
