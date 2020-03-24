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

public class INImplementations {
	
	public static void main (String[] args) {
		Plotter p = new AwarePlotter();
		p.setPixelsPerPoint((Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(p, config);

		try {
			List<String> vars=Arrays.asList("t");
			//Trapezoids implementation
			
			/*String functionA = "1-p(p(-t,0.5),2)";

			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, functionA, Color.GREEN));
			p.addColorRunnable(TaskGenerator.getTrapezoidsIN(p, vars, functionA, -2F, 0F, 2F, null, Color.BLUE));
			
			
			String functionB = "sin(4*p(p(t,0.5),2)/pi)";

			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, functionB, Color.GREEN));
			p.addColorRunnable(TaskGenerator.getTrapezoidsIN(p, vars, functionB, 0F, 5F, 1F, null, Color.BLUE));
			*/
			
			String function = "p(t,-2)";
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, function, Color.GREEN));
			p.addColorRunnable(TaskGenerator.getTrapezoidsIN(p, vars, function, -4F, -1F, 1F, null, Color.BLUE));
			p.addColorRunnable(TaskGenerator.getRandomIN(p, vars, function, 1F, 4F, 100, Color.RED));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
