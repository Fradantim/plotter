package com.fradantim.plotter.core.Threads;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Plotter;

public interface ColorRunnable extends Runnable{
	
	public Color getColor();
	
	public void setPlotter(Plotter plotter);	
	
	public default String getFormattedName() {
		return toString();
	}
	
	public void innerRun();
	
	public default void run() {
		try {
			innerRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
