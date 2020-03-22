package com.fradantim.plotter.java.swing;

 import java.awt.LayoutManager;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import javax.swing.JButton;
 import javax.swing.JFrame;
 import javax.swing.JOptionPane;
 import javax.swing.JTextArea;
 
 
 public class UiMainNS
   extends JFrame
   implements ActionListener
 {
   private static final long serialVersionUID = 1L;
   private static String version = "2.0.3";
   
   private String action1 = "Convertir Texto";
   private String action2 = "Generar Imagen";
   
   private JButton jbtngo1;
   
   private JButton jbtngo2;
   
   private JButton jBtnAct1;
   private JButton jBtnAct2;
   private String title0 = "Como una Navaja Suiza pero para las facturas de mainframe v" + version;
   private String title1 = "Conversor ASCII > EBCDIC(284)";
   private String title2 = "Generador de Imagenes";
   
   private JTextArea jtxtin1;
   
   private JTextArea jtxtout1;
   private JTextArea jtxtinput1;
   private JTextArea jtxtoutput1;
   private JTextArea jtxtlenght1;
   private JTextArea jtxtlenghtvar1;
   private String ToolTipInTxt = "<html>Texto en ASCII de entrada<br>Utilizar ruta completa al archivo o puede utilizar solo el nombre si el .jar se encuentra en el mismo directorio.</html>";
 
   
   private String ToolTipOutTxt = "<html>Texto en EBCDIC(284) de salida<br>Utilizar ruta completa al archivo o puede utilizar solo el nombre si el .jar se encuentra en el mismo directorio.</html>";
   
   private JTextArea jtxtPicInTxt;
   
   private JTextArea jtxtPicInBox;
   
   private JTextArea jtxtTemplateInTxt;
   
   private JTextArea jtxtTemplateInBox;
   
   private JTextArea jtxtOutputTxt;
   
   private JTextArea jtxtOutputBox;
   
   private JTextArea jtxtNameTxt;
   
   private JTextArea jtxtNameBox;
   
   private JTextArea jtxtPosXTxt;
   
   private JTextArea jtxtPosXBox;
   private JTextArea jtxtPosYTxt;
   private JTextArea jtxtPosYBox;
   private JTextArea jtxtSizeWTxt;
   private JTextArea jtxtSizeWBox;
   private JTextArea jtxtSizeHTxt;
   private JTextArea jtxtSizeHBox;
   private String ToolTipPic = "<html>Imagen que se pondra sobre el Template.<br>Se aceptan varios formatos de imagen<br>Utilizar ruta completa al archivo o puede utilizar solo el nombre si el .jar se encuentra en el mismo directorio.</html>";
 
   
   private String ToolTipTemplate = "<html>Imagen que se pondra de fondo.<br>El tamaño del template define el tamaño del archivo de salida<br>Se aceptan varios formatos de imagen<br>Se puede utilizar ruta completa al archivo o puede utilizar solo el nombre si el .jar se encuentra en el mismo directorio.</html>";
 
 
   
   private String ToolTipSalida = "<html>Directorio donde se depositaran las imagenes resultantes</html>";
   private String ToolTipName = "<html>Leyenda que se pondra en el template.<br>Puede estar en blanco.</html>";
   
   private String ToolTipPosX = "<html>Posicion X donde aparece la imagen.<br>Medida en pixeles!</html>";
   
   private String ToolTipPosY = "<html>Posicion Y donde aparece la imagen.<br>Medida en pixeles!</html>";
   
   private String ToolTipSizeW = "<html>Ancho que tomara la imagen.<br>Se mantienen proporciones<br>Medida en pixeles!</html>";
 
   
   private String ToolTipSizeH = "<html>Alto que tomara la imagen.<br>Se mantienen proporciones<br>Medida en pixeles!</html>";
 
 
 
   
   public void actionPerformed(ActionEvent e) {
     int x = getX(), y = getY();
     if (e.getSource() == this.jbtngo1) {
       empty();
       init();
       set1(x, y);
       addbtns();
     } 
     if (e.getSource() == this.jbtngo2) {
       empty();
       init();
       set2(x, y);
       addbtns();
     } 
     
     if (e.getSource() == this.jBtnAct1) {
       ASCII2EBCDIC();
     }
     if (e.getSource() == this.jBtnAct2) {
       Imagenerate();
     }
   }
   
   private void okMessage() {
     JOptionPane.showMessageDialog(null, "FIN!");
   }
   public void ASCII2EBCDIC() {
     String error = "No se puede escribir o leer archivos. \nExiste entrada? \nSe poseen permisos? \nVerificar que no estén tomados por otros procesos.\n";
     String libErr = "No se encuentran las librerias de traducción. Esta aplicación deberia correr en JAVA 1.5 o mayor.\n";
     JOptionPane.showMessageDialog(null, "Iniciando conversión.");
     //JOptionPane.showMessageDialog(null, String.valueOf(error) + ed.toString());
   }
 
   
   public void Imagenerate() {
     String error = "No se puede escribir o leer archivos. \nExiste entrada? \nSe poseen permisos? \nVerificar que no estén tomados por otros procesos.";
  
     JOptionPane.showMessageDialog(null, "Iniciando proceso.");
   }
 
 
   
   private void addbtns() {
     this.jbtngo1 = new JButton(this.action1);
     this.jbtngo1.setBounds(10, 10, 150, 40);
     this.jbtngo1.addActionListener(this);
     
     this.jbtngo2 = new JButton(this.action2);
     this.jbtngo2.setBounds(165, 10, 150, 40);
     this.jbtngo2.addActionListener(this);
     
     getContentPane().add(this.jbtngo1);
     getContentPane().add(this.jbtngo2);
   }
   
   private void set1(int x, int y) {
     setDefaultCloseOperation(3);
     setBounds(x, y, 500, 180);
     setLayout((LayoutManager)null);
     setTitle(this.title1);
     
     this.jtxtin1 = new JTextArea("Entrada");
     this.jtxtin1.setBounds(10, 60, 50, 20);
     this.jtxtin1.setEditable(false);
     this.jtxtin1.setToolTipText(this.ToolTipInTxt);
     
     this.jtxtinput1 = new JTextArea("in.txt");
     this.jtxtinput1.setBounds(70, 60, 400, 20);
     this.jtxtinput1.setToolTipText(this.ToolTipInTxt);
     
     this.jtxtout1 = new JTextArea("Salida");
     this.jtxtout1.setBounds(10, 90, 50, 20);
     this.jtxtout1.setEditable(false);
     this.jtxtout1.setToolTipText(this.ToolTipOutTxt);
     
     this.jtxtoutput1 = new JTextArea("out");
     this.jtxtoutput1.setBounds(70, 90, 400, 20);
     this.jtxtoutput1.setToolTipText(this.ToolTipOutTxt);
     
     this.jtxtlenght1 = new JTextArea("Largo");
     this.jtxtlenght1.setBounds(10, 120, 50, 20);
     this.jtxtlenght1.setEditable(false);
     
     this.jtxtlenghtvar1 = new JTextArea("1996");
     this.jtxtlenghtvar1.setBounds(70, 120, 45, 20);
     
     this.jBtnAct1 = new JButton(this.action1);
     this.jBtnAct1.setBounds(320, 120, 150, 20);
     this.jBtnAct1.addActionListener(this);
     
     getContentPane().add(this.jtxtin1);
     getContentPane().add(this.jtxtout1);
     getContentPane().add(this.jtxtinput1);
     getContentPane().add(this.jtxtoutput1);
     getContentPane().add(this.jtxtlenght1);
     getContentPane().add(this.jtxtlenghtvar1);
     getContentPane().add(this.jBtnAct1);
   }
 
   
   private void set2(int x, int y) {
     setDefaultCloseOperation(3);
     setBounds(x, y, 500, 280);
     setLayout((LayoutManager)null);
     setTitle(this.title2);
 
 
     
     this.jtxtPicInTxt = new JTextArea("Imagen");
     this.jtxtPicInTxt.setBounds(10, 60, 53, 20);
     this.jtxtPicInTxt.setEditable(false);
     this.jtxtPicInTxt.setToolTipText(this.ToolTipPic);
     
     this.jtxtPicInBox = new JTextArea("Foto.png");
     this.jtxtPicInBox.setBounds(70, 60, 400, 20);
     this.jtxtPicInBox.setToolTipText(this.ToolTipPic);
 
     
     this.jtxtTemplateInTxt = new JTextArea("Template");
     this.jtxtTemplateInTxt.setBounds(10, 90, 53, 20);
     this.jtxtTemplateInTxt.setEditable(false);
     this.jtxtTemplateInTxt.setToolTipText(this.ToolTipTemplate);
     
     this.jtxtTemplateInBox = new JTextArea("Template.png");
     this.jtxtTemplateInBox.setBounds(70, 90, 400, 20);
     this.jtxtTemplateInBox.setToolTipText(this.ToolTipTemplate);
 
     
     this.jtxtNameTxt = new JTextArea("Leyenda");
     this.jtxtNameTxt.setBounds(10, 120, 53, 20);
     this.jtxtNameTxt.setEditable(false);
     this.jtxtNameTxt.setToolTipText(this.ToolTipName);
     
     this.jtxtNameBox = new JTextArea("Inserte leyenda aqui (o borre esto).");
     this.jtxtNameBox.setBounds(70, 120, 400, 20);
     this.jtxtNameBox.setToolTipText(this.ToolTipName);
 
     
     this.jtxtOutputTxt = new JTextArea("Salida");
     this.jtxtOutputTxt.setBounds(10, 150, 53, 20);
     this.jtxtOutputTxt.setEditable(false);
     this.jtxtOutputTxt.setToolTipText(this.ToolTipSalida);
     
     this.jtxtOutputBox = new JTextArea("fout/");
     this.jtxtOutputBox.setBounds(70, 150, 400, 20);
     this.jtxtOutputBox.setToolTipText(this.ToolTipSalida);
 
     
     this.jtxtPosXTxt = new JTextArea("PosX");
     this.jtxtPosXTxt.setBounds(10, 180, 53, 20);
     this.jtxtPosXTxt.setEditable(false);
     this.jtxtPosXTxt.setToolTipText(this.ToolTipPosX);
     
     this.jtxtPosXBox = new JTextArea("17");
     this.jtxtPosXBox.setBounds(70, 180, 53, 20);
     this.jtxtPosXBox.setToolTipText(this.ToolTipPosX);
 
     
     this.jtxtPosYTxt = new JTextArea("PosY");
     this.jtxtPosYTxt.setBounds(130, 180, 53, 20);
     this.jtxtPosYTxt.setEditable(false);
     this.jtxtPosYTxt.setToolTipText(this.ToolTipPosY);
     
     this.jtxtPosYBox = new JTextArea("108");
     this.jtxtPosYBox.setBounds(190, 180, 53, 20);
     this.jtxtPosYBox.setToolTipText(this.ToolTipPosY);
 
     
     this.jBtnAct2 = new JButton("Crear Imagenes");
     this.jBtnAct2.setBounds(250, 180, 220, 50);
     this.jBtnAct2.addActionListener(this);
 
     
     this.jtxtSizeWTxt = new JTextArea("Ancho");
     this.jtxtSizeWTxt.setBounds(10, 210, 53, 20);
     this.jtxtSizeWTxt.setEditable(false);
     this.jtxtSizeWTxt.setToolTipText(this.ToolTipSizeW);
     
     this.jtxtSizeWBox = new JTextArea("266");
     this.jtxtSizeWBox.setBounds(70, 210, 53, 20);
     this.jtxtSizeWBox.setToolTipText(this.ToolTipSizeW);
 
     
     this.jtxtSizeHTxt = new JTextArea("Alto");
     this.jtxtSizeHTxt.setBounds(130, 210, 53, 20);
     this.jtxtSizeHTxt.setEditable(false);
     this.jtxtSizeHTxt.setToolTipText(this.ToolTipSizeH);
     
     this.jtxtSizeHBox = new JTextArea("259");
     this.jtxtSizeHBox.setBounds(190, 210, 53, 20);
     this.jtxtSizeHBox.setToolTipText(this.ToolTipSizeH);
 
 
     
     getContentPane().add(this.jtxtPicInTxt);
     getContentPane().add(this.jtxtPicInBox);
     getContentPane().add(this.jtxtTemplateInTxt);
     getContentPane().add(this.jtxtTemplateInBox);
     getContentPane().add(this.jtxtNameTxt);
     getContentPane().add(this.jtxtNameBox);
     getContentPane().add(this.jtxtOutputTxt);
     getContentPane().add(this.jtxtOutputBox);
     getContentPane().add(this.jtxtPosXTxt);
     getContentPane().add(this.jtxtPosXBox);
     getContentPane().add(this.jtxtPosYTxt);
     getContentPane().add(this.jtxtPosYBox);
     getContentPane().add(this.jtxtSizeWTxt);
     getContentPane().add(this.jtxtSizeWBox);
     getContentPane().add(this.jtxtSizeHTxt);
     getContentPane().add(this.jtxtSizeHBox);
     
     getContentPane().add(this.jBtnAct2);
   }
 
   
   private void empty() {
     getContentPane().removeAll();
   }
 
   
   private UiMainNS init() {
     return new UiMainNS();
   }
   
   public static void main(String[] args) {
     UiMainNS ui = new UiMainNS();
     ui.setDefaultCloseOperation(3);
     ui.setBounds(50, 50, 500, 100);
     ui.setLayout((LayoutManager)null);
     ui.addbtns();
     ui.setResizable(false);
     ui.setTitle(ui.title0);
     ui.setVisible(true);
   }
 }


/* Location:              C:\Users\Fradantim\Downloads\NS_2.0.3\NS_2.0.3.jar\\ui\UiMainNS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */