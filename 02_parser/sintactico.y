%{
#include <stdio.h>

int yylex();
int yyerror(char *s);

%}

%token ID NUMERO DECIMAL STR CHAR T F  
%token SUMA RESTA MULTI DIV MODULO
%token AND OR NOT MAYOR MENOR IGUALMAY IGUALMEN IGUAL ASIGNACION
%token FUNCION ENTRADA SISTEMA NEW PRINT PRINTLN
%token CONDICIONAL ELSE ELIF BUCLE ITERATIVO MULTIPLE CASE BREAK DEFAULT RETORNO NEW
%token CHARACTER FLOAT ENTERO BOLEANO STRING CONSTANTE TUPLA ARRAY
%token SLINEA RPAREN LPAREN RBRACKET LBRACKET RCORCHETE LCORCHETE COMA PUNTOCOMA PUNTO DOSPUNTOS 
%token EXEC

%type <string> ID
%type <entero> NUMERO
%type <real> DECIMAL
%type <string> STR
%type <string> CHAR

%union 
{
    char *string;
    int entero;
    float real;
}

%%
/* regles */

/* variable : produccion {regla semantica}
           |
           ;
*/

// ------------------------------------------------------------------------------------------------------------------ //
//                                                       GRAMMAR CORE                                                 //

PROGRAM : PROGRAM_INIT LCORCHETE SENTS RCORCHETE;

PROGRAM_INIT: ; 

DECLS: DECL DECLS | ;

SENTS : SENT SENTS | ;
        
SENT : ASIGN_SENT | DECLARACION_SENT | CONDICIONAL_SENT | ITERATIVO_SENT | FUNCION_SENT | INCRDECR_SENTS;


// ----------------------------------------------------------------------------------------------------------------- //
//                                                       DECLACRACION_SENT                                           //

DECLARACION_SENT : DECLARACIONES PUNTOCOMA;

DECLARACIONES:  DEC | DEC_ARRAY;

DEC: TYPE ID DEC_ASIGN;

DEC_ASIGN: ASIGNACION EXPRESION | ;

DEC_ARRAY: TYPE ID ARRAY ARRAY_DEC | ID INIT_ARRAY; // int a [][]* =? new int [valor] [valor]  int a[]
                                     // a = new int[2][2] no se puede hacer hasta que pedro trabaje, la de chambiar nos se la sabe (INCOMPLETO)
                                     // si que se puede manin el segundo caso es ID INIT_ARRAY 
ARRAY_DEC: INIT_ARRAY | ;

INIT_ARRAY: ASIGNACION NEW TYPE DIM_ARRAY;

DIM_ARRAY: DIM_ARRAY LCORCHETE VALOR RCORCHETE // dimensiones infinitas 
          | LCORCHETE VALOR RCORCHETE; // se acaba con minimi uno

VALOR: ID | ENTERO ;

TYPE: ENTERO | CHARACTER | FLOAT | BOLEANO | TUPLA ;


// ----------------------------------------------------------------------------------------------------------------- //
//                                                       ASIGNACION_SENT                                             //

ASIGN_SENT : ASIGN PUNTOCOMA; //le he metido un landa por si al momento de llamar a una expresion no hay nada deberia de seguir siendo valido
                              // puse que era una expresion pero era una asignacion quito el lambda, igualmente una expresion es del tipo A && B nunca va estar vacio eso

ASIGN :  ASIGN_SIMPLE | ASIGN_ARRAY;

ASIGN_SIMPLE: ID ASIGNACION EXPRESION;

ASIGN_ARRAY : ID DIM_ARRAY ASIGNACION EXPRESION;



// ----------------------------------------------------------------------------------------------------------------- //
//                                                       CONDICIONAL_SENT                                            //
SWITCH : MULTIPLE LPAREN ID RPAREN LBRACKET CASES RBRACKET; //switch (id) {cases}   {crear nodo de switch}


CASES :  CASE INIT_CASES DOSPUNTOS SENTS BREAK PUNTOCOMA CASES //case INIT: expresion; break;   case 1: break; si no hay expresion deriva en landa
        | DEFAULT DOSPUNTOS SENTS BREAK PUNTOCOMA    //default: expresion break; si no hay expresion deriba en landa
        | ; //no hay ningun case ni default o ya no hay que poner mas casos

INIT_CASES : NUMERO 
            | STR 
            | CHAR ; //   case 1:   case "uno":  case '1': 


IF_COND : CONDICIONAL LPAREN EXPRESION RPAREN LBRACKET SENTS RBRACKET  ELSE_COND;   

    //ns si poner que if_cond derive en esto LPAREN EXPRESION RPAREN LBRACKET SENTS RBRACKET  ELSE_COND o añadirle eso al else_cond
ELSE_COND: ELIF LPAREN EXPRESION RPAREN LBRACKET SENTS RBRACKET  ELSE_COND
          | ELSE LBRACKET SENTS RBRACKET
          | ;

// ----------------------------------------------------------------------------------------------------------------- //
//                                                       ITERATIVO_SENT                                              //
ITERATIVO_SENT: BUCLE_SENT | FOR_SENT;
FOR_SENT: ITERATIVO LPAREN VARFOR PUNTOCOMA EXPRESION_BOOL PUNTOCOMA AUGDISM RPAREN LCORCHETE SENTS RCORCHETE;
VARFOR: DEC | ID;
AUGDISM: EXPRESION_NUM | INCRDECR_SENT;
// ----------------------------------------------------------------------------------------------------------------- //
//                                                       BUCLE_WHILE_SENT                                              //
BUCLE_SENT : BUCLE LPAREN EXPRESION RPAREN LBRACKET SENTS RBRACKET;

// ----------------------------------------------------------------------------------------------------------------- //
//                                                       FUNCION_SENT                                                //
FUNCION_SENT: FUNC PUNTOCOMA;
FUNC: FUNCTION_USE | FUNCTION_DECLARATION;
FUNCTION_DECLARATION: FUNCTION_NAME RCORCHETE SENTS LCORCHETE;
FUNCTION_NAME: FUNCION TYPE ID;
FUNCTION_USE: FUNCI RPAREN EXPRESION LPAREN;
FUNCI: SYSTEM_FUN | USER_FUN;
SYSTEM_FUN: ENTRADA_FUN | SALIDA_FUN; 
SALIDA_FUN: PRINT | PRINTLN;
ENTRADA_FUN: SISTEMA PUNTO ENTRADA;
USER_FUN: ID;


// ----------------------------------------------------------------------------------------------------------------- //
//                                                       EXPRESION                                                   //

OPLOG: AND 
      | OR 
      | NOT 
      | MAYOR 
      | MENOR 
      | IGUALMAY 
      | IGUALMEN 
      | IGUAL; 

OP: SUMA 
    | RESTA 
    | MULTI 
    | DIV 
    | MODULO;

BOOL: T 
      | F;

NUMERICO: NUMERO 
          | DECIMAL;

EXPRESION: EXPRESION_SIMPLE EXPRESION 
          | ; // Hay que mirar que operador ponemos entre exprsimple y expresion habria que poner uno global

EXPRESION_SIMPLE: EXPRESION_BOOL 
                  | EXPRESION_NUM 
                  | BOOL 
                  | NUMERICO;

EXPRESION_BOOL: ID OPLOG ID 
                | BOOL OPLOG BOOL;

EXPRESION_NUM: NUMERICO OP NUMERICO | ID OP ID | ID OP NUMERICO | NUMERICO OP ID;

// ----------------------------------------------------------------------------------------------------------------- //
//                                                       INCRDECR_SENT                                                   //
INCRDECR_SENTS: INCRDECR_SENT PUNTOCOMA;
INCRDECR_SENT: INCR_SENT | DECR_SENT;
INCR_SENT: ID SUMA SUMA;
DECR_SENT: ID RESTA RESTA;

%%
/* accions */
main(int argc, char *argv [])
{
  yyparse();
}

yyerror(char *s)
{
  fprintf(stderr, "error: %s\n", s);
}