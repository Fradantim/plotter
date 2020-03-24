package com.fradantim.plotter.core.Threads;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.core.Renderizable;
import com.fradantim.plotter.core.RenderizableComponent;
import com.fradantim.plotter.core.processor.EulerPVIProcessor;
import com.fradantim.plotter.core.processor.FunctionProcessor;
import com.fradantim.plotter.core.processor.ImprovedEulerPVIProcessor;
import com.fradantim.plotter.core.processor.PVIProcessor;
import com.fradantim.plotter.core.processor.RungeKuttaPVIProcessor;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class TaskGenerator {
	
	public static ColorRunnable getSimplePipeTaskTask(Plotter plotter, Renderizable renderizable) {
		return new SimplePipeTask(plotter, renderizable);
	}

	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function) {
		return new SimpleFunctionTask(plotter, vars, function, null, null);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, null, color);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer derivationTimes) {
		return new SimpleFunctionTask(plotter, vars, function, derivationTimes, null);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer derivationTimes, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, derivationTimes, color);
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

abstract class Graphicable implements ColorRunnable{
	@JsonIgnore
	protected Plotter plotter;
	protected Color color;

	/** empty constructor for serialization*/
	public Graphicable() {}
	
	public Graphicable(Plotter plotter, Color color) {
		this.plotter=plotter;
		this.color=color!=null ? color : Colorizer.DEFAULT_COLOR;
	}
	
	@Override
	public void setPlotter(Plotter p) {
		this.plotter=p;
	}
	
	@Override
	public Color getColor() {
		return color;
	}
}
	
final class SimplePipeTask extends Graphicable{
	private Renderizable renderizable;
	
	/** empty constructor for serialization*/
	public SimplePipeTask() {}
	
	public SimplePipeTask(Plotter plotter, Renderizable renderizable) {
		super(plotter, renderizable.getColor());
		this.renderizable = renderizable;
	}

	@Override
	public void innerRun() {
		plotter.addRenderizables(Arrays.asList(renderizable));
	}
}

final class SimpleFunctionTask extends Graphicable{
	private List<String> vars;
	private String function;
	private Integer derivationTimes;
	private String name;
	
	/** empty constructor for serialization*/
	public SimpleFunctionTask() {}
	
	public SimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer derivationTimes, Color color) {
		super(plotter, color);
		this.vars = vars;
		this.function = function;
		this.derivationTimes = derivationTimes!=null ? derivationTimes : 0;
		name="<html>F : <b>f("+String.join(",", vars)+")</b>=";
		if(derivationTimes==null || derivationTimes==0) {
			name+=function;
		} else {
			name+="("+function+")";
			for(int i=0;i<derivationTimes;i++) {
				name+="'";
			}
		}
		name+="</html>";
	}

	@Override
	public void innerRun() {
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(FunctionProcessor.getImage(vars, function, plotter.getDomainByVar(vars), plotter.getPixelsPerPoint(),derivationTimes), color),
						color, 
						name.replaceAll("<[a-z]*>", "").replaceAll("</[a-z]*>", "")
						)
				);
	}
	
	@Override
	public String toString() {
		return name.replaceAll("<[a-z]*>", "").replaceAll("</[a-z]*>", "");
	}
	
	@Override
	public String getFormattedName() {
		return name;
	}
}


abstract class PVITask extends Graphicable{
	protected PVIProcessor pviProcessor;
	protected String name;
	
	/** empty constructor for serialization*/
	public PVITask() {}
	
	public PVITask(PVIProcessor pviProcessor, Plotter plotter, Color color) {
		super(plotter,color);
		this.pviProcessor=pviProcessor;
		name="<html>"+pviProcessor.getName()+": "
				+"<b>f("+String.join(",", pviProcessor.getVars())+")</b>="+pviProcessor.getFunction()+"; "
				+ "<b>t<sub>0</sub></b>= "+pviProcessor.getT0()+"; "
				+ "<b>x<sub>0</sub></b>= "+pviProcessor.getX0()+"; "
				+ "<b>T</b>="+pviProcessor.getT()+"; "
				+ "<b>h</b>="+pviProcessor.getH()+"; "
				+ "<b>N</b>="+pviProcessor.getN()
				+ "</html>";
	}
	
	@Override
	public void innerRun() {
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(pviProcessor.getImage(),color),
						color, 
						pviProcessor.toString()
						)
				);
	}
	
	@Override
	public String getFormattedName() {
		return name;
	}
}


final class EulerPVITask extends PVITask{
	
	/** empty constructor for serialization*/
	public EulerPVITask() {}
	
	public EulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		super(new EulerPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N), plotter, color);
	}
}

final class ImprovedEulerPVITask extends PVITask{
	
	/** empty constructor for serialization*/
	public ImprovedEulerPVITask() {}
	
	public ImprovedEulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		super(new ImprovedEulerPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N), plotter, color);
	}
}

final class RungeKuttaPVITask extends PVITask{
	
	/** empty constructor for serialization*/
	public RungeKuttaPVITask() {}
	
	public RungeKuttaPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		super(new RungeKuttaPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N), plotter, color);
	}
}