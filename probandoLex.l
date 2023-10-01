%{
#include <stdio.h>
%}
digit [0-9]
letter [A-Za-z]
id {letter}({letter}|{digit})*
%%
{digit}+ { printf("nombre : %s\n", yytext); }
{id} { printf("identificador: %s\n", yytext); }
. { printf("altres : %s\n", yytext); }
%%
void main() {
yylex();
}