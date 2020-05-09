package com.fradantim.plotter.java.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fradantim.plotter.core.Threads.TaskGenerator;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public abstract class INComponent implements ComponentGenerator{
	
	protected static final List<String> vars=Arrays.asList("t");

	protected JTextField functionTF = new JTextField();
	protected JTextField aTF = new JTextField();
	protected JTextField bTF = new JTextField();
	protected JTextField hTF = new JTextField();
	protected JTextField NTF = new JTextField();
	
	protected JLabel functionLabel = new JLabel("f(t): ");
	protected JLabel aLabel = new JLabel("a: ");
	protected JLabel bLabel = new JLabel("b: ");
	protected JLabel TLabel = new JLabel("T: ");
	protected JLabel hLabel = new JLabel("h: ");
	protected JLabel NLabel = new JLabel("N: ");
	
	protected String function;
	protected Float a;
	protected Float b;
	protected Float h;
	protected Integer N;
	protected Color color;
	
	protected abstract String getName();
	
	protected JComboBox<Color> colorBox = ColorComboBox.getComboBox();
	


	protected JButton addButon = new JButton("Agregar");
	
	@Override
	public Component getComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new SizeableGridLayout(0,2));
		panel.add(new JLabel(""));
		panel.add(new JLabel(getName()));
		
		panel.add(functionLabel);
		panel.add(functionTF);
		
		panel.add(aLabel);
		panel.add(aTF);
		
		panel.add(bLabel);
		panel.add(bTF);
		
		panel.add(hLabel);
		panel.add(hTF);
		
		panel.add(NLabel);
		panel.add(NTF);
		
		panel.add(addButon);
		panel.add(colorBox);
		
		return panel;
	}
	
	public static INComponent getTrapezoidsIN() {
		return new TrapezoidsINPanel();
	}
	
	public static INComponent getRandomIN() {
		return new RandomINPanel();
	}
	
	
	
	protected void retrieveValues(){
		function = functionTF.getText();
		
		if(function == null || function.isEmpty()) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de f.</html>");
		}
		
		try {
			 a= Float.parseFloat(aTF.getText());
		} catch (NumberFormatException | NullPointerException e) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de a.</html>");
		}
		
		try {
			b= Float.parseFloat(bTF.getText());
		} catch (NumberFormatException | NullPointerException e) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de b.</html>");
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
		
		color=(Color)colorBox.getSelectedItem();
	}
}

final class TrapezoidsINPanel extends INComponent{

	@Override
	protected String getName() { return "Metodo por Trapezoides. Solo ingrese h o N, no ambos.";}
	
	{
		addButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					MainWindow.addColorRunnable(TaskGenerator.getTrapezoidsIN(null, vars, function, a, b, h, N, Colorizer.awtColorTobadLogicColor(color)));
					MainWindow.drawMainWindow();
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					 ex.printStackTrace();
				}
			}
		});
	}
}

final class RandomINPanel extends INComponent{

	@Override
	protected String getName() { return "Metodo inimputable.";}
	
	{
		hLabel.setVisible(false);
		hTF.setVisible(false);
		addButon.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					MainWindow.addColorRunnable(TaskGenerator.getRandomIN(null, vars, function, a, b, N, Colorizer.awtColorTobadLogicColor(color)));
					MainWindow.drawMainWindow();
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					 ex.printStackTrace();
				}
			}
		});
	}
}
