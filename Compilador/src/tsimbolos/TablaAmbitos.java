package tsimbolos;

public class TablaAmbitos {

    private int[] ta;
    //Indica cual es la maxima capacida inicial de la tabla de ambitos
    //En caso de que se necesite mas espacio la tabla se amplia en INCREMENTO espacios
    private int maxCap; 
    // Indice que indica porque elemento del array vamos   
    int ind;
    //variable 
    final int INCREMENTO=30;                    

    public TablaAmbitos() {
        maxCap = 50;
        ind = 0;
        this.ta = new int[maxCap];
    }

    public void nuevoAmbito(int apuntadorTE) {
        if(ind==maxCap){
            aumentarArray();
        }
        ta[ind] = apuntadorTE;
        ind++;
    }

    public void aumentarArray(){
        int maxCapAnt = maxCap;
        maxCap += INCREMENTO;
        int newArray[] = new int[maxCap];
        for(int i=0;i<maxCapAnt;i++){
            newArray[i] = ta[i];
        }
        ta = newArray;
    }

    public void setAmbito(int n, int apuntadorTE) {
        ta[n] = apuntadorTE;
    }

    public int getAmbito(int n) {
        int ambito = -1;
        if(n<=ind){
            ambito = ta[n];
        }
        if(ambito==-1){
            System.out.println("TablaAmbitos.java : Ambito no disponible");
        }
        return ambito;
    }


    @Override
    public String toString() {
        String data = "\n";
        for(int i=0;i<ind;i++){
            data+="["+i+"]->"+ta[i]+"\n";

        }
        return "{"+ data +"}";
    }



}
