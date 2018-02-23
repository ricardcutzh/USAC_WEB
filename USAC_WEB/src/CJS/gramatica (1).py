
#Comentarios

#Nota: Agregar la precedencia de operadores en flex

'Comentario simple
'/ Comentario multilinea   /'

dimv ="DimV"



VARIABLE        ->"DimV" CUERPOVAR ";"     

CUERPOVAR       ->LISTA_ID
                | LISTA_ID ":" VALOR
                | LISTA_ID ":" VECTOR

                | LISTA_ID "{" VALOR "}"
                | LISTA_ID "{" VALOR "}"  ":"  VALOR 

                | LISTA_ID ":" DOCUMENTO 

                | id "+" "+"
                | id "-" "-"     #Hay que validar de que no se este agregando la misma variable como valor cuando se declara
                
LISTA_ID        ->LISTA_ID "," id 
                |id

ASIGNA_VARIABLE ->CUERPOVAR ";"


VALOR           ->E


E               ->E "^" E 
                | E "/" E
                | E "*" E
                | E "+" E
                | E "-" E
                | E "%" E

                #realcional

                | E "==" E
                | E "!=" E
                | E "<" E
                | E "<=" E 
                | E ">=" E 

                #logicas

                | E "&&" E
                | E "||" E
                | "!" E 

                | "(" E ")"

                | numeroValor
                | decimalValor
                | id
                | cadenaValor
                | charValor
                | boolValor

                | USAR_METODO
                | VECTOR


VECTOR          ->"{" LISTA_VECTOR "}" ";"

LISTA_VECTOR    ->LISTA_VECTOR "," VALOR  #OBSERVACIÓN: PUEDE VENIR OTRO VECTOR DENTRO
                |VALOR

USAR_METODO     -> USAR_METODO "." id
                |  USAR_METODO "." id "("  ")"
                |  USAR_METODO "." id "(" PARAMETROS  ")" 
                |  USAR_METODO "." "setElemento" "(" PARAMETROS  ")" 
                |  id 
                |  id "("  ")"
                |  id "(" PARAMETROS ")"
                |  DOCUMENTO



SENTENCIA       ->SI 
                | SELECCIONA
                | FOR
                | WHILE
                | DOWHILE


SI              -> "Si" "(" VALOR ")" "{" CUERPO "}" 
                |  "Si" "(" VALOR ")" "{" CUERPO "}" SINO

SINO            ->"Sino" "{" CUERPO "}"


SELECCIONA      ->"Selecciona" "(" VALOR ")" "{" LISTA_CASOS "}"
                |"Selecciona" "(" VALOR ")" "{" LISTA_CASOS DEFECTO "}"

LISTA_CASOS     -> LISTA_CASOS  "Caso" VALOR ":" CUERPO
                |"Caso" VALOR ":" CUERPO

DEFECTO         ->"Defecto" ":" CUERPO


PARA            ->"Para" "("  ASIG VALOR ";" ASIG2 ")"  "{" CUERPO "}"

ASIG            ->VARIABLE
                 |ASIGNA_VARIABLE

ASIG2           ->CUERPOVAR
                | "-" "-"
                | "+" "+"

MIENTRAS        ->"Mientras" "(" VALOR ")" "{" CUERPO "}"
                |"Mientras" "(" VALOR ")" "{" CUERPO  "}"






NATIVAS         ->IMPRIMIR
                | MENSAJE
                


IMPRIMIR        ->"Imprimir" "(" VALOR ")" ";"

MENSAJE         ->"Mensaje" "(" VALOR ")" ";"

DOCUMENTO       ->"Documento" "." "Observador" "(" VALOR  "." FUNC2 ")"  #no estoy seguro si es como a punto, no se ve bien en el enunciado :(
                | "Documento" "." "Obtener" "(" VALOR ")" 

FUNC2           -> USAR_METODO  
                | "funcion"  "(" PARAMETROS ")" "{" CUERPO "}"
                | "funcion"  "(" ")"  "{" CUERPO "}"


METODOS         ->METODO
                | FUNCION

FUNCION         ->"funcion" id "(" PARAMETROS ")" "{" CUERPO "}"
                | "funcion" id "(" ")"  "{" CUERPO "}"

PARAMETROS      ->PARAMETROS "," VALOR
                | VALOR

CUERPO          ->VARIABLE
                | ASIGNA_VARIABLE
                | NATIVAS
                | SENTENCIA

                | "DETENER" #Validar que el ambito sea Para o Mientras
                | "RETORNAR" #Validar que el ambito sea funcion