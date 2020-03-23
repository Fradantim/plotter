package com.fradantim.plotter.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.fradantim.plotter.core.renderizable.generator.AxisGenerator;

/** An implementation of Plotter which keeps it's Renderizables in memory and refreshes the screen */
public class AwarePlotter extends Plotter {
	
	private List<Renderizable> renderizables = new ArrayList<>();
	private Rectangle rectangleOverScreen;
	@Override
	protected void afterCreate() {
		renderizables.addAll(AxisGenerator.getAxis(getDisplayResolution(),pixelsPerPoint));
		rectangleOverScreen= new Rectangle(0, 0, (int) getDisplayResolution().x, (int)  getDisplayResolution().y);
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
		stats.add(new Stadistic(" - "," - - - - - - - - - - - "));
				
		return stats;
	}
	
	@Override
	public boolean readyToRender() {
		if(rectangleOverScreen!= null)
			return super.readyToRender();
		return false;
	}
	
	public void emptyRenderizables() {
		this.renderizables=new ArrayList<>();
	}
	
	private synchronized void addRenderizable(Renderizable renderizable) {
		renderizables.add(renderizable);
	}
	
	public void addRenderizables(Collection<? extends Renderizable> renderizables) {
		renderizables.forEach(r -> { 
			r.scale(1F*pixelsPerPoint);
			r.move(getCenter());
			if(r.collidesWith(rectangleOverScreen)) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) { e.printStackTrace();}
				addRenderizable(r);
			}
			});

	}
	
	public void addRenderizables(RenderizableComponent component) {
		
		while (!readyToRender()) {
			System.out.println(Thread.currentThread().getId()+": Waiting for plotter to be ready to render.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		components.add(component);
		addRenderizables(component.getRenderizables());
	}
}
