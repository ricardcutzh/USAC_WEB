/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;

/**
 *
 * @author richard
 */
import AST.TError;
import CJS_EXEC.*;
import java.util.ArrayList;
public class CJS_OB {
    
    Tabla_Funciones funciones;
    Tabla_Variables variables;
    ArrayList<TError> errores;
    ArrayList<NodoImprimir> impresion;
    
    public CJS_OB()
    {
        this.funciones = new Tabla_Funciones();
        this.variables = new Tabla_Variables();
        this.errores = new ArrayList<>();
        this.impresion = new ArrayList<>();
    }

    public Tabla_Funciones getFunciones() {
        return funciones;
    }

    public void setFunciones(Tabla_Funciones funciones) {
        this.funciones = funciones;
    }

    public Tabla_Variables getVariables() {
        return variables;
    }

    public void setVariables(Tabla_Variables variables) {
        this.variables = variables;
    }
    
    
    public boolean ejecutaCSJ(AST.ASTNodo raiz)
    {
        try {
            Interprete_CJS interprete = new Interprete_CJS(raiz);
            if(interprete.ejecutaCJS())
            {
                this.errores = interprete.getErrores();
                this.funciones = interprete.getFunciones();
                this.variables = interprete.getVariable();
                this.impresion = interprete.getImpresion();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean ejecutaCJS(AST.ASTNodo raiz, Tabla_Variables variables, Tabla_Funciones funciones)
    {
        try {
            Interprete_CJS interprete = new Interprete_CJS(raiz, variables, funciones);
            if(interprete.ejecutaCJSAlternativo())
            {
                this.errores = interprete.getErrores();
                this.impresion = interprete.getImpresion();
                return  true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean agregarVariables(Variable var)
    {
        if(!this.variables.AddVariable(var))
        {
            return false;
        }
        else return true;
    }
    
    public boolean agregarFuncion(Funcion f)
    {
        if(!this.funciones.addFuncion(f))
        {
            return false;
        }
        return true;
    }

    public ArrayList<NodoImprimir> getImpresion() {
        return impresion;
    }
    
    
}
