/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CHTML_EXEC;
import AST.ASTNodo;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
//LLAMO AL INTERPRETE DEL CSS
import CCSS_EXEC.*;
import java.io.FileReader;
import AST.TError;
import Objetos.*;
import com.sun.xml.internal.ws.util.StringUtils;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javafx.scene.layout.Pane;
import javax.swing.border.LineBorder;
/**
 *
 * @author richard
 */
public class Interprete_CHTML {
    
    ASTNodo raiz;
    JPanel principal;
    JTabbedPane pestania;
    int indexPesta;
    //ERRORES
    ArrayList<TError> errores;
    //LISTA DE DEFINICIONES CSS PARA PODER APLICAR ESTILOS
    CCSS_OB definicionesCSS;
    //LISTA DE ARCHIVOS CSS
    ArrayList<String> archivosCSS;
    //LISTA DE ARBOLES DE CJS
    ArrayList<NodoCJS> archivosCJS;
    ///////////////////////////////
    int ocupado = 0;
    ///////////////////////////////
    ///////////////////////////////
    //PILA DE PANELES
    ArrayList<JPanel> paneles;
    ///////////////////////////////
    Tabla_Componente tabla_componentes;
    ///////////////////////////////
    public Interprete_CHTML(ASTNodo raiz, JPanel principal)
    {
        this.raiz = raiz;
        this.principal = principal;
        this.definicionesCSS = new CCSS_OB();
        this.errores = new ArrayList<>();
        this.archivosCSS = new ArrayList<>();
        this.archivosCJS = new ArrayList<>();
        this.paneles = new ArrayList<>();
    }
    
    
    public Interprete_CHTML(ASTNodo raiz, JPanel principal, JTabbedPane pestania, int Index)
    {
        this.raiz = raiz;
        this.principal = principal;
        this.pestania = pestania;
        this.indexPesta = Index;
        this.definicionesCSS = new CCSS_OB();
        this.errores = new ArrayList<>();
        this.archivosCSS = new ArrayList<>();
        this.archivosCJS = new ArrayList<>();
        this.paneles = new ArrayList<>();
        this.tabla_componentes = new Tabla_Componente();
    }

    
    
    public ArrayList<TError> getErrores() {
        return errores;
    }
    
    
    
    //INICIO DE EJECUCION DEL CODIGO CHTML
    
    public boolean InicioCHTML()
    {
        try 
        {
            if(raiz.contarHijos()==2)
            {
                //OBTENGO LOS ENCABEZADOS
                E_Encabezado(raiz.getHijo(0));
                //OBTENGO LAS ETIQUETAS DEL CUERPO
                this.paneles.add(0, principal);
                Cuerpo(raiz.getHijo(1));
                //IMPRIMO LAS DEFINICIONES DEL CSS
                this.definicionesCSS.imprimeDefiniciones();
            }
            return true;
        } catch (Exception e) 
        {
            System.err.println("Interpretando CHTML: "+e.toString());
            return false;
        }
    }
    
    /*PARA PODER PROCESAR EL ENCABEZADO DEL CHTML*/
    private Object E_Encabezado(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "ENCABEZADO":
            {
                if(raiz.contarHijos()>0)
                {
                    E_Encabezado(raiz.getHijo(0));
                }
                break;
            }
            case "L_ENCABEZADO":
            {
                if(raiz.contarHijos()==2)//TIENE DOS HIJOS
                {
                    E_Encabezado(raiz.getHijo(0));
                    E_Encabezado(raiz.getHijo(1));
                }
                if(raiz.contarHijos()==1)//TIENE UN HIJO
                {
                    E_Encabezado(raiz.getHijo(0));
                }
                break;
            }
            case "CCSS":
            {
                if(raiz.contarHijos()==1)
                {
                    String rut = (String)E_Encabezado(raiz.getHijo(0));
                    CCSS_OB auxiliar = new CCSS_OB();
                    ArrayList<Definicion> defs = auxiliar.obtenDefiniciones(rut);
                    if(defs!=null)
                    {
                        this.archivosCSS.add(rut);
                        for(int x = 0; x<defs.size(); x++)
                        {
                            //AGREGO TODAS LAS DEFINICIONES CSS QUE HAY EN EL ARCHIVOS
                            //ASI PUEDO DECLARAR VARIOS Y AUN ASI TENERLAS TODAS
                            this.definicionesCSS.definiciones.add(defs.get(x));
                        }
                    }
                    else
                    {
                        TError error = new TError(rut, "Error Semantico", "La Ruta No Existe", raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores.add(error);
                    }
                    if(auxiliar.errores.size()>0)
                    {
                        ArrayList<TError> errorescss = auxiliar.errores;
                        for(int x = 0; x< errorescss.size(); x++)
                        {
                            errorescss.get(x).setArchivo(rut);
                            this.errores.add(errorescss.get(x));
                        }
                    }
                }
                break;
            }
            case "CJS":
            {
                if(raiz.contarHijos()==1)
                {
                    String rut = (String)E_Encabezado(raiz.getHijo(0));
                    NodoCJS nodo = new NodoCJS(rut);
                    if(nodo.EjecutaAnalisis())
                    {
                        //ANADO EL NODO A LA LISTA PARA SU POSTERIOR EJECUCION
                        this.archivosCJS.add(nodo);
                    }
                    if(nodo.getNumeroErrores()>0)
                    {
                        ArrayList<TError> erroresCjsSint = nodo.erroresSintacticos;
                        for(int x = 0; x< erroresCjsSint.size(); x++)
                        {
                            erroresCjsSint.get(x).setArchivo(rut);
                            this.errores.add(erroresCjsSint.get(x));
                        }
                    }
                }
                break;
            }
            case "TITULO":
            {
                if(raiz.contarHijos()==1)
                {
                    String rut = (String)E_Encabezado(raiz.getHijo(0));
                    if(rut!=null)
                    {
                        pestania.setTitleAt(indexPesta, rut);
                    }
                }
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                    Fuente f = new Fuente(atts, this.definicionesCSS);
                    
                    Font fe = f.dameLaFuente();
                    String texto = raiz.getHijo(1).getEtiqueta();
                    this.pestania.setTitleAt(indexPesta, texto);
                    if(texto!=null)
                    {   
                        //////////////////////////////////////////////////////////////////////
                        if(f.isCaptitalt()){
                            texto = upperCaseAllFirst(texto);
                        }
                        if(f.isMayuscula()){
                            texto = texto.toUpperCase();
                        }
                        if(f.isMinuscula()){
                            texto = texto.toLowerCase();
                        }
                        PintaTitulo pintor = new PintaTitulo(pestania, indexPesta, atts, definicionesCSS);
                        pintor.pintalo();
                        this.pestania.setFont(fe);
                        //////////////////////////////////////////////////////////////////////
                    }
                }
                break;
            }
            case "RUTA":
            {
                if(raiz.contarHijos()==1)
                {
                    String etiqueta = raiz.getHijo(0).getEtiqueta();
                    etiqueta = etiqueta.replace("\\", "/");
                    return etiqueta;
                }
                break;
            }
            case "TXT":
            {
                if(raiz.contarHijos()==1)
                {
                    return raiz.getHijo(0).getEtiqueta();
                }
                break;
            }
        }
        return null;
    }
    
    private Object Cuerpo(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "CUERPO":
            {
                if(raiz.contarHijos()==2)
                {
                    Cuerpo(raiz.getHijo(0));//FONDO
                    Cuerpo(raiz.getHijo(1));
                }
                if(raiz.contarHijos()==1)
                {
                    Cuerpo(raiz.getHijo(0));//SOLO FONDO
                }
                break;
            }
            case "FONDO":
            {
                if(raiz.contarHijos()==1)
                {
                    String fondo = raiz.getHijo(0).getEtiqueta();
                    Color col = stringToColor(fondo);
                    if(col!=null)
                    {
                        this.principal.setBackground(col);
                    }
                    else
                    {
                        col = ColorHex(fondo);
                        this.principal.setBackground(col);
                    }
                }
                break;
            }
            case "L_CUERPO"://ESTE ES EL PRINCIPAL DEL CUERPO
            {
                if(raiz.contarHijos()==2)//EMPIEZA LA PINTADA DEL CUERPO
                {
                    //ME MUEVO MAS A LA IZQUIERDA
                    Cuerpo(raiz.getHijo(0));
                    //LUEGO ME MUEVO A LA DERECHA
                    Cuerpo(raiz.getHijo(1));
                }
                if(raiz.contarHijos()==1)
                {
                    Cuerpo(raiz.getHijo(0));
                }
                break;
            }
            case "PANEL":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                    if(atts!=null)
                    {
                        String id = traeID(atts);
                        
                        GeneraPanel generador = new GeneraPanel(definicionesCSS, atts);
                        Panel p = generador.gerenameElPanel();
                        if(!id.equals("NAC")){this.tabla_componentes.addComponenete(new Component(p,"panel", id));}
                        this.paneles.get(0).add(p);//ANADO AL PADRE
                        this.paneles.add(0, p);//EL NUEVO PADRE
                        Cuerpo(raiz.getHijo(1));
                        this.paneles.remove(0);//VUELE A SALIR
                    }
                }
                if(raiz.contarHijos()==1)
                {
                    if(raiz.getHijo(0).getEtiqueta().equals("L_ATTS"))
                    {
                        ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                        if(atts!=null)
                        {
                            String id = traeID(atts);
                            GeneraPanel generador = new GeneraPanel(definicionesCSS, atts);
                            Panel p = generador.gerenameElPanel();
                            if(!id.equals("NAC")){this.tabla_componentes.addComponenete(new Component(p,"panel", id));}
                            this.paneles.get(0).add(p);
                        }
                    }
                    else
                    {
                        Panel p = new Panel(false, false, 0, Color.white, 500, 500);
                        this.paneles.get(0).add(p);
                        this.paneles.add(0, p);
                        Cuerpo(raiz.getHijo(0));
                        this.paneles.remove(0);
                    }
                }
                break;
            }
            case "SALTO":
            {
                JPanel panel = new JPanel();
                panel.setBackground(Color.white);
                panel.setPreferredSize(new Dimension(2000-ocupado, 10));//OJO EL 2000 QUE SEA CONFIGURABLE DE ACUERDO AL TAMANIO QUE YO SETEE
                this.paneles.get(0).add(panel);
                break;
            }
            case "TEXTO":
            {
                if(raiz.contarHijos()==2)
                {
                     ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                     if(atts!=null)
                     {
                         String tid = traeID(atts);
                         String texto = raiz.getHijo(1).getEtiqueta();
                         GeneraTexto generador = new GeneraTexto(definicionesCSS, atts, texto);
                         ETexto tex = generador.generameLabel();
                         if(!tid.equals("NAC")){this.tabla_componentes.addComponenete(new Component(tex,"label", tid));}
                         this.paneles.get(0).add(tex);
                     }
                }
                if(raiz.contarHijos()==1)
                {
                    String texto = raiz.getHijo(0).getEtiqueta();
                    ETexto textol = new ETexto(false, false, 0, Color.GREEN);
                    textol.setText(texto);
                    this.paneles.get(0).add(textol);
                }
                break;
            }
            case "CAJA_TEXTO":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                    if(atts!=null)
                    {
                        String tid = traeID(atts);
                        String texto = raiz.getHijo(1).getEtiqueta();
                        GeneraTexto generador = new GeneraTexto(definicionesCSS, atts, texto);
                        Caja_Texto cajita = generador.generameTextField();
                        if(!tid.equals("NAC")){this.tabla_componentes.addComponenete(new Component(cajita, "textfield", tid));}
                        this.paneles.get(0).add(cajita);
                    }
                }
                if(raiz.contarHijos()==1)
                {
                    String texto = raiz.getHijo(0).getEtiqueta();
                    Caja_Texto cajita = new Caja_Texto(false, false, 0, Color.black);
                    this.paneles.get(0).add(cajita);
                }
                break;
            }
            case "TEXTO_A":
            {
                if(raiz.contarHijos()==2)
                {
                     ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                     if(atts!=null)
                     {
                         String tid = traeID(atts);
                         String texto = raiz.getHijo(1).getEtiqueta();
                         GeneraTexto generador = new GeneraTexto(definicionesCSS, atts, texto);
                         AreaTexto area = generador.generameTextArea();
                         if(!tid.equals("NAC")){this.tabla_componentes.addComponenete(new Component(area, "texpane", tid));}
                        this.paneles.get(0).add(area);
                     }
                }
                if(raiz.contarHijos()==1)
                {
                    String texto = raiz.getHijo(0).getEtiqueta();
                    AreaTexto area = new AreaTexto(false, false, 0, Color.black);
                    this.paneles.get(0).add(area);
                }
                break;
            }
            case "SPINNER":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                    if(atts!=null)
                    {
                        String tid = traeID(atts);
                        int num = (Integer)Cuerpo(raiz.getHijo(1));
                        GeneraTexto generador = new GeneraTexto(definicionesCSS, atts,"");
                        JSpinner spinner = generador.generameSpinner(num);
                        if(!tid.equals("NAC")){this.tabla_componentes.addComponenete(new Component(spinner, "spinner", tid));}
                        this.paneles.get(0).add(spinner);
                    }
                }
                if(raiz.contarHijos()==1)
                {
                    int num = (Integer)Cuerpo(raiz.getHijo(0));
                    JSpinner spiner = new JSpinner(new SpinnerNumberModel(0, 0, num, 1));
                    spiner.setPreferredSize(new Dimension(100, 50));
                    this.paneles.get(0).add(spiner);
                }
                break;
            }
            case "CONTADOR":
            {
                if(raiz.contarHijos()==1)
                {
                    try {
                        int num = Integer.parseInt(raiz.getHijo(0).getEtiqueta());
                        return num;
                    } catch (Exception e) {
                        return 0;
                    }
                }
                break;
            }
        }
        return null;
    }
    
    
    private Object Lista_Atributos(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "L_ATTS":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoAtributo> atts = (ArrayList<NodoAtributo>)Lista_Atributos(raiz.getHijo(0));
                    if(atts!=null)
                    {
                        NodoAtributo at = (NodoAtributo)Lista_Atributos(raiz.getHijo(1));
                        if(at!=null)
                        {
                            atts.add(at);
                        }
                    }
                    return atts;
                }
                if(raiz.contarHijos()==1)
                {
                    ArrayList<NodoAtributo> atts = new ArrayList<>();
                    NodoAtributo at = (NodoAtributo)Lista_Atributos(raiz.getHijo(0));
                    if(at!=null)
                    {
                        atts.add(at);
                    }
                    return atts;
                }
                break;
            }
            case "ID":
            {
                if(raiz.contarHijos() == 1)
                {
                    NodoAtributo at = new NodoAtributo(raiz.getEtiqueta().toLowerCase(), raiz.getHijo(0).getEtiqueta());
                    return at;
                }
                break;
            }
            case "ALTO":
            {
                if(raiz.contarHijos() == 1)
                {
                    NodoAtributo at = new NodoAtributo(raiz.getEtiqueta().toLowerCase(), raiz.getHijo(0).getEtiqueta());
                    return at;
                }
                break;
            }
            case "ANCHO":
            {
                if(raiz.contarHijos() == 1)
                {
                    NodoAtributo at = new NodoAtributo(raiz.getEtiqueta().toLowerCase(), raiz.getHijo(0).getEtiqueta());
                    return at;
                }
                break;
            }
            case "ALINEADO":
            {
                if(raiz.contarHijos() == 1)
                {
                    NodoAtributo at = new NodoAtributo(raiz.getEtiqueta().toLowerCase(), raiz.getHijo(0).getEtiqueta());
                    return at;
                }
                break;
            }
            case "GRUPO":
            {
                if(raiz.contarHijos() == 1)
                {
                    NodoAtributo at = new NodoAtributo(raiz.getEtiqueta().toLowerCase(), raiz.getHijo(0).getEtiqueta());
                    return at;
                }
                break;
            }
            case "RUTA":
            {
                if(raiz.contarHijos() == 1)
                {
                    NodoAtributo at = new NodoAtributo(raiz.getEtiqueta().toLowerCase(), raiz.getHijo(0).getEtiqueta());
                    return at;
                }
                break;
            }
        }
        return null;
    }
    
    private Color stringToColor(String color)
    {
        color = color.toLowerCase();
        switch(color)
        {
            case "red":
            {
                return Color.red;
            }
            case "blue":
            {
                return Color.blue;
            }
            case "yellow":
            {
                return Color.yellow;
            }
            case "green":
            {
                return Color.green;
            }
            case "orange":
            {
                return Color.orange;
            }
            case "white":
            {
                return Color.white;
            }
            case "black":
            {
                return Color.black;
            }
        }
        return null;
    }
    
    private Color ColorHex(String color)
    {
        try {
            Color col = Color.decode(color);
            return col;
        } catch (Exception e) {
            return Color.GRAY;
        }
    }
    
    
    private String traeID(ArrayList<NodoAtributo> atts)
    {
        for(int x = 0; x < atts.size(); x++)
        {
            if(atts.get(x).getNombre().toLowerCase().equals("id"))
            {
                return atts.get(x).getValor();
            }
        }
        return "NAC";
    }
    
    public  String upperCaseAllFirst(String value) {

        char[] array = value.toCharArray();
        // Uppercase first letter.
        array[0] = Character.toUpperCase(array[0]);

        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        // Result.
        return new String(array);
    }
}
