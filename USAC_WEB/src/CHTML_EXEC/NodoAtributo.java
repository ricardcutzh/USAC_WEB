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
public class NodoAtributo {
    String nombre;
    String valor;
    
    public NodoAtributo(String nombre, String valor)
    {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }
    
    
}
