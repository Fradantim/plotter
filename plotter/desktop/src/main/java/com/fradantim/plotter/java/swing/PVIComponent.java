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

public abstract class PVIComponent implements ComponentGenerator{
	
	protected static final List<String> vars=Arrays.asList("t","x");

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
	protected Color color;
	
	protected abstract String getName();
	
	protected JComboBox<Color> colorBox = ColorComboBox.getComboBox();
	


	protected JButton addButon = new JButton("Agregar");
	
	@Override
	public Component getComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new SizeableGridLayout(0,2));
		panel.add(new JLabel(""));
		panel.add(new JLabel(getName()+" Solo ingrese h o N, no ambos."));
		
		panel.add(functionLabel);
		panel.add(functionTF);
		
		panel.add(t0Label);
		panel.add(t0TF);
		
		panel.add(x0Label);
		panel.add(x0TF);
		
		panel.add(TLabel);
		panel.add(TTF);
		
		panel.add(hLabel);
		panel.add(hTF);
		
		panel.add(NLabel);
		panel.add(NTF);
		
		panel.add(addButon);
		panel.add(colorBox);
		
		return panel;
	}
	
	public static PVIComponent getEulerPVI() {
		return new EulerPVIPanel();
	}
	
	public static PVIComponent getImprovedEulerPVI() {
		return new ImprovedEulerPVIPanel();
	}
	
	public static PVIComponent getRungeKuttaEulerPVI() {
		return new RungeKuttaPVIPanel();
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
		
		color=(Color)colorBox.getSelectedItem();
	}
}

final class EulerPVIPanel extends PVIComponent{

	@Override
	protected String getName() { return "Metodo de Euler.";}
	
	{
		addButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					MainWindow.addColorRunnable(TaskGenerator.getEulerPVI(null, vars, function, t0, x0, T, h, N, Colorizer.awtColorTobadLogicColor(color)));
					MainWindow.drawMainWindow();
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					 ex.printStackTrace();
				}
			}
		});
	}
}

final class ImprovedEulerPVIPanel extends PVIComponent{

	@Override
	protected String getName() { return "Metodo de Euler Mejorado.";}
	
	{
		addButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					MainWindow.addColorRunnable(TaskGenerator.getImprovedEulerPVI(null, vars, function, t0, x0, T, h, N, Colorizer.awtColorTobadLogicColor(color)));
					MainWindow.drawMainWindow();
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					 ex.printStackTrace();
				}
			}
		});
	}
}

final class RungeKuttaPVIPanel extends PVIComponent{

	@Override
	protected String getName() { return "Metodo de Runge Kutta de 4to orden.";}
	
	{
		addButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					MainWindow.addColorRunnable(TaskGenerator.getRungeKuttaPVI(null, vars, function, t0, x0, T, h, N, Colorizer.awtColorTobadLogicColor(color)));
					MainWindow.drawMainWindow();
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					 ex.printStackTrace();
				}
			}
		});
	}
}