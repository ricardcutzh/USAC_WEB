/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;

/**
 *
 * @author richard
 */
import CCSS_EXEC.Atributo;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import CCSS_EXEC.Elemento;
import Objetos.Panel;
import java.awt.Dimension;
public class GeneraPanel {
    CCSS_OB definiciones;
    ArrayList<NodoAtributo> chtmlAtts;
    FlowLayout layout;
    Color color;
    //PARA EL BORDE//
    boolean borde;
    boolean redondo;
    Color coloBorde;
    int basicStrike;
    /////////////////
    int alto = 500;
    int ancho = 500;
    /////////////////
    Elemento id;
    Elemento grupo;
    /////////////////
    Color colotext;
    /////////////////
    boolean opaco;
    
    public GeneraPanel(CCSS_OB defs, ArrayList<NodoAtributo> chtmlAtts)
    {
        this.definiciones = defs;
        this.chtmlAtts = chtmlAtts;
        layout = new FlowLayout();
        color = Color.white;
        borde = false;
        redondo = false;
        coloBorde = Color.BLACK;
        id = null;
        grupo = null;
        colotext = Color.BLACK;
        basicStrike = 10;
        opaco = false;
    }
    
    public Panel gerenameElPanel()
    {
        for(int x= 0; x< this.chtmlAtts.size(); x++)
        {
            if(this.chtmlAtts.get(x).getNombre().equals("id"))
            {
                this.id = definiciones.buscaEnDefiniciones(this.chtmlAtts.get(x).getValor(), "id");
            }
            else if(this.chtmlAtts.get(x).getNombre().equals("grupo"))
            {
                this.grupo = definiciones.buscaEnDefiniciones(this.chtmlAtts.get(x).getValor(), "grupo");
            }
            else if(this.chtmlAtts.get(x).getNombre().equals("alto"))
            {
                try {
                    alto = Integer.parseInt(this.chtmlAtts.get(x).getValor());
                } catch (Exception e) {
                    alto = 500;
                }
            }
            else if(this.chtmlAtts.get(x).getNombre().equals("ancho"))
            {
                try {
                    ancho = Integer.parseInt(this.chtmlAtts.get(x).getValor());
                } catch (Exception e) {
                    ancho = 500;
                }
            }
            else if(this.chtmlAtts.get(x).getNombre().equals("alineado"))
            {
                String aux = this.chtmlAtts.get(x).getValor();
                if(aux.equals("izquierda"))
                {
                    layout = new FlowLayout(FlowLayout.LEFT);
                }
                else if(aux.equals("derecha"))
                {
                    layout = new FlowLayout(FlowLayout.RIGHT);
                }
                else if(aux.equals("centrado"))
                {
                    layout = new FlowLayout(FlowLayout.CENTER);
                }
                else if(aux.equals("justificado"))
                {
                    layout = new FlowLayout(FlowLayout.LEADING);
                }
            }
        }
        if(id!=null)
        {
            interpretaElementoCSS(id);
        }
        if(grupo!=null)
        {
            interpretaElementoCSS(grupo);
        }
        
        Panel p = new Panel(borde,redondo, basicStrike, coloBorde, alto, ancho);
        //asignando fondo
        p.setBackground(color);
        //opaque
        //p.setOpaque(opaco);
        if(opaco)
        {
            p.setBackground(new Color(0,0,0,100));
        }
        p.setLayout(layout);
        return p;
    }
    
    public void interpretaElementoCSS(Elemento el)
    {
        InterpretaColor interp = new InterpretaColor();
        ArrayList<Atributo> atts = el.getAtributos();
        for(int x = 0; x < atts.size(); x++)
        {
            String aux = atts.get(x).getNombreAtributo();
            if(aux.equals("fondoelemento"))
            {
                color = interp.stringToColor(atts.get(x).getValores().get(0));
            }
            else if(aux.equals("borde"))
            {
                borde = true;
                ArrayList<String> vals = atts.get(x).getValores();
                if(vals.size()==3)
                {
                    try {
                        basicStrike = Integer.parseInt(vals.get(0));
                    } catch (Exception e) {
                        basicStrike = 10;
                    }
                    coloBorde = interp.stringToColor(vals.get(1));
                    if(vals.get(2).toLowerCase().equals("true"))
                    {
                       redondo = true;
                    }
                    else
                    {
                       redondo = false;
                    }
                }
                
            }
            else if(aux.equals("opaque"))
            {
                if(atts.get(x).getValores().get(0).toLowerCase().equals("true"))
                {
                    opaco = true;
                }
            }
            else if(aux.equals("alineado"))
            {
                String aux1 = atts.get(x).getValores().get(0);
                if(aux1.equals("izquierda"))
                {
                    layout = new FlowLayout(FlowLayout.LEFT);
                }
                else if(aux1.equals("derecha"))
                {
                    layout = new FlowLayout(FlowLayout.RIGHT);
                }
                else if(aux1.equals("centrado"))
                {
                    layout = new FlowLayout(FlowLayout.CENTER);
                }
                else if(aux1.equals("justificado"))
                {
                    layout = new FlowLayout(FlowLayout.LEADING);
                }
            }
        }
    }
    
}
