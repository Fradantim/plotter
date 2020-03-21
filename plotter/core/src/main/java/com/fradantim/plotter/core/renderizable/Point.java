package com.fradantim.plotter.core.renderizable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class Point implements Renderizable{

	private Vector2 point;
	private Color color;
	
	public Point(Vector2 point, Color color) {
		this.point = new Vector2(point);
		this.color = color;
	}
	
	public Point(Vector2 pointA) {
		this(pointA,Colorizer.DEFAULT_COLOR);
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        shapeRenderer.setColor(color);
       	shapeRenderer.point(point.x, point.y,0);
        shapeRenderer.end();
	}
	
	@Override
	public void render(Pixmap pixmap, Color color) {
		pixmap.setColor(color);
		pixmap.drawPixel((int) point.x, (int) point.y);
	}

	public Vector2 getPoint() {
		return point;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void move(Vector2 vector) {
		point.add(vector);
	}

	@Override
	public void scale(Float scale) {
		point.scl(scale);
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+" "+point;
	}

	@Override
	public void setColor(Color color) {
		this.color=color;
	}

	@Override
	public Point getCopy() {
		return new Point(point.cpy());
	}
}
