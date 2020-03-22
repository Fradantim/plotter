package com.fradantim.plotter.core.Threads;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Plotter;

public interface ColorRunnable extends Runnable{
	
	public Color getColor();
	
	public void setPlotter(Plotter p );	

}
