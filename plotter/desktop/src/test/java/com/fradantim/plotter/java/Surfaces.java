package com.fradantim.plotter.java;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.fradantim.plotter.core.AwarePlotter;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.core.Threads.TaskGenerator;
import com.fradantim.plotter.core.renderizable.Circle;
import com.fradantim.plotter.core.renderizable.Cross;
import com.fradantim.plotter.core.renderizable.Quadrilateral;
import com.fradantim.plotter.core.renderizable.Triangle;
import com.fradantim.plotter.core.renderizable.generator.TriangleGenerator;
import com.fradantim.plotter.core.util.FileSystemUtil.AppProperty;


public class Surfaces {

	public static void main(String args[]) throws IOException {
		Triangle t = TriangleGenerator.simpleTriangle(new Vector2(0, 0), new Vector2(-2,0), new Vector2(0,-2));
		System.out.println(t+" "+t.getArea());
		
		Quadrilateral q = new Quadrilateral(new Vector2(0, 0), new Vector2(1,0), new Vector2(1,1), new Vector2(0,1));
		q.setFilled(false);
		
		Quadrilateral q2 = new Quadrilateral(new Vector2(2, 0), new Vector2(2,1), new Vector2(3,3), new Vector2(3,0));
		System.out.println(q+" "+q.getArea());
		
		Plotter p = new AwarePlotter();
		p.setPixelsPerPoint((Integer) AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(p, config);

		try {
			p.addColorRunnable(TaskGenerator.getSimplePipeTaskTask(p, t));
			p.addColorRunnable(TaskGenerator.getSimplePipeTaskTask(p, q));
			p.addColorRunnable(TaskGenerator.getSimplePipeTaskTask(p, q2));
			
			Circle circleA = new Circle(new Vector2(-1,2), 0.5F);
			Circle circleB = new Circle(new Vector2(1,2), 0.5F);
			
			circleA.setFilled(false);
			circleB.setFilled(true);
			
			p.addColorRunnable(TaskGenerator.getSimplePipeTaskTask(p, circleA));
			p.addColorRunnable(TaskGenerator.getSimplePipeTaskTask(p, circleB));
			
			Cross cross = new Cross(new Vector2(-1,1), 0.5F);
			p.addColorRunnable(TaskGenerator.getSimplePipeTaskTask(p, cross));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
