package com.fradantim.plotter.core;

import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fradantim.plotter.core.renderizable.Renderizable;
import com.fradantim.plotter.core.renderizable.generator.AxisGenerator;

/** An implementation of Plotter which doesn't keeps it's Renderizables in memory and does not refreshes the screen,
 *  once a Renderizable is added it wont be removed. <br>
 *  <b>NOT YET READY FOR USE</b><br>
 *  (and may be never done)*/
public class ObliviousPlotter extends Plotter {
	
	private boolean firstTime=true;
	int i =0;
	@Override
	protected void afterCreate() {	}
	
	protected void doRender() {
		if(firstTime) {
			firstTime=false;
			
			Gdx.graphics.setContinuousRendering(false);
			
			Stage stage= new Stage();
			stage.setActionsRequestRendering(false);
			Gdx.input.setInputProcessor(stage);
			
			
			Gdx.gl.glClearColor(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(), BACKGROUND_COLOR.getAlpha());
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			addRenderizables(AxisGenerator.getAxis(getDisplayResolution(),pixelsPerPoint));
		}
		
		
		addRenderizables(AxisGenerator.getAxis(getDisplayResolution(),pixelsPerPoint,Color.GOLD));
		
		addRenderizables(AxisGenerator.getAxis(getDisplayResolution(),pixelsPerPoint,Color.RED));
		
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
	}

	public synchronized void addRenderizable(Renderizable renderizable) {
		renderizable.render(shapeRenderer);
		
		Gdx.graphics.requestRendering();
	}
	
	@Override
	public synchronized void addRenderizables(Collection<? extends Renderizable> renderizables) {
		renderizables.forEach(r -> addRenderizable(r));
		Gdx.graphics.requestRendering();
	}

	@Override
	public void addRenderizables(RenderizableComponent component) {
		addRenderizables(component.getRenderizables());
	}	
}
