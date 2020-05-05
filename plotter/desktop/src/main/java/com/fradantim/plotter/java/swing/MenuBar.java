package com.fradantim.plotter.java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fradantim.plotter.core.AwarePlotter;
import com.fradantim.plotter.core.Plotter;
import com.fradantim.plotter.core.util.FileSystemUtil;
import com.fradantim.plotter.core.util.FileSystemUtil.AppProperty;

public class MenuBar {
	
	private static final String EXIT_NOTIFICATION="<html>Recuerde. Debe presionar <b>[ESCAPE]</b> para salir del mainframe.<br> "
			+ "<br>"
			+ "<i>(si no se rompio algo)</i></html>";		

	public static JMenuBar buildMenuBar(JFrame frame) {
		JMenuBar menuBar=new JMenuBar(); 
	    menuBar.add(getStartMenu());
	    menuBar.add(getAddGraphMenu()); 
	    return menuBar;
	}
	
	public static JMenu getStartMenu() {
		JMenu start=new JMenu("Inicio");
		JMenuItem graph, settings, help;
		
		graph=new JMenuItem("Graficar");
		graph.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, EXIT_NOTIFICATION);
				
				Plotter p = new AwarePlotter();
				p.setPixelsPerPoint((Integer)AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
				LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
				new LwjglApplication(p, config);
				
				while (!p.readyToRender()) {
					System.out.println(Thread.currentThread().getId()+": Waiting for plotter to be ready to render.");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
				p.addColorRunnables(MainWindow.getJobs());
				
				FileSystemUtil.saveLastJobs(MainWindow.getJobs());
			}
		});
	    
		settings=new JMenuItem("Opciones");
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(SettingsComponent.getSettingsComponent().getComponent());
			}
		});
		help=new JMenuItem("Ayuda");
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(HelpComponent.getSettingsComponent().getComponent());
			}
		});
		
		start.add(graph);
		start.add(settings);
		start.add(help);
		
		return start;
	}

	public static JMenu getAddGraphMenu() {
		JMenu addGraphMenu;  
		
		addGraphMenu=new JMenu("Agregar grafico");
		
		JMenuItem addSimpleFunction= new JMenuItem("Funcion Simple");
		addSimpleFunction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MainWindow.drawMainWindow(SimpleFunctionComponent.getSimpleFunctionWindow().getComponent());
				}
			});
		
		addGraphMenu.add(addSimpleFunction);
		addGraphMenu.add(getAddGraphPVISubMenu());
		addGraphMenu.add(getAddGraphINSubMenu());
	    
	    return addGraphMenu;
	}
	
	public static JMenu getAddGraphPVISubMenu() {
		JMenu subMenuPVI;
		JMenuItem pviEuler, pviEulerMej, pviRungeKutta;
		
		subMenuPVI=new JMenu("Problema de Valor Inicial");
	    pviEuler=new JMenuItem("Metodo de Euler");
	    pviEuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(PVIComponent.getEulerPVI().getComponent());
			}
		});
	    
	    pviEulerMej=new JMenuItem("Metodo de Euler Mejorado");  
	    pviEulerMej.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(PVIComponent.getImprovedEulerPVI().getComponent());
			}
		});
	    
	    pviRungeKutta=new JMenuItem("Metodo de Runge-Kutta de orden 4"); 
	    pviRungeKutta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(PVIComponent.getRungeKuttaEulerPVI().getComponent());
			}
		});
	    
	    subMenuPVI.add(pviEuler); subMenuPVI.add(pviEulerMej); subMenuPVI.add(pviRungeKutta);
	    
	    return subMenuPVI;
	}
	

	public static JMenu getAddGraphINSubMenu() {
		JMenu subMenuIN;
		JMenuItem inTrapecios, inMonteCarlo;
		
		subMenuIN=new JMenu("Integracion Numerica");
		inTrapecios=new JMenuItem("Trapecio");  
		inTrapecios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(INComponent.getTrapezoidsIN().getComponent());
			}
		});
		
		inMonteCarlo=new JMenuItem("MonteCarlo");
		inMonteCarlo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(INComponent.getRandomIN().getComponent());
			}
		});
		
		subMenuIN.add(inTrapecios);
		subMenuIN.add(inMonteCarlo);
		
		return subMenuIN;
	}
	
}

