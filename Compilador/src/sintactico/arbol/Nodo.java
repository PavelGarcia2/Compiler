package sintactico.arbol;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

public class Nodo extends ComplexSymbol {

    int linea;
    int columna;
    static int nNodo = 1;           // Identificador de Nodo
    boolean vacio;
    
    public Nodo(String name, Boolean v){
        super(name, nNodo++);
        vacio = v;
    }

    public Nodo(String name, Boolean v, int l, int c){
        super(name, nNodo++);
        linea = l;
        columna = c;
        vacio = v;
    }

}
