package com.fradantim.plotter.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.fradantim.plotter.core.renderizable.generator.AxisGenerator;

public class Plotter implements ApplicationListener {
	
	private static final int MIN_FONT_SIZE=12, SIGNIFICATIVE_DECIMALS=2;

	private static final float PIXEL_LINE_WIDTH=2F;
	
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer; //para las formas
	
	private OrthographicCamera camera;
	
	private BitmapFont font;
	
	private Vector2 camPos = new Vector2(0,0);
	
	private Integer fontSize, pixelsPerPoint;

	private boolean fullScreen = true;
	
	private List<Renderizable<?>> renderizables = new ArrayList<>();
	private List<Float> domainPoints;
	
	//PARA DEBUG
	private boolean debugOnScreen = true, listStadistics = true, drawGrid = true;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.gl.glLineWidth(PIXEL_LINE_WIDTH);
		shapeRenderer = new ShapeRenderer(); //necesito inicializacion tardia
		
		camera = new OrthographicCamera();
		camera.position.set(new Vector3(0,0,0));
		camera.update();
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		start();
		switchScreenMode(fullScreen);
		
		renderizables.addAll(AxisGenerator.getAxis(getDisplayResolution(),pixelsPerPoint));
	}
	
	public void start() { }
	 
	@Override
	public void render () {
		fillDomainPoints();
		fillFontSize();
		camera.setToOrtho(false, getDisplayResolution().x , getDisplayResolution().y);

		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(debugOnScreen) {
			debugOnScreen();
		}
		
		getInput();
		
	    for(Renderizable<?> renderizable: renderizables) {
			renderizable.render(shapeRenderer);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	private void getInput() {
		if(isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if(isKeyJustPressed(Keys.F1)) {
			start();
		}
		
		if(isKeyJustPressed(Keys.F3)) {
			debugOnScreen = (debugOnScreen) ? false : true;
		}
		
		if(isKeyJustPressed(Keys.NUM_1)) {
			listStadistics = (listStadistics) ? false : true;
		}
		
		if(isKeyJustPressed(Keys.NUM_2)) {
			drawGrid = (drawGrid) ? false : true;
		}
		
		if(isKeyJustPressed(Keys.F6)) {
			changeFontSize(!isKeyPressed(Keys.SHIFT_LEFT));
		}
		
		if(isKeyJustPressed(Keys.F4)) {
			fullScreen = (fullScreen) ? false : true;
			switchScreenMode(fullScreen);
		}
		
		if(isKeyJustPressed(Keys.NUM_0)) {
			if(fullScreen) {
				switchScreenMode(false);
			}
			System.out.println("INSIDE DEBUG");
		}
		
		if(isDebugUpPressed())    { camPos.y+=16; }
		if(isDebugDownPressed())  { camPos.y-=16; }
		if(isDebugRightPressed()) { camPos.x+=16; }
		if(isDebugLeftPressed())  { camPos.x-=16; }
	}
	
	private boolean isDebugDownPressed()  { return Gdx.input.isKeyPressed(Keys.S); }
	private boolean isDebugUpPressed()    { return Gdx.input.isKeyPressed(Keys.W); }
	private boolean isDebugRightPressed() { return Gdx.input.isKeyPressed(Keys.D); }
	private boolean isDebugLeftPressed()  { return Gdx.input.isKeyPressed(Keys.A); }

	private void debugOnScreen() {
		batch.begin();
	
		if(listStadistics) {
			String statsStr = String.join("\n",getStadistics().stream().map(Stadistic::toString).collect(Collectors.toList()));
			font.draw(batch, statsStr, getCamTopLeft().x, getCamTopLeft().y);
		}
		
		batch.end();
	}
		
	/** returns pixel in to the OS Windows */
	private Vector2 getDisplayPointerPos() {
		return new Vector2(Gdx.input.getX(),getDisplayResolution().y-Gdx.input.getY());
	}

	/** returns pixel in to the inner grid */
	private Vector2 transformToCoordinates(Vector2 point) {
		return new Vector2(point).sub(getCenter()).scl(1F/pixelsPerPoint);
	}
	
	private Vector2 getCamTopLeft() {
		return new Vector2(getCamPos().x, getCamPos().y+getDisplayResolution().y);
	}
	
	private Vector2 getCamPos() {
		return camPos;
	}
	
	private BitmapFont getStadisticsFont(int oldFontSize) {
		if(oldFontSize!=this.fontSize || font==null) {
			font = FontUtils.getOnScreenFont(fontSize);
		}
		return font;
	}
	
	private Vector2 getDisplayResolution() {
		return new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	
	private void switchScreenMode(boolean fullScreen) {
		if (fullScreen)
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()));
		else
			Gdx.graphics.setWindowedMode((int) 1280, (int) 720);
	}
	
	private void changeFontSize(boolean up) {
		int oldFontSize=fontSize;
		if (up)
			fontSize++;
		else if(fontSize>MIN_FONT_SIZE)
			fontSize--;
		font=getStadisticsFont(oldFontSize);
	}
	
	private boolean isKeyJustPressed(int... keys) {
		for(int key: keys) {
			if(!Gdx.input.isKeyJustPressed(key)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isKeyPressed(int... keys) {
		for(int key: keys) {
			if(!Gdx.input.isKeyPressed(key)) {
				return false;
			}
		}
		return true;
	}

	public List<Stadistic> getStadistics() {
		List<Stadistic> stats = new ArrayList<>();
		stats.add(new Stadistic("Gdx.gfx.FPS&deltaTime",String.valueOf(Gdx.graphics.getFramesPerSecond())));
		stats.add(new Stadistic(this,"FontSize",String.valueOf(fontSize)));
		stats.add(new Stadistic(this,"#Renderizables_",renderizables.size()));
		stats.add(new Stadistic(this,"#PixelsPerPoint",pixelsPerPoint));
		stats.add(new Stadistic(this,"DisplayPixRes (W,H)",getDisplayResolution()));
		stats.add(new Stadistic(this,"CamPos____ (W,H)",getCamPos()+">"+getCamPos()));
		stats.add(new Stadistic(this,"CamTopLeft (W,H)",getCamTopLeft()+">"+getCamTopLeft()));
		stats.add(new Stadistic(this,"DisplayPointerPos (x,y)",beautifyVector(getDisplayPointerPos(),SIGNIFICATIVE_DECIMALS)));
		stats.add(new Stadistic(this,"Coordinates____ + (x,y)",beautifyVector(transformToCoordinates(getDisplayPointerPos()), SIGNIFICATIVE_DECIMALS)));
		return stats;
	}
	
	public String beautifyVector(Vector2 vector, int significativeDecimals) {
		return "("
		+String.format("%+8."+significativeDecimals+"f",vector.x) +","
		+String.format("%+8."+significativeDecimals+"f",vector.y) +")";
	}
	
	public Vector2 getCenter() {
		return new Vector2(getDisplayResolution()).scl(.5F);
	}
	
	public void resize(int width, int height) {}

	public void pause() {}

	public void resume() {}
	
	
	public void emptyRenderizables() {
		this.renderizables=new ArrayList<>();
	}
	
	public synchronized void addRenderizable(Renderizable<?> renderizable) {
		List<Renderizable<?>> nuevosRenderizables= new ArrayList<>();
		nuevosRenderizables.addAll(this.renderizables);
		renderizable.scale(1F*pixelsPerPoint);
		renderizable.move(getCenter());
		nuevosRenderizables.add(renderizable);
		
		this.renderizables=nuevosRenderizables;
	}
	
	public synchronized void addRenderizables(Collection<? extends Renderizable<?>> renderizables) {
		List<Renderizable<?>> nuevosRenderizables= new ArrayList<>();
		Vector2 center = getCenter();
		renderizables.forEach(r -> {
			r.scale(1F*pixelsPerPoint);
			r.move(center);
		});
		nuevosRenderizables.addAll(this.renderizables);
		nuevosRenderizables.addAll(renderizables);
		
		this.renderizables=nuevosRenderizables;
	}
	
	public List<Float> getDomainPoints(){
		return domainPoints;
	}
	
	private void fillDomainPoints() {
		if(domainPoints== null) {
			List<Float> result = new ArrayList<>();
			
			for(int i=0; i< (int)getDisplayResolution().x/pixelsPerPoint; i++) {
				for(float d=0; d<1;d+=1D/pixelsPerPoint) {
					result.add(i+d);
					result.add(-i-d);
				}
			}
			Collections.sort(result);
			domainPoints=result;
		}
	}
	
	private void fillFontSize() {
		if(fontSize==null || fontSize==0) {
			fontSize=FontUtils.getFontSize(Gdx.graphics.getWidth()*Gdx.graphics.getHeight());
			font =  getStadisticsFont(fontSize);
		}
	}

	public Integer getPixelsPerPoint() {
		return pixelsPerPoint;
	}

	public void setPixelsPerPoint(Integer pixelsPerPoint) {
		this.pixelsPerPoint = pixelsPerPoint;
	}
	
	public Float getHeigth() {
		return getDisplayResolution().y/2/pixelsPerPoint;
	}
}
