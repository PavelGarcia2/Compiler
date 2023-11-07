%{
#include <stdio.h>

int yylex();
int yyerror(char *s);

%}

%token ID NUMERO DECIMAL STR CHAR T F  
%token SUMA RESTA MULTI DIV MODULO
%token AND OR NOT MAYOR MENOR IGUALMAY IGUALMEN IGUAL ASIGNACION
%token FUNCION ENTRADA SISTEMA NEW PRINT PRINTLN
%token CONDICIONAL ELSE ELIF BUCLE ITERATIVO MULTIPLE CASE BREAK DEFAULT RETORNO
%token CHARACTER FLOAT ENTERO BOLEANO STRING CONSTANTE TUPLA
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

PROGRAM : EXEC LCORCHETE SENTS RCORCHETE;
SENTS : SENT SENTS | ;
SENT : EXPRESION_SENT | DECLARACION_SENT | CONDICIONAL_SENT | ITERATIVO_SENT | FUNCION_SENT; /* incompleto */
DECLARACION_SENT : DECLARACION PUNTOCOMA;
DECLARACION: ENTERO ID | CHARACTER ID | FLOAT ID | BOLEANO ID | STRING ID;
EXPRESION_SENT : EXPRESION PUNTOCOMA;
EXPRESION :  ID ASIGNACION EXPRESION | ; /* incompleto */


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