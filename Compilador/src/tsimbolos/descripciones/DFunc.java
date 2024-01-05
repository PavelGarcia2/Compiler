package tsimbolos.descripciones;
import herramientas.Tipo;

public class DFunc extends Descripcion {

    int valor;
    Tipo tipo;

    public DFunc(int valor, Tipo tipo) {
        super(TDesc.dfunc);
        this.valor = valor;
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public Tipo getTipo() {
        return tipo;
    }
    

}