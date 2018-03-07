/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;

import java.awt.Font;


/**
 *
 * @author richard
 */
import java.util.ArrayList;
import CCSS_EXEC.*;
public class Fuente {
    
    //VARIBALES BOOLEANAS QUE INDICAN SI EL TEXTO ES CAPITAL
    boolean captitalt = false;
    boolean minuscula = false;
    boolean mayuscula = false;
    //ARREGLO DE ENTEROS QUE INDICA QUE ESTILOS COLOCARLE
    ArrayList<Integer> estilos;
    //ARREGLO DE ATRIBUTOS QUE VIENEN DESDE CHTML
    ArrayList<NodoAtributo> atributos;
    //GRUPOS U IDS
    Elemento id=null, grupo=null;
    //DEFINICIONES
    CCSS_OB defs;
    ////////////////////////////////////////////////
    String nombreF;
    /////////////////////////////////////////////////
    Font fuenteRetorno;
    Font fuente = new Font("Helvetica", Font.PLAIN, 12);
    double tamanio =12;
    
    public Fuente(ArrayList<NodoAtributo>atributos, CCSS_OB definiciones)
    {
        this.atributos = atributos;
        this.estilos = new ArrayList<>();
        this.defs = definiciones;
        //procesaAtributlosCHTML();
    }

    public boolean isCaptitalt() {
        return captitalt;
    }

    public boolean isMinuscula() {
        return minuscula;
    }

    public boolean isMayuscula() {
        return mayuscula;
    }
    
    private void procesaAtributlosCHTML()
    {
        Search s = new Search();
        NodoAtributo nodoId = s.buscameElAtributoCHTML(atributos, "id");
        if(nodoId!=null)
        {
            this.id = this.defs.buscaEnDefiniciones(nodoId.getValor(), "id");
        }
        NodoAtributo nodoG = s.buscameElAtributoCHTML(atributos, "grupo");
        if(nodoG!=null)
        {
            this.grupo = this.defs.buscaEnDefiniciones(nodoG.getValor(), "grupo");
        }
    }
    
    public Font dameLaFuente()
    {
        procesaAtributlosCHTML();
        if(id!=null)
        {
            generameFuente(id);
        }
        if(grupo!=null)
        {
            generameFuente(grupo);
        }
        
        int d = Font.PLAIN;
        for(Integer i : this.estilos)
        {
            d = i;
        }
        fuenteRetorno = new Font(nombreF, d, (int)tamanio);
        return fuenteRetorno;
    }
    
    public void generameFuente(Elemento elemento)
    {
        Atributo at = elemento.buscaAtributo("letra");
        if(at!=null)
        {
            nombreF = at.getValores().get(0);
        }
        Atributo formato = elemento.buscaAtributo("formato");
        if(formato!=null)
        {
            ArrayList<String> vals = formato.getValores();
            for(String v : vals)
            {
                if(v.toLowerCase().equals("negrilla")){this.estilos.add(Font.BOLD);}
                else if(v.toLowerCase().equals("cursiva")){this.estilos.add(Font.ITALIC);}
                else if(v.toLowerCase().equals("minuscula")){this.minuscula = true;}
                else if(v.toLowerCase().equals("mayuscula")){this.mayuscula = true;}
                else if(v.toLowerCase().equals("capital-t")){this.captitalt = true;}
            }
        }
        Atributo tam = elemento.buscaAtributo("tamtex");
        if(tam!=null)
        {
            try {
                tamanio = Double.parseDouble(tam.getValores().get(0));
            } catch (Exception e) {
                tamanio = 15;
            }
        }
        
    }
}
