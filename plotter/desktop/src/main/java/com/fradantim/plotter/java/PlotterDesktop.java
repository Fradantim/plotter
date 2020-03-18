package com.fradantim.plotter.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.java.Threads.TaskGenerator;

public class PlotterDesktop {
	
	private static final Integer PIXELS_PER_POINT=250;
	
	public static void main (String[] args) {
		Plotter p = new Plotter();
		p.setPixelsPerPoint(PIXELS_PER_POINT);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(p, config);

		List<Float> domainPoints= p.getDomainPoints();
		while (domainPoints == null || domainPoints.isEmpty()) {
			System.out.println("SLEEP");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			domainPoints= p.getDomainPoints();
		}
		
		try {
			int corePoolSize = Runtime.getRuntime().availableProcessors();
			corePoolSize = corePoolSize<=1 ? corePoolSize : corePoolSize-1;
			ExecutorService service = Executors.newFixedThreadPool(corePoolSize);
			
			
			List<String> vars=Arrays.asList("t");
			HashMap<String, List<Float>> domainByVar= new HashMap<>();
			domainByVar.put(vars.get(0), domainPoints);
			
			/*
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "p(-t,3)", domainByVar, PIXELS_PER_POINT, Color.CORAL));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "log(t)", domainByVar, PIXELS_PER_POINT, Color.CYAN));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "tan(t)", domainByVar, PIXELS_PER_POINT, Color.GREEN));
			*/
			
			//Funciones salto
			/*
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "p(e,-p(t,2))+1", domainByVar, PIXELS_PER_POINT, Color.RED));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "t-toInt(t)", domainByVar, PIXELS_PER_POINT, Color.BLUE));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "t/abs(t)", domainByVar, PIXELS_PER_POINT, Color.YELLOW));
			*/
						
			//dna
			/*
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t)", domainByVar, PIXELS_PER_POINT, Color.RED));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t+pi)", domainByVar, PIXELS_PER_POINT, Color.BLUE));
			*/
			
			//trifasica
			/*
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t)", domainByVar, PIXELS_PER_POINT, Color.BLUE));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t+pi*2/3)", domainByVar, PIXELS_PER_POINT, Color.RED));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, "sin(t+pi*4/3)", domainByVar, PIXELS_PER_POINT, Color.GREEN));
			*/
			
			//derivacion
			/*
			Color[] colors = {Color.BROWN,Color.MAGENTA,Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW,Color.GREEN,Color.BLUE};
			String function = "p(t,"+(colors.length-1)+")/32";
			
			for(int i=0; i< colors.length; i++) {
				service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, function, domainByVar, PIXELS_PER_POINT, i, colors[i]));
			}
			*/		
			
			//Euler PVI
			/* */
			String functionA = "p(t,2)";
			String derivatedfunctionA = "2*t";
			
			String functionB = "-p(t,2)";
			String derivatedfunctionB = "-2*t";
			
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, functionA, domainByVar, PIXELS_PER_POINT, Color.GREEN));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, functionB, domainByVar, PIXELS_PER_POINT, Color.BLUE));

			List<String> eulerVars=Arrays.asList("t","x");
			
			Color[] colors = {Color.BROWN,Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW, Color.BLUE,Color.WHITE};
			
			for(int i=0; i< colors.length; i++) {	
				System.out.println((i+1)+" "+1F/(Math.pow(2, i)));
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionA, 0F, 0F, 4F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionA, -4F, 16F, 0F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionB, 0F, 0F, -4F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionB, 4F, -16F, 0F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
