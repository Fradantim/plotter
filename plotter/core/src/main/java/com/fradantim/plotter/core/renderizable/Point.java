package com.fradantim.plotter.core.renderizable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.Renderizable;

public class Point implements Renderizable{

	private Vector2 point;
	private Color color;
	
	public Point(Vector2 point, Color color) {
		this.point = point;
		this.color = color;
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        shapeRenderer.setColor(color);
       	shapeRenderer.point(point.x, point.y,0);
        shapeRenderer.end();
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
}
