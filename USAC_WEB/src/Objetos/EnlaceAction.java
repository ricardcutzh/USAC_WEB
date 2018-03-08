/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import CHTML_EXEC.CHTML;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author richard
 */
public class EnlaceAction extends MouseAdapter{

    JPanel Principal;//PAIGNA DE CONTENIDO
    JTable ErrorTable;//REFEENCIA A LA TABLA DE ERRORES
    JTable PrintTable;//REFERENCIA A LA TABLA DE IMPRESION
    JTabbedPane padre;//REFERENCIA AL QUE CREA LAS PESTANIAS
    JTabbedPane MainTabPage;//REFERENCIA A EL ADMINISTRADOR DE PESTANIAS DE LA PAGINA PRINCIPAL
    int index; //INDICE DE LA PAGINA A LA QUE ESTOY HACIENDO REFERENCIA
    String ruta;//LA RUTA DEL ARCHIVO A CARGAR
    ////////////////
    int alto,ancho;
    ValidadorRuta validador;
    
    public EnlaceAction(JPanel Principal, JTable ErrorTable, JTable PrintTable, JTabbedPane padre, JTabbedPane MainTabPage ,int index, String ruta, int alto, int ancho)
    {
        this.Principal = Principal;
        this.ErrorTable = ErrorTable;
        this.PrintTable = PrintTable;
        this.padre = padre;
        this.MainTabPage = MainTabPage;
        this.index = index;
        this.ruta = ruta;
        this.alto = alto;
        this.ancho = ancho;
        this.validador = new ValidadorRuta(ruta);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
        if(validador.esValida())
        {
            GUI.Pestania.paginas.add(ruta);
            CHTML otro = new CHTML(Principal, ErrorTable, PrintTable, padre, MainTabPage, index, ruta, alto, ancho);
            otro.executeCHTML();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "La Pagina Solicitada no Existe!");
        }
    }
    
    
    
    
}
