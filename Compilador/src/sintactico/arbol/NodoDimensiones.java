package sintactico.arbol;

public class NodoDimensiones extends Nodo{
    
    NodoDimArray nodoDimArray; 
    
    public NodoDimensiones(NodoDimArray nDimArr,int l, int c) {
        super("DIMENSIONES", false, l, c);
        nodoDimArray = nDimArr;
    }

    public NodoDimensiones(){
        super("DIMENSIONES",true);
    }

    public NodoDimArray getNodoDimArray() {
        return nodoDimArray;
    }
}
