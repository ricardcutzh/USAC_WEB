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
import AST.ASTNodo;
public class Funcion {
    
    String nombre;//NOMBRE DE LA FUNCION EN CUESTION
    ASTNodo raiz;//SENTENCIAS QUE EJECUTA
    ArrayList<Parametro> parametros;//PARAMETROS QUE TIENE
    
    public Funcion(String nombre, ASTNodo raiz)
    {
        this.nombre = nombre;
        this.raiz = raiz;
        this.parametros = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ASTNodo getRaiz() {
        return raiz;
    }

    public void setRaiz(ASTNodo raiz) {
        this.raiz = raiz;
    }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Parametro> parametros) {
        this.parametros = parametros;
    }
    
    public boolean coloca_valores_parametros(ArrayList<String> valores)
    {
        try 
        {
            if(valores.size()== this.parametros.size())
            {
                for(int x = 0; x < this.parametros.size(); x++)
                {
                    //ASIGNO LOS VALORES QUE ESTA FUNCION VA A EJECUTAR
                    this.parametros.get(x).setValor(valores.get(x));
                }
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) 
        {
            return false;
        }
    }
    
    public int getNumeroParametros()
    {
        return this.parametros.size();
    }
    
    public Parametro getParametroIndex(int x)
    {
        return this.parametros.get(x);
    }
}
