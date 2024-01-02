package sintactico.arbol;

public class NodoDeclArray extends Nodo{

    NodoDeclArray nodoDeclArray;
    NodoSimbolos lCorchete;
    NodoSimbolos rCorchete;

    public NodoDeclArray(NodoDeclArray nDeclArr,NodoSimbolos lC, NodoSimbolos rC,int l, int c) {
        super("DECLARRAY", false, l, c);
        nodoDeclArray = nDeclArr;
        lCorchete = lC;
        rCorchete = rC;
    }

    public NodoDeclArray(){
        super("DECLARRAY", true);
    }
}
