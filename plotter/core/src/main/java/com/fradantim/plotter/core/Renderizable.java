package com.fradantim.plotter.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Renderizable extends Movable, Scalable{
	
	public Color getColor();
	
	public default void render(ShapeRenderer shapeRenderer) {
		render(shapeRenderer, getColor());
	}
	
	public void render(ShapeRenderer shapeRenderer, Color color);
}
