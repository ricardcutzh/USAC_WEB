/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;
import AST.ASTNodo;
/**
 *
 * @author richard
 */
import CJS.*;
import java.io.FileReader;
import java.util.ArrayList;
import AST.TError;
public class NodoCJS {
    //RAIZ DEL ARBOL CJS
    int numeroErrores;
    String nombreArchivo;
    ASTNodo raiz;
    ArrayList<TError> erroresSintacticos;
    public NodoCJS(String nombreArchivo) 
    {
        this.nombreArchivo = nombreArchivo;
        this.numeroErrores = 0;
    }

    public int getNumeroErrores() {
        return numeroErrores;
    }

    public void setNumeroErrores(int numeroErrores) {
        this.numeroErrores = numeroErrores;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public ASTNodo getRaiz() {
        return raiz;
    }

    public void setRaiz(ASTNodo raiz) {
        this.raiz = raiz;
    }

    public ArrayList<TError> getErroresSintacticos() {
        return erroresSintacticos;
    }

    public void setErroresSintacticos(ArrayList<TError> erroresSintacticos) {
        this.erroresSintacticos = erroresSintacticos;
    }
    
    
    public boolean EjecutaAnalisis()
    {
        boolean ret = false;
        try 
        {
            LexCJS lex = new LexCJS(new FileReader(this.nombreArchivo));
            parser p = new parser(lex);
            p.parse();
            if(parser.raiz!=null)
            {
                this.raiz = parser.raiz;
                ret = true;
            }
            this.numeroErrores = parser.errores.size();
            this.erroresSintacticos = parser.errores;
        } catch (Exception e) 
        {
            this.raiz = null;
            System.err.println("Nodo CJS file: "+this.nombreArchivo+" | "+e.toString());
            ret = false;
        }
        return ret;
    }
}
