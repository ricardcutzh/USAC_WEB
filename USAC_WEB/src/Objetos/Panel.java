/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.*;
/**
 *
 * @author richard
 */
public class Panel extends JPanel{
    FlowLayout layout;
    boolean esRedondo;
    boolean tineBorde;
    int strike;
    Color colorBorde;
    //LAYOUTS
    int alto;
    int ancho;
   public Panel(boolean  esRedondo)
   {

       
   }
   
   
   public Panel(boolean Borde, boolean redondo, int basicStrike, Color colorBorde, int alto, int ancho)
   {
       super();
       this.tineBorde = Borde;
       this.esRedondo = redondo;
       this.strike = basicStrike;
       this.colorBorde = colorBorde;
       this.alto = alto;
       this.ancho = ancho;
       setPreferredSize(new Dimension(ancho, alto));
       
   }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if(esRedondo && tineBorde)
        {
            g2.setColor(colorBorde);
            g2.setStroke(new BasicStroke(this.strike));
            g2.drawRoundRect(0, 0, getWidth()-2, getHeight()-2, 40, 40);
            
        }
        else if(!esRedondo && tineBorde)
        {
            g2.setColor(colorBorde);
            g2.setStroke(new BasicStroke(this.strike));
            g2.drawRect(0, 0, ancho, alto);
            
        }
    }
   
   
    
}
