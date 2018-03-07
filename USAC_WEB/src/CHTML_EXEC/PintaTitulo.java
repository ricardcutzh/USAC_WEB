/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;

import CCSS_EXEC.*;
import java.awt.Color;

import javax.swing.JTabbedPane;

/**
 *
 * @author richard
 */
import java.util.ArrayList;
public class PintaTitulo {
    JTabbedPane pestania;
    int index;
    ArrayList<NodoAtributo> atributos;
    CCSS_OB definiciones;
    Elemento id;
    Elemento grupo;

    public PintaTitulo(JTabbedPane pestania, int indexp, ArrayList<NodoAtributo> atributos, CCSS_OB definiciones) {
        this.pestania = pestania;
        this.index = indexp;
        this.atributos = atributos;
        this.definiciones = definiciones;
    }
    
    public void pintalo()
    {
        Search s = new Search();
        NodoAtributo nodoId = s.buscameElAtributoCHTML(atributos,"id");
        if(nodoId!=null)
        {
            this.id = this.definiciones.buscaEnDefiniciones(nodoId.getValor(), "id");
        }
        NodoAtributo nodogrupo = s.buscameElAtributoCHTML(atributos, "grupo");
        if(nodogrupo!=null)
        {
            this.grupo = this.definiciones.buscaEnDefiniciones(nodogrupo.getValor(),"grupo");
        }
        if(id!=null)
        {
            pintaDefinicionElemento(id);
        }
        if(grupo!=null)
        {
            pintaDefinicionElemento(grupo);
        }
    }
    
    
    public void pintaDefinicionElemento(Elemento el)
    {
        Atributo colorText = el.buscaAtributo("colortext");
        if(colorText!=null)
        {
            InterpretaColor col = new InterpretaColor();
            Color c = col.stringToColor(colorText.getValores().get(0));
            this.pestania.setForegroundAt(index, c);
        }
        Atributo texto = el.buscaAtributo("texto");
        if(texto!=null)
        {
            this.pestania.setTitleAt(index, texto.getValores().get(0));
        }
    }
}
