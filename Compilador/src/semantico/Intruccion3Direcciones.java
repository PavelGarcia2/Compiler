package semantico;

public class Intruccion3Direcciones{

    private TipoInstruccion tipoIntruccion;
    private final Operador3Direcciones[] operadores;

    public Intruccion3Direcciones(TipoInstruccion tipoIntruccion, Operador3Direcciones operador1, Operador3Direcciones operador2, Operador3Direcciones operador3){
        this.operadores = new Operador3Direcciones[3];
        this.tipoIntruccion = tipoIntruccion;
        this.operadores[0] = operador1;
        this.operadores[1] = operador2;
        this.operadores[2] = operador3;
    }

    public Operador3Direcciones[] getOperadores(){
        return this.operadores;
    }

    public TipoInstruccion getTipoIntruccion(){
        return this.tipoIntruccion;
    }

    public void setTipoIntruccion(TipoInstruccion tipoIntruccion){
        this.tipoIntruccion = tipoIntruccion;
    }

    public void setOperador(Operador3Direcciones operador, int posicion){
        this.operadores[posicion] = operador;
    }

    @Override
    public String toString(){
        if(tipoIntruccion.getTipoIntruccion().length() == 4){
            
        }

        switch(tipoIntruccion.getTipoIntruccion().length()){
            case 4:
            return tipoIntruccion.getTipoIntruccion() + "   | "
                + ((operadores[0] != null) ? operadores[0].toString() : "null") + " | "
                + ((operadores[1] != null) ? operadores[1].toString() : "null") + " | "
                + ((operadores[2] != null) ? operadores[2].toString() : "null");
            case 5:
            return tipoIntruccion.getTipoIntruccion() + "  | "
                + ((operadores[0] != null) ? operadores[0].toString() : "null") + " | "
                + ((operadores[1] != null) ? operadores[1].toString() : "null") + " | "
                + ((operadores[2] != null) ? operadores[2].toString() : "null");
            default:
            return tipoIntruccion.getTipoIntruccion() + "  | "
                + ((operadores[0] != null) ? operadores[0].toString() : "null") + " | "
                + ((operadores[1] != null) ? operadores[1].toString() : "null") + " | "
                + ((operadores[2] != null) ? operadores[2].toString() : "null");
        }
    }
}