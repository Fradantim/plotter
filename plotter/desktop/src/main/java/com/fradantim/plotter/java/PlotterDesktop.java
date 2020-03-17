package com.fradantim.plotter.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;
import com.fradantim.plotter.java.processor.FunctionProcessor;
import com.fradantim.plotter.java.processor.PVIEulerProcessor;
import com.fradantim.plotter.java.processor.ProblemaValorInicialProcessor;

public class PlotterDesktop {
	private static final Integer PIXELS_PER_POINT=100;
	
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
			/*
			p.addRenderizables(new FunctionProcessor("t", "p(-t,3)",PIXELS_PER_POINT).getImage(domainPoints, Color.CORAL));
			p.addRenderizables(new FunctionProcessor("t", "log(t)",PIXELS_PER_POINT).getImage(domainPoints, Color.CYAN));
			p.addRenderizables(new FunctionProcessor("t", "tan(t)",PIXELS_PER_POINT).getImage(domainPoints, Color.GREEN));
			*/
			//Funciones salto
			/*
			p.addRenderizables(new FunctionProcessor("t", "p(e,-p(t,2))+1",PIXELS_PER_POINT).getImage(domainPoints, Color.RED));
			p.addRenderizables(new FunctionProcessor("t", "t-toInt(t)",PIXELS_PER_POINT).getImage(domainPoints, Color.BLUE));
			p.addRenderizables(new FunctionProcessor("t", "t/abs(t)",PIXELS_PER_POINT).getImage(domainPoints, Color.YELLOW));
			*/
			
			//dna
			/*
			p.addRenderizables(new FunctionProcessor("t", "sin(t)",PIXELS_PER_POINT).getImage(domainPoints, Color.BLUE));
			p.addRenderizables(new FunctionProcessor("t", "sin(t+pi)",PIXELS_PER_POINT).getImage(domainPoints, Color.GREEN));
			*/
			
			//trifasica
			/*
			p.addRenderizables(new FunctionProcessor("t", "sin(t)",PIXELS_PER_POINT).getImage(domainPoints, Color.BLUE));
			p.addRenderizables(new FunctionProcessor("t", "sin(t+pi*2/3)",PIXELS_PER_POINT).getImage(domainPoints, Color.RED));
			p.addRenderizables(new FunctionProcessor("t", "sin(t+pi*4/3)",PIXELS_PER_POINT).getImage(domainPoints, Color.GREEN));
			*/
			
			//derivacion
			/*
			Color[] colors = {Color.BROWN,Color.MAGENTA,Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW,Color.GREEN,Color.BLUE};
			List<String> vars=Arrays.asList("t");
			String function = "p(t,"+(colors.length-1)+")/32";
			//String function = "tan(t)";
			
			HashMap<String, List<Float>> elementsByVar= new HashMap<>();
			elementsByVar.put(vars.get(0), domainPoints);
			
			LocalDateTime start = LocalDateTime.now();
			for(int i=0; i< colors.length; i++) {				
				p.addRenderizables(FunctionProcessor.getImage(vars, function, elementsByVar, PIXELS_PER_POINT, i, colors[i]));
			}
			LocalDateTime end = LocalDateTime.now();
			
			
			long seconds = start.until( end, ChronoUnit.SECONDS );
			System.out.println("Duration: "+seconds+" seconds");
			*/
			
			
			//
			List<String> vars=Arrays.asList("t");
			HashMap<String, List<Float>> elementsByVar= new HashMap<>();
			elementsByVar.put(vars.get(0), domainPoints);
			
			String function = "p(t,2)";
			String derivatedfunction = "2*t";
			
			//String function = "p(t,0.5)";
			//String derivatedfunction = "p(2*p(t,0.5),-1)";
			
			p.addRenderizables(Colorizer.colorize(FunctionProcessor.getImage(vars, function, elementsByVar, PIXELS_PER_POINT, 0), Color.GREEN));
			

			ProblemaValorInicialProcessor eulerProcessor = new PVIEulerProcessor();
			List<String> eulerVars=Arrays.asList("t","x");
			
			Color[] colors = {Color.BROWN,Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW};
			
			for(int i=0; i< colors.length; i++) {	
				System.out.println((i+1)+" "+1F/(Math.pow(2, i)));
				p.addRenderizables(Colorizer.colorize(eulerProcessor.getImage(eulerVars, derivatedfunction, 0F, 0F, 2F, new Double(1F/(Math.pow(2, i))).floatValue(), null), colors[i]));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
