package sintactico.arbol;

public class NodoSigno extends Nodo{
    
    // 1 es mas
    // 0 es nada
    // -1 es menos

    private int signo;

    public NodoSigno(int signo , int l, int c){
        super("SIGNO",false,l,c);
        this.signo = signo;
    }

    public NodoSigno(int signo){
        super("SIGNO",true);
        this.signo = signo;
    }

    public int getSigno(){
        return signo;
    }
}
