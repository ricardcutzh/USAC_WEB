/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CHTML_EXEC;
import AST.ASTNodo;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author richard
 */
public class Interprete_CHTML {
    
    ASTNodo raiz;
    JPanel principal;
    
    //LISTA DE CSS Y CJS QUE SE DEBEN DE CARGAR
    ArrayList<Encabezado> encabezados;
    //TITULO DE LA PAGINA QUE DEBE DE COLOCARSER
    String titulo;
    String fondocuerpo;
    
    public Interprete_CHTML(ASTNodo raiz, JPanel principal)
    {
        this.raiz = raiz;
        this.principal = principal;
        encabezados = new ArrayList<Encabezado>();
        this.titulo = "";
        this.fondocuerpo = "";
    }
    
    //INICIO DE EJECUCION DEL CODIGO CHTML
    
    public boolean InicioCHTML()
    {
        try 
        {
            if(raiz.contarHijos()==4)
            {
                //OBTENGO LOS ENCABEZADOS
                E_Encabezado(raiz.getHijo(1));
                //OBTENGO LAS ETIQUETAS DEL CUERPO
                Cuerpo(raiz.getHijo(2));
            }
            return true;
        } catch (Exception e) 
        {
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
                    E_Encabezado(raiz.getHijo(1));
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
                    if(rut!=null)
                    {
                        Encabezado nuevo = new Encabezado(rut, "ccss");
                        this.encabezados.add(nuevo);
                    }
                }
                break;
            }
            case "CJS":
            {
                if(raiz.contarHijos()==1)
                {
                    String rut = (String)E_Encabezado(raiz.getHijo(0));
                    if(rut!=null)
                    {
                        Encabezado nuevo = new Encabezado(rut,"cjs");
                        this.encabezados.add(nuevo);
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
                        this.titulo = rut;
                    }
                }
                break;
            }
            case "RUTA":
            {
                if(raiz.contarHijos()==1)
                {
                    return raiz.getHijo(0).getEtiqueta();
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
                if(raiz.contarHijos()>0)
                {
                    Cuerpo(raiz.getHijo(0));
                    if(raiz.contarHijos()==3)
                    {
                        Cuerpo(raiz.getHijo(1));
                    }
                }
                break;
            }
            case "cuerpo":
            {
                if(raiz.contarHijos()==1)
                {
                    Cuerpo(raiz.getHijo(0));
                }
                break;
            }
            case "FONDO":
            {
                if(raiz.contarHijos()==1)
                {
                    String fondo = (String)Cuerpo(raiz.getHijo(0));
                    if(fondo!=null)
                    {
                        this.fondocuerpo = fondo;
                    }
                    else{
                        this.fondocuerpo = "";
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
}
