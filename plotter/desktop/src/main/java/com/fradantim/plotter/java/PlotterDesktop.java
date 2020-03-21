package com.fradantim.plotter.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.AwarePlotter;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;
import com.fradantim.plotter.java.Threads.TaskGenerator;

public class PlotterDesktop {
	
	private static final Integer PIXELS_PER_POINT=200;
	
	public static void main (String[] args) {
		Plotter p = new AwarePlotter();
		//Plotter p = new ObliviousPlotter();
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
			/* 	*/
			Color[] colors = {Color.BROWN,Color.MAGENTA,Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW,Color.GREEN,Color.BLUE};
			String function = "p(t,"+(colors.length-1)+")/32";
			
			for(int i=0; i< colors.length; i++) {
				service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, function, domainByVar, PIXELS_PER_POINT, i, colors[i]));
			}
			
			
			//Euler PVI
			/* 
			String functionA = "p(t,2)";
			String derivatedfunctionA = "2*t";
			
			String functionB = "-p(t,2)";
			String derivatedfunctionB = "-2*t";
			
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, functionA, domainByVar, PIXELS_PER_POINT, Color.GREEN));
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, functionB, domainByVar, PIXELS_PER_POINT, Color.BLUE));

			List<String> eulerVars=Arrays.asList("t","x");
			
			Color[] colors = {Color.BROWN,Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW, Color.BLUE,Color.WHITE};
			
			for(int i=0; i< colors.length; i++) {	
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionA, 0F, 0F, 4F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionA, -4F, 16F, 0F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionB, 0F, 0F, -4F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionB, 4F, -16F, 0F, new Double(1F/(Math.pow(2, i))).floatValue(), null,colors[i]));
			}
			*/
			
			
			//Euler PVI again
			/* 
			String functionA = "p(t,2)";
			String derivatedfunctionA = "2*t";
			
			int times=10;
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, functionA, domainByVar, PIXELS_PER_POINT, Colorizer.getColorFromGradient(0)));
			List<String> eulerVars=Arrays.asList("t","x");
			
			for(int i=0; i< times-1; i++) {	
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionA, -2F, 4F, 3F, new Double(1F/(Math.pow(2, i))).floatValue(), null,Colorizer.getColorFromGradient((times-i))));
			}
			*/
			//some colors
			/* 
			Line line = new Line(new Vector2(-6F,-3F), new Vector2(-5F,-2F));
			service.submit(TaskGenerator.getSimplePipeTaskTask(p, line));

			int width=256;
			for(int i=0; i<width; i++) {
				Line newLine;
				newLine = new Line(new Vector2(i*4F/PIXELS_PER_POINT,4), new Vector2(i*4F/PIXELS_PER_POINT,3));
				newLine.setColor(Colorizer.getColorFromGradient(i));
				service.submit(TaskGenerator.getSimplePipeTaskTask(p,newLine));
				for(Integer mod : new Integer [] {2,4,8,16,32,64,128}) {
					if(i%mod==0) {
						int exp=new Double(Math.log(mod) / Math.log(2)).intValue();
						newLine = new Line(new Vector2(i*4F/PIXELS_PER_POINT,4-exp), new Vector2(i*4F/PIXELS_PER_POINT,3-exp));
						newLine.setColor(Colorizer.getColorFromGradient(i));
						service.submit(TaskGenerator.getSimplePipeTaskTask(p,newLine));
					}
				}
			}*/
		
			//Euler PVI vs Improved 
			/*  
			String functionA = "p(t,4)";
			String derivatedfunctionA = "4*p(t,3)";
			
			int times=2;
			service.submit(TaskGenerator.getSimpleFunctionTask(p, vars, functionA, domainByVar, PIXELS_PER_POINT, Colorizer.getColorFromGradient(-1)));
			List<String> eulerVars=Arrays.asList("t","x");
			
			for(int i=0; i< times; i++) {
				service.submit(TaskGenerator.getEulerPVI(p, eulerVars, derivatedfunctionA, 0F, 0F, 4F, new Double(1F/(Math.pow(2, i))).floatValue(), null,Colorizer.getColorFromGradient((times-i)*4)));
				service.submit(TaskGenerator.getImprovedEulerPVI(p, eulerVars, derivatedfunctionA, 0F, 0F, -4F, new Double(1F/(Math.pow(2, i))).floatValue(), null,Colorizer.getColorFromGradient((times-i)*4)));
			}
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
