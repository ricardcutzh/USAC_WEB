/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_EXEC;

import java.util.ArrayList;

/**
 *
 * @author richard
 */
public class Variable {
    
    String identificador;//NOMBRE DE LA VARIABLE
    String tipo;//TIPO DE DATO
    boolean esVector;
    ArrayList<String> valores;
    
    public Variable(String identificador)
    {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEsVector() {
        return esVector;
    }

    public void setEsVector(boolean esVector) {
        this.esVector = esVector;
    }

    public ArrayList<String> getValores() {
        return valores;
    }

    public void setValores(ArrayList<String> valores) {
        this.valores = valores;
    }
    
    public String getValor()
    {
        return this.valores.get(0);
    }
    
}
