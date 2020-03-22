package com.fradantim.plotter.java.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.fradantim.plotter.core.renderizable.generator.Colorizer;

public class ColorComboBox {

	public static JComboBox<Color> getComboBox(){
		JComboBox<Color> colorBox = new JComboBox<Color>();
		colorBox.setEditable(false);

		ActionListener action = new ChangeColorActionListener(colorBox);
		colorBox.addActionListener(action);
		colorBox.setModel(new DefaultComboBoxModel<Color>(Colorizer.getSimpleAwtColorsArray()));
		
		colorBox.setRenderer(new ColorCellRenderer());
		
		//load the first color;
		action.actionPerformed(null);
		
		return colorBox;
	}
}

final class ColorCellRenderer extends JLabel implements ListCellRenderer<Color>{
	private static final long serialVersionUID = 1L;
	
	public ColorCellRenderer() {
        super();
        setOpaque(true);
    }

	@Override
	public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		setText(value.toString());
		
		setBackground(value);
  		setForeground(value);
  		
        setBorder(null);
        return this;
	}
	
}

final class ChangeColorActionListener implements ActionListener{

	JComboBox<Color> comboBox;
	
	ChangeColorActionListener(JComboBox<Color> comboBox){
		this.comboBox=comboBox;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Color c = (Color) comboBox.getSelectedItem();
		comboBox.setBackground(c);
		comboBox.setForeground(c);
	}
}
