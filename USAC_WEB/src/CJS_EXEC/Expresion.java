/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_EXEC;

import AST.ASTNodo;
import AST.TError;
import java.util.ArrayList;

/**
 *
 * @author richard
 */
public class Expresion {
    Tabla_Funciones funciones;
    ArrayList<Ambito> ambitos;
    ASTNodo raiz;
    ArrayList<TError> errores_semanticas;
    
    public Expresion(Tabla_Funciones funciones, ArrayList<Ambito> ambitos, ASTNodo raiz, ArrayList<TError> errores)
    {
        this.funciones = funciones;
        this.ambitos = ambitos;
        this.raiz = raiz;
        this.errores_semanticas = errores;
    }
    
    //INICIO DE LA EVALUACION DE LA EXPRESION
    public Object evaluaExpresion()
    {
        try 
        {
            return evalua(this.raiz);
        } catch (Exception e) 
        {
            System.err.println("Error al Evaluar la expresion: "+e.toString());
            return null;
        }
    }
    
    public Object evalua(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "EXPRESION":
            {
                if(raiz.contarHijos()==1)
                {
                    Object retorno = evalua(raiz.getHijo(0));
                }
                break;
            }
            case "E":
            {
                if(raiz.contarHijos()==3)
                {
                    if(raiz.getHijo(1).getEtiqueta().equals("+"))//SUMA
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaSuma(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Suma entre: "+val1.getTipo()+" y "+val2.getTipo()+" incompatible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("-"))//RESTA
                    {
                        
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("*"))//MULTIPLICACION
                    {
                        
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("/"))//DIVISION
                    {
                        
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("%"))//MODULAR
                    {
                        
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("^"))//ELEVADO
                    {
                        
                    }
                    
                }
                break;
            }
            case "NUMERICO":
            {
                if(raiz.contarHijos()==1)
                {
                    NodoOperacion op = new NodoOperacion(raiz.getHijo(0).getEtiqueta(),"numerico",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                    return op;
                }
                break;
            }
            case "IDENTIFICADOR":
            {
                if(raiz.contarHijos()==1)
                {
                    NodoOperacion op;
                    String id = raiz.getHijo(0).getEtiqueta();
                    Variable aux = this.ambitos.get(0).busca_Variable(id);
                    if(aux!=null)//EXITE EN ESTE AMBITO?
                    {
                        op = new NodoOperacion(aux.getValor(),aux.getTipo(),raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        return op;
                    }
                    else//BUSCA EN GLOBAL O MAS ARRIBA!
                    {
                        Variable aux2 = busca_Var_Global(id);
                        if(aux2!=null)//SIi EXISTE MAS ARRIBA, TRAE SU VALOR
                        {
                            op = new NodoOperacion(aux2.getValor(),aux2.getTipo(),raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                            return op;
                        }
                        else//SI NO EXISTE..... ERROR DE SEMANTICA
                        {
                            TError error = new TError(raiz.getHijo(0).getEtiqueta(),"Error Semantico","Esta variable no Existe...", raiz.getHijo(0).getLine(),raiz.getHijo(0).getColumn());
                            this.errores_semanticas.add(error);
                        }
                    }
                }
                break;
            }
            case "DATE":
            {
                if(raiz.contarHijos()==1)
                {
                    NodoOperacion op = new NodoOperacion(raiz.getHijo(0).getEtiqueta(), "date", raiz.getHijo(0).getLine(),raiz.getHijo(0).getColumn());
                    return op;
                }
                break;
            }
            case "DATETIME":
            {
                if(raiz.contarHijos()==1)
                {
                    NodoOperacion op = new NodoOperacion(raiz.getHijo(0).getEtiqueta(), "datetime", raiz.getHijo(0).getLine(),raiz.getHijo(0).getColumn());
                    return op;
                }
                break;
            }
            case "BOOLEAN":
            {
                if(raiz.contarHijos()==1)
                {
                    NodoOperacion op = new NodoOperacion(raiz.getHijo(0).getEtiqueta(), "boolean", raiz.getHijo(0).getLine(),raiz.getHijo(0).getColumn());
                    return op;
                }
                break;
            }
            case "CADENA":
            {
                if(raiz.contarHijos()==1)
                {
                    NodoOperacion op = new NodoOperacion(raiz.getHijo(0).getEtiqueta(), "cadena", raiz.getHijo(0).getLine(),raiz.getHijo(0).getColumn());
                    return op;
                }
                break;
            }
            case "VALOR_VECTOR":
            {
                break;
            }
            case "CONTEO":
            {
                break;
            }
            case "ATEXTO":
            {
                break;
            }
            case "EXE_FUNC":
            {
                break;
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    
    public Variable busca_Var_Global(String nombre)
    {
        for(int x = 1; x < this.ambitos.size();x++)
        {
            Variable aux = this.ambitos.get(x).busca_Variable(nombre);
            if(aux!=null)
            {
                return aux;
            }
        }
        return null;
    }
    
    
    private NodoOperacion realizaSuma(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("boolean"))
        {
            boolean va1 = false, va2 = false;
            if(val1.getValor().toLowerCase().equals("true")){va1 = true;}
            if(val2.getValor().toLowerCase().equals("true")){va2 = true;}
            boolean res = va1 || va2;
            NodoOperacion op = new NodoOperacion(String.valueOf(res),"boolean", val2.getLinea(), val2.getColumna());
            return op;
        }
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("numerico"))
        {
            int va1 = 0;
            if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
            double va2 = Double.parseDouble(val2.getValor());
            double res = va1 + va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(),val2.getColumna());
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("boolean"))
        {
            int va2 = 0;
            if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
            double va1 = Double.parseDouble(val1.getValor());
            double res = va1 + va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(),val2.getColumna());
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))
        {
            double va1 = 0;
            double va2 = 0;
            va1 = Double.parseDouble(val1.getValor());
            va2 = Double.parseDouble(val2.getValor());
            double res = va1+va2;
            return new NodoOperacion(String.valueOf(res),"numerico",val2.getLinea(), val2.getColumna());
        }
        if(val1.getTipo().equals("cadena") || val2.getTipo().equals("cadena"))
        {
            String res = val1.getValor()+val2.getValor();
            return new NodoOperacion(res, "cadena", val2.getLinea(), val2.getColumna());
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaResta(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("boolean"))
        {
            
        }
        return new NodoOperacion("error","error",0,0);
    }
}
