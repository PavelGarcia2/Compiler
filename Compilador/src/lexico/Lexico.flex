package lexico;
import java.io.*;
import static lexico.Tokens.*;

%%
// Declaraciones

%public                 // Indicamos que la clase será pública
%class Scanner          // Indicamos el nombre de la clase que se generará
%standalone


// Elementos del codigo

digito      = [0-9]
tilde       = [ÁÀÉÈÍÓÒÚÏÜáàéèíóòúïü]
letra       = [A-Za-z]|{tilde}
id          = {letra}({letra}|{digito})*
slinea      = [\n\r]+
espacios    = [ \t]+
numero      = {digito}+
decimal     = {digito}*{punto}{digito}+
lparen	    = \(
rparen	    = \)
lbracket	= \{
rbracket	= \}
lcorchete   = \[
rcorchete   = \]
coma        = \,
puntoComa   = \;
dosPuntos   = \:
punto       = \.
array       = {lcorchete}{rcorchete}({lcorchete}{rcorchete})*
str         = "\""[^"\""]*"\""
char        = "\'"[^"\'"]"\'"
comLinea    = "//"([^\n\r])*
multiCom    = "/*"[^"*/"]*"*/"


// Operadores

and         = \&
or          = \|
not         = \!
suma        = \+
resta       = \-
multi       = \*
div         = \/
modulo      = "mod"
tupla       = "tup"
mayor       = \>
menor       = \<
igualMay    = "=>" 
igualMen    = "=<"
asignacion  = "="
igual       = "=="


// Palabras reservadadas

w_true      = ("true"|"True")
w_false     = ("false"|"False")
w_program   = "program"
w_fun       = "fun"
w_switch    = "switch"
w_if        = "if"
w_else      = "else"
w_elif      = "elif"
w_while     = "while"
w_for       = "for"
w_const     = "const"
w_return    = "return"
w_print     = "print"
w_println   = "println"
w_new       = "new"
w_case      = "case"
w_break     = "break"
w_default   = "default"
w_char      = "char"
w_float     = "float"
w_int       = "int"
w_bool      = "bool"
w_str       = "str"
w_in        = "in"
w_sys       = "sys"
w_exec      = "exec"

%{
// Este codigo se copiara en la clase generada Scanner
public String lexeme;

public String muestraError(String lexema){
    return "Error: No esta permitido "+lexema;
}
%}



%%
//Reglas

{w_program}                            { return t_program; } 
{w_return}                             { return t_return; } 
{w_exec}                               { return t_exec; }
{espacios}                             { /* ignore */ }
{w_fun}                                { return t_fun; }
{multiCom}                             { /* ignore */ }
{comLinea}                             { /* ignore */ }
{w_in}                                 { return t_in; }
{w_sys}                                { return t_sys; }
{and}                                  { return t_and; }
{or}                                   { return t_or; }
{not}                                  { return t_not; }
{asignacion}                           { return t_asignacion; }
{suma}                                 { return t_suma; }
{resta}                                { return t_resta; }
{multi}                                { return t_multiplicacion; }
{div}                                  { return t_division; }
{modulo}                               { return t_modulo; }
{mayor}                                { return t_mayor; }
{menor}                                { return t_menor; }
{igualMay}                             { return t_igualMayor; }
{igualMen}                             { return t_igualMenor; }
{igual}                                { return t_igual; }
{w_new}                                { return t_new; }
{w_case}                               { return t_case; }
{w_break}                              { return t_break; }
{w_default}                            { return t_default; }
{w_if}                                 { return t_if; }
{w_else}                               { return t_else; }
{w_elif}                               { return t_elif; }
{w_while}                              { return t_while; }
{w_for}                                { return t_for; }
{w_switch}                             { return t_switch; }
{w_char}                               { return t_char; }
{w_float}                              { return t_float; }
{w_int}                                { return t_int; }
{w_bool}                               { return t_bool; }
{w_str}                                { return t_str; }
{w_const}                              { return t_const; }
{tupla}                                { return t_tupla; }
{array}                                { return t_array; }
{str}                                  { lexeme=yytext(); return t_lineaCaracteres; }
{char}                                 { lexeme=yytext(); return t_caracter; }
{decimal}                              { lexeme=yytext(); return t_decimal; }
{w_true}                               { return t_true; }
{w_false}                              { return t_false; }
{w_print}                              { return t_print; }
{w_println}                            { return t_println; }
{id}                                   { lexeme=yytext(); return t_id; }
{numero}                               { lexeme=yytext(); return t_numero; }
{slinea}                               { return t_saltoLinea; }
{rparen}                               { return t_rParentesis; }
{lparen}                               { return t_lParentesis; }
{rbracket}                             { return t_rBracket; } 
{lbracket}                             { return t_lBracket; }
{rcorchete}                            { return t_rCorchete; } 
{lcorchete}                            { return t_lCorchete; }
{coma}                                 { return t_coma; }
{puntoComa}                            { return t_puntoComa; }
{punto}                                { return t_punto;}
{dosPuntos}                            { return t_dosPuntos; }  
.                                      { return muestraError(yytext()); } 
