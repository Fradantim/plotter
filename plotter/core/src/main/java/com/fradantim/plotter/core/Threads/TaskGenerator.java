package com.fradantim.plotter.core.Threads;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.RenderizableComponent;
import com.fradantim.plotter.core.processor.FunctionProcessor;
import com.fradantim.plotter.core.processor.EulerPVIProcessor;
import com.fradantim.plotter.core.processor.ImprovedEulerPVIProcessor;
import com.fradantim.plotter.core.processor.RungeKuttaPVIProcessor;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class TaskGenerator {
	
	public static Runnable getSimplePipeTaskTask(Plotter plotter, Renderizable renderizable) {
		return new SimplePipeTask(plotter, renderizable);
	}

	public static Runnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, null, null);
	}
	
	public static Runnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, null, color);
	}
	
	public static Runnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Integer derivationTimes) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, derivationTimes, null);
	}
	
	public static Runnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Integer derivationTimes, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, derivationTimes, color);
	}
	
	public static Runnable getEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N) {
		return new EulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, null);
	}
	
	public static Runnable getEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		return new EulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, color);
	}
	
	public static Runnable getImprovedEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N) {
		return new ImprovedEulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, null);
	}
	
	public static Runnable getImprovedEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		return new ImprovedEulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, color);
	}
	
	public static Runnable getRungeKuttaPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N) {
		return new RungeKuttaPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, null);
	}
	
	public static Runnable getRungeKuttaPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		return new RungeKuttaPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, color);
	}
}

final class SimplePipeTask implements Runnable{
	private Plotter plotter;
	private Renderizable renderizable;
	
	public SimplePipeTask(Plotter plotter, Renderizable renderizable) {
		this.plotter = plotter;
		this.renderizable = renderizable;
	}

	@Override
	public void run() {
		plotter.addRenderizables(Arrays.asList(renderizable));
	}
}

final class SimpleFunctionTask implements Runnable{
	private Plotter plotter;
	private List<String> vars;
	private String function;
	private HashMap<String, List<Float>> domainByVar;
	private Integer pixelsPerPoint;
	private Integer derivationTimes;
	private Color color;
	
	public SimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Integer derivationTimes, Color color) {
		this.plotter = plotter;
		this.vars = vars;
		this.function = function;
		this.domainByVar = domainByVar;
		this.pixelsPerPoint = pixelsPerPoint;
		this.derivationTimes = derivationTimes!=null ? derivationTimes : 0;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
	}

	@Override
	public void run() {
		
		String name="F : f("+String.join(",", vars)+")=";
		
		if(derivationTimes==0) {
			name+=function;
		} else {
			name+="("+function+")";
			for(int i=0;i<derivationTimes;i++) {
				name+="'";
			}
		}
		
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(FunctionProcessor.getImage(vars, function, domainByVar, pixelsPerPoint,derivationTimes), color),
						color, 
						name
						)
				);
		
		plotter.addRenderizables(Colorizer.colorize(FunctionProcessor.getImage(vars, function, domainByVar, pixelsPerPoint,derivationTimes), color));
	}
}

final class EulerPVITask implements Runnable{
	private Plotter plotter;
	private List<String> vars;
	private String derivatedFunction;
	@SuppressWarnings("unused")
	private Float t0, x0, T, h;
	private Integer N;
	private Color color;
	
	public EulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		this.plotter=plotter;
		this.vars = vars;
		this.derivatedFunction = derivatedFunction;
		this.t0 = t0;
		this.x0 = x0;
		this.T = T;
		this.h = h;
		this.N = N;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
	}

	@Override
	public void run() {
		EulerPVIProcessor e = new EulerPVIProcessor();
		
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(e.getImage(vars, derivatedFunction, t0, x0, T, h, N),color),
						color, 
						e.toString()
						)
				);
	}
}

final class ImprovedEulerPVITask implements Runnable{
	private Plotter plotter;
	private List<String> vars;
	private String derivatedFunction;
	@SuppressWarnings("unused")
	private Float t0, x0, T, h;
	private Integer N;
	private Color color;
	
	public ImprovedEulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		this.plotter=plotter;
		this.vars = vars;
		this.derivatedFunction = derivatedFunction;
		this.t0 = t0;
		this.x0 = x0;
		this.T = T;
		this.h = h;
		this.N = N;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
	}

	@Override
	public void run() {
		ImprovedEulerPVIProcessor e = new ImprovedEulerPVIProcessor();
				
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(e.getImage(vars, derivatedFunction, t0, x0, T, h, N),color),
						color, 
						e.toString()
						)
				);
	}
}

final class RungeKuttaPVITask implements Runnable{
	private Plotter plotter;
	private List<String> vars;
	private String derivatedFunction;
	@SuppressWarnings("unused")
	private Float t0, x0, T, h;
	private Integer N;
	private Color color;
	
	public RungeKuttaPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		this.plotter=plotter;
		this.vars = vars;
		this.derivatedFunction = derivatedFunction;
		this.t0 = t0;
		this.x0 = x0;
		this.T = T;
		this.h = h;
		this.N = N;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
	}

	@Override
	public void run() {
		
		RungeKuttaPVIProcessor e = new RungeKuttaPVIProcessor();
		
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(e.getImage(vars, derivatedFunction, t0, x0, T, h, N),color),
						color, 
						e.toString()
						)
				);
	}
}