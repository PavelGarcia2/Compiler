package sintactico.arbol;

public class NodoContParam extends Nodo{

    NodoPar parametro;

    public NodoContParam(NodoPar parametro, int l,int c){
        super("ContParam",false,l,c);
        this.parametro = parametro;
    }

    public NodoContParam(){
        super("ContParam",true);
    }


    public NodoPar getParametro() {
        return parametro;
    }

 }