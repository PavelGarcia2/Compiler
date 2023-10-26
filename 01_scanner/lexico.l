/* Para la ejecución con archivo introducimos ./a.out -nombre archivo- */
/* Librerias */
%{
#include <stdio.h>
#include "tokens.h"

int yylval;
float yylvalF;
char *yylvalID;

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
dosPuntos   \:
punto       \.

and         \&
or          \|

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

funcion     "fun"
multiple    "switch"
condicional "if"
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

str         "\""[^"\""]*"\""
char        "\'"[^"\'"]"\'"
decimal     {digito}*{punto}{digito}+

t           ("true"|"True")
f           ("false"|"False")

asignacion  "="
igual       "=="

comLinea    "//"([^\n\r])*
multiCom    "/*"[^"*/"]*"*/"

entrada     "in"
sistema     "sys"

/* Acciones */
%%
{funcion}                              { printing(yytext); return FUNCION; }
{multiCom}                             { printing(yytext); return MULTICOM; }
{comLinea}                             { printing(yytext); return COMLINEA; }
{entrada}                              { printing(yytext); return ENTRADA; }
{sistema}                              { printing(yytext); return SISTEMA; }
{and}                                  { printing(yytext); return AND; }
{or}                                   { printing(yytext); return OR; }
{asignacion}                           { printing(yytext); return ASIGNACION; }
{suma}                                 { printing(yytext); return SUMA; }
{resta}                                { printing(yytext); return RESTA; }
{multi}                                { printing(yytext); return MULTI; }
{div}                                  { printing(yytext); return DIV; }
{modulo}                               { printing(yytext); return MODULO; }
{mayor}                                { printing(yytext); return MAYOR; }
{menor}                                { printing(yytext); return MENOR; }
{igualMay}                             { printing(yytext); return IGUALMAY; }
{igualMen}                             { printing(yytext); return IGUALMEN; }
{igual}                                { printing(yytext); return IGUAL; }
{new}                                  { printing(yytext); return NEW; }
{case}                                 { printing(yytext); return CASE; }
{break}                                { printing(yytext); return BREAK; }
{default}                              { printing(yytext); return DEFAULT; }
{condicional}                          { printing(yytext); return CONDICIONAL; }
{bucle}                                { printing(yytext); return BUCLE; }
{iterativo}                            { printing(yytext); return ITERATIVO; }
{multiple}                             { printing(yytext); return MULTIPLE; }
{character}                            { printing(yytext); return CHARACTER; }
{float}                                { printing(yytext); return FLOAT; }
{entero}                               { printing(yytext); return ENTERO; }
{boleano}                              { printing(yytext); return BOLEANO; }
{string}                               { printing(yytext); return STRING; }
{constante}                            { printing(yytext); return CONSTANTE; }
{tupla}                                { printing(yytext); return TUPLA; }
{str}                                  { printing(yytext); return STR; }
{char}                                 { printing(yytext); return CHAR; }
{decimal}                              { printing(yytext); yylvalF = atof(yytext); return DECIMAL; }
{t}                                    { printing(yytext); return T; }
{f}                                    { printing(yytext); return F; }
{print}                                { printing(yytext); return PRINT; }
{println}                              { printing(yytext); return PRINTLN; }
{id}                                   { printing(yytext); yylvalID = yytext; return ID; }
{numero}                               { printing(yytext); yylval = atoi(yytext); return NUMERO; }
{slinea}                               { printing(yytext); return SLINEA; }
{espacios}                             {  /* ignore */ }
{rparen}                               { printing(yytext); return RPAREN; }
{lparen}                               { printing(yytext); return LPAREN; }
{rbracket}                             { printing(yytext); return RBRACKET; } 
{lbracket}                             { printing(yytext); return LBRACKET; }
{rcorchete}                            { printing(yytext); return RCORCHETE; } 
{lcorchete}                            { printing(yytext); return LCORCHETE; }
{coma}                                 { printing(yytext); return COMA; }
{puntoComa}                            { printing(yytext); return PUNTOCOMA; }
{punto}                                { printing(yytext); return PUNTO;}
{dosPuntos}                            { printing(yytext); return DOSPUNTOS; }   
{retorno}                              { printing(yytext); return RETORNO; } 
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

         int tok;

         while(tok = yylex())
         {
            printf("%d", tok);
            if(tok == NUMERO)
            {
               printf("Numero = %d\n", yylval);
            }
            else if (tok == DECIMAL)
            {
               printf("Decimal = %f\n", yylvalF);
            }
            else if (tok == ID)
            {
               printf("ID = %s\n", yylvalID);
            }
            else 
            {
               printf("\n");
            }
         }
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


