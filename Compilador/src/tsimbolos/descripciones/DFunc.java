package tsimbolos.descripciones;
import herramientas.Tipo;
import sintactico.arbol.NodoFunc;

public class DFunc extends Descripcion {

    int valor;
    Tipo tipo;
    NodoFunc nodoFunc;

    public DFunc(int valor, Tipo tipo, NodoFunc nodoFunc) {
        super(TDesc.dfunc);
        this.valor = valor;
        this.tipo = tipo;
        this.nodoFunc = nodoFunc;
    }

    public NodoFunc getNodoFunc() {
        return nodoFunc;
    }

    public int getValor() {
        return valor;
    }

    public Tipo getTipo() {
        return tipo;
    }
    

}