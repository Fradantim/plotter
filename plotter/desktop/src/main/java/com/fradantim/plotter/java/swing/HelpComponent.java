package com.fradantim.plotter.java.swing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class HelpComponent implements ComponentGenerator{
	
	private final static JPanel panel = new JPanel();
	
	static {
		panel.setLayout(new SizeableGridLayout(0,2));
		
		JPanel innerPanel= new JPanel();
		innerPanel.setLayout(new SizeableGridLayout(0,1));
		
		innerPanel.add(labelInBlack("Ayuda"));
		
		innerPanel.add(getKeysPanel());
		
		innerPanel.add(getConstantsPanel());	
		
		innerPanel.add(getSimpleOperationsPanel());
		
		panel.add(innerPanel);

		panel.add(getOperationsPanel());
	}
	
	private static JPanel getKeysPanel() {
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new SizeableGridLayout(0,1));
		keyPanel.add(labelInBlack("Botones útiles en pantalla completa"));
		
		JPanel keyInnerPanel = new JPanel();
		keyInnerPanel.setLayout(new SizeableGridLayout(0,2));
		
		keyInnerPanel.add(labelInBlack("[ESCAPE]"));
		keyInnerPanel.add(new JLabel("Salir de pantalla completa."));
		
		keyInnerPanel.add(labelInBlack("[F3]"));
		keyInnerPanel.add(new JLabel("Apagar / Mostrar texto en pantalla ."));
		
		keyInnerPanel.add(labelInBlack("[F4]"));
		keyInnerPanel.add(new JLabel("Cambiar pantalla completa / ventana."));
		
		keyInnerPanel.add(labelInBlack("[F6]"));
		keyInnerPanel.add(new JLabel("Aumentar tamaño de la letra ([SHIFT]+[F6] para achicar)."));
		
		keyInnerPanel.add(labelInBlack("[F12]"));
		keyInnerPanel.add(new JLabel("Realizar una captura de pantalla."));
		
		keyPanel.add(keyInnerPanel);
		keyPanel.add(new JSeparator(), BorderLayout.CENTER);
		
		return keyPanel;
	}
	
	private static JPanel getSimpleOperationsPanel() {
		JPanel simpleOpPanel = new JPanel();
		simpleOpPanel.setLayout(new SizeableGridLayout(0,1));
		simpleOpPanel.add(labelInBlack("Lista de operaciones simples"));
				
		JPanel keyInnerPanel = new JPanel();
		keyInnerPanel.setLayout(new SizeableGridLayout(0,2));
		
		keyInnerPanel.add(labelInHtml("a + b"));
		keyInnerPanel.add(labelInHtml(inBlack("Suma: ")+"a+b"));
		
		keyInnerPanel.add(labelInHtml("a - b"));
		keyInnerPanel.add(labelInHtml(inBlack("Resta: ")+"a-b"));
		
		keyInnerPanel.add(labelInHtml("a . b"));
		keyInnerPanel.add(labelInHtml(inBlack("Producto: ")+"a*b"));
		
		keyInnerPanel.add(labelInHtml("a / b"));
		keyInnerPanel.add(labelInHtml(inBlack("División: ")+"a/b"));
		
		simpleOpPanel.add(keyInnerPanel);
		
		simpleOpPanel.add(labelInBlack("Dividir por 0 no va a romper la aplicaión, se va a romper por sus propios medios."));
		
		simpleOpPanel.add(new JSeparator(), BorderLayout.CENTER);
		
		return simpleOpPanel;
	}
	
	private static JPanel getOperationsPanel() {
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new SizeableGridLayout(0,1));
		keyPanel.add(labelInBlack("Lista de operaciones Avanzadas"));
		
		JPanel keyInnerPanel = new JPanel();
		keyInnerPanel.setLayout(new SizeableGridLayout(0,2));
	
		keyInnerPanel.add(labelInHtml("a"+sup("n")));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Potencia: ") + "a elevado a la potencia n: <ul>" + 
						"  <li>potencia(a,n)</li>" + 
						"  <li>pot(a,n)</li>" + 
						"  <li>p(a,n)</li>" +
						"  <li>power(a,n)</li>" +
						"  <li>pwr(a,n)</li>" +
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("sin(a)"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Seno: ") + "a expresado en radianes: <ul>" + 
						"  <li>sin(a)</li>" + 
						"  <li>sen(a)</li>" + 
						"  <li>seno(a)</li>" +
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("cos(a)"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Coseno: ") + "a expresado en radianes: <ul>" + 
						"  <li>cos(a)</li>" + 
						"  <li>coseno(a)</li>" + 
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("tan(a)"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Tangente: ") + "a expresado en radianes: <ul>" + 
						"  <li>tan(a)</li>" + 
						"  <li>tangente(a)</li>" + 
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("log"+sub("10")+"(a)"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Logaritmo: ")+"logaritmo base 10 de a <ul>" + 
						"  <li>log(a)</li>" + 
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("log"+sub("b")+"(a)"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Logaritmo: ")+"logaritmo base b de a <ul>" + 
						"  <li>log(a,b)</li>" + 
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("ln(a)"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Logaritmo natural: ")+"logaritmo base e de a, e número de Euler <ul>" + 
						"  <li>ln(a)</li>" + 
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("|a|"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Módulo: ")+"absoluto de a <ul>" + 
						"  <li>abs(a)</li>" + 
						"</ul>")));
		
		keyPanel.add(keyInnerPanel);
		
		keyPanel.add(new JSeparator(), BorderLayout.CENTER);

		return keyPanel;
	}
	
	
	private static JPanel getConstantsPanel() {
		JPanel constPanel = new JPanel();
		constPanel.setLayout(new SizeableGridLayout(0,1));
		constPanel.add(labelInBlack("Lista de constantes"));
				
		JPanel keyInnerPanel = new JPanel();
		keyInnerPanel.setLayout(new SizeableGridLayout(0,2));
		
		keyInnerPanel.add(labelInHtml("&pi;"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("Pi: ") + "("+Math.PI+") <ul>" + 
						"  <li>pi</li>" + 
						"  <li>PI</li>" + 
						"</ul>")));
		
		keyInnerPanel.add(labelInHtml("e"));
		keyInnerPanel.add(new JLabel(inHTML( 
				inBlack("e: ") + "("+Math.E+") <ul>" + 
						"  <li>e</li>" + 
						"  <li>E</li>" + 
						"</ul>")));
		
		constPanel.add(keyInnerPanel);
		
		constPanel.add(new JSeparator(), BorderLayout.CENTER);

		return constPanel;
	}
	
	private static String inTag(String str, String tag) {
		return "<"+tag+">"+str+"</"+tag+">";
	}
	
	private static String inHTML(String str) {
		return inTag(str,"html");
	}
	
	private static String sub(String str) {
		return inTag(str,"sub");
	}
	
	private static String sup(String str) {
		return inTag(str,"sup");
	}
	
	private static String inBlack(String str) {
		return inTag(str,"b");
	}
	
	private static JLabel labelInHtml(String str) {
		return new JLabel(inHTML(str));
	}
	
	private static JLabel labelInBlack(String str) {
		return labelInHtml(inBlack(str));
	}
	
	
	
	public Component getComponent() {
		return panel;
	}
	
	public static HelpComponent getSettingsComponent() {
		return new HelpComponent();
	}
}
