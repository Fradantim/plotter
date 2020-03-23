package com.fradantim.plotter.java.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fradantim.plotter.core.Threads.TaskGenerator;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class SimpleFunctionComponent implements ComponentGenerator{
	
	private static final List<String> vars=Arrays.asList("t");

	private JTextField functionTF = new JTextField();
		
	private JLabel functionLabel = new JLabel("f(t): ");
	private JLabel derLabel = new JLabel("(dt/dx):");
	
	private String function;
	private Integer derivations;
	private Color color;
	
	private JComboBox<Color> colorBox = ColorComboBox.getComboBox();
	private JComboBox<Integer> derBox = new JComboBox<Integer>();
	{
		derBox.setEditable(false);
		derBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0,1,2,3,4}));
	}
	


	protected JButton addButon = new JButton("Agregar");
	
	{
		addButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					MainWindow.addColorRunnable(TaskGenerator.getSimpleFunctionTask(null, vars, function, derivations, Colorizer.AwtColorTobadLogicColor(color)));
					MainWindow.drawMainWindow();
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					 ex.printStackTrace();
				}
			}
		});
	}
	
	public Component getComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new SizeableGridLayout(0,2));
		panel.add(new JLabel(""));
		panel.add(new JLabel("Funcion simple R->R."));
		
		panel.add(functionLabel);
		panel.add(functionTF);
		
		panel.add(derLabel);
		panel.add(derBox);
		
		panel.add(addButon);
		panel.add(colorBox);
		
		return panel;
	}
	
	public static SimpleFunctionComponent getSimpleFunctionWindow() {
		return new SimpleFunctionComponent();
	}
	
	private void retrieveValues(){
		function = functionTF.getText();
		
		if(function == null || function.isEmpty()) {
			throw new IllegalArgumentException("<html>Error, no se pudo recuperar el valor de f.</html>");
		}
		
		derivations=(Integer)derBox.getSelectedItem();
		color=(Color)colorBox.getSelectedItem();
	}
}
