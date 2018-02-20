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
        String hola = "hola";
        
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
    
}
