%{
    /*definiciones*/
%}

%union{
    int entero;
    char simbolo;
    float real;
    char *string;
}


%token FUNCION
%token ENTRADA
%token SISTEMA
%token AND
%token OR
%token NOT
%token ASIGNACION
%token SUMA
%token RESTA
%token MULTI
%token DIV
%token MODULO
%token MAYOR
%token MENOR
%token IGUALMAY
%token IGUALMEN
%token IGUAL
%token NEW
%token CASE
%token BREAK
%token DEFAULT
%token CONDICIONAL
%token ELSE
%token ELIF
%token BUCLE
%token ITERATIVO
%token MULTIPLE
%token CHARACTER
%token FLOAT
%token ENTERO
%token BOLEANO
%token STRING
%token CONSTANTE
%token TUPLA
%token ARRAY
%token <string> STR
%token <simbolo> CHAR
%token <real> DECIMAL
%token T
%token F
%token PRINT
%token PRINTLN
%token <string> ID
%token <simbolo> NUMERO
%token SLNIEA
%token RPAREN
%token LPAREN
%token RBRACKET
%token LBRACKET
%token RCORCHETE
%token LCORCHETE
%token COMA
%token PUNTOCOMA
%token PUNTO
%token DOSPUNTOS
%token RETORNO
%token EJECUTAR

/* Gramática */

%%

PROGRAMA : DECL_VARS MAIN DECL_FUNCIONES;


MAIN : EJECUTAR LCORCHETE SENTS RCORCHETE ;
 
 
DECL_VARS : DECL_VAR DECL_VARS 
         |
		 ;

DECL_VAR : TIPO ID DECL_ESP ASIGN  PUNTOCOMA ; 

DECL_ESP : DECL_ARRAY
         | DECL_TUPLA;

DECL_ARRAY : DECL_ARRAY LCORCHETE RCORCHETE
          |
		  ;

DECL_TUPLA : LPAREN TIPO COMA TIPO RPAREN;

ASIGN : ASIGNACION T_ASIGN;

T_ASIGN : ASIGN_NORMAL
       | ASIGN_ARRAY
       | ASIGN_TUPLA;

ASIGN_NORMAL : EXPRESION;

ASIGN_ARRAY: NEW TIPO DIM_ARRAY;

DIM_ARRAY: DIM_ARRAY LCORCHETE EXPRESION RCORCHETE 
         | 
		 ;

ASIGN_TUPLA: NEW TUPLA DIM_TUPLA;

DIM_TUPLA: LPAREN EXPRESION COMA EXPRESION RPAREN;

REAL_ASIGN: ID ASIGNACION EXPRESION PUNTOCOMA;

TIPO: ENTERO
    | CHARACTER
    | FLOAT
    | BOLEANO
    | TUPLA
    | STRING
	;

/*******         las declaraciones iría antes que las instrucciones ----v----         *******/
FUNC: FUNCION TIPO DECL_ARRAY ID LPAREN PARAMETROS RPAREN LCORCHETE DECL_VARS SENTS RCORCHETE;

/* ** QUITAR ESTAS PRODUCCIONES
FUNC: FUNCION TIPO TIPO_ARR ID LPAREN PARAMETROS RPAREN LCORCHETE SENTS RCORCHETE;

TIPO_ARR: TIPO_ARR LCORCHETE RCORCHETE
        | 
		;
 ** */

DECL_FUNCIONES: DECL_FUNCIONES FUNC
              | 
			  ;
			  

SENTS: SENTS SENT 
     | 
	 ;

SENT: OTRAS_SENTS
 /* | DECL_VARS       <--  esto lía*/
    | REAL_ASIGN;


OTRAS_SENTS: CONDICIONAL LPAREN PARAMETROS RPAREN LCORCHETE SENTS RCORCHETE
           | BUCLE LPAREN PARAMETROS RPAREN LCORCHETE SENTS RCORCHETE
           | ITERATIVO LPAREN ID PUNTOCOMA EXPRESION PUNTOCOMA OP_RAPIDOS RPAREN LCORCHETE SENTS RCORCHETE
           | MULTIPLE LPAREN ID RPAREN LCORCHETE CASOS RCORCHETE;


CASOS: CASOS CASE INIT_CASES DOSPUNTOS SENTS BREAK PUNTOCOMA
     | CASO_DEFAULT
     | 
	 ;

CASO_DEFAULT: DEFAULT DOSPUNTOS SENTS BREAK PUNTOCOMA;

INIT_CASES: NUMERO
          | STR
          | CHAR
		  ;

/* ******** *
   esto es correcto, está comentado para que no dé errores el haber comentado a su vez EXPRESION_ARIT y EXPRESION_LOG
OP_LOG: AND
      | OR
      | MAYOR
      | MENOR
      | IGUALMAY
      | IGUALMEN
      | IGUAL
	  ;

OP_ARIT: SUMA
       | RESTA
       | MULTI
       | DIV
       | MODULO
	   ;
    hasta aquí
* ******* */

OP_RAPIDOS: OP_RAPIDO_S
          | OP_RAPIDO_R;

OP_RAPIDO_S: ID SUMA SUMA;

OP_RAPIDO_R: ID RESTA RESTA;


EXPRESION: LPAREN EXPRESION RPAREN
         | NOT EXPRESION
/* ******* *
   estas dos producciones deben arreglarse porque así como está no funcionará
         | EXPRESION_ARIT
         | EXPRESION_LOG 
 * ******* */
         | ID
         | NUMERO
         | DECIMAL
         | CHAR
         | STR
         | LLAMADA_FUNC
		 ;

/****
EXPRESION_ARIT: EXPRESION OP_ARIT EXPRESION;

EXPRESION_LOG: EXPRESION OP_LOG EXPRESION;
****/

LLAMADA_FUNC: ID LPAREN PARAMETROS RPAREN PUNTOCOMA;

PARAMETROS: PARAM_SIMPLE
          | PARAM_COMPUESTO
		  ;


PARAM_SIMPLE: EXPRESION;

PARAM_COMPUESTO: PARAMETROS COMA EXPRESION;
%%



int main(){
    yyparse();
    return 0;

}

yyerror(char *error){
    prinft("ERROR: %s\n", error);
    return 0;
}
