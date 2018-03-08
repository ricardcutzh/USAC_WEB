/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.File;

/**
 *
 * @author richard
 */
public class ValidadorRuta {
    
    String ruta;
    
    public ValidadorRuta(String ruta)
    {
        this.ruta = ruta;
    }
    
    
    public boolean esValida()
    {
        try {
            File f = new File(ruta);
            if(f.exists())
            {
                return true;
            }
            return  false;
        } catch (Exception e) {
            
            return false;
        }
    }
    
    
}
