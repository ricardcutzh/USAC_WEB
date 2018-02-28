/*----------AREA DE COLDIGO DE USUARIO---------------------------------------------------------------*/
//------->PAQUETES E IMPORTACIONES
package CJS;
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
%class LexCJS
%cupsym Simbolos
%cup
%char
%column
%full
%ignorecase
%line
%unicode

//------------> EXPRESIONES REGULARES
comentMultilinea = \'\/[^]*\/\'
comentarioLinea = \'(.)*
identificador = [a-zA-Z]([a-zA-Z]|[0-9]|_)*
numerico = [0-9]+(\.[0-9]+)*
cadena = \"(\\.|[^\"\\])*\"
cadena2 = \'(\\.|[^\'\\])*\'
//------------> ESTADOS

%%
/*----------AREA DE REGLAS LEXICAS-------------------------------------------------------------------*/
<YYINITIAL> "dimv"                        {return new Symbol(Simbolos.dimv, yycolumn, yyline, yytext());}
<YYINITIAL> "true"                        {return new Symbol(Simbolos.tTrue, yycolumn, yyline, yytext());}
<YYINITIAL> "false"                       {return new Symbol(Simbolos.tFalse, yycolumn, yyline, yytext());}
<YYINITIAL> "conteo"                      {return new Symbol(Simbolos.conteo, yycolumn, yyline, yytext());}
<YYINITIAL> "atexto"                      {return new Symbol(Simbolos.atexto ,yycolumn, yyline, yytext());}
<YYINITIAL> "si"                          {return new Symbol(Simbolos.si ,yycolumn, yyline, yytext());}
<YYINITIAL> "sino"                        {return new Symbol(Simbolos.sino ,yycolumn, yyline, yytext());}
<YYINITIAL> "selecciona"                  {return new Symbol(Simbolos.selecciona ,yycolumn, yyline, yytext());}
<YYINITIAL> "caso"                        {return new Symbol(Simbolos.caso ,yycolumn, yyline, yytext());}
<YYINITIAL> "defecto"                     {return new Symbol(Simbolos.defecto ,yycolumn, yyline, yytext());}
<YYINITIAL> "para"                        {return new Symbol(Simbolos.para ,yycolumn, yyline, yytext());}
<YYINITIAL> "mientras"                    {return new Symbol(Simbolos.mientras ,yycolumn, yyline, yytext());}
<YYINITIAL> "detener"                     {return new Symbol(Simbolos.detener ,yycolumn, yyline, yytext());}
<YYINITIAL> "imprimir"                    {return new Symbol(Simbolos.imprimir ,yycolumn, yyline, yytext());}
<YYINITIAL> "funcion"                     {return new Symbol(Simbolos.funcion ,yycolumn, yyline, yytext());}
<YYINITIAL> "retornar"                    {return new Symbol(Simbolos.retornar ,yycolumn, yyline, yytext());}
<YYINITIAL> "mensaje"                     {return new Symbol(Simbolos.mensaje ,yycolumn, yyline, yytext());}
<YYINITIAL> "documento"                   {return new Symbol(Simbolos.documento ,yycolumn, yyline, yytext());}
<YYINITIAL> "obtener"                     {return new Symbol(Simbolos.obtener ,yycolumn, yyline, yytext());}
<YYINITIAL> "setElemento"                 {return new Symbol(Simbolos.setElemento ,yycolumn, yyline, yytext());}
<YYINITIAL> "observador"                  {return new Symbol(Simbolos.observador ,yycolumn, yyline, yytext());}
//---------->SIMBOLOS
<YYINITIAL> ";"                           {return new Symbol(Simbolos.pcoma, yycolumn, yyline, yytext());}
<YYINITIAL> ":"                           {return new Symbol(Simbolos.dosP, yycolumn, yyline, yytext());}
<YYINITIAL> ","                           {return new Symbol(Simbolos.coma, yycolumn, yyline, yytext());}
<YYINITIAL> "+"                           {return new Symbol(Simbolos.mas, yycolumn, yyline, yytext());}
<YYINITIAL> "-"                           {return new Symbol(Simbolos.menos, yycolumn, yyline, yytext());}
<YYINITIAL> "*"                           {return new Symbol(Simbolos.por, yycolumn, yyline, yytext());}
<YYINITIAL> "/"                           {return new Symbol(Simbolos.divi, yycolumn, yyline, yytext());}
<YYINITIAL> "^"                           {return new Symbol(Simbolos.elev, yycolumn, yyline, yytext());}
<YYINITIAL> "%"                           {return new Symbol(Simbolos.mod, yycolumn, yyline, yytext());}
<YYINITIAL> "++"                          {return new Symbol(Simbolos.aum, yycolumn, yyline, yytext());}
<YYINITIAL> "--"                          {return new Symbol(Simbolos.dism, yycolumn, yyline, yytext());}
<YYINITIAL> "("                           {return new Symbol(Simbolos.oParent, yycolumn, yyline, yytext());}
<YYINITIAL> ")"                           {return new Symbol(Simbolos.cParent, yycolumn, yyline, yytext());}
<YYINITIAL> "=="                          {return new Symbol(Simbolos.igualacion, yycolumn, yyline, yytext());}
<YYINITIAL> "!="                          {return new Symbol(Simbolos.diferente, yycolumn, yyline, yytext());}
<YYINITIAL> "<"                           {return new Symbol(Simbolos.menor, yycolumn, yyline, yytext());}
<YYINITIAL> ">"                           {return new Symbol(Simbolos.mayor, yycolumn, yyline, yytext());}
<YYINITIAL> "<="                          {return new Symbol(Simbolos.menorigual, yycolumn, yyline, yytext());}
<YYINITIAL> ">="                          {return new Symbol(Simbolos.mayorigual, yycolumn, yyline, yytext());}
<YYINITIAL> "&&"                          {return new Symbol(Simbolos.and, yycolumn, yyline, yytext());}
<YYINITIAL> "||"                          {return new Symbol(Simbolos.or, yycolumn, yyline, yytext());}
<YYINITIAL> "!"                           {return new Symbol(Simbolos.not, yycolumn, yyline, yytext());}
<YYINITIAL> "{"                           {return new Symbol(Simbolos.oKey, yycolumn, yyline, yytext());}
<YYINITIAL> "}"                           {return new Symbol(Simbolos.cKey, yycolumn, yyline, yytext());}
<YYINITIAL> "."                           {return new Symbol(Simbolos.punto, yycolumn, yyline, yytext());}
//---------->EXPRESIONES ER
<YYINITIAL> {identificador}               {return new Symbol(Simbolos.iden, yycolumn, yyline, yytext());}
<YYINITIAL> {numerico}                    {return new Symbol(Simbolos.numeric, yycolumn, yyline, yytext());}
<YYINITIAL> {cadena2}                     {System.out.println(yytext()); return new Symbol(Simbolos.cadena2, yycolumn, yyline, yytext());}
<YYINITIAL> {cadena}                      {return new Symbol(Simbolos.cadena1, yycolumn, yyline, yytext());}
<YYINITIAL> {comentMultilinea}            {System.out.println(yytext());/*System.out.println(yytext());/*IGNORA LOS COMENTARIOS*/}
<YYINITIAL> {comentarioLinea}             {System.out.println(yytext());/*System.out.println(yytext());/*IGNORA LOS COMENTARIOS*/}
[ \t\r\f\n]                               {/*NO HAGO NADA*/}
//----------->ERRORES LEXICOS
.                              {
                                    TError errorlex = new TError(yytext(),"Error Lexico","Caracter no Reconocido", yyline, yycolumn);
                                    LexError.add(errorlex);
                               }
