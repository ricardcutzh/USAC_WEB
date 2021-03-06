/*----------AREA DE COLDIGO DE USUARIO---------------------------------------------------------------*/
//------->PAQUETES E IMPORTACIONES
package CHTML;
import java_cup.runtime.*;
//import AST.Error;
import java.util.ArrayList;

/*----------AREA DE OPCIONES Y DECLARACIONES---------------------------------------------------------*/
%%
%{
    //CODIGO DE USUARIO EN SINTAXIS JAVA

    //LISTA DE ERRORES LEXICOS QUE SE GENERAN

%}

//--------->DIRECTIVAS
%public
%class LexCHTML
%cupsym Simbolos
%cup
//%standalone
%char
%column
%full
%ignorecase
%line
%unicode

//------------> EXPRESIONES REGULARES
entero = [0-9]+
palabra = [a-zA-Z]([a-zA-Z]|[0-9]|_|-)*
cadena = \".*\"
comentarios = \<\/\/\-[^]*\-\/\/\>
frase = >([a-zA-Z]([a-zA-Z]|[0-9]|_|-)+|[ \n\t\r\n]+)*\<
tokenTexto=\<(.)*TEXTO(.)*\>(.|[\n])*\<(.)*FIN-TEXTO(.)*\>
//------------> ESTADOS
%%
/*----------AREA DE REGLAS LEXICAS-------------------------------------------------------------------*/
<YYINITIAL> {frase}             {System.out.println("Encontre frase: "+yytext()); return new Symbol(Simbolos.frase, yycolumn, yyline, yytext());}
//---------->PALABRAS RESERVADAS
<YYINITIAL> "chtml"             {/*System.out.println("Encontre:  chtml "+yytext()); */return new Symbol(Simbolos.chtml, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-chtml"         {/*System.out.println("Encontre:  fin-chtml "+yytext());*/ return new Symbol(Simbolos.finChtml, yycolumn, yyline, yytext());}
<YYINITIAL> "encabezado"        {/*ystem.out.println("Encontre:  encabezado "+yytext());*/ return new Symbol(Simbolos.encabezado, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-encabezado"    {/*System.out.println("Encontre:  fin-encabezado "+yytext());*/ return new Symbol(Simbolos.finEncabezado, yycolumn, yyline, yytext());}
<YYINITIAL> "titulo"            {/*System.out.println("Encontre:  titulo "+yytext());*/ return new Symbol(Simbolos.titulo, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-titulo"        {/*System.out.println("Encontre:  fin-titulo "+yytext());*/ return new Symbol(Simbolos.finTitulo, yycolumn, yyline, yytext());}
<YYINITIAL> "cuerpo"            {/*System.out.println("Encontre:  cuerpo "+yytext());*/ return new Symbol(Simbolos.cuerpo, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-cuerpo"        {/*System.out.println("Encontre:  fin-cuerpo "+yytext());*/ return new Symbol(Simbolos.finCuerpo, yycolumn, yyline, yytext());}
<YYINITIAL> "cjs"               {/*System.out.println("Encontre:  cjs");*/ return new Symbol(Simbolos.cjs, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-cjs"           {/*System.out.println("Encontre:  fin-cjs");*/ return new Symbol(Simbolos.finCjs, yycolumn, yyline, yytext());}
<YYINITIAL> "ccss"              {/*System.out.println("Encontre:  ccss");*/ return new Symbol(Simbolos.ccss, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-ccss"          {/*System.out.println("Encontre:  fin-ccss");*/ return new Symbol(Simbolos.finCcss, yycolumn, yyline, yytext());}
<YYINITIAL> "panel"             {System.out.println("Encontre:  panel"); return new Symbol(Simbolos.panel, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-panel"         {System.out.println("Encontre:  fin-panel"); return new Symbol(Simbolos.finPanel, yycolumn, yyline, yytext());}
<YYINITIAL> "texto"             {System.out.println("Encontre:  texto"); return new Symbol(Simbolos.texto, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-texto"         {System.out.println("Encontre:  fin-texto"); return new Symbol(Simbolos.finTexto, yycolumn, yyline, yytext());}
<YYINITIAL> "imagen"            {System.out.println("Encontre:  imagen"); return new Symbol(Simbolos.imagen, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-imagen"        {System.out.println("Encontre:  fin-imagen"); return new Symbol(Simbolos.finImagen, yycolumn, yyline, yytext());}
<YYINITIAL> "boton"             {System.out.println("Encontre:  boton"); return new Symbol(Simbolos.boton, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-boton"         {System.out.println("Encontre:  fin-boton"); return new Symbol(Simbolos.finBoton, yycolumn, yyline, yytext());}
<YYINITIAL> "enlace"            {System.out.println("Encontre:  enlace"); return new Symbol(Simbolos.enlace, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-enlace"        {System.out.println("Encontre:  fin-enlace"); return new Symbol(Simbolos.finEnlace, yycolumn, yyline, yytext());}
<YYINITIAL> "tabla"             {System.out.println("Encontre:  tabla"); return new Symbol(Simbolos.tabla, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-tabla"         {System.out.println("Encontre:  fin-tabla"); return new Symbol(Simbolos.finTabla, yycolumn, yyline, yytext());}
/*<YYINITIAL> "texto_a"           {System.out.println("Encontre:  texto_a"); return new Symbol(Simbolos.texto_a, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-texto_a"       {System.out.println("Encontre:  fin-texto_a"); return new Symbol(Simbolos.finTexto_a, yycolumn, yyline, yytext());}
<YYINITIAL> "caja_texto"        {System.out.println("Encontre:  caja_texto"); return new Symbol(Simbolos.caja_texto, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-caja_texto"    {System.out.println("Encontre:  fin-caja_texto"); return new Symbol(Simbolos.finCaja_texto, yycolumn, yyline, yytext());}
<YYINITIAL> "caja"              {System.out.println("Encontre:  caja"); return new Symbol(Simbolos.caja, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-caja"          {System.out.println("Encontre:  fin-caja"); return new Symbol(Simbolos.finCaja, yycolumn, yyline, yytext());}
<YYINITIAL> "spinner"           {System.out.println("Encontre:  spinner"); return new Symbol(Simbolos.spinner, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-spinner"       {System.out.println("Encontre:  fin-spinner"); return new Symbol(Simbolos.finSpinner, yycolumn, yyline, yytext());}
<YYINITIAL> "fil_t"             {System.out.println("Encontre:  fil_t"); return new Symbol(Simbolos.fil_t, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-fil_t"         {System.out.println("Encontre:  fin-fil_t"); return new Symbol(Simbolos.finFil_t, yycolumn, yyline, yytext());}
<YYINITIAL> "cb"                {System.out.println("Encontre:  cb"); return new Symbol(Simbolos.cb, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-cb"            {System.out.println("Encontre:  fin-cb"); return new Symbol(Simbolos.finCb, yycolumn, yyline, yytext());}
<YYINITIAL> "ct"                {System.out.println("Encontre:  ct"); return new Symbol(Simbolos.ct, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-ct"            {System.out.println("Encontre:  fin-ct"); return new Symbol(Simbolos.finCt, yycolumn, yyline, yytext());}
<YYINITIAL> "opcion"            {System.out.println("Encontre:  opcion"); return new Symbol(Simbolos.opcion, yycolumn, yyline, yytext());}
<YYINITIAL> "fin-opcion"        {System.out.println("Encontre:  fin-opcion"); return new Symbol(Simbolos.finOpcion, yycolumn, yyline, yytext());}
<YYINITIAL> "salto-fin"         {System.out.println("Encontre:  salto-fin"); return new Symbol(Simbolos.saltoFin, yycolumn, yyline, yytext());}*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
<YYINITIAL> "fondo"             {/*System.out.println("Encontre:  fondo");*/ return new Symbol(Simbolos.fondo, yycolumn, yyline, yytext());}
<YYINITIAL> "ruta"              {/*System.out.println("Encontre:  ruta");*/ return new Symbol(Simbolos.ruta, yycolumn, yyline, yytext());}
/*<YYINITIAL> "click"             {System.out.println("Encontre:  click"); return new Symbol(Simbolos.click, yycolumn, yyline, yytext());}
<YYINITIAL> "id"                {System.out.println("Encontre:  id"); return new Symbol(Simbolos.id, yycolumn, yyline, yytext());}
<YYINITIAL> "grupo"             {System.out.println("Encontre:  grupo"); return new Symbol(Simbolos.grupo, yycolumn, yyline, yytext());}
<YYINITIAL> "alto"              {System.out.println("Encontre:  alto"); return new Symbol(Simbolos.alto, yycolumn, yyline, yytext());}
<YYINITIAL> "ancho"             {System.out.println("Encontre:  ancho"); return new Symbol(Simbolos.ancho, yycolumn, yyline, yytext());}
<YYINITIAL> "alineado"          {System.out.println("Encontre:  alineado"); return new Symbol(Simbolos.alineado, yycolumn, yyline, yytext());}*/
//---------->FIN PALABRAS RESERVADAS

//---------->CARACTERES DEL LENGUAJE
<YYINITIAL> "<"                 {/*System.out.println("Encontre:  opentag");*/ return new Symbol(Simbolos.opentag, yycolumn, yyline, yytext());}
<YYINITIAL> ">"                 {/*System.out.println("Encontre:  closetag");*/ return new Symbol(Simbolos.closetag, yycolumn, yyline, yytext());}
<YYINITIAL> "="                 {/*System.out.println("Encontre:  igual");*/ return new Symbol(Simbolos.igual, yycolumn, yyline, yytext());}
<YYINITIAL> ";"                 {/*System.out.println("Encontre:  pcoma");*/ return new Symbol(Simbolos.pcoma, yycolumn, yyline, yytext());}
<YYINITIAL> ","                 {/*System.out.println("Encontre:  coma");*/ return new Symbol(Simbolos.coma, yycolumn, yyline, yytext());}
//---------->FIN CARACTERES DEL LENGUAJE

//---------->EXPRESIONES ER

<YYINITIAL> {cadena}            {/*System.out.println("Encontre cadena: "+yytext());*/ return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext());}
<YYINITIAL> {comentarios}       {/*System.out.println("Encontre comentario: "+yytext());*/ /*return new Symbol(Simbolos.entero, yycolumn, yyline, yytext());*/}
[ \t\r\f\n]                     {/*System.out.println("Encontre delimitador: "+yytext()); return new Symbol(Simbolos.delim, yycolumn, yyline, yytext());*/}
//[\n]                            {/*System.out.println("Encontre salto de linea: "+yytext());*/ return new Symbol(Simbolos.lineaFin, yycolumn, yyline, yytext(//));}
//---------->FIN EXPRESIONES ER

//----------->ERRORES LEXICOS
.                               {System.out.println("ERROR LEXICO: "+yytext()); /*return new Symbol(Simbolos.otro, yycolumn, yyline, yytext());*/}
