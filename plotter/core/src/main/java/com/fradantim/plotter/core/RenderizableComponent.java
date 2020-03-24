package com.fradantim.plotter.core;

import java.util.Collection;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.renderizable.Renderizable;

public class RenderizableComponent{
	
	private Collection<? extends Renderizable> renderizables;
	private Color color;
	private String toString;
	
	public RenderizableComponent(Collection<? extends Renderizable> collection, Color color, String toString) {
		this.renderizables = collection;
		this.color = color;
		this.toString = toString;
	}

	public Collection<? extends Renderizable> getRenderizables(){
		return renderizables;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String toString() {
		return toString;
	}
}
