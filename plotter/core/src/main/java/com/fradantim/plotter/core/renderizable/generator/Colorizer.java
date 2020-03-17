package com.fradantim.plotter.core.renderizable.generator;

import java.util.Collection;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Renderizable;

public class Colorizer {

	public static Renderizable colorize(Renderizable renderizable, Color color) {
		renderizable.setColor(color);
		return renderizable;
	}
	
	public static Collection<? extends Renderizable> colorize(Collection<? extends Renderizable> renderizables, Color color) {
		for(Renderizable renderizable: renderizables)
			colorize(renderizable, color);
		return renderizables;
	}
}
