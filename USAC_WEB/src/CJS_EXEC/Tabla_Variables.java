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
public class Tabla_Variables {
    ArrayList<Variable> variables;
    
    public Tabla_Variables()
    {
        //INICIALIZACION DE LAS VARIABLES QUE TIENE ESTA TABLA
        variables = new ArrayList<>();
    }
    
    public Variable buscaVariable(String nombre)
    {
        Variable aux;
        for(int x = 0; x < this.variables.size(); x++)
        {
            aux = this.variables.get(x);
            if(aux.getIdentificador().equals(nombre))
            {
                return aux;
            }
        }
        return null;
    }
    
    public boolean AddVariable(Variable var)
    {
        Variable aux = buscaVariable(var.getIdentificador());
        if(aux==null)
        {
            this.variables.add(var);
            return true;
        }
        else
        {
            return false;
        }
    }
}
