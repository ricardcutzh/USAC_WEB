/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CCSS_EXEC;

/**
 *
 * @author richard
 */
import java.util.ArrayList;
import AST.ASTNodo;

public class Interprete_CCSS {
    
    ASTNodo raiz;
    ArrayList<Definicion> definciones;
    
    public Interprete_CCSS(ASTNodo raiz)
    {
        this.raiz = raiz;
    }

    public ArrayList<Definicion> getDefinciones() {
        return definciones;
    }
    
    //METODO QUE INICIA LA EJECUCION DE EL CCSS
    public boolean ejecutaCSS()
    {
        try
        {
            this.definciones = new ArrayList<>();
            inicio_CSS(this.raiz);
            /*for(int x = 0; x < this.definciones.size(); x++)
            {
                this.definciones.get(x).print_definicion();
            }*/
            return true;
        }
        catch(Exception ex)
        {
            System.err.println(""+ex.toString());
            return false;
        }
    }
    
    //RECORRIDO DEL ARBOL
    ArrayList<Elemento> elementos_actuales;
    private Object inicio_CSS(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "INICIO_CSS":
            {
                if(raiz.contarHijos()==1)
                {
                    inicio_CSS(raiz.getHijo(0));
                }
                break;
            }
            case "L_CSS":
            {
                if(raiz.contarHijos()==2)//CUANDO SIGUE UNA LISTA DE DEFINICIONES
                {
                    inicio_CSS(raiz.getHijo(0));//MUEVO A IZQUIERDA -> L_CSS
                    inicio_CSS(raiz.getHijo(1));//MUEVO A DERECHA -> DEF
                }
                if(raiz.contarHijos()==1)
                {
                    inicio_CSS(raiz.getHijo(0));//MUEVO A DEF
                }
                break;
            }
            case "DEF":
            {
                if(raiz.contarHijos()==2)
                {
                    String nombre_def = raiz.getHijo(0).getEtiqueta();//OBTENGO EL NOMBRE DE LA DEFINICION DEL CSS
                    //CREO LA LISTA DE ELEMENTOS QUE TIENE UNA DEFINICION
                    elementos_actuales = new ArrayList<>();
                    //MUEVO A C_DEF PARA OBTENER LOS ELEMENTOS
                    Cuerpo_Definicion(raiz.getHijo(1));
                    //DECLARO MI NUEVA DEFINICION
                    Definicion def = new Definicion(nombre_def);
                    //AGREGO LOS ELEMENTOS ACTUALES
                    def.setElementos(elementos_actuales);
                    //AGREGO LA DEFINICION A LA LISTA DE DEFINICIONES
                    this.definciones.add(def);
                }
                break;
            }
            
        }
        return null;
    }
    
    /*EJECUTANDO EL CUERPO DE UNA DEFINICIONA*/
    ArrayList<Atributo> atributos_actuales;
    private Object Cuerpo_Definicion(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "C_DEF":
            {
                if(raiz.contarHijos()==2)
                {
                    Cuerpo_Definicion(raiz.getHijo(0));//MUEVO A LA IZUQIERDA PARA C_DEF RECURSIVA
                    Cuerpo_Definicion(raiz.getHijo(1));//BAJA PARA PODER BUSCAR UN ID O GRUPO (ELEMENTOS)
                }
                if(raiz.contarHijos()==1)
                {
                    Cuerpo_Definicion(raiz.getHijo(0));//BAJA EN BUSQUEDAD DE UN GRUPO O ID
                }
                break;
            }
            case "GRUPO":
            {
                if(raiz.contarHijos()==2)
                {
                    String nombre_grupo = raiz.getHijo(0).getEtiqueta();//OBTENGO EL NOMBRE DEL GRUPO A COLOCAR
                    //DECLARO UNA NUEVA LISTA DE ATRIBUTOS ACTUALES
                    atributos_actuales = new ArrayList<>();
                    //DECLARO EL NUEVO ELMENTO QUE VA A CONTENER LOS ATRIBUTOS ACTUALES
                    Elemento elemento = new Elemento(nombre_grupo, "grupo");
                    //TENGO QUE OBTENER LOS ATRIBUTOS O PROPIEDADES Y LUEGO ANADIRLO AL LA LISTA DE ELEMENTOS
                    Propiedades(raiz.getHijo(1));
                    //AGREGO LOE ATRIBUTOS AL ELEMENTO ACTUALEMENTE ANALIZADO
                    elemento.setAtributos(atributos_actuales);
                    //AGREGO AL ELEMENTO EN ELEMENTOS ACTUALES
                    elementos_actuales.add(elemento);
                }
                break;
            }
            case "ID":
            {
                if(raiz.contarHijos()==2)
                {
                    //OBTENGO EL NOMBRE DEL ID EN CUESTION
                    String nombre_id = raiz.getHijo(0).getEtiqueta();
                    //DECLARO UNA NUEVA LISTA DE ATRIBUTOS ACTUALES
                    atributos_actuales = new ArrayList<>();
                    //DECLARO UN NUEVO ELEMENTO PARA CONTENER LOS ATRIBUTOS ACTUALES
                   Elemento elemento = new Elemento(nombre_id, "id");
                   //TENGO QUE OBTENER LOS ATRIBUTOS Y PROPUEDEADES PARA LUEGO ANADIRLOS A LA LISTA DE ELEMENTOS
                    Propiedades(raiz.getHijo(1));
                   //AGREGO LOS ATRIBUTOS AL ELEMENTO ACTUALEMENTE ANALIZADO
                   elemento.setAtributos(atributos_actuales);
                   //AGREGO AL ELEMENTO EN ELEMENTOS ACTUALES
                   elementos_actuales.add(elemento);
                }
                break;
            }
        }
        return null;
    }
    
    private Object Propiedades(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "PRODIEDADES":
            {
                if(raiz.contarHijos()==2)
                {
                    //ME MUEVO PARA PROPIEDADES A LA IZQUIERDA
                    Propiedades(raiz.getHijo(0));
                    //LUEGO BUSCO LA PROPIEDAD ASOCIADA A LA DERECHA
                    Propiedades(raiz.getHijo(1));
                }
                if(raiz.contarHijos()==1)
                {
                    //ME MUEVO A BUSCAR LA PROPIEDAD EN EL UNICO HIJO
                    Propiedades(raiz.getHijo(0));
                }
                break;
            }
            case "TEXTO":
            {
                if(raiz.contarHijos()==1)
                {
                    //CREO EL NUEVO ATRIBUTO
                    Atributo atributo = new Atributo("texto");
                    //MANDO A EVALUAR LA CADENA
                    String valor = (String)evalua_Cadena(raiz.getHijo(0));
                    //AGREGO EL VALOR
                    atributo.agregaValor(valor);
                    //AGREGO EL ATRIBUTO A LA LISTA DE ATRIBUTOS ACTUALES
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "FORMATO":
            {
                if(raiz.contarHijos()==1)
                {
                    //CREAR EL NUEVO ATRIBUTO
                    Atributo atributo = new Atributo("formato");
                    //RECORRO SU LISTA DE VALORES
                    ArrayList<String> valores = (ArrayList<String>)Propiedades(raiz.getHijo(0));
                    //AGREGO EL VALOR O VALORES
                    if(valores!=null)
                    {
                        atributo.setValores(valores);
                    }     
                    //AGREGO EL ATRIBUTO ACTUAL A LA LISTA DE ACTUALES
                    atributos_actuales.add(atributo);
                }
            }
            case "L_FORMATO":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<String> valores = (ArrayList<String>)Propiedades(raiz.getHijo(0));
                    String nuevoval = raiz.getHijo(1).getEtiqueta();
                    if(valores !=null)
                    {
                        valores.add(nuevoval);
                    }
                    else
                    {
                        valores = new ArrayList<>();
                        valores.add(nuevoval);
                    }
                    return valores;
                }
                if(raiz.contarHijos()==1)
                {
                    //JALO EL PRIMER VALOR
                    String valor = raiz.getHijo(0).getEtiqueta();
                    //CREO UNA LISTA NUEVA, Y LAS SUBO
                    ArrayList<String> valores = new ArrayList<>();
                    valores.add(valor);
                    //RETORNO LA LISTA
                    return valores;
                }
                break;
            }
            case "LETRA":
            {
                if(raiz.contarHijos()==1)
                {
                    //CREEO EL NUEVO ATRIBUTO
                    Atributo atributo = new Atributo("letra");
                    //MANDO A EVALUAR LA CADENA
                    String val = (String)evalua_Cadena(raiz.getHijo(0));
                    //AGREGO EL VALOR A LA LISTA DE VALORES
                    atributo.agregaValor(val);
                    //AGREGO EL ATRIBUYO A LOS ACTUALES
                    atributos_actuales.add(atributo);
                    
                }
                break;
            }
            case "TAMTEXT":
            {
                if(raiz.contarHijos()==1)
                {
                    //CREO EL NUEVO ATRIBUTOA
                    Atributo atributo = new Atributo("tamtex");
                    //MANDO A EVALUAR LA EXPRESION
                    String val = (String)evalua_Expresion(raiz.getHijo(0));
                    //AGREGO EL VALOR A LA LISTA DE VALORES
                    atributo.agregaValor(val);
                    //AGREGO EL ATRIBUTO ACTUAL
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "FONDOELEM":
            {
                if(raiz.contarHijos()==1)
                {
                    //CREO EL NUEVO ATRIBUTO
                    Atributo atributo = new Atributo("fondoelemento");
                    //MANDO A EVALUAR LA CADENA
                    String val = (String)evalua_Cadena(raiz.getHijo(0));
                    //AGREGO EL VALOR RETORNADO
                    atributo.agregaValor(val);
                    //AGREGO EL ATRIBUTO ACTUAL
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "AUTOREM":
            {
                if(raiz.contarHijos()==2)
                {
                    //CREO LA NUEVA PROPIEDAD
                    Atributo atributo = new Atributo("autoredimension");
                    //OBTERNER EL VALOR FALSO
                    String booleano = (String)Propiedades(raiz.getHijo(0));
                    //OBTENER EL VALOR CONSTANTE, HORIZONTAL O VERTICAL
                    String dir = (String)Propiedades(raiz.getHijo(1));
                    //AGREGO LOS VALORES
                    atributo.agregaValor(booleano);
                    atributo.agregaValor(dir);
                    //AGREGO EL ATRIBUTO A LOS ACTUALES
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "BOOLEAN":
            {
                if(raiz.contarHijos()==1)
                {
                    return raiz.getHijo(0).getEtiqueta();
                }
                break;
            }
            case "DIRECCION":
            {
                if(raiz.contarHijos()==1)
                {
                    return raiz.getHijo(0).getEtiqueta();
                }
                break;
            }
            case "VISIBLE":
            {
                if(raiz.contarHijos()==1)
                {
                    //NUEVO ATRIBUTO
                    Atributo atributo = new Atributo("visible");
                    //OBTENGO EL VALOR
                    String booleano = (String)Propiedades(raiz.getHijo(0));
                    //AGREGO EL VALOR AL ATRIBUTO
                    atributo.agregaValor(booleano);
                    //AGREGO EL ATRIBUTO A LOS ACTUALES
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "ALINEADO":
            {
                if(raiz.contarHijos()==1)
                {
                    Atributo atributo = new Atributo("alineado");
                    String val = raiz.getHijo(0).getEtiqueta();
                    atributo.agregaValor(val);
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "BORDE":
            {
                if(raiz.contarHijos()==3)
                {
                    Atributo atributo = new Atributo("borde");
                    //OBTENER EL PRIMER VALOR DE EXPREISION
                    String val1 = (String)evalua_Expresion(raiz.getHijo(0));
                    atributo.agregaValor(val1);
                    //OBTENER EL SEGUNDO VALOR DE EXPCAD
                    String val2 = (String)evalua_Cadena(raiz.getHijo(1));
                    atributo.agregaValor(val2);
                    //OBTENER EL VALOR BOOLEANO
                    String boolnano = (String)Propiedades(raiz.getHijo(2));
                    atributo.agregaValor(boolnano);
                    //ANADIR EL ATRIBUTO A LOS ACTUALES
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "OPAQUE":
            {
                if(raiz.contarHijos()==1)
                {
                    Atributo atributo = new Atributo("opaque");
                    String booleano = (String)Propiedades(raiz.getHijo(0));
                    atributo.agregaValor(booleano);
                    atributos_actuales.add(atributo);
                }
                break;
            }
            case "COLORTEXT":
            {
                if(raiz.contarHijos()==1)
                {
                    Atributo atributo = new Atributo("colortext");
                    //MANDO A EVALUAR LA CADENA
                    String val1 = (String)evalua_Cadena(raiz.getHijo(0));
                    //AGREGO EL VALOR AL ATRIBUTO
                    atributo.agregaValor(val1);
                    //AGREGO EL ATRIBUTO A LA LISTA
                    atributos_actuales.add(atributo);
                }
                break;
            }
        }
        return null;
    }
    
    private Object evalua_Expresion(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "EXP":
            {
                if(raiz.contarHijos()==1)
                {
                    double valor = (double)evalua_Expresion(raiz.getHijo(0));
                    return String.valueOf(valor);
                }
                break;
            }
            case "E":
            {
                if(raiz.contarHijos()==3)
                {
                    //SI ES SUMA
                    if(raiz.getHijo(1).getEtiqueta().equals("+"))
                    {
                        try {
                            double val1 = (double)evalua_Expresion(raiz.getHijo(0));
                            double val2 = (double)evalua_Expresion(raiz.getHijo(2));
                            return val1 + val2;
                        } catch (Exception e) {
                            System.err.println("Error en la produccion E := H1 + H2");
                            return 0;
                        }
                    }
                    //SI ES RESTA
                    else
                    {
                        try {
                            double val1 = (double)evalua_Expresion(raiz.getHijo(0));
                            double val2 = (double)evalua_Expresion(raiz.getHijo(2));
                            return val1 - val2;
                        } catch (Exception e) {
                            System.err.println("Error en la produccion E := H1 - H2");
                            return 0;
                        }
                    }
                }
                break;
            }
            case "T":
            {
                if(raiz.contarHijos()==3)
                {
                    //SI ES MULTIPLICACION
                    if(raiz.getHijo(1).getEtiqueta().equals("*"))
                    {
                        try {
                            double val1 = (double)evalua_Expresion(raiz.getHijo(0));
                            double val2 = (double)evalua_Expresion(raiz.getHijo(2));
                            return val1 * val2;
                        } catch (Exception e) {
                            System.err.println("Error en la produccion T := H1 * H2");
                            return 0;
                        }
                    }
                    //SI ES DIVISION
                    else
                    {
                        try {
                            double val1 = (double)evalua_Expresion(raiz.getHijo(0));
                            double val2 = (double)evalua_Expresion(raiz.getHijo(2));
                            return val1 / val2;
                        } catch (Exception e) {
                            System.err.println("Error en la produccion T := H1 / H2");
                            return 0;
                        }
                    }
                }
                break;
            }
            case "ENTERO":
            {
                if(raiz.contarHijos()==1)
                {
                    try
                    {
                        double val = Double.parseDouble(raiz.getHijo(0).getEtiqueta());
                        return val;
                    }
                    catch(Exception ex)
                    {
                        System.err.println("Error en la produccion ENTERO");
                        return 0;
                    }
                }
                break;
            }
            case "DECIMAL":
            {
                if(raiz.contarHijos()==1)
                {
                    try
                    {
                        double val = Double.parseDouble(raiz.getHijo(0).getEtiqueta());
                        return val;
                    }
                    catch(Exception ex)
                    {
                        System.err.println("Error en la produccion ENTERO");
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    
    private Object evalua_Cadena(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "EXPCAD":
            {
                
                if(raiz.contarHijos()==3)
                {
                    String val1 = (String)evalua_Cadena(raiz.getHijo(0));
                    String val2 = (String)evalua_Cadena(raiz.getHijo(2));
                    return val1 + val2;
                }
                if(raiz.contarHijos()==1)
                {
                    return (String)evalua_Cadena(raiz.getHijo(0));
                }
                break;
            }
            case "CADENA":
            {
                if(raiz.contarHijos()==1)
                {
                    return raiz.getHijo(0).getEtiqueta();
                }
                else
                {
                    return "";
                }
            }
        }
        return "";
    }
}
