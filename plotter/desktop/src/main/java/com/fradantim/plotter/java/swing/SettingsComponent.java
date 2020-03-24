package com.fradantim.plotter.java.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.fradantim.plotter.core.util.FileSystemUtil.AppProperty;

public class SettingsComponent implements ComponentGenerator{

	private  SpinnerNumberModel pixelsPerPointNumberModel = new SpinnerNumberModel((int)AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue(),1,2000,1);
	private  SpinnerNumberModel pixelLineWidthNumberModel = new SpinnerNumberModel((int)AppProperty.PLOTTER_PIXEL_LINE_WIDTH.getCurrentValue(),1,2000,1);
	
	private JComboBox<Boolean> fullScreenBox = new JComboBox<Boolean>();
	private JSpinner pixelsPerPointSpinner = new JSpinner(pixelsPerPointNumberModel);
	private JSpinner pixelLineWidthSpinner = new JSpinner(pixelLineWidthNumberModel);
	private JComboBox<Color> backgroundColorBox = ColorComboBox.getComboBox();
	private JComboBox<Color> defaultColorBox = ColorComboBox.getComboBox();
	
	private JLabel fullScreenLabel = new JLabel("Pantalla Completa");
	private JLabel pixelsPerPointLabel = new JLabel("# pixeles por entero");
	private JLabel pixelLineWidthLabel = new JLabel("Ancho en pixeles de una linea");
	private JLabel backgroundColorLabel = new JLabel("Color de fondo");
	private JLabel defaultColorLabel = new JLabel("Color generico");
	
	private Boolean fullScreen;
	private Integer pixelsPerPoint;
	private Integer pixelLineWidth;
	private Color backgroundColor;
	private Color defaultColor;
	
	{
		fullScreenBox.setEditable(false);
		fullScreenBox.setModel(new DefaultComboBoxModel<Boolean>(new Boolean[] {true, false}));
	}
	
	protected JButton applyButon = new JButton("Aplicar");
	
	{
		fullScreenBox.setSelectedItem(AppProperty.PLOTTER_FULL_SCREEN.getCurrentValue());
		//pixelsPerPointSpinner.setValue(AppProperty.PLOTTER_PIXELS_PER_POINT.getCurrentValue());
		//pixelLineWidthSpinner.setValue(AppProperty.PLOTTER_PIXEL_LINE_WIDTH.getCurrentValue());
		backgroundColorBox.setSelectedItem(AppProperty.PLOTTER_BACKGROUND_COLOR.getCurrentValue());
		defaultColorBox.setSelectedItem(AppProperty.COLORIZER_DEFAULT_COLOR.getCurrentValue());
				
		applyButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					retrieveValues();
					AppProperty.COLORIZER_DEFAULT_COLOR.changeValue(defaultColor);
					AppProperty.PLOTTER_BACKGROUND_COLOR.changeValue(backgroundColor);
					AppProperty.PLOTTER_FULL_SCREEN.changeValue(fullScreen);
					AppProperty.PLOTTER_PIXEL_LINE_WIDTH.changeValue(pixelLineWidth);
					AppProperty.PLOTTER_PIXELS_PER_POINT.changeValue(pixelsPerPoint);
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
		panel.add(new JLabel(" -> "));
		panel.add(new JLabel("Opciones"));
		
		panel.add(fullScreenLabel);
		panel.add(fullScreenBox);
		
		panel.add(pixelLineWidthLabel);
		panel.add(pixelLineWidthSpinner);
		
		panel.add(pixelsPerPointLabel);
		panel.add(pixelsPerPointSpinner);
		
		panel.add(backgroundColorLabel);
		panel.add(backgroundColorBox);
		
		panel.add(defaultColorLabel);
		panel.add(defaultColorBox);
		
		panel.add(applyButon);
		panel.add(new JLabel(" <- "));
		
		return panel;
	}
	
	public static SettingsComponent getSettingsComponent() {
		return new SettingsComponent();
	}
	
	private void retrieveValues(){
		try {
			fullScreen = Boolean.parseBoolean(fullScreenBox.getSelectedItem().toString());
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR: No se pudo recuperar el valor de "+fullScreenLabel.getText()+".");
		}
		
		try {
			pixelsPerPoint = Integer.parseInt(pixelsPerPointSpinner.getValue().toString());
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR: No se pudo recuperar el valor de "+pixelsPerPointLabel.getText()+".");
		}
		
		try {
			pixelLineWidth = Integer.parseInt(pixelLineWidthSpinner.getValue().toString());
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR: No se pudo recuperar el valor de "+pixelLineWidthLabel.getText()+".");
		}
		
		try {
			backgroundColor = (Color)backgroundColorBox.getSelectedItem();
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR: No se pudo recuperar el valor de "+backgroundColorLabel.getText()+".");
		}
		
		try {
			defaultColor = (Color)defaultColorBox.getSelectedItem();
		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR: No se pudo recuperar el valor de "+defaultColorLabel.getText()+".");
		}
	}
}
