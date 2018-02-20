/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author richard
 */
import java.util.ArrayList;
public class ASTNodo {
    
    /*
    * ESTA CLASE ES PARA LA CREACION DEL ARBOL DE ANALISIS SINTACTICO
    */
    String cadenaDot;
    int idnodo;
    int line;
    int column;
    String etiqueta;
    ArrayList<ASTNodo> hijos;
    
    public ASTNodo(int idnodo, int line, int column, String etiqueta)
    {
        this.idnodo = idnodo;
        this.line = line;
        this.column = column;
        this.etiqueta = etiqueta;
        this.hijos = new ArrayList<>();
    }
    
    public ASTNodo(int idnodo, String etiqueta)
    {
        this.idnodo = idnodo;
        this.etiqueta = etiqueta;
        this.hijos = new ArrayList<>();
    }

    public int getIdnodo() 
    {
        return idnodo;
    }

    public int getLine() 
    {
        return line;
    }

    public int getColumn() 
    {
        return column;
    }

    public String getEtiqueta() 
    {
        return etiqueta;
    }
    
    public void addHijo(ASTNodo hijo)
    {
        this.hijos.add(hijo);
    }
    
    public ASTNodo getHijo(int index)
    {
        return this.hijos.get(index);
    }
    
    public String graficaAST(ASTNodo raiz)
    {
        this.cadenaDot = "digraph AST{\n node [shape=box];";
        recorreAST(raiz);
        this.cadenaDot += "}";
        return this.cadenaDot;
    }
    
    public int contarHijos()
    {
        return this.hijos.size();
    }
    
    private void recorreAST(ASTNodo raiz)
    {
        if(raiz!=null )
        {
            this.cadenaDot += "node_"+raiz.idnodo+"[label= \""+raiz.etiqueta+"\"];\n";
            if(raiz.contarHijos()>0)
            {
                for(int i = 0; i<raiz.hijos.size();i++)
                {
                    this.cadenaDot += "node_"+raiz.idnodo +"->node_"+raiz.getHijo(i).idnodo+";\n";
                    recorreAST(raiz.getHijo(i));
                }
            }
        }
    }
}
