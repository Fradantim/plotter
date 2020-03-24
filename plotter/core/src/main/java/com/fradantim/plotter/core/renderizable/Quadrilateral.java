package com.fradantim.plotter.core.renderizable;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class Quadrilateral implements Surface{

	private Vector2 pointA;
	private Vector2 pointB;
	private Vector2 pointC;
	private Vector2 pointD;
	private Color color;
	
	public Quadrilateral(Vector2 pointA, Vector2 pointB, Vector2 pointC, Vector2 pointD, Color color) {
		this.pointA = new Vector2(pointA);
		this.pointB = new Vector2(pointB);
		this.pointC = new Vector2(pointC);
		this.pointD = new Vector2(pointD);
		this.color = color;
	}
	
	public Quadrilateral(Vector2 pointA, Vector2 pointB, Vector2 pointC, Vector2 pointD) {
		this(pointA, pointB, pointC, pointD, Colorizer.DEFAULT_COLOR);
	}

	@Override
	public void render(ShapeRenderer shapeRenderer, Color color) {
        for(Triangle t: getInnerTriangles()) {
        	t.setColor(color);
        	t.render(shapeRenderer);
        }
	}
	
	@Override
	public void render(Pixmap pixmap, Color color) {
		for(Triangle t: getInnerTriangles()) {
        	t.setColor(color);
        	t.render(pixmap);
        }
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" A:"+pointA+", B:"+pointB+", C:"+pointC+", D:"+pointD;
	}
	
	public Vector2 getPointA() {
		return pointA;
	}

	public Vector2 getPointB() {
		return pointB;
	}
	
	public Vector2 getPointC() {
		return pointC;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public void setColor(Color color) {
		this.color=color;		
	}
	
	@Override
	public void move(Vector2 vector) {
		for(Vector2 point: Arrays.asList(pointA,pointB,pointC,pointD))
			point.add(vector);
	}

	@Override
	public void scale(Float scale) {
		for(Vector2 point: Arrays.asList(pointA,pointB,pointC,pointD))
			point.scl(scale);
	}

	@Override
	public Quadrilateral getCopy() {
		return new Quadrilateral(pointA.cpy(), pointB.cpy(), pointC.cpy(), pointD.cpy());
	}

	@Override
	public boolean collidesWith(Rectangle rect) {
		return rect.contains(pointA)||rect.contains(pointB)||rect.contains(pointC)||rect.contains(pointD);
	}

	@Override
	public Double getArea() {
		Double res=0D;
		for(Triangle t: getInnerTriangles()) {
			res+=t.getArea();
		}
		return res;
	}

	private Double getIntersectionX(Line lineA, Line lineB) {
		try {
			Double d = (lineB.getB()-lineA.getB())/(lineA.getM()-lineB.getM());
			return d;
		} catch (ArithmeticException e) {
			return null;
		}
	}
	
	private List<Triangle> getInnerTriangles(){
		//build a point E from the intersection of the two diagonal lines
		Line lineA, lineB;
		lineA= new Line(pointA, pointC);
		lineB= new Line(pointB, pointD);
		
		Double x= getIntersectionX(lineA, lineB);
		
		if(x!=null) {
			//Quadrilateral is ABCD
			
			Triangle triABC= new Triangle(pointA, pointB, pointC);
			Triangle triACD= new Triangle(pointA, pointC, pointD);
			
			return Arrays.asList(triABC,triACD);
			
		} else {
			//I chose two parallel lines... those don't intersect... my bad...
			//Quadrilateral is ABDC
			lineA= new Line(pointA, pointD);
			lineB= new Line(pointB, pointC);
			
			x= getIntersectionX(lineA, lineB);
			
			if(x!=null) {
			
				Triangle triABD= new Triangle(pointA, pointB, pointD);
				Triangle triACD= new Triangle(pointA, pointC, pointD);
				
				return Arrays.asList(triABD,triACD);
				
			} else {
				//I may not be as good at this as I thought I was...
				//Quadrilateral is ACBD
				Triangle triACB= new Triangle(pointA, pointC, pointB);
				Triangle triADB= new Triangle(pointB, pointD, pointB);
				
				return Arrays.asList(triACB,triADB);
			}
		}
	}
}
