package com.fradantim.plotter.java.processor;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.fradantim.plotter.core.Renderizable;

import bsh.Interpreter;

public class PVIEulerProcessor implements ProblemaValorInicialProcessor{

	private Float t0, x0, T, h;
	
	private Integer N, pointsPerPoints;
	
	private String var, function;
	
	private Interpreter i = new Interpreter();
	
	public void fillValues(String function, Float t0, Float x0, Float T, Float h, Integer N) {
		
	}

	public List<Renderizable> getImage(String var, String function,Integer pointsPerPoints,List<Float> domain, Color color){
		return null;
	}
	
	
}
