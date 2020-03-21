package com.fradantim.plotter.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public abstract class Plotter implements ApplicationListener {
	
	public final static Color BACKGROUND_COLOR= new Color(0, 0, 0, 0);
	
	protected static final int MIN_FONT_SIZE=12, SIGNIFICATIVE_DECIMALS=2;

	protected static final float PIXEL_LINE_WIDTH=2F;
	
	protected SpriteBatch batch;
	
	protected ShapeRenderer shapeRenderer; //para las formas
	
	protected OrthographicCamera camera;
	
	protected BitmapFont font;
	
	protected Vector2 camPos = new Vector2(0,0);
	
	protected Integer fontSize, pixelsPerPoint;

	protected boolean fullScreen = true, firstRender=true;
	
	protected List<Float> domainPoints;
	
	protected List<RenderizableComponent> components = new ArrayList<>();
	
	//PARA DEBUG
	protected boolean debugOnScreen = true, listStadistics = true, drawGrid = true;
	
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
		
		afterCreate();
	}
	
	protected abstract void afterCreate();
	
	public void start() { }
	 
	@Override
	public void render () {
		if(firstRender) {
			firstRender=false;
			fillDomainPoints();
			fillFontSize();
			camera.setToOrtho(false, getDisplayResolution().x , getDisplayResolution().y);

			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		}
		
		doRender();
		
		if(debugOnScreen) {
			debugOnScreen();
		}
		
	    getInput();
	}
	
	protected abstract void doRender();
	
	public abstract void addRenderizable(Renderizable renderizable) ;
	
	public abstract void addRenderizables(Collection<? extends Renderizable> renderizables);
	
	public abstract void addRenderizables(RenderizableComponent component);
	
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
		
		if(isKeyJustPressed(Keys.F12)) {
			takeScreenShot();
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

	
	public List<Stadistic> getStadistics() {
		List<Stadistic> stats = new ArrayList<>();
		stats.add(new Stadistic("Gdx.gfx.FPS&deltaTime",String.valueOf(Gdx.graphics.getFramesPerSecond())));
		stats.add(new Stadistic(this,"FontSize",String.valueOf(fontSize)));
		stats.add(new Stadistic(this,"#PixelsPerPoint",pixelsPerPoint));
		stats.add(new Stadistic(this,"DisplayPixRes (W,H)",getDisplayResolution()));
		stats.add(new Stadistic(this,"CamPos____ (W,H)",getCamPos()+">"+getCamPos()));
		stats.add(new Stadistic(this,"CamTopLeft (W,H)",getCamTopLeft()+">"+getCamTopLeft()));
		stats.add(new Stadistic(this,"DisplayPointerPos (x,y)",beautifyVector(getDisplayPointerPos(),SIGNIFICATIVE_DECIMALS)));
		stats.add(new Stadistic(this,"Coordinates____ + (x,y)",beautifyVector(transformToCoordinates(getDisplayPointerPos()), SIGNIFICATIVE_DECIMALS)));
		stats.add(new Stadistic(" - "," - - - - - - - - - - - "));
		return stats;
	}
	
	private void debugOnScreen() {
		batch.begin();
	
		if(listStadistics) {
			String statsStr = String.join("\n",getStadistics().stream().map(Stadistic::toString).collect(Collectors.toList()));
			font.draw(batch, statsStr, getCamTopLeft().x, getCamTopLeft().y);

			for(int i=0; i<components.size(); i++) {
				if(components.get(i)!=null) {
					BitmapFont tempFont = FontUtils.getOnScreenFont(fontSize, components.get(i).getColor());
					tempFont.draw(batch, (i+1) +" : "+components.get(i), getCamTopLeft().x, getCamTopLeft().y-(i+getStadistics().size())*fontSize);
				}
			}
			
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
	
	protected Vector2 getDisplayResolution() {
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
	
	public List<Float> getDomainPoints(){
		return domainPoints;
	}
	
	private void fillDomainPoints() {
		if(domainPoints== null) {
			List<Float> result = new ArrayList<>();
			
			for(int i=0; i< (int)getDisplayResolution().x*.6/pixelsPerPoint; i++) {
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
	
	public void takeScreenShot() {
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

		// this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
		for(int i = 4; i < pixels.length; i += 4) {
		    pixels[i - 1] = (byte) 255;
		}

		Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
		PixmapIO.writePNG(Gdx.files.external("mypixmap.png"), pixmap);
		pixmap.dispose();
	}
}
