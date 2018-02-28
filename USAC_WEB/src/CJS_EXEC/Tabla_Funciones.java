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
import java.util.ArrayList;
public class Tabla_Funciones {
    ArrayList<Funcion> funciones;
    
    public Tabla_Funciones()
    {
        this.funciones = new ArrayList<>();
    }
    
    public Funcion buscaFuncion(String nombre, int num_params)
    {
        Funcion aux;
        for(int x = 0; x < this.funciones.size(); x++)
        {
            aux = this.funciones.get(x);
            if(aux.getNombre().equals(nombre) && aux.getNumeroParametros() == num_params)//SI EL NUMERO DE PARAMETROS COINCIDEN ENTONCES....
            {
                //RETORNO LA FUNCION PARA SER EJECUTADA
                return aux;
            }
        }
        return null;
    }
    
    public boolean addFuncion(Funcion fun)
    {
        Funcion aux = buscaFuncion(fun.getNombre(), fun.getNumeroParametros());
        if(aux==null)//SI LA FUNCION NO EXISTE, LA ANIADO...
        {
            this.funciones.add(fun);
            return true;
        }
        else{//SI EXISTE, ENTONCES RETORNO FALSE Y VALIDO UN ERROR DE SEMANTICA
            return false;
        }
    }
}
