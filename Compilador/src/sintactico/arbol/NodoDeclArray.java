package sintactico.arbol;

public class NodoDeclArray extends Nodo{

    NodoDeclArray nodoDeclArray;
    NodoSimbolos lCorchete;
    NodoSimbolos rCorchete;

    public NodoDeclArray(NodoDeclArray nDeclArr,int l, int c) {
        super("DECLARRAY", false, l, c);
        nodoDeclArray = nDeclArr;
    }

    public NodoDeclArray(){
        super("DECLARRAY", true);
    }
}
