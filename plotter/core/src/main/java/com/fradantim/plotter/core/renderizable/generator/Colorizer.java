package com.fradantim.plotter.core.renderizable.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.util.FileSystemUtil.AppProperty;

public class Colorizer {
	
	private static final Float delta =12.28F;
	
	private static final List<Color> simpleColors=Arrays.asList(Color.WHITE, Color.LIGHT_GRAY, Color.GRAY,
			Color.DARK_GRAY, Color.BLACK, Color.BLUE, Color.NAVY, Color.ROYAL, Color.SLATE, Color.SKY, Color.CYAN, 
			Color.TEAL, Color.GREEN, Color.CHARTREUSE, Color.LIME, Color.FOREST, Color.OLIVE, Color.YELLOW, 
			Color.GOLD, Color.GOLDENROD, Color.ORANGE, Color.BROWN, Color.TAN, Color.FIREBRICK, Color.RED, 
			Color.SCARLET, Color.CORAL, Color.SALMON, Color.PINK, Color.MAGENTA, Color.PURPLE, Color.VIOLET, Color.MAROON);;
	
	private static final List<java.awt.Color> simpleAwtColors;
	private final static java.awt.Color[] simpleAwtColorsArray;
	
	static{
		simpleAwtColors= new ArrayList<>();
		
		simpleColors.forEach(c -> {
			simpleAwtColors.add(badLogicColorToAwtColor(c));
		});
		
		simpleAwtColorsArray= new java.awt.Color[Colorizer.getSimpleAwtColors().size()];
		Colorizer.getSimpleAwtColors().toArray(simpleAwtColorsArray);
	}
			
	public static final Color DEFAULT_COLOR= awtColorTobadLogicColor((java.awt.Color)AppProperty.COLORIZER_DEFAULT_COLOR.getCurrentValue());

	public static Renderizable colorize(Renderizable renderizable, Color color) {
		renderizable.setColor(color);
		return renderizable;
	}
	
	public static Collection<? extends Renderizable> colorize(Collection<? extends Renderizable> renderizables, Color color) {
		for(Renderizable renderizable: renderizables)
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
	
	public static List<Color> getSimpleColors(){
		return simpleColors;
	}
	
	public static List<java.awt.Color> getSimpleAwtColors(){
		return simpleAwtColors;
	}
	
	public static java.awt.Color[] getSimpleAwtColorsArray(){
		return simpleAwtColorsArray;
	}

	public static java.awt.Color badLogicColorToAwtColor(Color c){
		return new java.awt.Color(c.r, c.g, c.b, c.a);
	}
	
	public static Color awtColorTobadLogicColor(java.awt.Color c){
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}
}
