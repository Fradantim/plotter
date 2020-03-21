package com.fradantim.plotter.java.processor;

import java.util.List;

import com.fradantim.plotter.core.Renderizable;

public interface ProblemaValorInicialProcessor {
	
	//public Double getValue(List<String> vars, String function, Float t0, Double x0, Float T, Float h, Integer N, Float t);
	
	public List<Renderizable> getImage(List<String> vars, String function, Float t0, Float x0, Float T, Float h, Integer N);
	
}

