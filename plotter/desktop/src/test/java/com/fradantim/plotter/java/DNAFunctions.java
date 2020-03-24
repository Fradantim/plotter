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

public class DNAFunctions {
	
	public static void main (String[] args) {
		Plotter p = new AwarePlotter();
		p.setPixelsPerPoint((Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(p, config);

		try {
			List<String> vars=Arrays.asList("t");
			
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t)", Color.RED));
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t+PI)", Color.BLUE));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
