package com.fradantim.plotter.java.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fradantim.plotter.core.Threads.TaskGenerator;

public abstract class PVIComponent {

	protected JTextField functionTF = new JTextField();
	protected JTextField t0TF = new JTextField();
	protected JTextField x0TF = new JTextField();
	protected JTextField TTF = new JTextField();
	protected JTextField hTF = new JTextField();
	protected JTextField NTF = new JTextField();
	
	protected JLabel functionLabel = new JLabel("f(t,x): ");
	protected JLabel t0Label = new JLabel("<html>t<sub>0</sub></html>: ");
	protected JLabel x0Label = new JLabel("<html>x<sub>0</sub></html>: ");
	protected JLabel TLabel = new JLabel("T: ");
	protected JLabel hLabel = new JLabel("h: ");
	protected JLabel NLabel = new JLabel("N: ");
	
	protected String function;
	protected Float t0;
	protected Float x0;
	protected Float T;
	protected Float h;
	protected Integer N;
	
	protected abstract String getName();
	
	//TODO el combo de colores
	/*
	   JComboBox cmb = new JComboBox();
	    cmb.setEditable(true);
	    cmb.setEditor(new WComboBoxEditor(getContentPane().getBackground()));

	    // To change the arrow button's background        
	    cmb.setUI(new BasicComboBoxUI(){
	        protected JButton createArrowButton()
	        {
	            BasicArrowButton arrowButton = new BasicArrowButton(BasicArrowButton.SOUTH, null, null, Color.GRAY, null);
	            return arrowButton;
	        }
	    });
	    cmb.setModel(new DefaultComboBoxModel(new String[] { "a", "b", "c" }));
	*/
	protected JButton addButon = new JButton("Agregar");
	
	public Component getPVIWindow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(functionLabel, BorderLayout.CENTER);
		panel.add(functionTF, BorderLayout.CENTER);
		
		panel.add(t0Label, BorderLayout.CENTER);
		panel.add(t0TF, BorderLayout.CENTER);
		
		panel.add(x0Label, BorderLayout.CENTER);
		panel.add(x0TF, BorderLayout.CENTER);
		
		panel.add(TLabel, BorderLayout.CENTER);
		panel.add(TTF, BorderLayout.CENTER);
		
		panel.add(hLabel, BorderLayout.CENTER);
		panel.add(hTF, BorderLayout.CENTER);
		
		panel.add(NLabel, BorderLayout.CENTER);
		panel.add(NTF, BorderLayout.CENTER);
		
		//panel.add(t0Label, BorderLayout.CENTER);
		panel.add(addButon, BorderLayout.CENTER);
		
		return panel;
	}
	
	public static PVIComponent getEulerPVI() {
		return new EulerPVIPanel();
	}
	
	protected void retrieveValues(){
		function = functionTF.getText();
		
		if(function == null || function.isEmpty()) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de f.</html>");
		}
		
		try {
			 t0= Float.parseFloat(t0TF.getText());
		} catch (NumberFormatException | NullPointerException e) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de t<sub>0</sub>.</html>");
		}
		
		try {
			x0= Float.parseFloat(x0TF.getText());
		} catch (NumberFormatException | NullPointerException e) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de x<sub>0</sub>.</html>");
		}
		
		try {
			T= Float.parseFloat(TTF.getText());
		} catch (NumberFormatException | NullPointerException e) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de T.</html>");
		}
		
		try {
			h= Float.parseFloat(hTF.getText());
		} catch (NumberFormatException | NullPointerException e) {
			try {
				N= Integer.parseInt(NTF.getText());
			} catch (NumberFormatException | NullPointerException e1) {
				throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de h ni N.</html>");
			}
		}
		
		if(h!= null && N!= null) {
			throw new IllegalArgumentException("<html>Error, no puede cargarse tanto T como h, solo una debe cargarse.</html>");
		}
	}
}

final class EulerPVIPanel extends PVIComponent{

	@Override
	protected String getName() { return "Metodo de Euler. Solo cargue h o N, no ambos.";}
	
	{
		addButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					MainWindow.addColorRunnable(TaskGenerator.getEulerPVI(null, Arrays.asList("t","x"), function, t0, x0, T, h, N));
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					 ex.printStackTrace();
				}
				
				MainWindow.drawMainWindow();
			}
		});
		
	}
}