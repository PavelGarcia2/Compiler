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
        //runProgram();
    }

    private void inicializarTablaSimbolos() {
        System.out.println("INIT TS");
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
        d = new DTipus(Tipo.tsb_float, Float.MIN_VALUE,Float.MAX_VALUE);
        ts.poner("tsb_float", d, null);

        /* Para que las palabras reservadas queden en el ámbito 1 de forma exclusiva */
        ts.entrarBloque();
        System.out.println("HEMOS INIT TS");
    }
    
    public void runProgram(){
        NodoMain main = arbol.getNodoMain();
        if (main != null) {

            //System.out.println("INIT RUN PROGRAM");
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
            parser.report_error("No hemos encontrado el main",main);
        }
    }

    public void ctrlDeclConstantes(NodoDeclConst constList) {
        
        NodoDeclConst hijoDeclaracions = constList.getNodoDeclConst(); 
        if(hijoDeclaracions != null && !hijoDeclaracions.isEmpty()) {
            ctrlDeclConstantes(hijoDeclaracions);
        }        
        ctrlConst(constList.getNodoConst());
       
        System.out.println("FIN DECLCONST");
    }

    public void ctrlConst(NodoConst constante){

        System.out.println("INIT CONST");
        NodoSigno nodoSigno = constante.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral().getSigno();
        NodoTipo tipo = constante.getNodoTipo();
        NodoId id = constante.getNodoId();
        NodoAsignacion asignacion = constante.getNodoAsignacion();
        //System.out.println("HEMOS PILLADO LOS NODOS TIPO,ID,ASIGNACION");

        //System.out.println("COMPROBAMOS QUE SEA UN TIPO QUE EXISTE");
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString());
        //Comprovar que tipo es un tipo
        if(dt == null){        // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("No existe el tipo",constante);
        }
        //System.out.println("COMPROBADO");


        //Comprovar que el tipo es adecuado
        //System.out.println("COMPROBAMOS QUE EL TIPO ES ADECUADO");
        if((dt.getTsb() != Tipo.tsb_int) && (dt.getTsb() != Tipo.tsb_char) && (dt.getTsb() != Tipo.tsb_bool)){
            parser.report_error("No se pueden crear constantes de este tipo",constante);
        }
        //System.out.println("COMPROBADO");


        //Primero miramos que nodo literal no sea null
        ////System.out.println("COMPROBAMOS QUE LITERAL NO ES NULL");
        if(asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral()==null){
            parser.report_error("Estas asignando un valor incorrecto",constante); 
        }
        //ystem.out.println("COMPROBADO");

        //Cogemos el tipo de literal
        Tipo type = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral().getTipo();
        String valor = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral().getValor();
        

        //System.out.println("COMPROBAMOS QUE EL TIPO DE LA IZQUIERDA Y DERECHA SON IGUALES");
        //Mirar si el tipo de la izquierda y derecha son iguales
        
        if(type == Tipo.tsb_int){
            if(dt.getTsb() == Tipo.tsb_bool){
                parser.report_error("Estas asignando un valor de otro tipo",constante);
            }
        } else if(dt.getTsb() != type){
            parser.report_error("Estas asignando un valor de otro tipo",constante);
        }
        
        //System.out.println("COMPROBADO");
        //Si es un entero cogemos el signo
        int signo = 1;
        if(type == Tipo.tsb_int){
            signo = nodoSigno.getSigno();
        }
        //Mirar rango
        char aux;
        int rango;
        // Para mirar el rango
        if(dt.getTsb() == Tipo.tsb_char && type == Tipo.tsb_char){
           aux = valor.charAt(1);
           rango = (int) aux; 
        }else{
            //Mirar rango bien de enteros a la hora de convertirlo en int nos peta
            if(valor.length()>10){

            }
            rango = Integer.parseInt(valor);
        }        
        
        //System.out.println("MIRADO");        
        //System.out.println("COMPROBAMOS EL RANGO, switch");
        switch(dt.getTsb()){
            case tsb_char:
                rango = rango*signo;
                if(rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()){
                    parser.report_error("Has excedido los limites, char de dimensiones incorrectas.",constante);
                }
                break;
            case tsb_bool:
                // Comprobar si es true o false
                if(rango != -1 && rango != 0){
                    parser.report_error("Has excedido los limites, no es bool.",constante);
                }
                break;
            case tsb_int:
                //Mirar rango int
                rango = rango*signo;
                if(rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()){
                    parser.report_error("Has excedido los limites, int de dimensiones incorrectas.",constante);
                }
                break;
            default:
                parser.report_error("No permitimos crear constantes de esta forma",constante);
                break;
        }
        System.out.println("RANGO: "+rango);
        //System.out.println("COMPROBADO");

        //Creamos la nueva declaracion si todo ha ido bien
        //System.out.println("CREAMOS LA CONSTANTE EN LA TS");
        DConst dc = new DConst(dt.getTsb(),rango,id.getNombre());
        ts.poner(id.getNombre(), dc, constante);
        System.out.println("CREADO: "+id.getNombre());
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
            parser.report_error("No existe el tipo",var);
        }

        if(dt.getTsb() == Tipo.tsb_void){
            parser.report_error("No se pueden crear variables void",var);
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
                    parser.report_error("Estas asignando un valor incorrecto, no es int",nodo); 
                }

                valor = Integer.parseInt(nodo.getNodoLiteral().getValor());
                if(valor < dt.getLimiteInferior() && valor > dt.getLimiteSuperior()){
                    parser.report_error("Has excedido los limites",nodo);
                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_int);
                ts.poner(id.getNombre(), d, nodo);
                
            break;

            case Tipo.tsb_bool:
                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_true || nodo.getNodoLiteral().getTipo() != Tipo.tsb_false){
                    parser.report_error("Estas asignando un valor incorrecto, no es bool",nodo);
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
                    parser.report_error("Estas asignando un valor incorrecto, no es char",nodo); 
                }

                valor = Integer.parseInt(nodo.getNodoLiteral().getValor());
                if(valor < dt.getLimiteInferior() && valor > dt.getLimiteSuperior()){
                    parser.report_error("Has excedido los limites",nodo);
                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_char);
                ts.poner(id.getNombre(), d, nodo);

            break;
 
            case Tipo.tsb_float:

                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_float){
                    parser.report_error("Estas asignando un valor incorrecto, no es float",nodo); 
                }

                float val = Float.parseFloat(nodo.getNodoLiteral().getValor());
                if(val < dt.getLimiteInferior() && val > dt.getLimiteSuperior()){
                    parser.report_error("Has excedido los limites",nodo);
                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_float);
                ts.poner(id.getNombre(), d, nodo);
                
            break;

            case Tipo.tsb_str:

                if(nodo.getNodoLiteral().getTipo() != Tipo.tsb_str){
                    parser.report_error("Estas asignando un valor incorrecto, no es str",nodo); 
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
            parser.report_error("No existe el tipo",func);
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
                    parser.report_error("La funcion es booleana y el return no es booleano",ret);
                }
                
                break;

            case tsb_char:
                if(t != Tipo.tsb_char){
                    //No es un char error
                    parser.report_error("La funcion es de tipo character y el return no es character",ret);
                }
                
                break;

            case tsb_float:
                
                if(t != Tipo.tsb_float){
                    //No es un float error
                    parser.report_error("La funcion es de tipo float y el return no es float",ret);
                }
                break;

            case tsb_int:

                if(t != Tipo.tsb_int){
                    //No es un int error
                    parser.report_error("La funcion es de tipo int y el return no es int",ret);
                }
                break;

            case tsb_str:
                
                if(t != Tipo.tsb_str){
                    //No es un string error
                    parser.report_error("La funcion es de tipo string y el return no es string",ret);
                }
                break;

            case tsb_void: 

                    if(!ret.isEmpty()){
                        parser.report_error("La funcion es de tipo void y el return no es void",ret);
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
            parser.report_error("No existe el id",realAsign);
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
            parser.report_error("No existe el tipo",funParam);
        }

        //Compruebo si es void, en ese caso error
        if(funParam.getNodoTipo().getTipo() == Tipo.tsb_void){
            parser.report_error("No se puede utilizar parametros void",funParam);
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
