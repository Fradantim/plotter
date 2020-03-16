package com.fradantim.plotter.java.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Renderizable;

import bsh.EvalError;

public interface ProblemaValorInicialProcessor {
	
	public void fillValues(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N);

	public List<Renderizable> getImage(Integer pointsPerPoint, Color color);
	
	public default Double getImageAtElement(List<String> vars, String function, Map<String,Float> elementByVar) throws EvalError, FileNotFoundException, IOException {
		return FunctionProcessor.getImageAtElement(vars, function, elementByVar);
	}
}

