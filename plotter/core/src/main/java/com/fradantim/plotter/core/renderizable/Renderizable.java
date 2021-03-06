package com.fradantim.plotter.core.renderizable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public interface Renderizable extends Movable, Scalable, Copyable<Renderizable>{
	
	public Color getColor();
	
	public void setColor(Color color);
	
	public default void render(ShapeRenderer shapeRenderer) {
		render(shapeRenderer, getColor());
	}
	
	public void render(ShapeRenderer shapeRenderer, Color color);
	
	public default void render(Pixmap pixmap) {
		render(pixmap, getColor());
	}	
	
	public void render(Pixmap pixmap, Color color);
	
	
	public boolean collidesWith (Rectangle rect);
}
