package semantico;



public class Operador3Direcciones{

    private String etiqueta= null;
    private TipoI tipo;
    private Object valor;
    private int referencia = -2;
    private TipoCambio cambio;
    int valConst = -2;
    private String operador = null;
    private String literal;

    public static enum TipoCambio{
        INT, CHAR, BOOL, STRING;
    }

    public enum TipoI{
        LITERAL, REFERENCIA;
    }

    public TipoI getTipo(){
        return tipo;
    }

    public TipoCambio getTipoCambio(){
        return cambio;
    }

    public Operador3Direcciones(String etiqueta){
        this.etiqueta = etiqueta;
    }

    public Operador3Direcciones(String literal,boolean redundant){
        this.literal = literal;
    }

    public Operador3Direcciones(Object valor, TipoCambio cambio){
        this.valor = valor;
        this.cambio = cambio;
        this.tipo = TipoI.LITERAL;
    }

    public Operador3Direcciones(String operador,int redundant){
        this.operador = operador;
    }

    public Operador3Direcciones(int referencia, String var){
        this.tipo= TipoI.REFERENCIA;
        this.referencia = referencia;
    }

    public Operador3Direcciones(int constante){
        this.tipo= TipoI.REFERENCIA;
        this.valConst = constante;
    } 

    public String getEtiqueta(){
        return etiqueta;
    }

    public Object getValor(){
        return valor;
    }

    public int getReferencia(){
        return referencia;
    }

    @Override
    public String toString() {
        if(etiqueta != null){
            return "e"+etiqueta;
        }else if(valConst != -2){
            return valConst+"";
        }else if(referencia != -2){
            return "t"+referencia;
        }else if(operador != null){
            return operador;
        }else if(literal != null){
            return literal;
        }
        return "";
    }
}