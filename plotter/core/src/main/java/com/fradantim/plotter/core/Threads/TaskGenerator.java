package com.fradantim.plotter.core.Threads;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

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

	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer pixelsPerPoint) {
		return new SimpleFunctionTask(plotter, vars, function, pixelsPerPoint, null, null);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer pixelsPerPoint, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, pixelsPerPoint, null, color);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer pixelsPerPoint, Integer derivationTimes) {
		return new SimpleFunctionTask(plotter, vars, function, pixelsPerPoint, derivationTimes, null);
	}
	
	public static ColorRunnable getSimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer pixelsPerPoint, Integer derivationTimes, Color color) {
		return new SimpleFunctionTask(plotter, vars, function, pixelsPerPoint, derivationTimes, color);
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

final class SimpleFunctionTask implements ColorRunnable{
	private Plotter plotter;
	private List<String> vars;
	private String function;
	private Integer pixelsPerPoint;
	private Integer derivationTimes;
	private Color color;
	private String name;
	
	public SimpleFunctionTask(Plotter plotter, List<String> vars, String function, Integer pixelsPerPoint, Integer derivationTimes, Color color) {
		this.plotter = plotter;
		this.vars = vars;
		this.function = function;
		this.pixelsPerPoint = pixelsPerPoint;
		this.derivationTimes = derivationTimes!=null ? derivationTimes : 0;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
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
	public void run() {
		plotter.addRenderizables(
				new RenderizableComponent(
						Colorizer.colorize(FunctionProcessor.getImage(vars, function, plotter.getDomainByVar(vars), pixelsPerPoint,derivationTimes), color),
						color, 
						name.replaceAll("<[a-z]*>", "").replaceAll("</[a-z]*>", "")
						)
				);
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return name.replaceAll("<[a-z]*>", "").replaceAll("</[a-z]*>", "");
	}
	
	@Override
	public String getFormattedName() {
		return name;
	}

	@Override
	public void setPlotter(Plotter p) {
		this.plotter=p;
	}
}


abstract class PVITask implements ColorRunnable{
	protected PVIProcessor pviProcessor;
	protected String name;
	protected Plotter plotter;
	protected Color color;
	
	public PVITask(PVIProcessor pviProcessor, Plotter plotter, Color color) {
		this.pviProcessor=pviProcessor;
		this.plotter=plotter;
		this.color = color!=null ? color : Colorizer.DEFAULT_COLOR;
		
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
	public void run() {
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
	
	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setPlotter(Plotter plotter) {
		this.plotter=plotter;
	}
}


final class EulerPVITask extends PVITask{
	public EulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		super(new EulerPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N), plotter, color);
	}
}

final class ImprovedEulerPVITask extends PVITask{
	public ImprovedEulerPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		super(new ImprovedEulerPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N), plotter, color);
	}
}

final class RungeKuttaPVITask extends PVITask{
	public RungeKuttaPVITask(Plotter plotter, List<String> vars, String derivatedFunction, Float t0, Float x0, Float T, Float h, Integer N, Color color) {
		super(new RungeKuttaPVIProcessor(vars, derivatedFunction, t0, x0, T, h, N), plotter, color);
	}
}