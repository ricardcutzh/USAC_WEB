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
    public Interprete_CHTML(ASTNodo raiz, JPanel principal)
    {
        this.raiz = raiz;
        this.principal = principal;
        this.definicionesCSS = new CCSS_OB();
        this.errores = new ArrayList<>();
        this.archivosCSS = new ArrayList<>();
        this.archivosCJS = new ArrayList<>();
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
            case "L_CUERPO":
            {
                if(raiz.contarHijos()>0)//EMPIEZA LA PINTADA DEL CUERPO
                {
                    
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
    

}
