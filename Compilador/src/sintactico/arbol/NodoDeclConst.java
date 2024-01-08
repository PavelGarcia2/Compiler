package sintactico.arbol;

public class NodoDeclConst extends Nodo{

    NodoDeclConst nodoDeclConst;
    NodoConst nodoConst;

    public NodoDeclConst(NodoConst nodoConst, NodoDeclConst nodoDeclConst, int l, int c){
        super("DECLCONST",false,l,c);
        this.nodoConst = nodoConst;
        this.nodoDeclConst = nodoDeclConst;
    }

    public NodoDeclConst(){
        super("DECLCONST",true);
        this.nodoConst = null;
        this.nodoDeclConst = null; 
    }

    public NodoConst getNodoConst() {
        return nodoConst;
    }

    public NodoDeclConst getNodoDeclConst() {
        return nodoDeclConst;
    }
}