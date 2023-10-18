/* Para la ejecución con archivo introducimos ./a.out -nombre archivo- */
/* Librerias */
%{
#include <stdio.h>
%}

/* Expresiones Regulares */
digito      [0-9]
tilde       [ÁÀÉÈÍÓÒÚÏÜáàéèíóòúïü]
letra       [A-Za-z]|{tilde}
id          {letra}({letra}|{digito})*
slinea      [\n\r]+
espacios    [ \t]+
numero      {digito}+

lparen	   \(
rparen	   \)
lbracket	   \{
rbracket	   \}
lcorchete   \[
rcorchete   \]
coma        \,
puntoComa   \;
punto       \.

and         \&
or          \|

suma        \+
resta       \-
multi       \*
div         \/
modulo      "mod"

mayor       \>
menor       \<
igualMay    "=>" 
igualMen    "=<"

funcion     "fun"
multiple    "switch"
condicional "if"
bucle       "while"
iterativo   "for"
constante   "const"
retorno     "return"
print       "print"
println     "println"

character   "char"
float       "float"
entero      "int"
boleano     "bool"
string      "str"

str         "\""[^"\""]*"\""
char        "\'"[^"\'"]"\'"
flt         {digito}*{punto}{digito}+

true        ["true" | "True"]
false       ["false" | "False"]

asignacion  "="
igual       "=="

comLinea    "//"([^\n\r])*
multiCom    "/*"[^"*/"]*"*/"

entrada     "in"
sistema     "sys"

/* Acciones */
%%
{funcion}                              { printf("Declaracion de una funcion: %s\n", yytext); }
{multiCom}                             { printf("Comentario multiple: %s\n", yytext); }
{comLinea}                             { printf("Linea comentario: %s\n", yytext); }
{entrada}                              { printf("Entrada sistema: %s\n", yytext); }
{sistema}                              { printf("Sistema: %s\n", yytext); }

{and}                                  { printf("And: %s\n", yytext); }
{or}                                   { printf("Or: %s\n", yytext); }

{asignacion}                           { printf("Asignacion: %s\n", yytext); }
{suma}                                 { printf("Signo suma: %s\n", yytext); }
{resta}                                { printf("Signo resta: %s\n", yytext); }
{multi}                                { printf("Signo multiplicacion: %s\n", yytext); }
{div}                                  { printf("Signo division: %s\n", yytext); }
{modulo}                               { printf("Signo de modulo: %s\n", yytext); }

{mayor}                                { printf("Signo mayor: %s\n", yytext); }
{menor}                                { printf("Signo menor: %s\n", yytext); }
{igualMay}                             { printf("Signo de mayor igual: %s\n", yytext); }
{igualMen}                             { printf("Signo menor o igual: %s\n", yytext); }
{igual}                                { printf("Igualacion: %s\n", yytext); }

{condicional}                          { printf("Palabra reservada: %s\n", yytext); }
{bucle}                                { printf("Palabra reservada : %s\n", yytext); }
{iterativo}                            { printf("Palabra reservada: %s\n", yytext); }
{multiple}                             { printf("Palabra reservada: %s\n", yytext); }

{character}                            { printf("Palabra reservado char: %s\n", yytext); }
{float}                                { printf("Palabra reservada float: %s\n", yytext); }
{entero}                               { printf("Palabra reservada entero: %s\n", yytext); }
{boleano}                              { printf("Palabra reservada boolean: %s\n", yytext); }
{string}                               { printf("Palabra reservada string: %s\n", yytext); }

{str}                                  { printf("Contenido string %s\n", yytext); }
{char}                                 { printf("Contenido caracter: %s\n", yytext); }
{flt}                                  { printf("Contenido float: %s\n", yytext); }

{print}                                { printf("Palabra reservada print: %s\n", yytext); }
{println}                              { printf("Palabra reservada println: %s\n", yytext); }

{id}                                   { printf("identificador: %s\n", yytext); }
{numero}                               { printf("número: %s\n", yytext); }
{slinea}                               {  }
{espacios}                             {  }
{rparen}                               { printf("Parentesis derecho %s\n", yytext); }
{lparen}                               { printf("Parentesis izquierdo %s\n", yytext); }
{rbracket}                             { printf("Lleve cerrada %s\n", yytext); } 
{lbracket}                             { printf("Llave abierta %s\n", yytext); }
{rcorchete}                            { printf("Corchete cerrada %s\n", yytext); } 
{lcorchete}                            { printf("Corchete abierta %s\n", yytext); }
{coma}                                 { printf("Coma %s\n", yytext); }
{puntoComa}                            { printf("PuntoComa %s\n", yytext); }
{punto}                                { printf("Punto %s\n", yytext); }

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