/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_YY_SINTACTICOV2_TAB_H_INCLUDED
# define YY_YY_SINTACTICOV2_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 1
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    FUNCION = 258,                 /* FUNCION  */
    ENTRADA = 259,                 /* ENTRADA  */
    SISTEMA = 260,                 /* SISTEMA  */
    AND = 261,                     /* AND  */
    OR = 262,                      /* OR  */
    NOT = 263,                     /* NOT  */
    ASIGNACION = 264,              /* ASIGNACION  */
    SUMA = 265,                    /* SUMA  */
    RESTA = 266,                   /* RESTA  */
    MULTI = 267,                   /* MULTI  */
    DIV = 268,                     /* DIV  */
    MODULO = 269,                  /* MODULO  */
    MAYOR = 270,                   /* MAYOR  */
    MENOR = 271,                   /* MENOR  */
    IGUALMAY = 272,                /* IGUALMAY  */
    IGUALMEN = 273,                /* IGUALMEN  */
    IGUAL = 274,                   /* IGUAL  */
    NEW = 275,                     /* NEW  */
    CASE = 276,                    /* CASE  */
    BREAK = 277,                   /* BREAK  */
    DEFAULT = 278,                 /* DEFAULT  */
    CONDICIONAL = 279,             /* CONDICIONAL  */
    ELSE = 280,                    /* ELSE  */
    ELIF = 281,                    /* ELIF  */
    BUCLE = 282,                   /* BUCLE  */
    ITERATIVO = 283,               /* ITERATIVO  */
    MULTIPLE = 284,                /* MULTIPLE  */
    CHARACTER = 285,               /* CHARACTER  */
    FLOAT = 286,                   /* FLOAT  */
    ENTERO = 287,                  /* ENTERO  */
    BOLEANO = 288,                 /* BOLEANO  */
    STRING = 289,                  /* STRING  */
    CONSTANTE = 290,               /* CONSTANTE  */
    TUPLA = 291,                   /* TUPLA  */
    ARRAY = 292,                   /* ARRAY  */
    STR = 293,                     /* STR  */
    CHAR = 294,                    /* CHAR  */
    DECIMAL = 295,                 /* DECIMAL  */
    T = 296,                       /* T  */
    F = 297,                       /* F  */
    PRINT = 298,                   /* PRINT  */
    PRINTLN = 299,                 /* PRINTLN  */
    ID = 300,                      /* ID  */
    NUMERO = 301,                  /* NUMERO  */
    SLNIEA = 302,                  /* SLNIEA  */
    RPAREN = 303,                  /* RPAREN  */
    LPAREN = 304,                  /* LPAREN  */
    RBRACKET = 305,                /* RBRACKET  */
    LBRACKET = 306,                /* LBRACKET  */
    RCORCHETE = 307,               /* RCORCHETE  */
    LCORCHETE = 308,               /* LCORCHETE  */
    COMA = 309,                    /* COMA  */
    PUNTOCOMA = 310,               /* PUNTOCOMA  */
    PUNTO = 311,                   /* PUNTO  */
    DOSPUNTOS = 312,               /* DOSPUNTOS  */
    PROGRAM = 313,                 /* PROGRAM  */
    RETORNO = 314,                 /* RETORNO  */
    EJECUTAR = 315                 /* EJECUTAR  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
union YYSTYPE
{
#line 5 "sintacticov2.y"

    int entero;
    char simbolo;
    float real;
    char *string;

#line 131 "sintacticov2.tab.h"

};
typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;


int yyparse (void);


#endif /* !YY_YY_SINTACTICOV2_TAB_H_INCLUDED  */
