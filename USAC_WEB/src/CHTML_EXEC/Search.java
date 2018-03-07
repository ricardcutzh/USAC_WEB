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
import java.util.ArrayList;
import CCSS_EXEC.*;

public class Search {
    
    public Search()
    {
        
    }
    
    public NodoAtributo buscameElAtributoCHTML(ArrayList<NodoAtributo> atributos, String nombreAtributo)
    {
        NodoAtributo busquedad = null;
        for(int x = 0; x< atributos.size(); x++)
        {
            if(atributos.get(x).getNombre().toLowerCase().equals(nombreAtributo.toLowerCase()))
            {
                busquedad = atributos.get(x);
                break;
            }
        }
        return busquedad;
    }
    
    
    
}
