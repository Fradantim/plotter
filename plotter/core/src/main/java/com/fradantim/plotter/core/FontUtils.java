package com.fradantim.plotter.core;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontUtils {
	private static final int A_RES=1280*720;
	private static final int A_FONT=16;
	
	private static final int B_RES=3480*2160;
	private static final int B_FONT=36;
	
	// y=mx+b; m=(y1-y2)/(x1-x2); b=y1-mx1
	private static final int m=(A_RES-B_RES)/(A_FONT-B_FONT);
	private static final int b=A_RES-m*A_FONT;
	
	public static int getFontSize(int totalResolution) {
		//System.out.println("("+totalResolution+"-"+b+")/"+m+" = "+(totalResolution-b)/m);
		return (totalResolution-b)/m;
	}
		
	private static Map<Color, Map<Integer, BitmapFont>> fontCache = new HashMap<Color, Map<Integer,BitmapFont>>();
	
	public synchronized static BitmapFont getOnScreenFont(Integer newFontSize, Color color) {
		Map<Integer,BitmapFont> sizeMap = fontCache.get(color);
		
		if(sizeMap!= null) {
			BitmapFont mbf = sizeMap.get(newFontSize);
			if(mbf != null) {
				return mbf;
			} else {
				mbf= createFont(newFontSize, color);
				sizeMap.put(newFontSize, mbf);
				return mbf;
			}
		} else {
			sizeMap = new HashMap<>();
			BitmapFont mbf= createFont(newFontSize, color);
			sizeMap.put(newFontSize, mbf);
			fontCache.put(color, sizeMap);
			return mbf;
		}
	}
	
	private static BitmapFont createFont(Integer newFontSize, Color color) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Courier Prime.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = newFontSize;
		parameter.color = color;
		BitmapFont font = generator.generateFont(parameter); // font size 12
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		return font;
	}
	
}
