/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import javax.swing.*;
import CCSS_EXEC.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author richard
 */
public class ETexto extends JLabel{
    /////ATRIBUTOS CHTML//////////
    //////////////////////////////
    ///////CCSS//////////////////
    boolean tieneBorde;
    boolean esRedondo;
    int strike;
    Color c;
    //////////////////////////////
    public ETexto()
    {
        super();
        this.tieneBorde = false;
        this.esRedondo = false;
        strike = 0;
    }
    
    public ETexto(boolean tieneBorde, boolean esRedondo, int basicStrike, Color colorBorde)
    {
        this.tieneBorde = tieneBorde;
        this.esRedondo = esRedondo;
        this.strike = basicStrike;
        this.c = colorBorde;
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
