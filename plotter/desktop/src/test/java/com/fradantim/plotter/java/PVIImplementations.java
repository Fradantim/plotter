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

public class PVIImplementations {
	
	public static void main (String[] args) {
		Plotter p = new AwarePlotter();
		p.setPixelsPerPoint((Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(p, config);

		try {
			List<String> vars=Arrays.asList("t");
			//Euler PVI vs Improved vs RungeKutta
			
			String functionA = "ln(t+5)";
			String derivatedfunctionA = "1/(t+5)";
			
			p.addColorRunnable(TaskGenerator.getSimpleFunctionTask(p, vars, functionA, Color.WHITE));
			List<String> eulerVars=Arrays.asList("t","x");
			
			//Float h = 1F/4;
			Float h = 1F;
			p.addColorRunnable(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionA, -4F, 0F, 8F, h, null,Color.RED));
			p.addColorRunnable(TaskGenerator.getImprovedEulerPVI(p, eulerVars, derivatedfunctionA, -4F, 0F, 8F, h, null,Color.BLUE));
			p.addColorRunnable(TaskGenerator.getRungeKuttaPVI(p, eulerVars, derivatedfunctionA, -4F, 0F, 8F, h, null,Color.GREEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
