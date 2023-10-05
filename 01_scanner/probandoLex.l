/* Para la ejecución con archivo introducimos ./a.out -nombre archivo- */
/* Librerias */
%{
#include <stdio.h>
%}

/* Expresiones Regulares */
digit [0-9]
letter [A-Za-z]
id {letter}({letter}|{digit})*
blancos [ \n\t\r]+

/* Acciones */
%%
{digit}+ { printf("número: %s\n", yytext); }
{id} { printf("identificador: %s\n", yytext); }
{blancos} { }
. { printf("altres : %s\n", yytext); }
%%


void main(int argc, char* argv[]) {
   //Comprobamos que nos hayan introducido un sólo archivo con argument count
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
}
