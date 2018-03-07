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
import CCSS_EXEC.*;
import Objetos.AreaTexto;
import Objetos.Caja_Texto;
import Objetos.ETexto;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JSpinner;
public class GeneraTexto {
    CCSS_OB definiciones;
    ArrayList<NodoAtributo> atributos;
    Elemento id = null, grupo = null;
    //////////////////////
    String alineado;
    Fuente f;
    Font fu;
    String texto;
    Color fondoEl;
    boolean visible;
    /////////////////
    int strike;
    boolean redondo;
    boolean tieneBorde;
    //////////////////
    Color textColor;
    boolean opaco;
    ///////////////////
    Color colorBorde;
    int ancho = 200, alto = 200;
    ///////////////////////////
    int alignment;
    ///////////////////////////
    int aligmetTextPane;
    
    public GeneraTexto(CCSS_OB definiciones, ArrayList<NodoAtributo> atributos, String textoActual)
    {
        this.definiciones = definiciones;
        this.atributos = atributos;
        alineado = "izquierda";
        fu = new Font("Arial",Font.PLAIN, 12);
        texto = textoActual;
        fondoEl = Color.white;
        visible = true;
        strike = 10;
        redondo = false;
        tieneBorde = false;
        textColor = Color.black;
        this.colorBorde = Color.BLACK;
        this.opaco = false;
        this.alignment = SwingConstants.CENTER;
    }
    
    
    private void ProcesaAtributos()
    {
        Search s = new Search();
        NodoAtributo nodoid = s.buscameElAtributoCHTML(atributos, "id");
        if(nodoid!=null)
        {
            this.id = this.definiciones.buscaEnDefiniciones(nodoid.getValor(), "id");
        }
        NodoAtributo nodogrupo = s.buscameElAtributoCHTML(atributos, "grupo");
        if(nodogrupo!=null)
        {
            this.grupo = this.definiciones.buscaEnDefiniciones(nodogrupo.getValor(), "grupo");
        }
        NodoAtributo nodoAlto = s.buscameElAtributoCHTML(atributos, "alto");
        if(nodoAlto!=null)
        {
            try {
                this.alto = Integer.parseInt(nodoAlto.getValor());
            } catch (Exception e) {
                this.alto = 200;
            }
        }
        NodoAtributo nodoAncho = s.buscameElAtributoCHTML(atributos, "ancho");
        if(nodoAncho!=null)
        {
            try {
                this.ancho = Integer.parseInt(nodoAncho.getValor());
            } catch (Exception e) {
                this.ancho = 200;
            }
        }
        NodoAtributo nodoalineado = s.buscameElAtributoCHTML(atributos, "alineado");
        if(nodoalineado!=null)
        {
            if(nodoalineado.getValor().toLowerCase().equals("izquierda"))
            {
                this.alignment = SwingConstants.LEFT;
            }
            if(nodoalineado.getValor().toLowerCase().equals("derecha"))
            {
                this.alignment = SwingConstants.RIGHT;
            }
            if(nodoalineado.getValor().toLowerCase().equals("centrado"))
            {
                this.alignment = SwingConstants.CENTER;
            }
            if(nodoalineado.getValor().toLowerCase().equals("justificado"))
            {
                this.alignment = SwingConstants.LEADING;
            }
        }
    }
    
    public ETexto generameLabel()
    {
        ProcesaAtributos();
        if(this.id!=null)
        {
            completaProceso(id);
        }
        if(this.grupo!=null)
        {
            completaProceso(grupo);
        }
        f = new Fuente(atributos, definiciones);
        Font fu = f.dameLaFuente();
        ETexto retorno = new ETexto(tieneBorde, redondo, strike, colorBorde);
        retorno.setFont(fu);
        if(opaco)
        {
            retorno.setBackground(new Color(0,0,0,0));
        }
        else{
            retorno.setBackground(fondoEl);
        }
        retorno.setText(texto);
        if(!visible)
        {
            retorno.setVisible(false);
        }
        retorno.setHorizontalAlignment(alignment);
        retorno.setForeground(textColor);
        retorno.setOpaque(true);
        retorno.setPreferredSize(new Dimension(ancho, alto));
        return retorno;
    }
    
    public Caja_Texto generameTextField()
    {
        ProcesaAtributos();
        if(this.id!=null)
        {
            completaProceso(id);
        }
        if(this.grupo!=null)
        {
            completaProceso(grupo);
        }
        f = new Fuente(atributos, definiciones);
        Font fu = f.dameLaFuente();
        Caja_Texto cajita = new Caja_Texto(tieneBorde, redondo, strike, colorBorde);
        cajita.setFont(fu);
        if(f.minuscula)
        {
            texto = texto.toLowerCase();
            cajita.addKeyListener(new KeyAdapter()
            {
                public void keyTyped(KeyEvent e)
                {
                    char keychar = e.getKeyChar();
                    if(Character.isUpperCase(keychar))
                    {
                        e.setKeyChar(Character.toLowerCase(keychar));
                    }
                }
            });
        }
        if(f.captitalt)
        {
            texto = upperCaseAllFirst(texto);
            cajita.addKeyListener(new KeyAdapter(){
                public  String upperCaseAllFirst(String value) {
                    char[] array = value.toCharArray();
                    // Uppercase first letter.
                    array[0] = Character.toUpperCase(array[0]);

                    // Uppercase all letters that follow a whitespace character.
                    for (int i = 1; i < array.length; i++) {
                        if (Character.isWhitespace(array[i - 1])) {
                            array[i] = Character.toUpperCase(array[i]);
                        }
                    }

                    // Result.
                    return new String(array);
                }
                
                public void keyTyped(KeyEvent e)
                {
                    if(e.getKeyChar()==32)
                    {
                        cajita.setText(upperCaseAllFirst(cajita.getText()));
                    }
                }
            });
        }
        if(f.mayuscula)
        {
            texto = texto.toUpperCase();
            cajita.addKeyListener(new KeyAdapter(){
                 public void keyTyped(KeyEvent e)
                 {
                     char keychar = e.getKeyChar();
                     if(Character.isLowerCase(keychar))
                     {
                         e.setKeyChar(Character.toUpperCase(keychar));
                     }
                 }
            });
        }
        cajita.setBackground(fondoEl);
        cajita.setText(texto);
        if(!visible)
        {
            cajita.setVisible(false);
        }
        cajita.setHorizontalAlignment(alignment);
        cajita.setForeground(textColor);
        cajita.setOpaque(true);
        cajita.setPreferredSize(new Dimension(ancho, alto));
        return cajita;
    }
    
    public AreaTexto generameTextArea()
    {
        AreaTexto areatexto = null;
        try {
            StyleContext context = new StyleContext();
            StyledDocument document = new DefaultStyledDocument(context);
            
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
            StyleConstants.setAlignment(style, aligmetTextPane);
            
            ProcesaAtributos();
            if(this.id!=null)
            {
                completaProceso(id);
            }
            if(this.grupo!=null)
            {
                completaProceso(grupo);
            }
            f = new Fuente(atributos, definiciones);
            Font fu = f.dameLaFuente();
            areatexto = new AreaTexto(tieneBorde, redondo, strike, colorBorde);
            areatexto.setFont(fu);
            areatexto.setBackground(fondoEl);
            if(f.minuscula)
            {
                texto = texto.toLowerCase();
                areatexto.addKeyListener(new KeyAdapter()
                {
                    public void keyTyped(KeyEvent e)
                    {
                        char keychar = e.getKeyChar();
                        if(Character.isUpperCase(keychar))
                        {
                            e.setKeyChar(Character.toLowerCase(keychar));
                        }
                    }
                });
            }
            if(f.captitalt)
            {
                texto = upperCaseAllFirst(texto);
                areatexto.addKeyListener(new KeyAdapter()
                {
                    public void keyTyped(KeyEvent e)
                    {
                        if(e.getKeyChar()==32)
                        {
                            
                        }
                    }
                });
            }
            if(f.mayuscula)
            {
                texto = texto.toUpperCase();
                areatexto.addKeyListener(new KeyAdapter()
                {
                    public void keyTyped(KeyEvent e)
                    {
                        char keychar = e.getKeyChar();
                        if(Character.isLowerCase(keychar))
                        {
                            e.setKeyChar(Character.toUpperCase(keychar));
                        }
                    }
                });
            }
            areatexto.setText(texto);
            if(!visible)
            {
                areatexto.setVisible(false);
            }
            document.insertString(document.getLength(), texto, style);
            areatexto.setDocument(document);
        } catch (BadLocationException ex) {
            Logger.getLogger(GeneraTexto.class.getName()).log(Level.SEVERE, null, ex);
        }
        areatexto.setLineWrap(true);
        return areatexto;
    }
    
    public JSpinner generameSpinner(int max)
    {
        ancho = 150;
        alto = 35;
        ProcesaAtributos();
        if(this.id!=null)
        {
            completaProceso(id);
        }
        if(this.grupo!=null)
        {
            completaProceso(grupo);
        }
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, max, 1));
        spinner.setPreferredSize(new Dimension(ancho, alto));
        Fuente f = new Fuente(atributos, definiciones);
        spinner.setFont(fu);
        spinner.setBackground(fondoEl);
        return spinner;
    }
    
    private void completaProceso(Elemento el)
    {
        Atributo alinea = el.buscaAtributo("alineado");
        if(alinea!=null){this.alineado = alinea.getValores().get(0);}
        
        Atributo text = el.buscaAtributo("texto");
        if(text!=null){this.texto = text.getValores().get(0);}
        
        Atributo fon = el.buscaAtributo("fondoelemento");
        if(fon!=null)
        {
            InterpretaColor col = new InterpretaColor();
            this.fondoEl = col.stringToColor(fon.getValores().get(0));
        }
        
        Atributo visib = el.buscaAtributo("visible");
        if(visib!=null)
        {
            if(visib.getValores().get(0).toLowerCase().equals("true")){this.visible = true;}
            else {this.visible = false;}
        }
        
        Atributo opaco = el.buscaAtributo("opaque");
        if(opaco!=null)
        {
            if(opaco.getValores().get(0).toLowerCase().equals("true")){this.opaco=true;}
            else {this.opaco = false;}
        }
        
        Atributo ctexto = el.buscaAtributo("colortext");
        if(ctexto!=null)
        {
            InterpretaColor col = new InterpretaColor();
            this.textColor = col.stringToColor(ctexto.getValores().get(0));
        }
        
        Atributo bord = el.buscaAtributo("borde");
        if(bord!=null)
        {
            if(bord.getValores().size()==3)
            {
                this.tieneBorde = true;
                try {
                    this.strike = Integer.parseInt(bord.getValores().get(0));
                } catch (Exception e) {
                    this.strike = 10;
                }
                InterpretaColor col = new InterpretaColor();
                colorBorde =  col.stringToColor(bord.getValores().get(1));
                
                if(bord.getValores().get(2).toLowerCase().equals("true"))
                {
                    this.redondo = true;
                }
                else
                {
                    this.redondo = false;
                }
                
            }
        }
        
        Atributo alin = el.buscaAtributo("alineado");
        if(alin!=null)
        {
            if(alin.getValores().get(0).toLowerCase().equals("izquierda"))
            {
                this.alignment = SwingConstants.LEFT;
                this.aligmetTextPane = StyleConstants.ALIGN_LEFT;
            }
            if(alin.getValores().get(0).toLowerCase().equals("derecha"))
            {
                this.alignment = SwingConstants.RIGHT;
                this.aligmetTextPane = StyleConstants.ALIGN_RIGHT;
            }
            if(alin.getValores().get(0).toLowerCase().equals("centrado"))
            {
                this.alignment = SwingConstants.CENTER;
                this.aligmetTextPane = SwingConstants.CENTER;
            }
            if(alin.getValores().get(0).toLowerCase().equals("justificado"))
            {
                this.alignment = SwingConstants.LEADING;
                this.aligmetTextPane = SwingConstants.LEADING;
            }
        }
        
    }
    public  String upperCaseAllFirst(String value) {

        char[] array = value.toCharArray();
        // Uppercase first letter.
        array[0] = Character.toUpperCase(array[0]);

        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        // Result.
        return new String(array);
    }
}
