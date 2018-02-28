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
import AST.ASTNodo;
public class Funcion {
    
    String nombre;//NOMBRE DE LA FUNCION EN CUESTION
    ASTNodo raiz;//SENTENCIAS QUE EJECUTA
    ArrayList<Parametro> parametros;//PARAMETROS QUE TIENE
    
    public Funcion(String nombre, ASTNodo raiz)
    {
        this.nombre = nombre;
        this.raiz = raiz;
    }
}
