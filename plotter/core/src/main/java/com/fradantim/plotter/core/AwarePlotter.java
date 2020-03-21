package com.fradantim.plotter.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.fradantim.plotter.core.renderizable.generator.AxisGenerator;

/** An implementation of Plotter which keeps it's Renderizables in memory and refreshes the screen */
public class AwarePlotter extends Plotter {
	
	private List<Renderizable> renderizables = new ArrayList<>();
	
	@Override
	protected void afterCreate() {
		renderizables.addAll(AxisGenerator.getAxis(getDisplayResolution(),pixelsPerPoint));
	}
	 
	protected void doRender() {
		Gdx.gl.glClearColor(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(), BACKGROUND_COLOR.getAlpha());
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		///ugly iteration to avoid concurrency errors
		for(int i=0; i<renderizables.size(); i++) {
			if(renderizables.get(i)!=null) {
				//yes, it can be null in this context
				renderizables.get(i).render(shapeRenderer);
			}
		}
	}
	
	@Override
	public List<Stadistic> getStadistics() {
		List<Stadistic> stats = super.getStadistics();
		stats.add(new Stadistic(this,"#Renderizables_",renderizables.size()));
		return stats;
	}
		
	public void emptyRenderizables() {
		this.renderizables=new ArrayList<>();
	}
	
	public synchronized void addRenderizable(Renderizable renderizable) {
		renderizable.scale(1F*pixelsPerPoint);
		renderizable.move(getCenter());
		renderizables.add(renderizable);
	}
	
	public void addRenderizables(Collection<? extends Renderizable> renderizables) {
		renderizables.forEach(r -> { 
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) { e.printStackTrace();}
			addRenderizable(r);
			});

	}
}