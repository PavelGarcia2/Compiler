package lexico;

public class Literal {
    final private Object literal;
    final int linea;
    final int columna;
    
    public Literal(Object literal, int linea, int columna) {
        this.literal = literal;
        this.linea = linea;
        this.columna = columna;
    }
    
    public Object getLiteral() {
        return literal;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
}