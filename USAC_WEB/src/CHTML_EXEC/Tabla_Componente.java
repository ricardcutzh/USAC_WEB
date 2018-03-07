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
import javax.swing.*;

public class Tabla_Componente {
    
    ArrayList<Component> componentes;
    
    public Tabla_Componente()
    {
        this.componentes = new ArrayList<>();
    }
    
    public Component buscaComponente(String id)
    {
        for(Component c : componentes)
        {
            if(c.getId().equals(id))
            {
                return c;
            }
        }
        return null;
    }
    
    
    public Component buscaComponenteTipo(String id, String tipo)
    {
        for(Component c : componentes)
        {
            if(c.getId().equals(id) && c.getTipo().equals(tipo))
            {
                return c;
            }
        }
        return null;
    }
    
    public void addComponenete(Component componente)
    {
        this.componentes.add(componente);
    }
    
    
}
