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
    ArrayList<NodoOperacion> valVectores;
    int dimension;
    
    public Variable(String identificador)
    {
        this.identificador = identificador;
        this.tipo = "";
        this.valores = new ArrayList<>();
        this.esVector = false;
        this.valVectores = new ArrayList<>();
    }
    
    public String variableATexto()
    {
        if(this.esVector)
        {
            String ret = "[";
            for(int x = 0; x < this.valVectores.size(); x++)
            {
                if(!(x==this.valVectores.size()-1))
                {
                    ret = ret + this.valVectores.get(x).getValor()+",";
                }
                else
                {
                    ret = ret + this.valVectores.get(x).getValor();
                }
            }
            ret = ret + "]";
            return ret;
        }
        else
        {
            return this.getValor();
        }
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
    
    public void addValor(String val)
    {
        this.valores.add(val);
    }
    
    public String getValor()
    {
        return this.valores.get(0);
    }

    public ArrayList<NodoOperacion> getValVectores() {
        return valVectores;
    }

    public void setValVectores(ArrayList<NodoOperacion> valVectores) {
        this.valVectores = valVectores;
    }
    
    public NodoOperacion index_vector(int index)
    {
        return this.valVectores.get(index);
    }
    
    public void set_val_index(int index, NodoOperacion val)
    {
        this.valVectores.set(index, val);
        //this.valVectores.remove(index+1);
    }
    
    public int getSizeVector()
    {
        return this.valVectores.size();
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
    
    public void setValor(String valor)
    {
        this.valores.clear();
        this.valores.add(valor);
    }
}
