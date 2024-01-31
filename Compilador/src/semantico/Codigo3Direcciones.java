package semantico;

import java.util.ArrayList;
import java.util.Stack;
import herramientas.Tipo;
import semantico.Variable.TipoVar;
import sintactico.arbol.TipoArit;
import sintactico.arbol.TipoLog;


public class Codigo3Direcciones {
    
    private final TablaVariables tablaVariables;
    private final TablaProcedimientos tablaProcedimientos;
    private final Stack<String> pilaProcedimientos;
    private final ArrayList<Intruccion3Direcciones> intrucciones;

    public static final String etiqueta= "Etiqueta";
    public static final String variable= "Variable";
    private int contadorEtiquetas = 0;

    public Codigo3Direcciones(TablaVariables tablaVariables, TablaProcedimientos tablaProcedimientos) {
        this.tablaVariables = tablaVariables;
        this.tablaProcedimientos = tablaProcedimientos;
        this.pilaProcedimientos = new Stack<>();
        this.intrucciones = new ArrayList<>();
    }

    public String nuevaEtiqueta(){
        return etiqueta + contadorEtiquetas++;
    }

    public int nuevaVariable(TipoVar tipoVar,Tipo tipo,boolean esArray){
        tablaVariables.put(new Variable((variable + tablaVariables.getNV()),tipoVar,getFuncionActual(),tipo,esArray,tipo.getBytes()));
        return tablaVariables.getNV()-1;
    }

    public String getFuncionActual(){
        if(pilaProcedimientos.isEmpty()){
            return null;
        }
        return pilaProcedimientos.peek();
    }

    public void pushFuncionActual(String idProcedimiento){
        pilaProcedimientos.push(idProcedimiento);
    }

    public int nuevoProcedimiento(String idProcedimiento, int ambito, String etiquetaInicial,int parametros){

        tablaProcedimientos.put(idProcedimiento, new Procedimiento(ambito,etiquetaInicial,parametros,idProcedimiento));
        return tablaProcedimientos.getNP()-1;

    }

    public Procedimiento getProcedimiento(String idProcedimiento){
        return tablaProcedimientos.get(idProcedimiento);
    }

    public void genIntruccion(TipoInstruccion tipo, Operador3Direcciones op1, Operador3Direcciones op2, Operador3Direcciones res){
        Intruccion3Direcciones intruccion = new Intruccion3Direcciones(tipo,op1,op2,res);
        intrucciones.add(intruccion);
    }

    public ArrayList<Intruccion3Direcciones> getIntrucciones() {
        return intrucciones;
    }

    public void generaInstruccionAritmetica(TipoArit tipo, Operador3Direcciones uno, Operador3Direcciones dos, Operador3Direcciones res){

        switch(tipo){
            case SUMA:
                genIntruccion(TipoInstruccion.SUMA, uno, dos, res);
            break;
            case RESTA:
                genIntruccion(TipoInstruccion.RESTA, uno, dos, res);
            break;
            case MULT:
                genIntruccion(TipoInstruccion.MULTIPLICACION, uno, dos, res);
            break;
            case DIV:
                genIntruccion(TipoInstruccion.DIVISION, uno, dos, res);
            break;
            case MOD:
                System.out.println("HOLA SOY UN MODULO");
                genIntruccion(TipoInstruccion.MODULO, uno, dos, res);
            break;
        }
    }

    public void generaInstruccionLogica(TipoLog tipo, Operador3Direcciones uno, Operador3Direcciones dos, Operador3Direcciones res){
        //TipoLog.
        switch(tipo){
            case AND:
                genIntruccion(TipoInstruccion.AND, uno, dos, res);
            break;
            case OR:
                genIntruccion(TipoInstruccion.OR, uno, dos, res);
            break;
            case MAYOR:
                genIntruccion(TipoInstruccion.IFMAYOR, uno, dos, res);
            break;
            case MENOR:
                genIntruccion(TipoInstruccion.IFMENOR, uno, dos, res);
            break;
            case IGUALMAYOR:
                genIntruccion(TipoInstruccion.IFMAYORIGUAL, uno, dos, res);
            break;
            case IGUALMENOR:
                genIntruccion(TipoInstruccion.IFMENORIGUAL, uno, dos, res);
            break;
            case IGUALIGUAL:
                genIntruccion(TipoInstruccion.IFIGUAL , uno, dos, res);
            break;
            case DIFERENTE:
                genIntruccion(TipoInstruccion.IFDIFERENTE, uno, dos, res);
            break;
        }
    }
}