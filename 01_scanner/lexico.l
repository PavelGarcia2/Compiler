/* Para la ejecución con archivo introducimos ./a.out -nombre archivo- */
/* Librerias */
%{
#include <stdio.h>
%}

/* Expresiones Regulares */
digito [0-9]
letra [A-Za-z]
id {letra}({letra}|{digito})*
slinea [\n\r]+
espacios [ \t]+
numero {digito}+

lparen	\(
rparen	\)
lbracket	\{
rbracket	\}

suma         \+
resta        \-
multi        \*
div          \/
modulo       "mod"

mayor       \>
menor       \<
igualMay    "=>" 
igualMen    "=<"



condicional  "if"
bucle       "while"
iterativo   "for"


char        "char"
float       "float"
entero      "int"
boleano     "bool"

asignacion "="
igual "=="





/* Acciones */
%%
{asignacion}                           { printf("IAsignacion: %s\n", yytext); }
{suma}                                 { printf("Signo suma: %s\n", yytext); }
{resta}                                 { printf("Signo resta: %s\n", yytext); }
{multi}                                 { printf("Signo multiplicacion: %s\n", yytext); }
{div}                                 { printf("Signo division: %s\n", yytext); }
{modulo}                                 { printf("Signo de modulo: %s\n", yytext); }




{igual}                                { printf("Igualacion: %s\n", yytext); }
{condicional}                          { printf("Palabra reservada: %s\n", yytext); }
{bucle}                                { printf("Palabra reservada : %s\n", yytext); }
{iterativo}                            { printf("Palabra reservada: %s\n", yytext); }
{entero}                               { printf("Palabra reservada: %s\n", yytext); }
{boleano}                              { printf("Palabra reservada: %s\n", yytext); }
{id}                                   { printf("identificador: %s\n", yytext); }
{numero}                               { printf("número: %s\n", yytext); }
{slinea}                               { }
{espacios}                             { }
{rparen}                               { printf("Parentesis derecho %s\n", yytext); }
{lparen}                               { printf("Parentesis izquierdo %s\n", yytext); }
{rbracket}                             { printf("Lleve cerrada %s\n", yytext); } 
{lbracket}                             { printf("Llave abierta %s\n", yytext); }
.                                      { printf("altres : %s\n", yytext); } 
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
