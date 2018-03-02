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
import java.util.ArrayList;
public class Interprete_CJS {
    
    ASTNodo raiz;
    ArrayList<Ambito> ambitos;
    Tabla_Funciones funciones;
    
    public Interprete_CJS(ASTNodo raiz)
    {
        this.raiz = raiz;
        ambitos = new ArrayList<>();
        this.funciones = new Tabla_Funciones();
    }
    
    public boolean ejecutaCJS()
    {
        try 
        {
            Ambito global = new Ambito("global");
            this.ambitos.add(0, global);
            inicio_cjs(raiz);
            this.ambitos.get(0).imprime_tabla();
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
                }
                break;
            }
            
        }
        return null;
    }
}
