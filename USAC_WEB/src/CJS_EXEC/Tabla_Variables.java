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
    
    public Variable buscaVector(String nombre)
    {
        Variable aux;
        for(int x = 0; x < this.variables.size(); x++)
        {
            aux = this.variables.get(x);
            if(aux.getIdentificador().equals(nombre) && aux.isEsVector())
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
    
    public void imprime_variables()
    {
        System.out.println("/////////////////////////////// TABLA DE SIMBOLOS /////////////////////////////////////");
        for(int x = 0; x < this.variables.size(); x++)
        {
            
            if(variables.get(x).esVector)
            {
                System.out.print("/// *- Vector: "+variables.get(x).getIdentificador()+" | valores: [");
                for(int c = 0; c < variables.get(x).getSizeVector();c++)
                {
                    System.out.print(variables.get(x).index_vector(c).getValor()+",");
                }
                System.out.println("]");
            }
            else if(variables.get(x).getValores().size()>0)
            {
                System.out.println("/// *- "+variables.get(x).getIdentificador()+" | valor: "+variables.get(x).getValor());
            }
            else
            {
                System.out.println("/// *- "+variables.get(x).getIdentificador()+" | valor: null");
            }
        }
        System.out.println("///////////////////////////////////////////////////////////////////////////////////////");
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }
    
    
}
