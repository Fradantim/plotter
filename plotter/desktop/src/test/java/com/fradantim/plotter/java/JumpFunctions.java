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

public class JumpFunctions {
	
	public static void main (String[] args) {
		Plotter p = new AwarePlotter();
		p.setPixelsPerPoint((Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(p, config);

		try {
			List<String> vars=Arrays.asList("t");
			
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "p(e,-p(t,2))+1", Color.RED));
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "t-toInt(t)", Color.BLUE));
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, "t/abs(t)", Color.YELLOW));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
