package sintactico.arbol;

public class NodoDeclFuncP extends Nodo {

    NodoDeclFuncParams nodoDeclFuncParams;


    public NodoDeclFuncP(NodoDeclFuncParams nodoDeclFuncParams,int l, int c) {
        super("DECLFUNCP", false, l, c);
        this.nodoDeclFuncParams = nodoDeclFuncParams;
    }

    public NodoDeclFuncP() {
        super("DECLFUNCP", true);
    }

    public NodoDeclFuncParams getNodoDeclFuncParams() {
        return nodoDeclFuncParams;
    }

}
