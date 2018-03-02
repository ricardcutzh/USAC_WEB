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
public class Ambito {
    
    Tabla_Variables tabla_simbolos;
    String nombre;
    
    public Ambito(String nombre)
    {
        this.nombre = nombre;
        tabla_simbolos = new Tabla_Variables();
    }

    public Tabla_Variables getTabla_simbolos() {
        return tabla_simbolos;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Variable busca_Variable(String nombre)
    {
        return tabla_simbolos.buscaVariable(nombre);
    }
    
    public Variable busca_vector(String nombre)
    {
        return tabla_simbolos.buscaVector(nombre);
    }
    
    public boolean agregaVariableAlAmbito(Variable var)
    {
        if(tabla_simbolos.AddVariable(var))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void imprime_tabla()
    {
        this.tabla_simbolos.imprime_variables();
    }
}
