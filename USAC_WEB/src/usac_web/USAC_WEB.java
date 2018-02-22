/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usac_web;

/**
 *
 * @author richard
 */
import CHTML.Auxiliares.*;
import CHTML.LexCHTML;
import CHTML.parser;
import AST.ASTNodo;
import CCSS.*;
import static CHTML.parser.raiz;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
public class USAC_WEB {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) throws IOException {
        //pruebaCCSS();
        pruebaCHTML();
    }
    
    public static void pruebaCHTML()
    {
        try {
            // TODO code application logic here
            LexCHTML lex = new LexCHTML(new FileReader("entrada.txt"));
            parser p = new parser(lex);
            try {
                p.parse();
                if(parser.raiz!=null)
                {
                    System.out.println(parser.raiz.graficaAST(raiz)); 
                }
                else
                {
                    System.out.println("Erroe al crear el arbol");
                }
            } catch (Exception ex) {
                Logger.getLogger(USAC_WEB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(USAC_WEB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void pruebaCCSS()
    {
        try
        {
            CCSS.LexCSS lexer = new CCSS.LexCSS(new FileReader("CSSP.txt"));
            CCSS.parser p = new CCSS.parser(lexer);
            p.parse();
            if(CCSS.parser.raiz!=null)
            {
                System.out.println(CCSS.parser.raiz.graficaAST(CCSS.parser.raiz));
            }
            System.out.println("//----------------------------------------------------------------------------------------------");
            System.out.println("//-----------------------------------ERRORES----------------------------------------------------");
            int er =  CCSS.parser.errores.size();
            for(int x = 0; x< er; x++)
            {
                System.out.println("Error: "+CCSS.parser.errores.get(x).getLexema()+" | LINEA: "+CCSS.parser.errores.get(x).getLine()+" | COLUMNA: "+CCSS.parser.errores.get(x).getColumn());
            }
            System.out.println("//----------------------------------------------------------------------------------------------");
            System.out.println("//----------------------------------------------------------------------------------------------");
        }
        catch(Exception ex)
        {
            
        }
    }
    
}
