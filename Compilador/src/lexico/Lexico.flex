package lexico;

import java.io.*;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

import sintactico.ParserSym;

%%
// Declaraciones

%public                 // Indicamos que la clase será pública
%class Scanner          // Indicamos el nombre de la clase que se generará
%line
%column
%cup
%char
%int

%eofval{
  return symbol(ParserSym.EOF);
%eofval}

//============================//
//   ESTRUCTURA DE PROGRAMA   //
//============================//

w_main      = "main"
w_fun       = "fun"
w_return    = "return"
w_new       = "new"
w_const     = "const"

//============================//
//     CONTROL DE FLUJO       //
//============================//

w_switch    = "switch"
w_if        = "if"
w_else      = "else"
w_elif      = "elif"
w_while     = "while"
w_for       = "for"
w_case      = "case"
w_break     = "break"
w_default   = "default"


//============================//
//         TIPOS              //
//============================//

w_char      = "char"
w_float     = "float"
w_int       = "int"
w_bool      = "bool"
w_str       = "str"
w_true      = ("true"|"True")
w_false     = ("false"|"False")
w_tupla       = "tup"

//============================//
//         OPERADORES         //
//============================//

and         = \&
or          = \|
not         = \!
suma        = \+
resta       = \-
multi       = \*
div         = \/
modulo      = "mod"
mayor       = \>
menor       = \<
igualMay    = "=>" 
igualMen    = "=<"
asignacion  = "="
igual       = "=="


//============================//
//         SIMBOLOS           //
//============================//

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


//============================//
//        FUNCIONES           //
//============================//

w_print     = "print"
w_println   = "println"
w_in        = "in"
w_sys       = "sys"


//============================//
//        COMENTARIOS         //
//============================//

comLinea    = "//"([^\n\r])*
multiCom    = "/*"[^"*/"]*"*/"


//============================//
//        TEXTO               //
//============================//

tilde       = [ÁÀÉÈÍÓÒÚÏÜáàéèíóòúïü]
letra       = [A-Za-z]|{tilde}
id          = {letra}({letra}|{digito})*
slinea      = [\n\r]+
espacios    = [ \t]+
str         = "\""[^"\""]*"\""
char        = "\'"[^"\'"]"\'"


//============================//
//         NUMERO             //
//============================//
digito      = [0-9]
numero      = {digito}+
decimal     = {digito}*{punto}{digito}+



%{

    // Construcció d'un symbol sense atribut associat.
    private ComplexSymbol symbol(int type) {
        // Sumar 1 per a que la primera línia i columna no sigui 0.
        Location esquerra = new Location(yyline+1, yycolumn+1);
        Location dreta = new Location(yyline+1, yycolumn+yytext().length()+1);

        return new ComplexSymbol(ParserSym.terminalNames[type], type, esquerra, dreta);
    }

    // Construcció d'un symbol amb un atribut associat.
    private Symbol symbol(int type, Object value) {
        // Sumar 1 per a que la primera línia i columna no sigui 0.
        Location esquerra = new Location(yyline+1, yycolumn+1);
        Location dreta = new Location(yyline+1, yycolumn+yytext().length()+1);

        return new ComplexSymbol(ParserSym.terminalNames[type], type, esquerra, dreta, value);
    }

    private void muestraError(String lexema, int linea){
        System.out.println("ERROR: no reconocemos " + lexema + "en la linea " + linea );
    }

%}


%%
//Reglas

{w_main}                               { return symbol(ParserSym.tMain); } 
{w_return}                             { return symbol(ParserSym.tReturn); } 
{espacios}                             { /* ignore */ }
{w_fun}                                { return symbol(ParserSym.tFun); }
{multiCom}                             { /* ignore */ }
{comLinea}                             { /* ignore */ }
{w_in}                                 { return symbol(ParserSym.tIn); }
{w_sys}                                { return symbol(ParserSym.tSys); }
{and}                                  { return symbol(ParserSym.tAnd); }
{or}                                   { return symbol(ParserSym.tOr); }
{not}                                  { return symbol(ParserSym.tNot); }
{asignacion}                           { return symbol(ParserSym.tIgual); }
{suma}                                 { return symbol(ParserSym.tSuma); }
{resta}                                { return symbol(ParserSym.tResta); }
{multi}                                { return symbol(ParserSym.tMult); }
{div}                                  { return symbol(ParserSym.tDiv); }
{modulo}                               { return symbol(ParserSym.tMod); }
{mayor}                                { return symbol(ParserSym.tMayor); }
{menor}                                { return symbol(ParserSym.tMenor); }
{igualMay}                             { return symbol(ParserSym.tIgualMay); }
{igualMen}                             { return symbol(ParserSym.tMenor); }
{igual}                                { return symbol(ParserSym.tIgualIgual); }
{w_new}                                { return symbol(ParserSym.tNew); }
{w_case}                               { return symbol(ParserSym.tCase); }
{w_break}                              { return symbol(ParserSym.tBreak); }
{w_default}                            { return symbol(ParserSym.tDefault); }
{w_if}                                 { return symbol(ParserSym.tIf); }
{w_else}                               { return symbol(ParserSym.tElse); }
{w_elif}                               { return symbol(ParserSym.tElif); }
{w_while}                              { return symbol(ParserSym.tWhile); }
{w_for}                                { return symbol(ParserSym.tFor); }
{w_switch}                             { return symbol(ParserSym.tSwitch); }
{w_char}                               { return symbol(ParserSym.tChar); }
{w_float}                              { return symbol(ParserSym.tFloat); }
{w_int}                                { return symbol(ParserSym.tInt); }
{w_bool}                               { return symbol(ParserSym.tBool); }
{w_str}                                { return symbol(ParserSym.tStr); }
{w_const}                              { return symbol(ParserSym.tConst); }
{w_tupla}                              { return symbol(ParserSym.tTupla); }
{array}                                { return symbol(ParserSym.tArray); }
{str}                                  { return symbol(ParserSym.tLinea, yytext()); }
{char}                                 { return symbol(ParserSym.tCaracter, yytext()); }
{decimal}                              { return symbol(ParserSym.tDecimal, yytext()); }
{w_true}                               { return symbol(ParserSym.tTrue); }
{w_false}                              { return symbol(ParserSym.tFalse); }
{w_print}                              { return symbol(ParserSym.tPrint); }
{w_println}                            { return symbol(ParserSym.tPrintln); }
{id}                                   { return symbol(ParserSym.tId, yytext()); }
{numero}                               { return symbol(ParserSym.tEntero, yytext()); }
{slinea}                               { return symbol(ParserSym.tSlinea); }
{rparen}                               { return symbol(ParserSym.tRparen); }
{lparen}                               { return symbol(ParserSym.tLparen); }
{rbracket}                             { return symbol(ParserSym.tRbracket); } 
{lbracket}                             { return symbol(ParserSym.lRbracket); }
{rcorchete}                            { return symbol(ParserSym.tRcorchete); } 
{lcorchete}                            { return symbol(ParserSym.lRcorchete); }
{coma}                                 { return symbol(ParserSym.tComa);}
{puntoComa}                            { return symbol(ParserSym.tPuntocoma); }
{punto}                                { return symbol(ParserSym.tPunto); }
{dosPuntos}                            { return symbol(ParserSym.tDospuntos); }  
.                                      { return muestraError(yytext(), yyline); } 