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
import java.util.Date;
import java.text.SimpleDateFormat;
import GUI.*;

public class USAC_WEB {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) throws IOException {
        //pruebaCCSS();
        //pruebaCHTML();
        pruebaCJS();
        //System.out.println(es_fecha("0"));
        //System.out.println(es_fecha_tiempo("01/12/201719:45:22"));
       //USAC_WEB_GUI ventana = new USAC_WEB_GUI();
       //ventana.setVisible(true);
    }
    
    public static void pruebaCHTML()
    {
        try {
            // TODO code application logic here
            LexCHTML lex = new LexCHTML(new FileReader("PRUEBA2.txt"));
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
                //System.out.println(CCSS.parser.raiz.graficaAST(CCSS.parser.raiz));
                CCSS_EXEC.Interprete_CCSS ex = new CCSS_EXEC.Interprete_CCSS(CCSS.parser.raiz);
                ex.ejecutaCSS();
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
    
    public static void pruebaCJS()
    {
        try
        {
            CJS.LexCJS lexer = new CJS.LexCJS(new FileReader("cjsP.txt"));
            CJS.parser p = new CJS.parser(lexer);
            p.parse();
            if(CJS.parser.raiz!=null)
            {
                System.out.println(CJS.parser.raiz.graficaAST(CJS.parser.raiz));
                
            }
            System.out.println("//----------------------------------------------------------------------------------------------");
            System.out.println("//-----------------------------------ERRORES----------------------------------------------------");
            int er =  CJS.parser.errores.size();
            for(int x = 0; x< er; x++)
            {
                System.out.println("Error: "+CJS.parser.errores.get(x).getLexema()+" | LINEA: "+CJS.parser.errores.get(x).getLine()+" | COLUMNA: "+CJS.parser.errores.get(x).getColumn());
            }
            System.out.println("//----------------------------------------------------------------------------------------------");
            System.out.println("//----------------------------------------------------------------------------------------------");
        }
        catch(Exception ex)
        {
            System.out.println("ERROR AL PARSEAR...");
        }
    }

    public static boolean es_fecha(String valor)
    {
        try
        {
            SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
            Date da = d.parse(valor);
            if(valor.equals(d.format(da)))
            {
                return true;
            }
            return false;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
    public static boolean es_fecha_tiempo(String valor)
    {
        try
        {
            SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date da = d.parse(valor);
            if(valor.equals(d.format(da)))
            {
                return true;
            }
            return false;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
    public static boolean es_booleano(String valor)
    {
        try
        {
            if(valor.equals("false") || valor.equals("true"))
            {
                return true;
            }
            return false;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
