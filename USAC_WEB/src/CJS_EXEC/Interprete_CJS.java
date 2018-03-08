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
import AST.ASTNodo;
import AST.TError;
import java.util.ArrayList;
public class Interprete_CJS {
    
    ASTNodo raiz;
    ArrayList<Ambito> ambitos;
    Tabla_Funciones funciones;
    Tabla_Variables variable;
    ArrayList<TError> errores;
    ArrayList<NodoImprimir> impresion;
    
    public Interprete_CJS(ASTNodo raiz)
    {
        this.raiz = raiz;
        ambitos = new ArrayList<>();
        this.funciones = new Tabla_Funciones();
    }
    
    ///////////////////////////////////////////////////////////
    //CONSTRUCTOR ALTERNATIVO PARA PODER MANEJAR VARIABLES EN//
    //MULTIPLES ARCHIVOS A LA VEZ, ES DECIR, LE PASO LAS VAR///
    //QUE YA FUERON DEFINIDAS Y ENTONCES ASI EL OTRO ARCHIVO///
    //YA CUENTA CON LAS MISMAS/////////////////////////////////
    public Interprete_CJS(ASTNodo raiz, Tabla_Variables tabla_variables, Tabla_Funciones funciones)
    {
        this.raiz = raiz;
        this.funciones = funciones;
        this.ambitos = new ArrayList<>();
        this.variable = tabla_variables;
    }
    public boolean  ejecutaCJSAlternativo()
    {
        try 
        {
            Ambito global = new Ambito("global");
            this.ambitos.add(0, global);
            this.ambitos.get(0).setTabla_simbolos(variable);
            inicio_cjs(raiz);
            return true;
        } catch (Exception e) 
        {
            System.err.println("Error en CJS: "+e.toString());
            return false;
        }
    }
    ///////////////////////////////////////////////////////////
    
    public boolean ejecutaCJS()
    {
        try 
        {
            Ambito global = new Ambito("global");
            this.ambitos.add(0, global);
            inicio_cjs(raiz);
            this.ambitos.get(0).imprime_tabla();
            this.funciones.imprime_funciones();
            
            return true;
        } catch (Exception e) 
        {
            System.err.println("Error en CJS: "+e.toString());
            return false;
        }
    }
    
    //METODOS QUE INICIAN CON LA EJECUCION DEL JS
    private Object inicio_cjs(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "INICIO_CJS":
            {
                if(raiz.contarHijos()==1)
                {
                    //EJECUTA UN CUERPO DE CJS
                    CuerpoCJS cuerpo = new CuerpoCJS(ambitos, raiz.getHijo(0), funciones);
                    cuerpo.ejecutaCuerpo();
                    this.funciones = cuerpo.getFunciones();
                    this.variable = cuerpo.dameGlobales();
                    this.impresion = cuerpo.getImp();
                    this.errores = cuerpo.errores_semanticas;
                }
                break;
            }
            
        }
        return null;
    }

    public Tabla_Funciones getFunciones() {
        return funciones;
    }

    public Tabla_Variables getVariable() {
        return variable;
    }

    public ArrayList<TError> getErrores() {
        return errores;
    }

    public ArrayList<NodoImprimir> getImpresion() {
        return impresion;
    }
    
    
}
