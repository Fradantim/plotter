package com.fradantim.plotter.java;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.AwarePlotter;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.core.Threads.TaskGenerator;
import com.fradantim.plotter.core.util.FileSystemUtil.AppProperty;

public class TriphasicFunctions {
	
	public static void main (String[] args) {
		Plotter p = new AwarePlotter();
		p.setPixelsPerPoint((Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(p, config);

		try {
			List<String> vars=Arrays.asList("t");
			
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t)", Color.BLUE));
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t+pi*2/3)", Color.RED));
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t+pi*4/3)", Color.GREEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
