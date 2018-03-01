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
    
    public boolean ejecutaCuerpo()
    {
        try 
        {
            //AQUI INICIO EJECUCION
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
        }
        return null;
    }
     
    
}
