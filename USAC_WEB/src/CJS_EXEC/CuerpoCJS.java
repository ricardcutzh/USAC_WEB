/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_EXEC;

/**
 *
 * @author richard
 */
import java.util.ArrayList;
import AST.*;
public class CuerpoCJS {
    
    Tabla_Funciones funciones;
    ArrayList<Ambito> ambitos;
    ASTNodo raiz;
    ArrayList<TError> errores_semanticas;
    
    public CuerpoCJS(ArrayList<Ambito> ambitos, ASTNodo raiz, Tabla_Funciones funciones)
    {
        this.ambitos = ambitos;
        this.raiz = raiz;
        this.funciones = funciones;
        this.errores_semanticas = new ArrayList<>();
    }
    
    public Variable busca_en_Ambitos(String nombre)
    {
        Variable aux = null;
        for(int x = 0; x < this.ambitos.size(); x++)
        {
            aux = this.ambitos.get(x).busca_Variable(nombre);
            if(aux!=null)
            {
                return aux;
            }
        }
        return aux;
    }
    
    public Variable busca_vector_Ambitos(String nombre)
    {
        Variable aux = null;
        for(int x = 0; x < this.ambitos.size(); x++)
        {
            aux = this.ambitos.get(x).busca_vector(nombre);
            if(aux!=null)
            {
                return aux;
            }
        }
        return aux;
    }
    
    
    public boolean ejecutaCuerpo()
    {
        try 
        {
            //AQUI INICIO EJECUCION
            inicia_ejecucion(raiz);
            return true;
        } catch (Exception e) 
        {
            System.err.println("Error al ejecutar el cuerpo de CJS");
            return false;
        }
    }
    
    private Object inicia_ejecucion(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "CUERPO_CJS":
            {
                if(raiz.contarHijos()==2)
                {
                    //ME VOY MAS A LA IZQUIERDA
                    inicia_ejecucion(raiz.getHijo(0));
                    //ME VOY A LA DERECHA
                    inicia_ejecucion(raiz.getHijo(1));
                }
                if(raiz.contarHijos()==1)
                {
                    //BAJO EN EL ARBOL
                    inicia_ejecucion(raiz.getHijo(0));
                }
                break;
            }
            case "C_VAR_1"://VARIABLES QUE SOLO FUERON DECLARADAS
            {
                if(raiz.contarHijos()==1)
                {
                    //ME VOY A LA LISTA DE IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    if(ids!=null)
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = new Variable(ids.get(x).getEtiqueta());
                            var.setEsVector(false);
                            if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                            {
                                //SE GENERA UN ERROR DE SEMANTICA AGREGAR LA LINEA Y LA COLUMNA
                                TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                            
                        }
                    }
                }
                break;
            }
            case "C_VAR_2":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(ids!=null && !res.getValor().equals("error"))
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = new Variable(ids.get(x).getEtiqueta());
                            var.setEsVector(false);
                            var.setTipo(res.getTipo());
                            var.addValor(res.getValor());
                            if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                }
                break;
            }
            case"C_VAR_3":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    NodoOperacion res = (NodoOperacion)inicia_ejecucion(raiz.getHijo(1));//DIMENSION DEL ARRAY
                    if(ids!=null && res!=null)
                    {
                        if(res.getTipo().equals("numerico"))
                        {
                            for(int x = 0; x < ids.size(); x++)
                            {
                                Variable var = new Variable(ids.get(x).getEtiqueta());
                                var.setEsVector(true);
                                var.setTipo("vector");
                                ArrayList<NodoOperacion> valores = new ArrayList<>();
                                int dim = Integer.parseInt(res.getValor());
                                var.setDimension(dim);
                                for(int d = 0; d < dim; d++)
                                {
                                    valores.add(new NodoOperacion("0","0",0,0));
                                }
                                var.setValVectores(valores);
                                if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                                {
                                    TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                    this.errores_semanticas.add(error);
                                }
                            }
                        }
                    }
                }
                break;
            }
            case "C_VAR_4":
            {
                if(raiz.contarHijos()==4)
                {
                    //OBTENIENDO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //DIMENSION
                    NodoOperacion dimension = (NodoOperacion)inicia_ejecucion(raiz.getHijo(1));
                    //PRIMERA POSICION
                    Expresion exp1 = new Expresion(funciones, ambitos, raiz.getHijo(2), errores_semanticas);
                    NodoOperacion prPos = (NodoOperacion)exp1.evaluaExpresion();
                    //LISTA DE EXPRESIONES QUE HABRIAN OBTENIDAS DEL VECTOR
                    ArrayList<NodoOperacion> ops = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(3));
                    if(ids!=null && dimension.getTipo().equals("numerico") && !prPos.getValor().equals("error") && ops!=null)
                    {
                        int dim = Integer.parseInt(dimension.getValor());
                        ops.add(0, prPos);
                        int size = ops.size();
                        if(dim == size)
                        {
                            for(int x = 0; x< ids.size(); x++)
                            {
                                Variable var = new Variable(ids.get(x).getEtiqueta());
                                var.setEsVector(true);
                                var.setTipo("vector");
                                var.setValVectores(ops);
                                var.setDimension(dim);
                                if(!this.ambitos.get(x).agregaVariableAlAmbito(var))
                                {
                                    TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                    this.errores_semanticas.add(error);
                                }
                            }
                        }
                        else
                        {
                            //ERROR SEMANTICO
                            TError error = new TError("Dimension: "+String.valueOf(dim), "Error Semantico", "El tamanio del vector no coincide con valores",dimension.getLinea(), dimension.getColumna());
                            this.errores_semanticas.add(error);
                        }
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Declaracion Invalida","Error Semantico","Parametros invalidos al declara un vector",dimension.getLinea(), dimension.getColumna());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "C_VAR_5":
            {
                if(raiz.contarHijos()==3)
                {
                    //OBTENIENDO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //PRIMERA POSICION
                    Expresion exp1 = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion prPos = (NodoOperacion)exp1.evaluaExpresion();
                    //LISTA DE EXPRESIONES QUE HABRIAN OBTENIDAS DEL VECTOR
                    ArrayList<NodoOperacion> ops = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(2));
                    if(ids!=null && !prPos.getValor().equals("error") && ops!=null)
                    {
                        ops.add(0,prPos);
                        int dimen = ops.size();
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = new Variable(ids.get(x).getEtiqueta());
                            var.setEsVector(true);
                            var.setTipo("vector");
                            var.setDimension(dimen);
                            var.setValVectores(ops);
                            if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                       TError error = new TError("Declaracion Invalida","Error Semantico","Parametros invalidos al declara un vector",prPos.getLinea(), prPos.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "C_VAR_6":
            {
                //AUN PENDIENTE
                break;
            }
            case "AS_VAR_1":
            {
                if(raiz.contarHijos()==2)
                {
                    //OBTENGO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //OBTENER LA EXPRESION
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(ids!=null && !res.getValor().equals("error"))
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = busca_en_Ambitos(ids.get(x).getEtiqueta());
                            if(var!=null)
                            {
                                var.setEsVector(false);
                                var.setTipo(res.getTipo());
                                var.setValor(res.getValor());
                            }
                            else
                            {
                                //ERROR SEMANTICO
                                TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Asignacion a variable no declarada", ids.get(x).getLine(), ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Asingnacion Invalida","Error Semantico","Parametros invalidos para la asignacion",res.getLinea(), res.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "AS_VAR_2":
            {
                if(raiz.contarHijos()==3)
                {
                    //OBTENGO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //OBTENER EL INDEX AL QUE ME ESTOY REFIRIENDO
                    NodoOperacion index = (NodoOperacion)inicia_ejecucion(raiz.getHijo(1));
                    //EL NUEVO VALOR A ASIGNAR
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(2), errores_semanticas);
                    NodoOperacion valor = (NodoOperacion)exp.evaluaExpresion();
                    if(ids!=null && index.getTipo().equals("numerico") && !valor.getValor().equals("error"))
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = busca_vector_Ambitos(ids.get(x).getEtiqueta());
                            if(var!=null)
                            {
                                int dimension = var.getDimension();
                                int indice = Integer.parseInt(index.getValor());
                                if(indice < dimension)
                                {
                                    var.set_val_index(indice, valor);
                                }
                                else
                                {
                                    TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Indice fuera de limites del Vector", ids.get(x).getLine(), ids.get(x).getColumn());
                                }
                            }
                            else
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Asignacion a vector no declarada", ids.get(x).getLine(), ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                        TError error = new TError("Asignacion Invalida","Error Semantico","Parametros invalidos para la asignaciona un vector",valor.getLinea(), valor.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "AS_VAR_3":
            {
                if(raiz.contarHijos()==3)
                {
                    //LISTA DE IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //PRIMERA EXPRESION
                    Expresion primera = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion first = (NodoOperacion)primera.evaluaExpresion();
                    //OBTENGO LOS DEMAS VALORES
                    ArrayList<NodoOperacion> valores = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(2));
                    if(ids!=null && !first.getValor().equals("error") && valores!=null)
                    {
                        valores.add(0, first);
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable vec = busca_vector_Ambitos(ids.get(x).getEtiqueta());
                            if(vec!=null)
                            {
                                int dimension = vec.getDimension();
                                int vals = valores.size();
                                if(dimension == vals)
                                {
                                    vec.setValVectores(valores);
                                }
                                else
                                {
                                   TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Dimension y Cantidad de Valores no Coinciden", ids.get(x).getLine(), ids.get(x).getColumn());
                                    this.errores_semanticas.add(error); 
                                }
                            }
                            else
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Asignacion a vector no declarada", ids.get(x).getLine(), ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                        TError error = new TError("Asignacion Invalida","Error Semantico","Parametros invalidos para la asignaciona un vector",first.getLinea(), first.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "INDEX":
            {
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    return op;
                }
                break;
            }
            case "ARRAY":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoOperacion> op = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(1));
                    if(op==null)
                    {
                        op = new ArrayList<>();
                    }
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(!res.getValor().equals("error"))
                    {
                        op.add(0,res);
                    }
                    return op;
                }
                if(raiz.contarHijos()==1)
                {
                    ArrayList<NodoOperacion> op = new ArrayList<>();
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(!res.getValor().equals("error"))
                    {
                        op.add(res);
                    }
                    return op;
                }
                break;
            }
            case "L_ID":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    if(ids==null)
                    {
                        ids = new ArrayList<>();
                    }
                    ids.add(raiz.getHijo(1));
                    return ids;
                }
                if(raiz.contarHijos()==1)
                {
                    
                    ArrayList<ASTNodo> ids = new ArrayList<>();
                    ids.add(raiz.getHijo(0));
                    return ids;
                }
            }
            case "DIMENSION":
            {
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    return res;
                }
                break;
            }
        }
        return null;
    }
     
    
}
