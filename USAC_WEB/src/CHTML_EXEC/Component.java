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

public class Component {
    
    JComponent componente;
    String tipo;
    String id;
    
    public Component(JComponent componente, String tipo, String id)
    {
        this.componente = componente;
        this.tipo = tipo;
        this.id = id;
    }

    public JComponent getComponente() {
        return componente;
    }

    public void setComponente(JComponent componente) {
        this.componente = componente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    
}
