/* Para la ejecución con archivo introducimos ./a.out -nombre archivo- */
/* Librerias */
%{
#include <stdio.h>
#include "tokens.h"
void muestraError();
%}

/* Expresiones Regulares */
digito      [0-9]
tilde       [ÁÀÉÈÍÓÒÚÏÜáàéèíóòúïü]
letra       [A-Za-z]|{tilde}
id          {letra}({letra}|{digito})*
slinea      [\n\r]+
espacios    [ \t]+
numero      {digito}+
decimal     {digito}*{punto}{digito}+

lparen	   \(
rparen	   \)
lbracket	   \{
rbracket	   \}
lcorchete   \[
rcorchete   \]
coma        \,
puntoComa   \;
dosPuntos   \:
punto       \.

and         \&
or          \|
not         \!

suma        \+
resta       \-
multi       \*
div         \/
modulo      "mod"
tupla       "tup"

mayor       \>
menor       \<
igualMay    "=>" 
igualMen    "=<"

programa    "program"
funcion     "fun"
multiple    "switch"
condicional "if"
else        "else"
elif        "elif"

bucle       "while"
iterativo   "for"
constante   "const"
retorno     "return"
print       "print"
println     "println"
new         "new"
case        "case"
break       "break"
default     "default"

character   "char"
float       "float"
entero      "int"
boleano     "bool"
string      "str"
array       {lcorchete}{rcorchete}({lcorchete}{rcorchete})*

str         "\""[^"\""]*"\""
char        "\'"[^"\'"]"\'"

t           ("true"|"True")
f           ("false"|"False")

asignacion  "="
igual       "=="

comLinea    "//"([^\n\r])*
multiCom    "/*"[^"*/"]*"*/"

entrada     "in"
sistema     "sys"

ejecutar    "exec"

/* Acciones */
%%
{programa}                             { return PROGRAM; } 
{retorno}                              { return RETORNO; } 
{ejecutar}                             { return EJECUTAR; }
{espacios}                             { /* ignore */ }
{funcion}                              { return FUNCION; }
{multiCom}                             { /* ignore */ }
{comLinea}                             { /* ignore */ }
{entrada}                              { return ENTRADA; }
{sistema}                              { return SISTEMA; }
{and}                                  { return AND; }
{or}                                   { return OR; }
{not}                                  { return NOT; }
{asignacion}                           { return ASIGNACION; }
{suma}                                 { return SUMA; }
{resta}                                { return RESTA; }
{multi}                                { return MULTI; }
{div}                                  { return DIV; }
{modulo}                               { return MODULO; }
{mayor}                                { return MAYOR; }
{menor}                                { return MENOR; }
{igualMay}                             { return IGUALMAY; }
{igualMen}                             { return IGUALMEN; }
{igual}                                { return IGUAL; }
{new}                                  { return NEW; }
{case}                                 { return CASE; }
{break}                                { return BREAK; }
{default}                              { return DEFAULT; }
{condicional}                          { return CONDICIONAL; }
{else}                                 { return ELSE; }
{elif}                                 { return ELIF; }
{bucle}                                { return BUCLE; }
{iterativo}                            { return ITERATIVO; }
{multiple}                             { return MULTIPLE; }
{character}                            { return CHARACTER; }
{float}                                { return FLOAT; }
{entero}                               { return ENTERO; }
{boleano}                              { return BOLEANO; }
{string}                               { return STRING; }
{constante}                            { return CONSTANTE; }
{tupla}                                { return TUPLA; }
{array}                                { return ARRAY; }
{str}                                  { yylval.string = stdrup(yytext); return STR; }
{char}                                 { yylval.simbolo = yytext;return CHAR; }
{decimal}                              { yylval.real = atof(yytext); return DECIMAL; }
{t}                                    { return T; }
{f}                                    { return F; }
{print}                                { return PRINT; }
{println}                              { return PRINTLN; }
{id}                                   { yylval.string = stdrup(yytext); return ID; }
{numero}                               { yylval.entero = atoi(yytext); return NUMERO; }
{slinea}                               { return SLINEA; }
{rparen}                               { return RPAREN; }
{lparen}                               { return LPAREN; }
{rbracket}                             { return RBRACKET; } 
{lbracket}                             { return LBRACKET; }
{rcorchete}                            { return RCORCHETE; } 
{lcorchete}                            { return LCORCHETE; }
{coma}                                 { return COMA; }
{puntoComa}                            { return PUNTOCOMA; }
{punto}                                { return PUNTO;}
{dosPuntos}                            { return DOSPUNTOS; }  
.                                      { muestraError(); } 
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

void muestraError()
{
   printf("altres : %s\n", yytext);
}


