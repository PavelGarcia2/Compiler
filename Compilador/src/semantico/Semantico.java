package semantico;

import sintactico.arbol.*;
import sintactico.Parser; 
import tsimbolos.*;
import tsimbolos.descripciones.*;
import tsimbolos.descripciones.DTipus.TSB;
import sintactico.arbol.Tipo;

/**
 * Semantico
 */
public class Semantico {
    private final Parser parser;
    private final NodoPrograma arbol;
    private TablaSimbolos ts ;

    public Semantico(NodoPrograma arbol, Parser parser){
        this.arbol=arbol;
        //creamos la tabla de simbolos y la inicializamos
        this.parser = parser;
        this.ts = new TablaSimbolos(parser);
        inicializarTablaSimbolos();

    }

    private void inicializarTablaSimbolos() {

        //Inicializamos el bool
        DTipus d = new DTipus(TSB.tsb_bool,-1,0);
        ts.poner("tsb_bool", d);

        //Inicializamos el valor true
        DConst dC = new DConst("true", -1);
        ts.poner("true", dC);
        
        //Inicializamos el valor false
        dC = new DConst("false", 0);
        ts.poner("false", dC);
        
        //Inicializamos el char
        d = new DTipus(TSB.tsb_char,0,255);
        ts.poner("tsb_char", d);

        //Inicializamos el int
        d = new DTipus(TSB.tsb_int,Integer.MIN_VALUE,Integer.MAX_VALUE);
        ts.poner("tsb_int", d);

        /* Para que las palabras reservadas queden en el ámbito 1 de forma exclusiva */
        ts.entrarBloque();

    }

    public void runProgram(){
        NodoMain main = arbol.getNodoMain();
        if (main != null) {

            //comprobar las constantes
            NodoDeclConst constList = arbol.getNodoDeclaracionConstantes();
            if(constList != null && !constList.isEmpty()){
                ctrlDeclConstantes(constList);
            }

            //obtenemos todas las declaracioes de variables
            NodoDeclVars varList = arbol.getNodoDeclaracionVariables();
            //si la lista no esta vacia o es nula
            if(varList !=null && !varList.isEmpty()){
                //controlamos todas las declaraciones de variables
                ctrlDeclListVariables(varList);
            }

            //obtenemos todas las declaraciones de procedures
            NodoDeclFunc funcList = arbol.getNodoDeclaracionFunciones();
            if (funcList != null && !funcList.isEmpty()) {
                ctrlDeclListFunciones(funcList);
            }

        } else {
            parser.report_error("ERROR: No hemos encontrado el main",main);
        }
    }

    public void ctrlDeclConstantes(NodoDeclConst constList) {

        NodoDeclConst hijo = constList.getHijo();
        if(hijo!=null && !hijo.isEmpty()){
            ctrlDeclConstantes(hijo);
        } else if(hijo.isEmpty()){      //Hemos llegado a la producción con lambda
            // do nothing
        } else {
            ctrlConst(hijo.getNodoConst());
        }
    }

    public void ctrlConst(NodoConst constante){

        NodoTipo tipo = constante.getNodoTipo();
        NodoId id = constante.getNodoId();
        NodoAsignacion asignacion = constante.getNodoAsignacion();

        //Comprovar que tipo es un tipo
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString());
        if(dt ==null){        // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("ERROR: No existe el tipo",constante);
        }

        //Comprovar que el tipo es adecuado
        if(dt.getTsb() != DTipus.TSB.tsb_int && dt.getTsb() != DTipus.TSB.tsb_char && dt.getTsb() != DTipus.TSB.tsb_bool){
            parser.report_error("ERROR: No se pueden crear constantes de este tipo",constante);
        }

        //Comprovar que el tipo y el valor son compatibles

        // Si es un literal (char, int)
        if(asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral() != null){
            Tipo tipoValor = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral().getTipo();
            if(tipoValor)
            
        // Si es un bool
        } else if(asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getBool() != -1){

        }

        
        
    }

    public void ctrlDeclListVariables(NodoDeclVars varList) {

        NodoDeclVars hijo = varList.getNodoDeclaracionVariables();
        
        if(hijo!=null && !hijo.isEmpty()){
            ctrlDeclListVariables(hijo);
        }else if(hijo.isEmpty()){} //Hemos llegado a la producción con lambda
        else{    
            ctrlVar(varList.getNodoDeclaracionVariable());
        }
    }

    public void ctrlVar(NodoVar var){

        Descripcion d = ts.consultarTD(var.getNodoTipo().getTipo().toString());

        if(d==null){ //NO ES UN TIPO
            parser.report_error("ERROR: No existe el tipo",var);
        }   
        

    }

    public void ctrlDeclListFunciones(NodoDeclFunc funcList) {

    }



    //metodo que controla la declaración de las tablas(arrays)
    public void ctrlDeclArray(NodoDeclArray declArray, Descripcion.TDesc modifier){
       
    }
}