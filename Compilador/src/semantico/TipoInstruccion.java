package semantico;

public enum TipoInstruccion{

    ASIGNACION("asignacion"),
    COPIA("copia"),
    SUMA("suma"),
    SUMARAPIDA("sumaRapida"),
    RESTARAPIDA("restaRapida"),
    RESTA("resta"),
    MULTIPLICACION("multiplicacion"),
    DIVISION("division"),
    MODULO("modulo"),
    NEGACION("negacion"),
    AND("and"),
    OR("or"),
    NOT("not"),
    IFMAYOR("ifMayor"),
    IFMENOR("ifMenor"),
    IFMAYORIGUAL("ifMayorIgual"),
    IFMENORIGUAL("ifMenorIgual"),
    IFDIFERENTE("ifDiferente"),
    IFIGUALI("ifIgual"),
    IFTRUEGOTO("ifTrueGoTo"),
    PREAMBULO("pmb"),
    PRINT("print"),
    PRINTLN("println"),
    IN("in"),
    CALL("call"),
    RETURN("return"),
    GOTO("goto"),
    SKIP("skip"),
    PARAM_SIMPLE("param_s"),
    PARAM_COMPOST("param_c"),
    IND_VAL("ind_val"),
    IND_ASS("ind_ass")
    ;


    String tipo;

    TipoInstruccion(String tipo){
        this.tipo = tipo;
    }

    public String getTipoIntruccion(){
        return this.tipo;
    }
}