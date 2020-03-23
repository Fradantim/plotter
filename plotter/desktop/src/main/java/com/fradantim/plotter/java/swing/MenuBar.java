package com.fradantim.plotter.java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.fradantim.plotter.java.PlotterDesktop;

public class MenuBar {

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
				PlotterDesktop.pipeline(MainWindow.getJobs());
			}
		});
	    
		settings=new JMenuItem("Opciones");
		help=new JMenuItem("Ayuda");
		
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
		inMonteCarlo=new JMenuItem("MonteCarlo");
		
		subMenuIN.add(inTrapecios);
		subMenuIN.add(inMonteCarlo);
		
		return subMenuIN;
	}
	
}

