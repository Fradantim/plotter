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
import com.fradantim.plotter.core.processor.PVIProcessor;
import com.fradantim.plotter.core.processor.RungeKuttaPVIProcessor;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class TaskGenerator {
	
	public static ColorRunnable getSimplePipeTaskTask(Plotter plotter, Renderizable renderizable) {
		return new SimplePipeTask(plotter, renderizable);
	}

	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, null, null);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, null, color);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Integer derivationTimes) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, derivationTimes, null);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Integer derivationTimes, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, domainByVar, pixelsPerPoint, derivationTimes, color);
	}
	
	public static ColorRunnable getEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N) {
		return new EulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, null);
	}
	
	public static ColorRunnable getEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		return new EulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, color);
	}
	
	public static ColorRunnable getImprovedEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N) {
		return new ImprovedEulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, null);
	}
	
	public static ColorRunnable getImprovedEulerPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		return new ImprovedEulerPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, color);
	}
	
	public static ColorRunnable getRungeKuttaPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N) {
		return new RungeKuttaPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, null);
	}
	
	public static ColorRunnable getRungeKuttaPVI(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		return new RungeKuttaPVITask(plotter, vars, derivatedFunction, t0, x0, T, h, N, color);
	}
}

final class SimplePipeTask implements ColorRunnable{
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

	@Override
	public Color getColor() {
		return renderizable.getColor();
	}

	@Override
	public void setPlotter(Plotter p) {
		this.plotter=p;
	}
}

final class SimpleFunctionTask implements ColorRunnable, NeedsDomain{
	private Plotter plotter;
	private List<String> vars;
	private String function;
	private HashMap<String, List<Float>> domainByVar;
	private Integer pixelsPerPoint;
	private Integer derivationTimes;
	private Color color;
	private String name;
	
	public SimpleFunctionTask(Plotter plotter, List<String> vars, String function, HashMap<String, List<Float>> domainByVar, Integer pixelsPerPoint, Integer derivationTimes, Color color) {
		this.plotter = plotter;
		this.vars = vars;
		this.function = function;
		this.domainByVar = domainByVar;
		this.pixelsPerPoint = pixelsPerPoint;
		this.derivationTimes = derivationTimes!=null ? derivationTimes : 0;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
		name="F : f("+String.join(",", vars)+")=";
		if(derivationTimes==null || derivationTimes==0) {
			name+=function;
		} else {
			name+="("+function+")";
			for(int i=0;i<derivationTimes;i++) {
				name+="'";
			}
		}
	}

	@Override
	public void run() {
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(FunctionProcessor.getImage(vars, function, domainByVar, pixelsPerPoint,derivationTimes), color),
						color, 
						name
						)
				);
		
		plotter.addRenderizables(Colorizer.colorize(FunctionProcessor.getImage(vars, function, domainByVar, pixelsPerPoint,derivationTimes), color));
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public void setDomain(HashMap<String, List<Float>> domainByVar) {
		this.domainByVar=domainByVar;
	}
	
	@Override
	public List<String> getVars(){
		return vars;
	}
	
	@Override
	public void setPlotter(Plotter p) {
		this.plotter=p;
	}
}

final class EulerPVITask implements ColorRunnable{
	private Plotter plotter;
	private Color color;
	private PVIProcessor e;
	
	public EulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		this.plotter=plotter;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
		
		e = new EulerPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N);
	}

	@Override
	public void run() {
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(e.getImage(),color),
						color, 
						e.toString()
						)
				);
	}
	
	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return e.toString();
	}
	
	@Override
	public void setPlotter(Plotter p) {
		this.plotter=p;
	}
}

final class ImprovedEulerPVITask implements ColorRunnable{
	private Plotter plotter;

	private Color color;
	private PVIProcessor e;
	
	public ImprovedEulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		this.plotter=plotter;
		e = new ImprovedEulerPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N);
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
	}

	@Override
	public void run() {
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(e.getImage(),color),
						color, 
						e.toString()
						)
				);
	}
	
	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return e.toString();
	}
	
	@Override
	public void setPlotter(Plotter p) {
		this.plotter=p;
	}
}

final class RungeKuttaPVITask implements ColorRunnable{
	private Plotter plotter;
	private Color color;
	private PVIProcessor e;
	
	public RungeKuttaPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		this.plotter=plotter;
		e = new RungeKuttaPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N);
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
	}

	@Override
	public void run() {
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(e.getImage(),color),
						color, 
						e.toString()
						)
				);
	}
	
	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return e.toString();
	}
	
	@Override
	public void setPlotter(Plotter p) {
		this.plotter=p;
	}
}