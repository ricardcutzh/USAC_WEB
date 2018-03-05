/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_EXEC;

import AST.ASTNodo;
import AST.TError;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public ArrayList<TError> getErrores_semanticas() {
        return errores_semanticas;
    }
    
    public Object evalua(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "EXPRESION":
            {
                if(raiz.contarHijos()==1)
                {
                    NodoOperacion retorno = (NodoOperacion)evalua(raiz.getHijo(0));
                    return retorno;
                }
                break;
            }
            case "E":
            {
                if(raiz.contarHijos()==3)
                {
                    /////////////////////////////////ARITMETICAS////////////////////////////////////////////////
                    if(raiz.getHijo(1).getEtiqueta().equals("+"))//SUMA
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaSuma(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Suma entre: "+val1.getTipo()+" y "+val2.getTipo()+" incompatible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("-"))//RESTA
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaResta(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Resta entre: "+val1.getTipo()+" y "+val2.getTipo()+" incompatible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("*"))//MULTIPLICACION
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaMultiplicacion(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Multiplicacion entre: "+val1.getTipo()+" y "+val2.getTipo()+" incompatible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("/"))//DIVISION
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaDivision(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Division entre: "+val1.getTipo()+" y "+val2.getTipo()+" incompatible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("%"))//MODULAR
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaModulo(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Modulo entre: "+val1.getTipo()+" y "+val2.getTipo()+" incompatible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("^"))//ELEVADO
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaElevado(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Elevado entre: "+val1.getTipo()+" y "+val2.getTipo()+" incompatible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    /////////////////////////////////RELACIONALES////////////////////////////////////////////////
                    if(raiz.getHijo(1).getEtiqueta().equals("=="))//IGUALACION
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaIgualacion(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Comparacion igualacion Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("!="))//DIFERENTE
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaDiferencia(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Comparacion diferencia Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("<"))//MENOR
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaMenor(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Comparacion menor Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals(">"))//MAYOR
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaMayor(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Comparacion mayor Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("<="))//MENOR IGUAL
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaMenorIgual(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Comparacion menor o igual Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals(">="))//MAYOR IGUAL
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaMayorIgual(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Comparacion mayor o igual Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    ////////////////////////////////////LOGICAS////////////////////////////////////////
                    if(raiz.getHijo(1).getEtiqueta().equals("AND"))
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaAND(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Operacion AND, Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("OR"))
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(2));
                        NodoOperacion res = realizaOR(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor()+ " | "+val2.getValor(), "Error Semantico", "Operacion OR, Invalida entre: "+val1.getTipo()+" y "+val2.getTipo(), res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                }
                if(raiz.contarHijos()==2)
                {
                    if(raiz.getHijo(1).getEtiqueta().equals("++"))
                    {
                        NodoOperacion val1 = new NodoOperacion("1", "numerico", 0, 0);
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion res = realizaSuma(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val2.getValor(), "Error Semantico", "Aumentar: "+val2.getTipo()+" no es posible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(1).getEtiqueta().equals("--"))
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(0));
                        NodoOperacion val2 = new NodoOperacion("1","numerico",val1.getLinea(), val1.getColumna());
                        NodoOperacion res = realizaResta(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor(), "Error Semantico", "Disminuir: "+val1.getTipo()+" no es posible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(0).getEtiqueta().equals("MENOS"))
                    {
                        NodoOperacion val1 = new NodoOperacion("-1", "numerico", 0, 0);
                        NodoOperacion val2 = (NodoOperacion)evalua(raiz.getHijo(1));
                        NodoOperacion res = realizaMultiplicacion(val1, val2);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val2.getValor(), "Error Semantico", "Mutiplicacion por -1: "+val2.getTipo()+" no es posible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
                    }
                    if(raiz.getHijo(0).getEtiqueta().equals("NOT"))
                    {
                        NodoOperacion val1 = (NodoOperacion)evalua(raiz.getHijo(1));
                        NodoOperacion res = realizaNOT(val1);
                        if(res.getTipo().equals("error"))
                        {
                            TError error = new TError(val1.getValor(), "Error Semantico", "Operacion NOT en tipos: "+val1.getTipo()+" no es posible", res.getLinea(),res.getColumna());
                            errores_semanticas.add(error);
                            return res;
                        }
                        else
                        {
                            return res;
                        }
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
                if(raiz.contarHijos()==2)
                {
                    String nombreVector = raiz.getHijo(0).getEtiqueta();
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(op.getTipo().equals("numerico"))
                    {
                        Variable vec = this.ambitos.get(0).busca_vector(nombreVector);
                        if(vec==null)
                        {
                            vec = busca_vector(nombreVector);
                        }
                        if(vec!=null)
                        {
                            int index = Integer.parseInt(op.getValor());
                            if(index < vec.getDimension())
                            {
                                NodoOperacion res = vec.index_vector(index);
                                return res;
                            }
                            else
                            {
                                TError error = new TError("Vector: "+nombreVector,"Error Semantico","Indice Fuera de las dimensiones del vector",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                        else
                        {
                            TError error = new TError("Vector: "+nombreVector,"Error Semantico","El vector especificado no existe",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                            this.errores_semanticas.add(error);
                        }
                    }
                }
                break;
            }
            case "CONTEO":
            {
                if(raiz.contarHijos()==1)
                {
                    String nombreVector = raiz.getHijo(0).getEtiqueta();
                    Variable vector = this.ambitos.get(0).busca_vector(nombreVector);
                    if(vector==null){vector = busca_vector(nombreVector);}
                    if(vector!=null)
                    {
                        NodoOperacion res = new NodoOperacion(String.valueOf(vector.getDimension()),"numerico", raiz.getLine(), raiz.getColumn());
                        return res;
                    }
                    else
                    {
                        TError error = new TError("Vector: "+nombreVector,"Error Semantico","El vector especificado no existe",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "ATEXTO":
            {
                if(raiz.contarHijos()==1)
                {
                    String nombre = raiz.getHijo(0).getEtiqueta();
                    Variable var = this.ambitos.get(0).busca_Variable(nombre);
                    if(var==null){var = this.ambitos.get(0).busca_vector(nombre);}
                    if(var==null){var = busca_Var_Global(nombre);}
                    if(var==null){var = this.ambitos.get(0).busca_vector(nombre);}
                    if(var==null){var = busca_vector(nombre);}
                    if(var!=null)
                    {
                        NodoOperacion op = new NodoOperacion(var.variableATexto(), "cadena", raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        return op;
                    }
                }
                break;
            }
            case "EXEC_FUNC":
            {
                if(raiz.contarHijos()==2)
                {
                    String nombre = raiz.getHijo(0).getEtiqueta();
                    ArrayList<NodoOperacion> parametros = (ArrayList<NodoOperacion>)ejecutaAuxiliar(raiz.getHijo(1));
                    if(parametros!=null)
                    {
                        Funcion f = this.funciones.buscaFuncion(nombre, parametros.size());
                        if(f!=null)
                        {
                           //VALIDACION DE LA CANTIDAD DE PARAMETROS
                            if(f.getNumeroParametros() == parametros.size())
                            {
                                Ambito ambito = new Ambito(nombre);
                                this.ambitos.add(0, ambito);
                                for(int x = 0; x < parametros.size() ; x++)
                                {
                                    Variable var = new Variable(f.getParametroIndex(x).getIdParametro());
                                    var.setEsVector(false);
                                    var.setTipo(parametros.get(x).getTipo());
                                    var.setValor(parametros.get(x).getValor());
                                    this.ambitos.get(0).agregaVariableAlAmbito(var);
                                }
                                //EJECUTO LA FUNCION
                                if(f.getRaiz()!=null)
                                {
                                    CuerpoCJS cuerpo = new CuerpoCJS(ambitos, f.getRaiz(), funciones);
                                    cuerpo.ejecutaCuerpo();
                                    if(cuerpo.huboReturn)
                                    {
                                       NodoOperacion res = cuerpo.getRetorno();
                                       if(res!=null)
                                       {
                                           this.ambitos.remove(0);
                                           return res;
                                       }
                                    }
                                }
                                this.ambitos.remove(0);
                            }
                            else
                            {
                                TError error = new TError("Funcion: "+nombre,"Error Semantico","La cantidad de parametros no coinciden",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                        else
                        {
                            TError error = new TError("Funcion: "+nombre,"Error Semantico","No existe una funcion declarada como: "+nombre,raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                            this.errores_semanticas.add(error);
                        }
                    }
                    else
                    {
                        TError error = new TError("Funcion: "+nombre,"Error Semantico","Esta funcion requiere parametros validos",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                if(raiz.contarHijos()==1)
                {
                    String nombre = raiz.getHijo(0).getEtiqueta();
                    Funcion f = this.funciones.buscaFuncion(nombre, 0);
                    if(f!=null)
                    {
                        Ambito ambito = new Ambito(nombre);
                        this.ambitos.add(0, ambito);
                        if(f.getRaiz()!=null)
                        {
                            CuerpoCJS cuerpo = new CuerpoCJS(ambitos,f.getRaiz(), funciones);
                            cuerpo.ejecutaCuerpo();
                            if(cuerpo.huboReturn)
                            {
                                NodoOperacion res = cuerpo.getRetorno();
                                if(res!=null)
                                {
                                    return res;
                                }
                            }
                        }
                        this.ambitos.remove(0);
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Funcion: "+nombre,"Error Semantico","No existe una funcion declarada como: "+nombre,raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private Object ejecutaAuxiliar(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "PARAMS":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoOperacion> parametros = (ArrayList<NodoOperacion>)ejecutaAuxiliar(raiz.getHijo(0));
                    if(parametros==null)
                    {
                        parametros = new ArrayList<>();
                    }
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(!op.getValor().equals("error"))
                    {
                        parametros.add(op);
                    }
                    return parametros;
                }
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    ArrayList<NodoOperacion> parametros = new ArrayList<>();
                    if(!op.getValor().equals("error"))
                    {
                        parametros.add(op);
                    }
                    return parametros;
                }
                break;
            }
        }
        return null;
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
    
    public Variable busca_vector(String nombre)
    {
        for(int x = 1; x < this.ambitos.size(); x++)
        {
            Variable aux = this.ambitos.get(x).busca_vector(nombre);
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
            double va1 = Double.parseDouble(val1.getValor());
            double va2 = Double.parseDouble(val2.getValor());
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
            double va1 = Double.parseDouble(val1.getValor());
            double va2 = 0;
            if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
            double res = va1 - va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))
        {
            double va1 = Double.parseDouble(val1.getValor());
            double va2 = Double.parseDouble(val2.getValor());
            double res = va1 - va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
        }
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("numerico"))
        {
            double va1 = 0;
            double va2 = Double.parseDouble(val2.getValor());
            if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
            double res = va1 - va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaMultiplicacion(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("boolean"))
        {
            boolean va1= false, va2 = false;
            if(val1.getValor().toLowerCase().equals("true")){va1 = true;}
            if(val2.getValor().toLowerCase().equals("true")){va2 = true;}
            boolean res = va1 && va2;
            return new NodoOperacion(String.valueOf(res),"boolean",val2.getLinea(), val2.getColumna());
        }
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("numerico"))
        {
            double va1 = 0;
            if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
            double va2 = Double.parseDouble(val2.getValor());
            double res = va1 * va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("boolean"))
        {
            double va1 = Double.parseDouble(val1.getValor());
            double va2 = 0;
            if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
            double res = va1 * va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))
        {
            double va1 = Double.parseDouble(val1.getValor());
            double va2 = Double.parseDouble(val2.getValor());
            double res = va1 * va2;
            return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna()); 
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaDivision(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))
        {
            try 
            {
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                double res = va1 / va2;
                return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
            } catch (Exception e) 
            {               
                System.err.println("Error en division: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaModulo(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))
        {
            try {
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                double res = va1 % va2;
                return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
            } 
            catch (Exception e) {
                System.err.println("Error en modulo: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("boolean"))
        {
            try 
            {  
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                double res = va1 % va2;
                return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
            } 
            catch (Exception e) 
            {
                System.err.println("Error en modulo: "+e.toString());
            } 
        }
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("numerico"))
        {
            try {
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                double res = va1 % va2;
                return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
            } catch (Exception e) {
                System.err.println("Error en modulo: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaElevado(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("numerico"))
        {
            try 
            {
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                double res = Math.pow(va1, va2);
                return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
            } catch (Exception e) {
                System.err.println("Error en Elevado: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("boolean"))
        {
            try 
            {
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                double res = Math.pow(va1, va2);
                return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
            } 
            catch (Exception e) {
                System.err.println("Error en Elevado: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))
        {
            try 
            {
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                double res = Math.pow(va1, va2);
                return new NodoOperacion(String.valueOf(res),"numerico", val2.getLinea(), val2.getColumna());
            } catch (Exception e) {
                System.err.println("Error en Elevado: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaIgualacion(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 == va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") && val2.getTipo().equals("numerico"))//CADENA / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va2 = Double.parseDouble(val2.getValor());
                if(va2 == val1.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("cadena"))// NUMERICO / CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                if(va1 == val2.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("date"))// DATE / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat va1 = new SimpleDateFormat("dd/MM/yyyy");
                Date v1 = va1.parse(val1.getValor());
                Date v2 = va1.parse(val2.getValor());
                if(v1.equals(v2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("datetime"))// DATE / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("date"))// DATETIME / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("datetime"))// DATETIME / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d1.parse(val2.getValor());
                if(da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("numerico"))// BOOLEANO / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 == va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("booleano"))// NUMERICO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                if(va1 == va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) 
            {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("booleano"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(val1.getValor().toLowerCase().equals(val2.getValor().toLowerCase()))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") || val2.getTipo().equals("cadena"))//SI NO ES NINGUNA DE LAS ANTERIORES HACE LA COMPARACION COMO CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(val1.getValor().equals(val2.getValor()))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Igualacion: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaDiferencia(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 != va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") && val2.getTipo().equals("numerico"))//CADENA / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va2 = Double.parseDouble(val2.getValor());
                if(va2 != val1.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("cadena"))// NUMERICO / CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                if(va1 != val2.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("date"))// DATE / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat va1 = new SimpleDateFormat("dd/MM/yyyy");
                Date v1 = va1.parse(val1.getValor());
                Date v2 = va1.parse(val2.getValor());
                if(!v1.equals(v2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("datetime"))// DATE / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(!da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("date"))// DATETIME / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(!da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("datetime"))// DATETIME / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d1.parse(val2.getValor());
                if(!da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("numerico"))// BOOLEANO / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 != va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("booleano"))// NUMERICO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                if(va1 != va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) 
            {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("booleano"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(!val1.getValor().toLowerCase().equals(val2.getValor().toLowerCase()))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") || val2.getTipo().equals("cadena"))//SI NO ES NINGUNA DE LAS ANTERIORES HACE LA COMPARACION COMO CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(!val1.getValor().equals(val2.getValor()))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Diferencia: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }

    private NodoOperacion realizaMenor(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 < va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") && val2.getTipo().equals("numerico"))//CADENA / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va2 = Double.parseDouble(val2.getValor());
                if(val1.getValor().length()<va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("cadena"))// NUMERICO / CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                if(va1 < val2.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("date"))// DATE / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat va1 = new SimpleDateFormat("dd/MM/yyyy");
                Date v1 = va1.parse(val1.getValor());
                Date v2 = va1.parse(val2.getValor());
                if(v1.before(v2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("datetime"))// DATE / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.before(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("date"))// DATETIME / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.before(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("datetime"))// DATETIME / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d1.parse(val2.getValor());
                if(da1.before(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("numerico"))// BOOLEANO / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 < va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("booleano"))// NUMERICO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                if(va1 < va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) 
            {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("booleano"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                int va1 = 0;
                int va2 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1=1;}
                if(val2.getValor().toLowerCase().equals("true")){va2=1;}
                if(va1 < va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") || val2.getTipo().equals("cadena"))//SI NO ES NINGUNA DE LAS ANTERIORES HACE LA COMPARACION COMO CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(val1.getValor().compareTo(val2.getValor())==-1)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaMayor(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 > va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") && val2.getTipo().equals("numerico"))//CADENA / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va2 = Double.parseDouble(val2.getValor());
                if(val1.getValor().length()>va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("cadena"))// NUMERICO / CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                if(va1 > val2.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("date"))// DATE / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat va1 = new SimpleDateFormat("dd/MM/yyyy");
                Date v1 = va1.parse(val1.getValor());
                Date v2 = va1.parse(val2.getValor());
                if(v1.after(v2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("datetime"))// DATE / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.after(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("date"))// DATETIME / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.after(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("datetime"))// DATETIME / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d1.parse(val2.getValor());
                if(da1.after(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("numerico"))// BOOLEANO / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 > va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("booleano"))// NUMERICO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                if(va1 > va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) 
            {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("booleano"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                int va1 = 0;
                int va2 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1=1;}
                if(val2.getValor().toLowerCase().equals("true")){va2=1;}
                if(va1 > va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") || val2.getTipo().equals("cadena"))//SI NO ES NINGUNA DE LAS ANTERIORES HACE LA COMPARACION COMO CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(val1.getValor().compareTo(val2.getValor())==1)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaMenorIgual(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 <= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") && val2.getTipo().equals("numerico"))//CADENA / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va2 = Double.parseDouble(val2.getValor());
                if(val1.getValor().length()<=va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("cadena"))// NUMERICO / CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                if(va1 <= val2.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("date"))// DATE / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat va1 = new SimpleDateFormat("dd/MM/yyyy");
                Date v1 = va1.parse(val1.getValor());
                Date v2 = va1.parse(val2.getValor());
                if(v1.before(v2) || v1.equals(v2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("datetime"))// DATE / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.before(da2) || da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("date"))// DATETIME / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.before(da2) || da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("datetime"))// DATETIME / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d1.parse(val2.getValor());
                if(da1.before(da2) || da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("numerico"))// BOOLEANO / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 <= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("booleano"))// NUMERICO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                if(va1 <= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) 
            {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("booleano"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                int va1 = 0;
                int va2 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1=1;}
                if(val2.getValor().toLowerCase().equals("true")){va2=1;}
                if(va1 <= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") || val2.getTipo().equals("cadena"))//SI NO ES NINGUNA DE LAS ANTERIORES HACE LA COMPARACION COMO CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(val1.getValor().compareTo(val2.getValor())==-1 || val1.getValor().compareTo(val2.getValor())==0)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Menor igual: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaMayorIgual(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("numerico"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 >= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") && val2.getTipo().equals("numerico"))//CADENA / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va2 = Double.parseDouble(val2.getValor());
                if(val1.getValor().length()>=va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("cadena"))// NUMERICO / CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                if(va1 >= val2.getValor().length())
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("date"))// DATE / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat va1 = new SimpleDateFormat("dd/MM/yyyy");
                Date v1 = va1.parse(val1.getValor());
                Date v2 = va1.parse(val2.getValor());
                if(v1.after(v2) || v1.equals(v2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("date") && val2.getTipo().equals("datetime"))// DATE / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.after(da2) || da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("date"))// DATETIME / DATE
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d2.parse(val2.getValor());
                if(da1.after(da2) || da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("datetime") && val2.getTipo().equals("datetime"))// DATETIME / DATETIME
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date da1 = d1.parse(val1.getValor());
                Date da2 = d1.parse(val2.getValor());
                if(da1.after(da2) || da1.equals(da2))
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("numerico"))// BOOLEANO / NUMERICO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1 = 1;}
                double va2 = Double.parseDouble(val2.getValor());
                if(va1 >= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("numerico") && val2.getTipo().equals("booleano"))// NUMERICO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                double va1 = Double.parseDouble(val1.getValor());
                double va2 = 0;
                if(val2.getValor().toLowerCase().equals("true")){va2 = 1;}
                if(va1 >= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) 
            {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("booleano") && val2.getTipo().equals("booleano"))// BOOLEANO / BOOLEANO
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                int va1 = 0;
                int va2 = 0;
                if(val1.getValor().toLowerCase().equals("true")){va1=1;}
                if(val2.getValor().toLowerCase().equals("true")){va2=1;}
                if(va1 >= va2)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        if(val1.getTipo().equals("cadena") || val2.getTipo().equals("cadena"))//SI NO ES NINGUNA DE LAS ANTERIORES HACE LA COMPARACION COMO CADENA
        {
            try 
            {
                NodoOperacion res = new NodoOperacion("false","boolean",val2.getLinea(), val2.getColumna());
                if(val1.getValor().compareTo(val2.getValor())==1 || val1.getValor().compareTo(val2.getValor())==0)
                {
                    res.setValor("true");
                }
                return res;
            } catch (Exception e) {
                System.err.println("Error en Mayor igual: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaAND(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("boolean"))
        {
            try 
            {
                boolean va1 = false;
                boolean va2 = false;
                if(val1.getValor().toLowerCase().equals("true")){va1 = true;}
                if(val2.getValor().toLowerCase().equals("true")){va2 = true;}
                boolean res = va1 && va2;
                return new NodoOperacion(String.valueOf(res),"boolean",val2.getLinea(), val2.getColumna());
            } catch (Exception e) {
                System.err.println("Error en AND: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaOR(NodoOperacion val1, NodoOperacion val2)
    {
        if(val1.getTipo().equals("boolean") && val2.getTipo().equals("boolean"))
        {
            try 
            {
                boolean va1 = false;
                boolean va2 = false;
                if(val1.getValor().toLowerCase().equals("true")){va1 = true;}
                if(val2.getValor().toLowerCase().equals("true")){va2 = true;}
                boolean res = va1 || va2;
                return new NodoOperacion(String.valueOf(res),"boolean",val2.getLinea(), val2.getColumna());
            } catch (Exception e) {
                System.err.println("Error en OR: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
    
    private NodoOperacion realizaNOT(NodoOperacion val1)
    {
        if(val1.getTipo().equals("boolean"))
        {
            try 
            {
                String v = "";
                if(val1.getValor().toLowerCase().equals("true")){v = "false";}
                else if(val1.getValor().toLowerCase().equals("false")){v = "true";}
                return new NodoOperacion(v, "boolean",val1.getLinea(), val1.getColumna());
            } 
            catch (Exception e) 
            {
                System.err.println("Error en NOT: "+e.toString());
            }
        }
        return new NodoOperacion("error","error",0,0);
    }
}
