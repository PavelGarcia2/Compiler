/* Para la ejecución con archivo introducimos ./a.out -nombre archivo- */
/* Librerias */
%{
#include <stdio.h>
%}

/* Expresiones Regulares */
digito      [0-9]
letra       [A-Za-z]
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
Scomilla    \' 
Ccomilla    \" 
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

sfuncion    "fun"
multiple    "switch"
condicional "if"
bucle       "while"
iterativo   "for"
constante   "const"
retorno     "return"

char        "char"
float       "float"
entero      "int"
boleano     "bool"
string      "str"

true        ["true" | "True"]
false       ["false" | "False"]

asignacion  "="
igual       "=="

comLinea    "//"
comInicio   "/*"
comFin      "*/"

entrada     "in"
salida      "out"
sistema     "sys"

/* Acciones */
%%

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

{char}                                 { printf("Palabra reservado char: %s\n", yytext); }
{float}                                { printf("Palabra reservada float: %s\n", yytext); }
{entero}                               { printf("Palabra reservada entero: %s\n", yytext); }
{boleano}                              { printf("Palabra reservada boolean: %s\n", yytext); }

{id}                                   { printf("identificador: %s\n", yytext); }
{numero}                               { printf("número: %s\n", yytext); }
{slinea}                               {  }
{espacios}                             {  }
{rparen}                               { printf("Parentesis derecho %s\n", yytext); }
{lparen}                               { printf("Parentesis izquierdo %s\n", yytext); }
{rbracket}                             { printf("Lleve cerrada %s\n", yytext); } 
{lbracket}                             { printf("Llave abierta %s\n", yytext); }
{rcorchet}                             { printf("Corchete cerrada %s\n", yytext); } 
{lbracket}                             { printf("Corchete abierta %s\n", yytext); }
{Scomilla}                             { printf("Comilla simple %s\n", yytext); }
{Ccomilla}                             { printf("Comilla compuesta %s\n", yytext); }
{coma}                                 { printf("Coma %s\n", yytext); }
{puntoComa}                            { printf("PuntoComa %s\n", yytext); }
{punto}                                { printf("Punto %s\n", yytext); }

{funcion}                              { printf("Declaración de una función: %s\n", yytext); }
{comentarioInicio}                     { printf("Inicio de comentario: %s\n", yytext); }
{comentarioFin}                        { printf("Final de comentarios: %s\n", yytext); }
{comentario}                           { printf("Linea comentario: %s\n", yytext); }
{in}                                   { printf("Entrada sistema: %s\n", yytext); }
{out}                                  { printf("Salida sistema: %s\n", yytext); }
{sys}                                  { printf("Sistema: %s\n", yytext); }
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