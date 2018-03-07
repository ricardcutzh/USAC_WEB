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
public class Elemento {
    
    String nombre;
    String tipo;
    ArrayList<Atributo> atributos;
    
    public Elemento(String nombre, String tipo)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.atributos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
    
    public Atributo getAtributoAt(int Index)
    {
        return this.atributos.get(Index);
    }
    
    public void addAtributo(Atributo atributo)
    {
        this.atributos.add(atributo);
    }
    
    public Atributo getAtributo(int Index)
    {
        return this.atributos.get(Index);
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(ArrayList<Atributo> atributos) {
        this.atributos = atributos;
    }
    
    public void print_Elemento()
    {
        System.out.println("    //////////////////////////////////////////////////////////////////////");
        System.out.println("    ////            "+this.nombre+" //// "+this.tipo+"              //////");
        System.out.println("    //////////////////////////////////////////////////////////////////////");
        for(int x = 0; x < this.atributos.size();x++)
        {
            this.atributos.get(x).print_Atributo();
        }
        System.out.println("    //////////////////////////////////////////////////////////////////////");
    }
    
    public Atributo buscaAtributo(String nombre)
    {
        Atributo at= null;
        for(int x = 0; x < this.atributos.size(); x++ )
        {
            
            if(this.atributos.get(x).getNombreAtributo().toLowerCase().equals(nombre))
            {
                at = this.atributos.get(x);
                return at;
            }
        }
        return at;
    }
}
