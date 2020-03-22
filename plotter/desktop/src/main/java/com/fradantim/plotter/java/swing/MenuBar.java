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
		JMenu inicio, menuPVI, menuIN;  
	    JMenuItem graficar, menuOpciones, menuAyuda, 
	    	pviEuler, pviEulerMej, pviRungeKutta,
	    	inTrapecios, inMonteCarlo;
	     
	    menuPVI=new JMenu("Problema de Valor Inicial");
	    pviEuler=new JMenuItem("Metodo de Euler");
	    pviEuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.drawMainWindow(PVIComponent.getEulerPVI().getPVIWindow());
			}
		});
	    pviEulerMej=new JMenuItem("Metodo de Euler Mejorado");  
	    pviRungeKutta=new JMenuItem("Metodo de Runge-Kutta de orden 4"); 
	    
	    menuPVI.add(pviEuler); menuPVI.add(pviEulerMej); menuPVI.add(pviRungeKutta);
	    
	    menuIN=new JMenu("Integracion Numerica");
	    inTrapecios=new JMenuItem("Trapecio");  
	    inMonteCarlo=new JMenuItem("MonteCarlo");  
	    
	    menuIN.add(inTrapecios); menuIN.add(inMonteCarlo);
	    
	    graficar=new JMenuItem("Graficar");
	    graficar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlotterDesktop.pipeline(MainWindow.getJobs());
				
			}
		});
	    
	    
	    menuOpciones=new JMenuItem("Opciones");
	    menuAyuda=new JMenuItem("Ayuda");
	    
	    menuBar.add(graficar);
	    menuBar.add(menuPVI); 
	    menuBar.add(menuIN);
	    menuBar.add(menuOpciones);
	    menuBar.add(menuAyuda);
	    return menuBar;
	}
}
