package com.fradantim.plotter.java;

import java.util.List;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.java.processor.FunctionProcessor;
import com.fradantim.plotter.java.processor.FunctionProcessor.RenderType;

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
			/*FunctionProcessor fp = new FunctionProcessor("t", "p(t,3)",PIXELS_PER_POINT);
			p.addRenderizables(fp.getImage(domainPoints, Color.RED));
			p.addRenderizables(fp.pointsToLines(fp.derivatePoints(fp.getImagePoints(domainPoints), Color.ORANGE)));*/


			Color[] colors = {Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW,Color.GREEN};
			String var="t";
			//String function = "p(t,"+(colors.length-1)+")/16";
			String function = "tan(t)";
			FunctionProcessor fp = new FunctionProcessor(PIXELS_PER_POINT,p.getHeigth()/2);
			
			for(int i=0; i< colors.length; i++) {				
				p.addRenderizables(fp.getImage(var, function, domainPoints, PIXELS_PER_POINT, i, colors[i]));
			}
			
			//p.addRenderizables(fp.pointsToLines(fp.derivatePoints(fp.getImagePoints(domainPoints), Color.ORANGE)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
