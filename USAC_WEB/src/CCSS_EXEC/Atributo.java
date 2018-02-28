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
public class Atributo {
    
    String nombreAtributo;
    ArrayList<String> valores;
    
    public Atributo(String nombreAtributo)
    {
        this.nombreAtributo = nombreAtributo;
        this.valores = new ArrayList<>();
    }

    public String getNombreAtributo() {
        return nombreAtributo;
    }

    public ArrayList<String> getValores() {
        return valores;
    }

    public void agregaValor(String val)
    {
        this.valores.add(val);
    }

    public void setValores(ArrayList<String> valores) {
        this.valores = valores;
    }
    
    public void print_Atributo()
    {
        System.out.print("      //// *- "+this.nombreAtributo+" = ");
        for(int x = 0; x < valores.size();x++)
        {
            System.out.print(valores.get(x)+",");
        }
        System.out.println("");
    }
    
}
