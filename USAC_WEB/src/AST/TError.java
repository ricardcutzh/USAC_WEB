/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author richard
 */
public class TError {
    /*
    * ESTA CLASE ES PARA CREAR LOS ERRORES QUE SE DETECTEN EN CUALQUIERA DE LOS 
    * 3 LENGUAJES
    */
   
    String lexema;
    String tipo;
    String descripcion;
    int line;
    int column;
    
    public TError(String lexema, String tipo, String descripcion, int line, int column)
    {
        this.lexema = lexema;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.line = line;
        this.column = column;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
    
    
}
