package tsimbolos.descripciones;
import herramientas.Tipo;
import sintactico.arbol.NodoFunc;

public class DFunc extends Descripcion {

    int np;
    Tipo tipo;
    NodoFunc nodoFunc;
    int retVal;

    public DFunc(int np, Tipo tipo, NodoFunc nodoFunc,int retVal) {
        super(TDesc.dfunc);
        this.np = np;
        this.tipo = tipo;
        this.nodoFunc = nodoFunc;
        this.retVal = retVal;
    }

    public int getRetVal() {
        return retVal;
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

}