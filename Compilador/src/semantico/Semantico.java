package semantico;

import sintactico.arbol.*;
import sintactico.Parser; 
import tsimbolos.*;
import tsimbolos.descripciones.DConst;
import tsimbolos.descripciones.DTipus;
import tsimbolos.descripciones.Descripcion;
import tsimbolos.descripciones.DTipus.TSB;


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

    public void runProgram(){
        NodoMain main = arbol.getNodoMain();
        if (main != null) {
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

        }
    }

    private void inicializarTablaSimbolos() {

        //Inicializamos el bool
        DTipus d = new DTipus(TSB.tsb_bool,-1,0);
        ts.poner("bool", d);

        //Inicializamos el valor true
        DConst dC = new DConst("true", -1);
        ts.poner("true", dC);
        
        //Inicializamos el valor false
        dC = new DConst("false", 0);
        ts.poner("false", dC);
        
        //Inicializamos el char
        d = new DTipus(TSB.tsb_char,0,255);
        ts.poner("char", d);

        //Inicializamos el int
        d = new DTipus(TSB.tsb_int,Integer.MIN_VALUE,Integer.MAX_VALUE);
        ts.poner("int", d);

        /* Para que las palabras reservadas queden en el ámbito 1 de forma exclusiva */
        ts.entrarBloque();

    }



    public void ctrlDeclListVariables(NodoDeclVars varList) {
    }

    public void ctrlDeclListFunciones(NodoDeclFunc funcList) {
    }


    //metodo que controla la declaración de las tablas(arrays)
    public void ctrlDeclArray(NodoDeclArray declArray, Descripcion.TDesc modifier){
       
    }
}