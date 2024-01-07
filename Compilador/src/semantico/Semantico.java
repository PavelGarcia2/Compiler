package semantico;

import sintactico.arbol.*;
import herramientas.Tipo;
import sintactico.Parser; 
import tsimbolos.*;
import tsimbolos.descripciones.*;

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
        DTipus d = new DTipus(Tipo.tsb_bool,-1,0);
        ts.poner("tsb_bool", d, null);

        //Inicializamos el valor true
        DConst dC = new DConst(Tipo.tsb_bool, -1,"true");
        ts.poner("true", dC, null);
        
        //Inicializamos el valor false
        dC = new DConst(Tipo.tsb_bool, 0, "false");
        ts.poner("false", dC, null);
        
        //Inicializamos el char
        d = new DTipus(Tipo.tsb_char,0,255);
        ts.poner("tsb_char", d, null);

        //Inicializamos el int
        d = new DTipus(Tipo.tsb_int,Integer.MIN_VALUE,Integer.MAX_VALUE);
        ts.poner("tsb_int", d, null);

        //Inicializamos el float
        d = new DTipus(Tipo.tsb_int, Float.MIN_VALUE,Float.MAX_VALUE);
        ts.poner("tsb_int", d, null);

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

            // //obtenemos todas las declaracioes de variables
            // NodoDeclVars varList = arbol.getNodoDeclaracionVariables();
            // //si la lista no esta vacia o es nula
            // if(varList !=null && !varList.isEmpty()){
            //     //controlamos todas las declaraciones de variables
            //     ctrlDeclListVariables(varList);
            // }

            // //obtenemos todas las declaraciones de procedures
            // NodoDeclFunc funcList = arbol.getNodoDeclaracionFunciones();
            // if (funcList != null && !funcList.isEmpty()) {
            //     ctrlDeclListFunciones(funcList);
            // }

        } else {
            parser.report_error("ERROR: No hemos encontrado el main",main);
        }
    }

    public void ctrlDeclConstantes(NodoDeclConst constList) {
        NodoDeclConst hijo = constList.getHijo();
        if(hijo!=null && !hijo.isEmpty()){
            System.out.println("entro Semantico.java");
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
        
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString());
        //Comprovar que tipo es un tipo
        if(dt == null){        // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("ERROR: No existe el tipo",constante);
        }

        //Comprovar que el tipo es adecuado
        if((dt.getTsb() != Tipo.tsb_int) && (dt.getTsb() != Tipo.tsb_char) && (dt.getTsb() != Tipo.tsb_bool)){
            parser.report_error("ERROR: No se pueden crear constantes de este tipo",constante);
        }

        //Primero miramos que nodo literal no sea null
        if(asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral()==null){
            parser.report_error("ERROR: Estas asignando un valor incorrecto",constante); 
        }

        //Cogemos el tipo de literal
        Tipo type = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral().getTipo();
        String valor = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral().getValor();
        
        //Mirar si el tipo de la izquierda y derecha son iguales
        if(dt.getTsb() != type){
            parser.report_error("ERROR: Estas asignando un valor de otro tipo",constante); 
        }

        //Mirar rango
        int rango = Integer.parseInt(valor);
        
        switch(type){
            case tsb_char:
                if(rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()){
                    parser.report_error("ERROR: Has excedido los limites",constante);
                }
                break;
            case tsb_bool:
                // Comppobar si es true o false
                if(rango != -1 || rango != 0){
                    parser.report_error("ERROR: Has excedido los limites",constante);
                }
                break;
            case tsb_int:
                //Mirar rango int
                if(rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()){
                    parser.report_error("ERROR: Has excedido los limites",constante);
                }
                break;
            default:
                parser.report_error("ERROR: No permitimos crear constantes de esta forma",constante);
                break;
        }

        //Creamos la nueva declaracion si todo ha ido bien
        DConst dc = new DConst(dt.getTsb(),rango,id.getNombre());
        ts.poner(id.getNombre(), dc, constante);
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
        NodoTipo tipo = var.getNodoTipo();
        NodoId id = var.getNodoId();
        NodoAsignacion asignacion = var.getNodoAsignacion();

        //Comprovar que tipo es un tipo existente
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString());
        if(dt == null){        // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("ERROR: No existe el tipo",var);
        }

        if(dt.getTsb() == Tipo.tsb_void){
            parser.report_error("ERROR: No se pueden crear variables void",var);
        }

        // Mirar si estamos declarando un array
        NodoDeclArray declaracionArray = var.getNodoDeclArray();
        if(declaracionArray != null && !declaracionArray.isEmpty()){
            
            //Miramos las dimensiones del array
            int dimArray = dimensionArr(declaracionArray);
            Darray da;
            //Mirar si estamos inicializando el array
            if(asignacion != null && !asignacion.isEmpty()){                
                
                // da = new Darray(0, dt.getTsb(),dimArray,false);
                // ts.poner(id.getName(), da, id);

                //Si estamos inicializando tenemos que comprobar que init y decl tengan la misma dim
                //NodoAsignacionArray nodoAsignacionArray = new
                
            } else {
                //Simplemente declaramos un array
                da = new Darray(0, dt.getTsb(),dimArray,false);
                ts.poner(id.getNombre(), da, id);
            }            
            
        }else{
            // Mirar si tenemos que hacer la asignacion
            if(asignacion != null && !asignacion.isEmpty()){
                ctrlAsignNormal(dt, asignacion,id);
            }else{
                Dvar d;
                d = new Dvar(0, dt.getTsb());
                ts.poner(id.getNombre(), d, id);           
            }
        }   
    }

    public int dimensionArr(NodoDeclArray declArray){
        //Contar dimensiones que tiene
        Nodo masDimensiones = declArray.getNodoDeclArray();
        int dim = 0;
        if(masDimensiones != null && !masDimensiones.isEmpty()){
            ctrlDeclArray(declArray);
        }
        dim++;
        return dim;
    }

    //metodo que controla la declaración de las tablas(arrays)
    public void ctrlDeclArray(NodoDeclArray declArray){        
       
    }   

    public void ctrlAsignNormal(DTipus dt, NodoAsignacion asign, NodoId id){
        NodoExpresion nodo = asign.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion();
        //Comprovar que el tipo de NodoTipo y el tipo de NodoASignacion es compatible
        //El tipo de NodoTipo lo tenemos en dt
        //El tipo de NodoAsignacion lo tenemos en expresion
        //Vamos a hacer el control segun lo que estemos declarando   
        
        int valor;
        Dvar d;

        switch(dt.getTsb()){

            case Tipo.tsb_int:
            
                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_int){
                    parser.report_error("ERROR: Estas asignando un valor incorrecto, no es int",nodo); 
                }

                valor = Integer.parseInt(nodo.getNodoLiteral().getValor());
                if(valor < dt.getLimiteInferior() && valor > dt.getLimiteSuperior()){
                    parser.report_error("ERROR: Has excedido los limites",nodo);
                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_int);
                ts.poner(id.getNombre(), d, nodo);
                
            break;

            case Tipo.tsb_bool:
                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_true || nodo.getNodoLiteral().getTipo() != Tipo.tsb_false){
                    parser.report_error("ERROR: Estas asignando un valor incorrecto, no es bool",nodo);
                } 
                /*
                else if(nodo.getBool()!= 0 && nodo.getBool()!=1){
                    parser.report_error("ERROR: Has excedido los limites",nodo);
                }
                */
                
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_bool);
                ts.poner(id.getNombre(), d, nodo);  
                              
            break;

            case Tipo.tsb_char:

                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_char){
                    parser.report_error("ERROR: Estas asignando un valor incorrecto, no es char",nodo); 
                }

                valor = Integer.parseInt(nodo.getNodoLiteral().getValor());
                if(valor < dt.getLimiteInferior() && valor > dt.getLimiteSuperior()){
                    parser.report_error("ERROR: Has excedido los limites",nodo);
                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_char);
                ts.poner(id.getNombre(), d, nodo);

            break;
 
            case Tipo.tsb_float:

                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_float){
                    parser.report_error("ERROR: Estas asignando un valor incorrecto, no es float",nodo); 
                }

                float val = Float.parseFloat(nodo.getNodoLiteral().getValor());
                if(val < dt.getLimiteInferior() && val > dt.getLimiteSuperior()){
                    parser.report_error("ERROR: Has excedido los limites",nodo);
                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_float);
                ts.poner(id.getNombre(), d, nodo);
                
            break;

            case Tipo.tsb_str:

                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_str){
                    parser.report_error("ERROR: Estas asignando un valor incorrecto, no es str",nodo); 
                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_str);
                ts.poner(id.getNombre(), d, nodo);

            break; 
        }
    } 

    public void ctrlDeclListFunciones(NodoDeclFunc funcList) {

        NodoDeclFunc hijo = funcList.getNodoDeclFunc();

        if(hijo !=null && !hijo.isEmpty()){
            ctrlDeclListFunciones(hijo);
        }else if(hijo.isEmpty()){
            //se ha derivado en landa
        }else{
            ctrlFunc(funcList.getNodoFunc());
        }
    }

    public void ctrlFunc(NodoFunc func){
        
        // comprobar el tipo
        NodoTipo tipo = func.getNodoTipo(); //cojo el tipo del nodo que me llega
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString()); //consulto dicho tipo en la tabla de simbolos

        //si el tipo no es null, es que es un tipo de los que tengo en la tabla de simbolos, si no error.
        if(dt == null){        // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("ERROR: No existe el tipo",func);
        }

        //comrpobar las delaraciones del array 
        NodoDeclArray declArray = func.getNodoDeclArray();
        if(declArray != null && !declArray.isEmpty()){ //signifca que tenemos una declaracion de array
            ctrlDeclArray(declArray);
        }

        //compruebo el id
        NodoId id = func.getNodoId();

        //comprobar la declaracion de los parametros de las funciones
        NodoDeclFuncP funcP = func.getNodoDeclFuncP();
        if(funcP != null && !funcP.isEmpty()){
            ctrlDeclFuncP(funcP);
        }

        ts.entrarBloque();

        //comprobar las declaraciones de las variables
        NodoDeclVars declVars = func.getNodoDeclVars();
        if(declVars != null && !declVars.isEmpty()){
            ctrlDeclListVariables(declVars);
        }

        //comprobar las sentencias
        if(func.getNodoSents() != null){
            ctrlSents(func.getNodoSents());
        }
        
        //comprobar el return
        NodoReturn ret = func.getNodoReturn();
        if(ret != null && !ret.isEmpty()){
            ctrlReturn(ret, func.getNodoTipo());
        }

        ts.salirBloque();
        
        //añadir la funcion a la tabla de simbolos
        ts.poner(id.getNombre(), new DFunc(2,func.getNodoTipo().getTipo()), func);

    }

    public void ctrlReturn(NodoReturn ret, NodoTipo tipo){
        
        NodoReturnParam paramReturn = ret.getNodoReturnParam();
        NodoId id = paramReturn.getNodoId();
        
        if(ret.getNodoReturnParam() != null){            
            if(paramReturn.getNodoLiteral()!=null){//caso de literal 
                consultarValidez(tipo,paramReturn.getNodoLiteral().getTipo(),ret);
            }else{//caso de de id
               DTipus dt = (DTipus) ts.consultarTD(id.getNombre().toString());            
                consultarValidez(tipo,dt.getTsb(),ret);
            }
        }else if(ret.isEmpty()){
            //se ha derivado en landa
        }        
    }

    private void consultarValidez(NodoTipo tipo, Tipo t, NodoReturn ret){
        switch(tipo.getTipo()){
            //si la funcion tiene un tipo booleano el rreturn tambien debe ser booleano
            case tsb_bool:
                //compruebo las posibles partes boleanas, si no es true o false error
                if (t !=  Tipo.tsb_true || t !=  Tipo.tsb_false ) {
                    //No es un bool error
                    parser.report_error("ERROR: La funcion es booleana y el return no es booleano",ret);
                }
                
                break;

            case tsb_char:
                if(t != Tipo.tsb_char){
                    //No es un char error
                    parser.report_error("ERROR: La funcion es de tipo character y el return no es character",ret);
                }
                
                break;

            case tsb_float:
                
                if(t != Tipo.tsb_float){
                    //No es un float error
                    parser.report_error("ERROR: La funcion es de tipo float y el return no es float",ret);
                }
                break;

            case tsb_int:

                if(t != Tipo.tsb_int){
                    //No es un int error
                    parser.report_error("ERROR: La funcion es de tipo int y el return no es int",ret);
                }
                break;

            case tsb_str:
                
                if(t != Tipo.tsb_str){
                    //No es un string error
                    parser.report_error("ERROR: La funcion es de tipo string y el return no es string",ret);
                }
                break;

            case tsb_void: 

                    if(!ret.isEmpty()){
                        parser.report_error("ERROR: La funcion es de tipo void y el return no es void",ret);
                    } 
                                
                break;

            default:
                break; 
        } 
    }


    public void ctrlSents(NodoSents sents){
        NodoSent sent = sents.getNodoSent();
        if(sent != null && !sent.isEmpty()){
            ctrlSent(sent);
        }else if(sent.isEmpty()){
            //se ha derivado en landa
        }else{
            ctrlSents(sents.getNodoSents());
        }
    }

    public void ctrlSent(NodoSent sent){

        if(sent.getNodoOtrasSent() != null){
            ctrlOtrasSent(sent.getNodoOtrasSent());
        }

        if(sent.getNodoRealAsign() !=null){
            ctrlRealAsign(sent.getNodoRealAsign());
        }
    }

    public void ctrlOtrasSent(NodoOtrasSent otras){
        
    }

    public void ctrlRealAsign(NodoRealAsign realAsign){
        // id = expresion ;
        NodoId id = realAsign.getNodoId();
        NodoExpresion expresion = realAsign.getNodoExpresion();
        int valor;

        //comprobar que el id existe en la tabla de simbolos
        if(ts.consultarTD(id.getNombre()) == null){
            parser.report_error("ERROR: No existe el id",realAsign);
        }

        //comprobar que el tipo de la expresion es compatible con el tipo del id y ver si es una constante o una variable

        DTipus dt = (DTipus) ts.consultarTD(id.getNombre().toString()); //tipo del id

        switch(dt.getTsb()){//dependiendo del tipo del id comprobamos el tipo de la expresion

            
           
        }

    }


    public void ctrlDeclFuncP(NodoDeclFuncP funcP){
        
        //Puede ser que declare parametros o no
        NodoDeclFuncParams hijo = funcP.getNodoDeclFuncParams();
        if(hijo != null && !hijo.isEmpty()){
            ctrlDeclFuncParams(hijo);
        }else if(hijo.isEmpty()){
            //se ha derivado en landa, no hay parametros
        }
    }

    public void ctrlDeclFuncParams(NodoDeclFuncParams funcParams){

        //mete un solo parametro o mete mas de uno
        if(funcParams.getNodoDeclFuncParam() !=null){
            ctrlDeclFunParam(funcParams.getNodoDeclFuncParam());
        }
        if(funcParams.getNodoDeclFuncParams() != null){
            ctrlDeclFuncParams(funcParams.getNodoDeclFuncParams());
        }

    }

    public void ctrlDeclFunParam(NodoDeclFuncParam funParam){

        //compruebo tipo
        //si el tipo no es null, es que es un tipo de los que tengo en la tabla de simbolos, si no error.
        if((DTipus) ts.consultarTD(funParam.getNodoTipo().getTipo().toString()) == null){ // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("ERROR: No existe el tipo",funParam);
        }

        //Compruebo si es void, en ese caso error
        if(funParam.getNodoTipo().getTipo() == Tipo.tsb_void){
            parser.report_error("ERROR: No se puede utilizar parametros void",funParam);
        }

        //miro si hay un array o no
        if(funParam.getNodoDeclArray() != null){
            ctrlDeclArray(funParam.getNodoDeclArray());
        }
    }
    

    public void ctrlAsignArray(){

    }

    public void ctrlDimAray(){
        
    }
}