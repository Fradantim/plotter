package com.fradantim.plotter.core.renderizable.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Renderizable;

public class Colorizer {
	
	private static final Float delta =12.28F;
	
	public static final Color DEFAULT_COLOR= Color.WHITE;

	public static Renderizable<?> colorize(Renderizable<?> renderizable, Color color) {
		renderizable.setColor(color);
		return renderizable;
	}
	
	public static Collection<? extends Renderizable<?>> colorize(Collection<? extends Renderizable<?>> renderizables, Color color) {
		for(Renderizable<?> renderizable: renderizables)
			colorize(renderizable, color);
		return renderizables;
	}
	
	public static Color getDefinedColorFromGradient(Integer index) {
		return getColorFromGradient(0.3F,0.3F,0.3F,0F,2F,4F, 128, 127, index);
	}
	
	public static Color getColorFromGradient(Integer index) {
		return getColorFromGradient(delta,delta,delta,0F,2F,4F, 128, 127, index);
	}
	
	public static Color getColorFromGradient(Float frequency1, Float frequency2, Float frequency3,
		Float phase1, Float phase2, Float phase3,
		Integer center, Integer width, Integer index) {
		
		Double red = Math.sin(frequency1*index + phase1) * width + center;
		Double grn = Math.sin(frequency2*index + phase2) * width + center;
		Double blu = Math.sin(frequency3*index + phase3) * width + center;
		
		return new Color(red.floatValue()/256,grn.floatValue()/256, blu.floatValue()/256,0);
	}
	
	
	public static List<Color> makeColorGradient(Float frequency1, Float frequency2, Float frequency3,
		Float phase1, Float phase2, Float phase3,
		Integer center, Integer width, Integer ammount) {
		List<Color> result= new ArrayList<Color>();
		for (int i=0; i<ammount; i++) {
			result.add(getColorFromGradient(frequency1, frequency2, frequency3, phase1, phase2, phase3, center, width, i));
		}
		return result;
	}
}
