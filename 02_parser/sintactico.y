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