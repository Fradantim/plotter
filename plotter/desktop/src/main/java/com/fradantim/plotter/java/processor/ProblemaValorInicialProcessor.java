package com.fradantim.plotter.java.processor;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Renderizable;

public interface ProblemaValorInicialProcessor {
	
	public void fillValues(String function, Float t0, Float x0, Float T, Float h, Integer N);

	public List<Renderizable> getImage(String var, String function,Integer pointsPerPoints,List<Float> domain, Color color);
}

