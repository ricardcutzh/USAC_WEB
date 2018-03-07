/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextField;

/**
 *
 * @author richard
 */
public class Caja_Texto  extends JTextField{
    ///////CCSS//////////////////
    boolean tieneBorde;
    boolean esRedondo;
    int strike;
    Color c;
    //////////////////////////////
    public Caja_Texto(boolean tieneBorde, boolean esRedondo, int basicStrike, Color colorBorder)
    {
        this.c = colorBorder;
        this.esRedondo = esRedondo;
        this.tieneBorde = tieneBorde;
        this.strike = basicStrike;
        this.setPreferredSize(new Dimension(200,25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if(esRedondo && tieneBorde)
        {
            g2.setColor(c);
            g2.setStroke(new BasicStroke(this.strike));
            g2.drawRoundRect(0, 0, getWidth()-2, getHeight()-2, 40, 40);
        }
        else if(!esRedondo && tieneBorde)
        {
            g2.setColor(c);
            g2.setStroke(new BasicStroke(this.strike));
            g2.drawRect(0, 0, getWidth(), getHeight());
        }
    }
    
    
}
