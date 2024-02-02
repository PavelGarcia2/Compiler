package semantico;



public class Operador3Direcciones{

    private String etiqueta= null;
    private TipoI tipo;
    private Object valor;
    private int referencia = -2;
    private TipoCambio cambio;
    Integer valConst = null;
    private String operador = null;
    private String literal;
    private float valFloat = -2;
    private String valString = null;
    private boolean proc;
    private String id;


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

    public Operador3Direcciones(String id,String etiqueta){
        this.etiqueta = etiqueta;
        this.id=id;
    }

    public Operador3Direcciones(String id,String literal,boolean redundant){
        this.literal = literal;
        this.id=id;
    }

    public Operador3Direcciones(String id,Object valor, TipoCambio cambio){
        this.valor = valor;
        this.cambio = cambio;
        this.tipo = TipoI.LITERAL;
        this.id=id;
    }

    public Operador3Direcciones(String id,String valString,float redundant){
        this.operador = operador;
        this.valString = valString;
        this.id=id;
    }

    public Operador3Direcciones(String id,String operador,int redundant){
        this.operador = operador;
        this.id=id;
    }

    public Operador3Direcciones(String id,int referencia, boolean proc){
        this.tipo= TipoI.REFERENCIA;
        this.referencia = referencia;
        this.proc = proc;
        this.id=id;
    }

    public Operador3Direcciones(String id,int constante){
        this.tipo= TipoI.REFERENCIA;
        this.valConst = constante;
        this.id=id;
    } 

    public Operador3Direcciones(String id,float valorFloat){
        this.tipo= TipoI.REFERENCIA;
        this.valFloat = valorFloat;
        this.id=id;
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

    public String getId(){
        return id;
    }

    public int getValConst(){
        return valConst;
    }

    
    public float getValFloat() {
        return valFloat;
    }

    public String getValString() {
        return valString;
    }

    @Override
    public String toString() {
        if(etiqueta != null){
            return "e"+etiqueta;
        }else if(valConst != null){
            return valConst+"";
        }else if(referencia != -2){
            if(!proc){
                return "t"+referencia;
            }else{
                return "p"+referencia;
            }
        }else if(operador != null){
            return operador;
        }else if(literal != null){
            return literal;
        } else if(valFloat != -2){
            return valFloat+"";
        } else if(valString != null){
            return valString;
        }
        return "";
    }
}