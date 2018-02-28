/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CCSS_EXEC;
import java.util.ArrayList;
/**
 *
 * @author richard
 */
public class Definicion {
    String nombre;
    ArrayList<Elemento> elementos;
    
    public Definicion(String nombre)
    {
        this.nombre = nombre;
        this.elementos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }
    
    public void addElemento(Elemento elemento)
    {
        this.elementos.add(elemento);
    }
    
    public Elemento getElementoAt(int index)
    {
        return this.elementos.get(index);
    }

    public void setElementos(ArrayList<Elemento> elementos) {
        this.elementos = elementos;
    }

    public ArrayList<Elemento> getElementos() {
        return elementos;
    }
    
    public void print_definicion()
    {
        System.out.println("//******************************************************************//");
        System.out.println("//DEFINCION: "+this.nombre);
        System.out.println("//******************************************************************//");
        for(int x = 0; x < this.elementos.size(); x++)
        {
            this.elementos.get(x).print_Elemento();
        }
        System.out.println("//******************************************************************//");
    }
    
    public Elemento busca_elemento(String nombre, String tipo)
    {
        for(int x = 0; x < this.elementos.size(); x++)
        {
            Elemento aux = this.elementos.get(x);
            if(aux.getNombre().equals(nombre) && aux.getTipo().equals(tipo))
            {
                return aux;
            }
        }
        return null;
    }
}
