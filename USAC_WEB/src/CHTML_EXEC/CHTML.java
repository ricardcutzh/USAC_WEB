/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;

/**
 *
 * @author richard
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import CHTML_EXEC.*;
import CHTML.*;
import java.io.FileReader;
import java.util.ArrayList;
import AST.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
public class CHTML {
    
    JPanel Principal;//PAIGNA DE CONTENIDO
    JTable ErrorTable;//REFEENCIA A LA TABLA DE ERRORES
    JTable PrintTable;//REFERENCIA A LA TABLA DE IMPRESION
    JTabbedPane padre;//REFERENCIA AL QUE CREA LAS PESTANIAS
    JTabbedPane MainTabPage;//REFERENCIA A EL ADMINISTRADOR DE PESTANIAS DE LA PAGINA PRINCIPAL
    int index; //INDICE DE LA PAGINA A LA QUE ESTOY HACIENDO REFERENCIA
    String ruta;//LA RUTA DEL ARCHIVO A CARGAR
    ////////////////
    int alto,ancho;
    
    public CHTML(JPanel Principal, JTable ErrorTable, JTable PrintTable, JTabbedPane padre, JTabbedPane MainTabPage ,int index, String ruta, int alto, int ancho)
    {
        this.Principal = Principal;
        this.ErrorTable = ErrorTable;
        this.PrintTable = PrintTable;
        this.padre = padre;
        this.index = index;
        this.ruta = ruta;
        this.alto = alto;
        this.ancho = ancho;
        this.MainTabPage = MainTabPage;
    }
    
    
    public boolean executeCHTML()
    {
        try 
        {
            Principal = new JPanel(new FlowLayout(FlowLayout.CENTER));
            Principal.setPreferredSize(new Dimension(ancho, alto));
            LexCHTML lex = new LexCHTML(new FileReader(this.ruta));
            parser p = new parser(lex);
            p.parse();
            if(parser.raiz!=null)
            {
                ASTNodo raizCHTML = parser.raiz;
                System.out.println(raizCHTML.graficaAST(raizCHTML));
                Interprete_CHTML nuevo = new Interprete_CHTML(raizCHTML, Principal, padre, index, MainTabPage);
                nuevo.setErrorTable(ErrorTable);
                nuevo.setPrintTable(PrintTable);
                nuevo.InicioCHTML();
                ErrorModel modelo = new ErrorModel(nuevo.getErrores());
                ErrorTable.setModel(modelo);
                ErrorTable.setVisible(true);
                PrintModel modeloIm = new PrintModel(nuevo.getImpresiones());
                PrintTable.setModel(modeloIm);
                PrintTable.setVisible(true);
            }
            else
            {
                ErrorModel modelo = new ErrorModel(parser.errores);
                ErrorTable.setModel(modelo);
                ErrorTable.setVisible(true);
            }
            JScrollPane scroll = new JScrollPane(Principal);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            JPanel chtml = new JPanel(new BorderLayout());
            //////////////////////////////
            //ANADIENDO EL CHTML A LA VISTA
            try {
                FileReader reader = new FileReader(ruta);
                BufferedReader br = new BufferedReader(reader);
                JTextArea tx1 = new JTextArea();
                tx1.read(br, ruta);
                br.close();
                tx1.requestFocus();
                tx1.setEditable(false);
                tx1.setFont(new Font("Lucida Console",Font.PLAIN,20));
                JScrollPane scr = new JScrollPane(tx1);
                scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                chtml.add(scr);
            } catch (Exception e) {
            }
            //////////////////////////////
            MainTabPage.add("CHTML", chtml);
            MainTabPage.addTab("Contenido", scroll);
            return true;
        } catch (Exception e) 
        {
            System.err.println("Error: "+e.toString());
            return false;
        }
    }
    
}
