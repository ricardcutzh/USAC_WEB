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
public class NodoImprimir {
    
    String mensaje;
    int linea;
    int columna;
    
    public NodoImprimir(String mensaje, int linea, int columna)
    {
        this.mensaje = mensaje;
        this.linea = linea;
        this.columna = columna;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
    
    
}
