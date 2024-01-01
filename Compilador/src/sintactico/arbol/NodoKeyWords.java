package sintactico.arbol;

public class NodoKeyWords extends Nodo{

    String keyword;
    
    public NodoKeyWords(String kw, int l, int c) {
        super("KEYWORD", false, l, c);
        keyword = kw;
    }
    
}
