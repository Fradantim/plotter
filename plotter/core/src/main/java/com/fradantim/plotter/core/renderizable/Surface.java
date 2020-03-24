package com.fradantim.plotter.core.renderizable;

public interface Surface extends Renderizable{
	
	public Double getArea();
	
	public void setFilled(Boolean filled);
	
	public Boolean getFilled();
}
