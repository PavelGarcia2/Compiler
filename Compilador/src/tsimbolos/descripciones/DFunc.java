package tsimbolos.descripciones;
import java.util.ArrayList;

import herramientas.Tipo;
import sintactico.arbol.NodoFunc;

public class DFunc extends Descripcion {

    int np;
    Tipo tipo;
    NodoFunc nodoFunc;
    int retVal;
    private ArrayList<Dargin> argumentos;

    public DFunc(int np, Tipo tipo, NodoFunc nodoFunc) {
        super(TDesc.dfunc);
        this.np = np;
        this.tipo = tipo;
        this.nodoFunc = nodoFunc;
        argumentos = new ArrayList<Dargin>();

    }

    public int getRetVal() {
        return retVal;
    }

    public ArrayList<Dargin> getArgumentos(){
        return argumentos;
    }

    public void setRetVal(int r){
        retVal = r;
    }

    public NodoFunc getNodoFunc() {
        return nodoFunc;
    }

    public int getnp() {
        return np;
    }

    public Tipo getTipo() {
        return tipo;
    }
    
    public void setNp(int np) {
        this.np = np;
    }
    
     public void addArg(Dargin dargin){
        argumentos.add(dargin);
    }

}