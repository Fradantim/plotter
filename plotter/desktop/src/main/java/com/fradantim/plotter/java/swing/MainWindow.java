package com.fradantim.plotter.java.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.fradantim.plotter.core.Threads.ColorRunnable;
import com.fradantim.plotter.core.renderizable.generator.Colorizer;
import com.fradantim.plotter.core.util.FileSystemUtil;

public class MainWindow{

	private static final JFrame frame = new JFrame("Plotter MyS");
	
	private static List<ColorRunnable> jobs = new ArrayList<>(); 
	
	public static void addColorRunnable(ColorRunnable c) {
		jobs.add(c);
	}
	
	public static List<ColorRunnable> getJobs(){
		return jobs;
	}
	
	static {
		/*List<String> vars=Arrays.asList("t");
		
		String functionA = "p(t,2)";
		String derivatedfunctionA = "2*t";
		
		List<String> eulerVars=Arrays.asList("t","x");
		
		jobs.add(TaskGenerator.getSimpleFunctionTask(null, vars, functionA, 0, Color.GREEN));
		jobs.add(TaskGenerator.getEulerPVI(null, eulerVars, derivatedfunctionA, 0F, 0F, 4F, 0.25F, null, Color.BLUE));
		*/
		jobs.addAll(FileSystemUtil.loadLastJobs());
	}
	
	private static Component drawList() {
		JPanel listJpanel = new JPanel();
		listJpanel.setLayout(new GridLayout(0,1));
		if(jobs.size()>0) {
			listJpanel.add(new Label("Elementos a cargar: "), BorderLayout.NORTH);
			JPanel inner = new JPanel();
			inner.setLayout(new SizeableGridLayout(0,2));
			for(int i=0; i<jobs.size(); i++) {
				JButton button = new JButton("X");
				button.setBackground(Colorizer.badLogicColorToAwtColor(jobs.get(i).getColor()));
				button.addActionListener(getButtonRemoveActionListener(i));
				inner.add(button);
				
				JLabel label = new JLabel(jobs.get(i).getFormattedName());
				inner.add(label);
				
				listJpanel.add(inner, BorderLayout.CENTER);
	        }
		} else {
			listJpanel.add(new Label("Elija elementos para cargar ^"), BorderLayout.NORTH);
		}
		
		//listJpanel.add(new JSeparator(), BorderLayout.CENTER);
		
		return listJpanel;
	}
	
	private static ActionListener getButtonRemoveActionListener(final Integer index) {
    	return new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	jobs.remove(jobs.get(index));
            	drawMainWindow();
            }
        };
    }
	
	public static void drawMainWindow() {
		drawMainWindow(null);
	}
	
	public static void drawMainWindow(Component newComponent) {
		frame.setVisible(false);
		Container pane = frame.getContentPane();
		pane.removeAll();
		pane.setLayout(new SizeableGridLayout(1,0));
		pane.add(drawList());
		if(newComponent!=null) {
			pane.add(newComponent);
		}
		
		frame.revalidate();
		frame.repaint();
		frame.pack();
        frame.setVisible(true);
	}
	
    
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFont(new Font("Dialog", 0, 20));
        frame.setJMenuBar(MenuBar.buildMenuBar(frame));  
        drawMainWindow();
    }
    
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            setUIFont (new javax.swing.plaf.FontUIResource("Courier",Font.PLAIN,16));
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration<?> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value instanceof javax.swing.plaf.FontUIResource)
            UIManager.put (key, f);
          }
        } 
}