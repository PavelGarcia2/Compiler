%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Definición de la estructura Token
struct Token {
    char type[20];   // Tipo de token (puede ser "INT", "FLOAT", "CHAR", "STRING", "IDENTIFIER", etc.)
    char value[100]; // Valor del token (por ejemplo, el valor de una cadena o número)
    int line;        // Línea en la que se encuentra el token
};

// Variable global para almacenar el token actual
struct Token token;

// Función para imprimir un token
void printToken() {
    printf("{ Token Type: %s ", token.type);
    printf("Token Value: %s ", token.value);
    printf("Line Number: %d }\n", token.line);
}
%}

%option noyywrap

%%
int             {
    strcpy(token.type, "INT");
    strcpy(token.value, yytext);
    token.line = yylineno;
    printToken();
}
float           {
    strcpy(token.type, "FLOAT");
    strcpy(token.value, yytext);
    token.line = yylineno;
    printToken();
}
char            {
    strcpy(token.type, "CHAR");
    strcpy(token.value, yytext);
    token.line = yylineno;
    printToken();
}
\"[^\"]*\"      {
    strcpy(token.type, "STRING");
    strcpy(token.value, yytext);
    token.line = yylineno;
    printToken();
}
[a-zA-Z_][a-zA-Z0-9_]*   {
    strcpy(token.type, "IDENTIFIER");
    strcpy(token.value, yytext);
    token.line = yylineno;
    printToken();
}
\=              {
    strcpy(token.type, "ASSIGNMENT");
    strcpy(token.value, yytext);
    token.line = yylineno;
    printToken();
}
\;              {
    strcpy(token.type, "SEMICOLON");
    strcpy(token.value, yytext);
    token.line = yylineno;
    printToken();
}
[ \t\n]         ;  /* Ignorar espacios en blanco y tabulaciones */
.               { printf("Caracter no reconocido: %s\n", yytext); }
%%

int main(int argc, char* argv[] ){
    if(argc == 2) // Es dos por el nombre del ejecutable mas el del archivo 
   {
      FILE *archivo;
      archivo = fopen(argv[1],"r"); // Es argvalue[1] porque argvalue[0] es el nombre de este programa por estandar

      if(archivo != NULL)
      {
         yyin = archivo;
         yylex();
      }
      else
      {
         perror("ERROR ");
      }

   }
   else
   {
      if(argc!=1)
      {
         printf("ERROR: Sólo se permite un archivo a la vez\n");
      }
      else 
      {
         printf("ERROR: Introduzca un archivo de lectura\n");
      }
   }
   return 0;
}
