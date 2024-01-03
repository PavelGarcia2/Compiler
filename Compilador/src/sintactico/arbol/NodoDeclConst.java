package sintactico.arbol;

public class NodoDeclConst extends Nodo{

    NodoConst nodoConst;
    NodoDeclConst nodoDeclConst;

    public NodoDeclConst(NodoConst nodoConst, NodoDeclConst nodoDeclConst, int l, int c){
        super("DECLCONST",false,l,c);
        this.nodoConst = nodoConst;
        this.nodoDeclConst = nodoDeclConst;
    }

    public NodoDeclConst(){
        super("DECLCONST",true); 
    }
}