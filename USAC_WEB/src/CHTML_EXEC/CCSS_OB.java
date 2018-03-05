/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;
import CCSS_EXEC.*;
import CCSS.*;
import java.io.FileReader;
import java.util.ArrayList;
import AST.TError;
/**
 *
 * @author richard
 */
public class CCSS_OB {
    ArrayList<Definicion> definiciones;
    
    ArrayList<TError>errores;
    public CCSS_OB()
    {
        this.definiciones = new ArrayList<>();
        errores = new ArrayList<>();
    }
    
    //SE ENCARGA DEL MANEJO DE LAS DEFINICIONES CSS PARA LLAMARLAS DESDE EL CHTML
    public Elemento buscaEnDefiniciones(String nombre, String tipo)
    {
        for(int x = 0; x < this.definiciones.size(); x++)
        {
            Elemento aux = this.definiciones.get(x).busca_elemento(nombre, tipo);
            if(aux!=null)
            {
                return aux;
            }
        }
        return null;
    }

    public ArrayList<TError> getErrores() {
        return errores;
    }
    
    
    
    public ArrayList<Definicion> obtenDefiniciones(String ruta)
    {
        try 
        {
            LexCSS lex = new LexCSS(new FileReader(ruta));
            parser p = new parser(lex);
            p.parse();
            ArrayList<Definicion> defins = null;
            if(parser.raiz!=null)
            {
                Interprete_CCSS interprete = new Interprete_CCSS(parser.raiz);
                interprete.ejecutaCSS();
                defins = interprete.getDefinciones();
                this.errores = parser.errores;
            }
            return defins;
        } catch (Exception e) {
            System.err.println("Objeto CCSS: "+e.toString());
            return null;
        }
    }
    
    public void imprimeDefiniciones()
    {
        for(int x = 0 ; x < this.definiciones.size(); x++)
        {
            this.definiciones.get(x).print_definicion();
        }
    }
}
