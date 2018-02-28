/*----------AREA DE COLDIGO DE USUARIO---------------------------------------------------------------*/
//------->PAQUETES E IMPORTACIONES
package CCSS;
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
%class LexCSS
%cupsym Simbolos
%cup
%char
%column
%full
%ignorecase
%line
%unicode

//------------> EXPRESIONES REGULARES
identificador = [a-zA-Z]([a-zA-Z]|[0-9]|_|-)*
comentMultilinea = \/\*[^]*\*\/
comentarioLinea = \/\/(.)*
entero = [0-9]+
decimal = [0-9]+\.[0-9]+
cadena = \"(\\.|[^\"\\])*\"
cadena2 = \'(\\.|[^\'\\])*\'
//------------> ESTADOS

%%
/*----------AREA DE REGLAS LEXICAS-------------------------------------------------------------------*/
<YYINITIAL> "alineado"                    {return new Symbol(Simbolos.alineado, yycolumn, yyline, yytext());}
<YYINITIAL> "izquierda"                   {return new Symbol(Simbolos.izquierda, yycolumn, yyline, yytext());}
<YYINITIAL> "derecha"                     {return new Symbol(Simbolos.derecha, yycolumn, yyline, yytext());}
<YYINITIAL> "centrado"                    {return new Symbol(Simbolos.centrado, yycolumn, yyline, yytext());}
<YYINITIAL> "justificado"                 {return new Symbol(Simbolos.justificado, yycolumn, yyline, yytext());}
<YYINITIAL> "grupo"                       {return new Symbol(Simbolos.grupo, yycolumn, yyline, yytext());}
<YYINITIAL> "id"                          {return new Symbol(Simbolos.id, yycolumn, yyline, yytext());}
<YYINITIAL> "texto"                       {return new Symbol(Simbolos.texto, yycolumn, yyline, yytext());}
<YYINITIAL> "formato"                     {return new Symbol(Simbolos.formato, yycolumn, yyline, yytext());}
<YYINITIAL> "negrilla"                    {return new Symbol(Simbolos.negrilla, yycolumn, yyline, yytext());}
<YYINITIAL> "cursiva"                     {return new Symbol(Simbolos.cursiva, yycolumn, yyline, yytext());}
<YYINITIAL> "mayuscula"                   {return new Symbol(Simbolos.mayus, yycolumn, yyline, yytext());}
<YYINITIAL> "minuscula"                   {return new Symbol(Simbolos.minus, yycolumn, yyline, yytext());}
<YYINITIAL> "capital-t"                   {return new Symbol(Simbolos.capital, yycolumn, yyline, yytext());}
<YYINITIAL> "letra"                       {return new Symbol(Simbolos.letra, yycolumn, yyline, yytext());}
<YYINITIAL> "tamtex"                     {return new Symbol(Simbolos.tamtext, yycolumn, yyline, yytext());}
<YYINITIAL> "fondoelemento"               {return new Symbol(Simbolos.felemento, yycolumn, yyline, yytext());}
<YYINITIAL> "autoredimension"             {return new Symbol(Simbolos.autoRed, yycolumn, yyline, yytext());}
<YYINITIAL> "horizontal"                  {return new Symbol(Simbolos.horizontal, yycolumn, yyline, yytext());}
<YYINITIAL> "vertical"                    {return new Symbol(Simbolos.vertical, yycolumn, yyline, yytext());}
<YYINITIAL> "visible"                     {return new Symbol(Simbolos.visible, yycolumn, yyline, yytext());}
<YYINITIAL> "borde"                       {return new Symbol(Simbolos.borde, yycolumn, yyline, yytext());}
<YYINITIAL> "opaque"                      {return new Symbol(Simbolos.opaque, yycolumn, yyline, yytext());}
<YYINITIAL> "colortext"                   {return new Symbol(Simbolos.colortext, yycolumn, yyline, yytext());}
//---------->SIMBOLOS
<YYINITIAL> "["                           {return new Symbol(Simbolos.oBracket, yycolumn, yyline, yytext());}
<YYINITIAL> "]"                           {return new Symbol(Simbolos.cBracket, yycolumn, yyline, yytext());}
<YYINITIAL> "true"                        {return new Symbol(Simbolos.Ttrue, yycolumn, yyline, yytext());}
<YYINITIAL> "false"                       {return new Symbol(Simbolos.Tfalse, yycolumn, yyline, yytext());}
<YYINITIAL> "("                           {return new Symbol(Simbolos.op, yycolumn, yyline, yytext());}
<YYINITIAL> ")"                           {return new Symbol(Simbolos.cp, yycolumn, yyline, yytext());}
<YYINITIAL> "+"                           {return new Symbol(Simbolos.mas, yycolumn, yyline, yytext());}
<YYINITIAL> "-"                           {return new Symbol(Simbolos.menos, yycolumn, yyline, yytext());}
<YYINITIAL> "*"                           {return new Symbol(Simbolos.por, yycolumn, yyline, yytext());}
<YYINITIAL> "/"                           {return new Symbol(Simbolos.div, yycolumn, yyline, yytext());}
<YYINITIAL> ":"                           {return new Symbol(Simbolos.dosP, yycolumn, yyline, yytext());}
<YYINITIAL> "="                           {return new Symbol(Simbolos.igual, yycolumn, yyline, yytext());}
<YYINITIAL> ";"                           {return new Symbol(Simbolos.pcoma, yycolumn, yyline, yytext());}
<YYINITIAL> ","                           {return new Symbol(Simbolos.coma, yycolumn, yyline, yytext());}
//---------->EXPRESIONES ER
<YYINITIAL> {entero}                      {return new Symbol(Simbolos.entero, yycolumn, yyline, yytext());}
<YYINITIAL> {cadena}                      {return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext());}
<YYINITIAL> {cadena2}                     {return new Symbol(Simbolos.cadena2, yycolumn, yyline, yytext());}
<YYINITIAL> {decimal}                     {return new Symbol(Simbolos.decimal, yycolumn, yyline, yytext());}
<YYINITIAL> {identificador}               {return new Symbol(Simbolos.identificador, yycolumn, yyline, yytext());}
<YYINITIAL> {comentMultilinea}            {/*System.out.println(yytext());/*IGNORA LOS COMENTARIOS*/}
<YYINITIAL> {comentarioLinea}             {/*System.out.println(yytext());/*IGNORA LOS COMENTARIOS*/}
[ \t\r\f\n]                               {/*NO HAGO NADA*/}
//----------->ERRORES LEXICOS
.                             {
                                    TError errorlex = new TError(yytext(),"Error Lexico","Caracter no Reconocido", yyline, yycolumn);
                                    LexError.add(errorlex);
                              }
