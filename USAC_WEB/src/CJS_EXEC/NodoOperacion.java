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
public class NodoOperacion {
    
    String valor;
    String tipo;
    int linea;
    int columna;
    ArrayList<NodoOperacion>valVectores;
    
    
    public NodoOperacion(String valor, String tipo, int linea, int columna)
    {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.valVectores = new ArrayList<>();
    }

    public String getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ArrayList<NodoOperacion> getValVectores() {
        return valVectores;
    }

    public void setValVectores(ArrayList<NodoOperacion> valVectores) {
        this.valVectores = valVectores;
    }
    
    
    
}
