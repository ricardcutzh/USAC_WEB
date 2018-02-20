/*----------AREA DE COLDIGO DE USUARIO---------------------------------------------------------------*/
//------->PAQUETES E IMPORTACIONES
package CHTML.Auxiliares;
import java_cup.runtime.*;
import AST.TError;
import java.util.ArrayList;
/*----------AREA DE OPCIONES Y DECLARACIONES---------------------------------------------------------*/
%%
%{
    //CODIGO DE USUARIO EN SINTAXIS JAVA
    public ArrayList<TError> LexError = new ArrayList<>();
%}

//--------->DIRECTIVAS
%public
%class auxiliarCHTML
%cupsym Symb
%cup
%char
%column
%full
%ignorecase
%line
%unicode

//------------> EXPRESIONES REGULARES
comentarios = \<\/\/\-[^]*\-\/\/\>
cadena = \"(\\.|[^\"\\])*\"
textoSalida = \>(\\.|[^\"\\])*\<
//------------> ESTADOS

%%
/*----------AREA DE REGLAS LEXICAS-------------------------------------------------------------------*/
<YYINITIAL> "texto"             {System.out.println("Encontre:  texto"); return new Symbol(Symb.texto, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-texto"         {System.out.println("Encontre:  fin-texto"); return new Symbol(Symb.finTexto, yycolumn, yyline, yytext());}

//----------->OTROS
<YYINITIAL> "id"                {/*System.out.println("Encontre:  id");*/ return new Symbol(Symb.id, yycolumn, yyline, yytext());}
<YYINITIAL> "grupo"             {/*System.out.println("Encontre:  grupo");*/ return new Symbol(Symb.grupo, yycolumn, yyline, yytext());}
<YYINITIAL> "alto"              {/*System.out.println("Encontre:  alto");*/ return new Symbol(Symb.alto, yycolumn, yyline, yytext());}
<YYINITIAL> "ancho"             {/*System.out.println("Encontre:  ancho"); */return new Symbol(Symb.ancho, yycolumn, yyline, yytext());}
<YYINITIAL> "alineado"          {/*System.out.println("Encontre:  alineado");*/ return new Symbol(Symb.alineado, yycolumn, yyline, yytext());}
//---------->SIMBOLOS
<YYINITIAL> "<"                 {/*System.out.println("Encontre:  opentag");*/ return new Symbol(Symb.opentag, yycolumn, yyline, yytext());}
<YYINITIAL> ">"                 {/*System.out.println("Encontre:  closetag");*/ return new Symbol(Symb.closetag, yycolumn, yyline, yytext());}
<YYINITIAL> "="                 {/*System.out.println("Encontre:  igual");*/ return new Symbol(Symb.igual, yycolumn, yyline, yytext());}
<YYINITIAL> ";"                 {/*System.out.println("Encontre:  pcoma");*/ return new Symbol(Symb.pcoma, yycolumn, yyline, yytext());}
<YYINITIAL> ","                 {/*System.out.println("Encontre:  coma");*/ return new Symbol(Symb.coma, yycolumn, yyline, yytext());}

//---------->EXPRESIONES ER
<YYINITIAL> {comentarios}       {/*System.out.println("Encontre comentario: "+yytext());*/ /*return new Symbol(Simbolos.entero, yycolumn, yyline, yytext());*/}
<YYINITIAL> {cadena}            {/*System.out.println("Encontre cadena: "+yytext());*/ return new Symbol(Symb.cadena, yycolumn, yyline, yytext());}
<YYINITIAL> {textoSalida}            {/*System.out.println("Encontre cadena: "+yytext());*/ return new Symbol(Symb.salida, yycolumn,yyline, yytext());}
//----------->ERRORES LEXICOS
.                              {/*ERRORES*/}
