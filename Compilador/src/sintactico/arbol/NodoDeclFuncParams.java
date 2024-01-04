package sintactico.arbol;

public class NodoDeclFuncParams extends Nodo {

    NodoDeclFuncParam nodoDeclFuncParam;
    NodoDeclFuncParams nodoDeclFuncParams;


    public NodoDeclFuncParams(NodoDeclFuncParam NodoDeclFuncParam, int l, int c) {
        super("DECLFUNCSPARAMS", false, l, c);
        this.nodoDeclFuncParam = NodoDeclFuncParam;
    }

    public NodoDeclFuncParams(NodoDeclFuncParam nodoDeclFuncParam, NodoDeclFuncParams nodoDeclFuncParams, int l, int c) {
        super("DECLFUNCPARAMS", false, l, c);
        this.nodoDeclFuncParam = nodoDeclFuncParam;
        this.nodoDeclFuncParams = nodoDeclFuncParams;
    }

    public NodoDeclFuncParams getNodoDeclFuncParams(){
        return nodoDeclFuncParams;
    }

    public NodoDeclFuncParam getNodoDeclFuncParam() {
        return nodoDeclFuncParam;
    }

}
