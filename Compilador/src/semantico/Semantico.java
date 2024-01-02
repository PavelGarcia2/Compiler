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
    private final NodoPrograma nodoPrograma;
    private TablaSimbolos ts ;

    public Semantico(NodoPrograma nodoPrograma, Parser parser){
        this.nodoPrograma=nodoPrograma;
        //creamos la tabla de simbolos y la inicializamos
        this.parser = parser;
        this.ts = new TablaSimbolos(parser);
        inicializarTablaSimbolos();

    }

    public void runProgram(){
        NodoMain main = nodoPrograma.getNodoMain();
        if (main != null) {
            //obtenemos todas las declaracioes de variables
            NodoDeclVars varList = nodoPrograma.getNodoDeclaracionVariables();
            //si la lista no esta vacia o es nula
            if(varList !=null && !varList.isEmpty()){
                //controlamos todas las declaraciones de variables
                ctrlDeclListVariables(varList);
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



    public void ctrlDeclListVariables(NodoDeclVars declList) {
        /*
        NodoDeclVars listaDecls = declList.getDeclList();
        //Lista con declaraciones?
        if (listaDecls != null && !listaDecls.isEmpty()) {
            handleDeclList(listaDecls);
        }
        //Una sola declaración
        handleDecl(declList.getDecl());*/
    }


    //metodo que controla la declaración de las tablas(arrays)
    public void ctrlDeclArray(NodoDeclArray declArray, Descripcion.TDesc modifier){
       
    }
}