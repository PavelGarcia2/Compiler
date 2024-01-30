package semantico;

import sintactico.arbol.*;

import java.util.ArrayList;

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
    private static TablaSimbolos ts;

    public Semantico(NodoPrograma arbol, Parser parser) {
        this.arbol = arbol;
        // creamos la tabla de simbolos y la inicializamos
        this.parser = parser;
        this.ts = new TablaSimbolos(parser);
        inicializarTablaSimbolos();
        // runProgram();
    }

    private void inicializarTablaSimbolos() {
        System.out.println("INIT TS");

        DTipus dv = new DTipus(Tipo.tsb_void, 0, 0);
        ts.poner("tsb_void", dv, null);

        // Inicializamos el bool
        DTipus d = new DTipus(Tipo.tsb_bool, -1, 0);
        ts.poner("tsb_bool", d, null);

        // Inicializamos el valor true
        DConst dC = new DConst(Tipo.tsb_bool, -1, "true");
        ts.poner("true", dC, null);

        // Inicializamos el valor false
        dC = new DConst(Tipo.tsb_bool, 0, "false");
        ts.poner("false", dC, null);

        // Inicializamos el char
        d = new DTipus(Tipo.tsb_char, 0, 255);
        ts.poner("tsb_char", d, null);

        // Inicializamos el int
        d = new DTipus(Tipo.tsb_int, Integer.MIN_VALUE, Integer.MAX_VALUE);
        ts.poner("tsb_int", d, null);

        // Inicializamos el float
        d = new DTipus(Tipo.tsb_float, Float.MIN_VALUE, Float.MAX_VALUE);
        ts.poner("tsb_float", d, null);

        // Inicializamos el str
        d = new DTipus(Tipo.tsb_str, 0, 1);
        ts.poner("tsb_str", d, null);

        /* Para que las palabras reservadas queden en el ámbito 1 de forma exclusiva */
        ts.entrarBloque();
        System.out.println("HEMOS INIT TS");
    }

    public void runProgram() {
        NodoMain main = arbol.getNodoMain();
        if (main != null) {

            System.out.println("INIT RUN PROGRAM");
            // comprobar las constantes
            NodoDeclConst constList = arbol.getNodoDeclaracionConstantes();
            if (constList != null && !constList.isEmpty()) {
                ctrlDeclConstantes(constList);
            }

            // obtenemos todas las declaracioes de variables
            NodoDeclVars varList = arbol.getNodoDeclaracionVariables();
            // si la lista no esta vacia o es nula
            if (varList != null && !varList.isEmpty()) {
                // controlamos todas las declaraciones de variables
                ctrlDeclListVariables(varList);
            }

            ctrlMain(main);

            // obtenemos todas las declaraciones de procedures
            NodoDeclFunc funcList = arbol.getNodoDeclaracionFunciones();
            if (funcList != null && !funcList.isEmpty()) {
                ctrlDeclListFunciones(funcList);
            }

        } else {
            parser.report_error("No hemos encontrado el main", main);
        }
    }

    public void ctrlDeclConstantes(NodoDeclConst constList) {
        // CAMBIAR A ITERATIVO
        System.out.println("INIT DECLCONSTS");
        NodoDeclConst hijoDeclaracions = constList.getNodoDeclConst();
        if (hijoDeclaracions != null && !hijoDeclaracions.isEmpty()) {
            ctrlDeclConstantes(hijoDeclaracions);
        }
        ctrlConst(constList.getNodoConst());

        // System.out.println("FIN DECLCONST");
    }

    public void ctrlConst(NodoConst constante) {

        NodoSigno nodoSigno = constante.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                .getNodoExpresion().getNodoLiteral().getSigno();
        NodoTipo tipo = constante.getNodoTipo();
        NodoId id = constante.getNodoId();
        NodoAsignacion asignacion = constante.getNodoAsignacion();
        // System.out.println("HEMOS PILLADO LOS NODOS TIPO,ID,ASIGNACION");

        // System.out.println("COMPROBAMOS QUE SEA UN TIPO QUE EXISTE");
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString());
        // Comprovar que tipo es un tipo
        if (dt == null) { // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("No existe el tipo", constante);
        }
        // System.out.println("COMPROBADO");

        // Comprovar que el tipo es adecuado
        // System.out.println("COMPROBAMOS QUE EL TIPO ES ADECUADO");
        if ((dt.getTsb() != Tipo.tsb_int) && (dt.getTsb() != Tipo.tsb_char) && (dt.getTsb() != Tipo.tsb_bool)) {
            parser.report_error("No se pueden crear constantes de este tipo", constante);
        }
        // System.out.println("COMPROBADO");

        // Primero miramos que nodo literal no sea null
        //// System.out.println("COMPROBAMOS QUE LITERAL NO ES NULL");
        if (asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral() == null) {
            parser.report_error("Estas asignando un valor incorrecto", constante);
        }
        // ystem.out.println("COMPROBADO");

        // Cogemos el tipo de literal
        Tipo type = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral()
                .getTipo();
        String valor = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral()
                .getValor();

        // System.out.println("COMPROBAMOS QUE EL TIPO DE LA IZQUIERDA Y DERECHA SON
        // IGUALES");
        // Mirar si el tipo de la izquierda y derecha son iguales

        if (type == Tipo.tsb_int) {
            if (dt.getTsb() == Tipo.tsb_bool) {
                parser.report_error("Estas asignando un valor de otro tipo", constante);
            }
        } else if (dt.getTsb() != type) {
            parser.report_error("Estas asignando un valor de otro tipo", constante);
        }

        // System.out.println("COMPROBADO");
        // Si es un entero cogemos el signo
        int signo = 1;
        if (nodoSigno != null && type == Tipo.tsb_int) {
            signo = nodoSigno.getSigno();
            // System.out.println(constante.getNodoId());
        }
        // Mirar rango
        char aux;
        int rango;
        // Para mirar el rango
        if (dt.getTsb() == Tipo.tsb_char && type == Tipo.tsb_char) {
            aux = valor.charAt(1);
            rango = (int) aux;
        } else {
            // Mirar rango bien de enteros a la hora de convertirlo en int nos peta
            if (valor.length() > 10) {

            }
            rango = Integer.parseInt(valor);
        }

        // System.out.println("MIRADO");
        // System.out.println("COMPROBAMOS EL RANGO, switch");
        switch (dt.getTsb()) {
            case tsb_char:
                rango = rango * signo;
                if (rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()) {
                    parser.report_error("Has excedido los limites, char de dimensiones incorrectas.", constante);
                }
                break;
            case tsb_bool:
                // Comprobar si es true o false
                if (rango != -1 && rango != 0) {
                    parser.report_error("Has excedido los limites, no es bool.", constante);
                }
                break;
            case tsb_int:
                // Mirar rango int
                rango = rango * signo;
                if (rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()) {
                    parser.report_error("Has excedido los limites, int de dimensiones incorrectas.", constante);
                }
                break;
            default:
                parser.report_error("No permitimos crear constantes de esta forma", constante);
                break;
        }
        // System.out.println("RANGO: " + rango);
        // System.out.println("COMPROBADO");

        // Creamos la nueva declaracion si todo ha ido bien
        // System.out.println("CREAMOS LA CONSTANTE EN LA TS");
        DConst dc = new DConst(dt.getTsb(), rango, id.getNombre());
        ts.poner(id.getNombre(), dc, constante);
        // System.out.println("CREADO: " + id.getNombre());
    }

    public void ctrlMain(NodoMain nodo) {

        // ?¿?¿?¿?¿
        ts.entrarBloque();
        //ts.entrarBloque();

        // Control de declaracion variables
        System.out.println("Comprobamos declaracion variables en ctrlMain()");
        if (nodo.getNodoDeclVars() != null && !nodo.getNodoDeclVars().isEmpty()) {
            ctrlDeclListVariables(nodo.getNodoDeclVars());
        }

        // Control de sentencias
        System.out.println("Comprobamos sentencias en ctrlMain()");
        if (nodo.getNodoSents() != null && !nodo.getNodoSents().isEmpty()) {
            ctrlSents(nodo.getNodoSents());
        }

        // ?¿?¿?¿?¿
        ts.salirBloque();
       // ts.salirBloque();

    }

    public void ctrlDeclListVariables(NodoDeclVars varList) {

        NodoDeclVars hijo = varList;
        NodoVar nodoVar;
        do {
            if (hijo != null) {
                nodoVar = hijo.getNodoDeclaracionVariable();
                ctrlVar(nodoVar);
            }
            hijo = hijo.getNodoDeclaracionVariables();

        } while (hijo != null);

        // System.out.println("FIN DECLVARS");
    }

    public void ctrlVar(NodoVar var) {
        // System.out.println("ctrlVar Entra");
        // System.out.println(var.getNodoId().getNombre());
        if (var.getNodoTipo() != null) {
            NodoTipo tipo = var.getNodoTipo();
            NodoId id = var.getNodoId();
            NodoAsignacion asignacion = var.getNodoAsignacion();

            // Comprovar que tipo es un tipo existente
            DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString());
            if (dt == null) { // NO EXISTE EL TIPO O NO ES UN TIPO
                parser.report_error("No existe el tipo", var);
            }

            if (dt.getTsb() == Tipo.tsb_void) {
                parser.report_error("No se pueden crear variables void", var);
            }

            // Mirar si estamos declarando un array
            NodoDeclArray declaracionArray = var.getNodoDeclArray();
            if (declaracionArray != null && !declaracionArray.isEmpty()) {

                // Miramos las dimensiones del array
                int dimArray = dimensionArr(declaracionArray);
                // System.out.println("DIMENSIONES ARRAY " + id.getNombre() + ": " + dimArray);
                Darray da;
                // Mirar si estamos inicializando el array

                if (asignacion != null && !asignacion.isEmpty()) {
                    NodoAsignacionArray nAA = asignacion.getNodoTipoAsignacion().getNodoAsignacionArray();
                    ctrlAsignArray(dt, nAA, id, dimArray);
                    // da = new Darray(0, dt.getTsb(),dimArray,false);
                    // ts.poner(id.getNombre(), da, id);

                } else {
                    parser.report_error("Se debe declarar el array", declaracionArray);
                }

            } else {
                // Mirar si tenemos que hacer la asignacion
                if (asignacion != null && !asignacion.isEmpty()) {
                    // System.out.println("ENTRA?2");
                    ctrlAsignNormal(dt, asignacion, id);
                } else {
                    Dvar d;
                    d = new Dvar(0, dt.getTsb());
                    ts.poner(id.getNombre(), d, id);
                    // System.out.println("Metemos la variable " + id.getNombre() + " en la TS");
                }
            }
        } else {
            // System.out.println("Entra2");
            // Aqui entramos si queremos setear algo
            if (ts.consultarTD(var.getNodoId().getNombre()) == null) {
                parser.report_error("La variable " + var.getNodoId().getNombre() + " no existe!", var.getNodoId());
            }

            // Comprovacion de tipos
            if (var.getNodoDimArray() == null) {
                Dvar d = (Dvar) ts.consultarTD(var.getNodoId().getNombre());
                Tipo tipoIzq = d.getTipus();
                // System.out.println("PreIF");
                if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                        .getNodoLiteral() != null) {
                    Tipo tipoDer = var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                            .getNodoExpresion().getNodoLiteral().getTipo();

                    // si char izq , solo puede ser char o int der
                    if (tipoIzq == Tipo.tsb_char) {
                        if (tipoDer != Tipo.tsb_char || tipoDer != Tipo.tsb_int) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    } else {
                        if (tipoIzq != tipoDer) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    }
                    // Mirar si es una expresion logica (se puede mirar lo de tnot) o expresion
                    // aritm
                } else if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                        .getNodoExpresionLog() != null
                        || var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                                .getNodoExpresionArit() != null) {

                    // Mirar si es un id
                } else if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                        .getNodoId() != null) {

                    if (ts.consultarTD(var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                            .getNodoExpresion().getNodoId().getNombre()) == null) {
                        parser.report_error(
                                "La variable "
                                        + var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                                                .getNodoExpresion().getNodoId().getNombre()
                                        + " no existe!",
                                var.getNodoId());
                    }
                    Dvar dId = (Dvar) ts.consultarTD(var.getNodoAsignacion().getNodoTipoAsignacion()
                            .getNodoAsignacionNormal().getNodoExpresion().getNodoId().getNombre());

                    Tipo tipoDer = dId.getTipus();

                    if (tipoIzq == Tipo.tsb_char) {
                        if (tipoDer != Tipo.tsb_char && tipoDer != Tipo.tsb_int) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    } else if (tipoIzq == Tipo.tsb_int || tipoIzq == Tipo.tsb_float) {
                        if (tipoDer != Tipo.tsb_int && tipoDer != Tipo.tsb_float) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    } else {
                        if (tipoIzq != tipoDer) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    }

                    // Mirar si es una expresion compuesta
                } else if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                        .getNodoExpresion() != null) {
                    System.out.println("Expresion compuesta " + var.getNodoAsignacion().getNodoTipoAsignacion()
                            .getNodoAsignacionNormal().getNodoExpresion());
                }

            } else {
                // AQUI HACEMOS set arr[2] = valor;

                NodoDimArray dimension = var.getNodoDimArray();
                NodoDimArray antiguo;
                // System.out.println("Evaluando el array: "+var.getNodoId().getNombre());

                int dim = 0;

                do {
                    dim++;
                    if (dimension.getNodoExpresion().getNodoLiteral() != null) {
                        if (dimension.getNodoExpresion().getNodoLiteral().getTipo() != Tipo.tsb_int) {
                            parser.report_error("No se pueden declarar array de esta forma",
                                    dimension.getNodoExpresion().getNodoLiteral());
                        }
                    } else if (dimension.getNodoExpresion().getNodoId() != null) {
                        // System.out.println("ENtro en id " +
                        // dimension.getNodoExpresion().getNodoId().getNombre());
                        Dvar d = (Dvar) ts
                                .consultarTD(dimension.getNodoExpresion().getNodoId().getNombre());

                        if (d == null) {
                            parser.report_error("La variable utilizada para asignar en el array no existe",
                                    dimension.getNodoExpresion().getNodoId());
                        }

                        if (d.getTipus() != Tipo.tsb_int) {
                            parser.report_error(
                                    "La variable utilizada para asignar en un array no es del tipo correcto",
                                    dimension.getNodoExpresion().getNodoId());
                        }
                    }

                    antiguo = dimension;

                    if (dimension.getNodoDimensiones() != null) {
                        dimension = dimension.getNodoDimensiones().getNodoDimArray();
                    }

                } while (antiguo.getNodoDimensiones() != null);

                // hay que mirar si estamos haciendo la asignacion con las dimensiones correctas
                Darray d = (Darray) ts.consultarTD(var.getNodoId().getNombre());
                // System.out.println(d.getDimensiones());
                // System.out.println(dim);
                if (d.getDimensiones() != dim) {
                    parser.report_error("Estas asignando valores a un array con las dimensiones incorrectas",
                            var.getNodoId());
                }

                // Ahora comprobamos que el valor de la expresion sea el compatible con la parte
                // izquierda
                // TO-DO Permitir meter char en ints, floats en ints e ints en floats
                if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                        .getNodoLiteral() != null) {
                    if (d.getTipus() != var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                            .getNodoExpresion().getNodoLiteral().getTipo()) {
                        parser.report_error("Valores no compatibles", var.getNodoAsignacion().getNodoTipoAsignacion()
                                .getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral());
                    }
                } else if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                        .getNodoId() != null) {
                    Dvar d2 = (Dvar) ts.consultarTD(var.getNodoAsignacion().getNodoTipoAsignacion()
                            .getNodoAsignacionNormal().getNodoExpresion().getNodoId().getNombre());
                    if (d2.getTipus() != d.getTipus()) {
                        parser.report_error("Valores no compatibles", var.getNodoAsignacion().getNodoTipoAsignacion()
                                .getNodoAsignacionNormal().getNodoExpresion().getNodoId());
                    }
                }
            }
        }
    }

    public int dimensionArr(NodoDeclArray declArray) {
        // Contar dimensiones que tiene
        int dim = 0;
        while (declArray != null && !declArray.isEmpty()) {
            dim++;
            declArray = declArray.getNodoDeclArray();
        }
        return dim;
    }

    public void ctrlAsignArray(DTipus dt, NodoAsignacionArray asign, NodoId id, int dimIzq) {
        // Hay que comprobar que el tipo de la derecha sea igual que el de la izquierda
        // El tipo de la izquierda lo tenemos en dt ahora miramos el tipo de la derecha
        // que esta en asign
        if (dt.getTsb() != asign.getNodoTipo().getTipo()) {
            parser.report_error("Tipos incompatibles", asign);
        }

        // Hay que mirar que las dimensiones del array sean las mismas que las de la
        // izquierda
        // int a[][] = new int[2][3];
        NodoDimArray dimArray = asign.getNodoDimArray();
        int dimDer = dimensionesAsignArray(dimArray);
        if (dimIzq != dimDer) {
            parser.report_error("Dimensiones incorrectas", asign);
        }

        // Hay que mirar si 2 y 3 son enteros
        // int a[][] = new int[2][3];
        NodoDimArray dimension = asign.getNodoDimArray();
        ArrayList<String> bounds = new ArrayList();
        do {
            if (dimension.getNodoExpresion().getNodoLiteral() != null) {
                if (dimension.getNodoExpresion().getNodoLiteral().getTipo() != Tipo.tsb_int) {
                    parser.report_error("no se pueden asignar estas dimensiones", dimension);
                } else {
                    bounds.add((dimension.getNodoExpresion().getNodoLiteral().getValor()));
                }
            } else if (dimension.getNodoExpresion().getNodoId() != null) {
                Dvar d = (Dvar) ts.consultarTD(dimension.getNodoExpresion().getNodoId().getNombre());
                if (d == null) {
                    parser.report_error("La variable no existe!", dimension);
                }
                if (d.getTipus() != Tipo.tsb_int) {
                    parser.report_error("La variable usada para declarar las dimensiones del array no es un entero",
                            dimension);
                }
                bounds.add((dimension.getNodoExpresion().getNodoId().getNombre()));
            } else {
                parser.report_error("no se pueden asignar estas dimensiones", dimension);
            }
            if (dimension.getNodoDimensiones() != null) {
                dimension = dimension.getNodoDimensiones().getNodoDimArray();
            }
        } while (dimension.getNodoDimensiones() != null);

        // Hay que guardar el 2 y el 3 en alguna parte para luego poder calcular out of
        // bounds
        // int a[][] = new int[2][3];
        Darray da = new Darray(0, dt.getTsb(), dimIzq, true, bounds);
        ts.poner(id.getNombre(), da, id);
    }

    public int dimensionesAsignArray(NodoDimArray dimArray) {
        int dim = 1;
        NodoDimensiones nDimensiones = dimArray.getNodoDimensiones();
        while (nDimensiones != null) {
            dim++;
            nDimensiones = nDimensiones.getNodoDimArray().getNodoDimensiones();
        }
        return dim;
    }

    public void ctrlAsignNormal(DTipus dt, NodoAsignacion asign, NodoId id) {
        // lo que tenemos a la derecha
        NodoExpresion nodo = asign.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion();
        // Comprovar que el tipo de NodoTipo y el tipo de NodoASignacion es compatible
        // El tipo de NodoTipo lo tenemos en dt
        // El tipo de NodoAsignacion lo tenemos en expresion
        // Vamos a hacer el control segun lo que estemos declarando

        int valor;
        Dvar d;

        switch (dt.getTsb()) {

            case tsb_int:
                if (nodo.getNodoLiteral() != null) {

                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_int
                            && nodo.getNodoLiteral().getTipo() != Tipo.tsb_float) {
                        parser.report_error("Estas asignando un valor incorrecto, no es int", nodo);
                    }

                    valor = (int) Float.parseFloat(nodo.getNodoLiteral().getValor());

                    if (valor < dt.getLimiteInferior() && valor > dt.getLimiteSuperior()) {
                        parser.report_error("Has excedido los limites", nodo);
                    }

                } else {
                    // a = b, primero consultar tabla de simbolos b tiene que existir
                    //Si es un array no se puede
                    Descripcion desc = ts.consultarTD(nodo.getNodoId().getNombre());
                    if(desc.getTDescripcion() == Descripcion.TDesc.darray.toString()){
                        parser.report_error("Estas asignando un valor incorrecto", nodo);
                    }
                    Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());
                    if (d3 == null) {
                        parser.report_error("Estas asignando un valor incorrecto, no existe", nodo);
                    }

                    if (d3.getTipus() != Tipo.tsb_float && d3.getTipus() != Tipo.tsb_int) {
                        parser.report_error("Estas asignando un valor incorrecto, no es int", nodo);
                    }

                }
                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_int);
                ts.poner(id.getNombre(), d, nodo);
                break;

            case tsb_bool:

                ctrlExp(nodo);
                // si tipo de izquierda es bool comprobamos deerecha
                // if (nodo.getNodoLiteral() != null && nodo.getNodoLiteral().getTipo() !=
                // Tipo.tsb_bool) {
                // parser.report_error("Estas asignando un valor incorrecto, no es bool", nodo);
                // } else if(nodo.getNodoId() != null){
                // Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());
                // if (d3 == null) {
                // parser.report_error("Estas asignando un valor incorrecto, no existe", nodo);
                // }

                // if (d3.getTipus() != dt.getTsb()) {
                // parser.report_error("Estas asignando un valor incorrecto, no es booleano",
                // nodo);
                // }

                // }else if(nodo.getNodoExpresionConNegacion() != null){

                // ctrlExpNeg(nodo.getNodoExpresionConNegacion());

                // }else if(nodo.getNodoExpresionLog() != null){
                // ctrlExpLog()
                // }

                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_bool);
                ts.poner(id.getNombre(), d, nodo);

                break;

            case tsb_char:

                if (nodo.getNodoLiteral() != null) {
                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_int
                            && nodo.getNodoLiteral().getTipo() != Tipo.tsb_char) {
                        parser.report_error("Estas asignando un valor incorrecto, no es char", nodo);
                    }

                    valor = Integer.parseInt(nodo.getNodoLiteral().getValor());

                    if (valor < dt.getLimiteInferior() || valor > dt.getLimiteSuperior()) {
                        parser.report_error("Has excedido los limites", nodo);
                    }
                } else {
                    Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());
                    if (d3 == null) {
                        parser.report_error("Estas asignando un valor incorrecto la variable no existe", nodo);
                    }

                    if (d3.getTipus() != Tipo.tsb_int && d3.getTipus() != Tipo.tsb_char) {
                        parser.report_error("Estas asignando un valor incorrecto, no es int o char", nodo);
                    }
                }

                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_char);
                ts.poner(id.getNombre(), d, nodo);

                break;

            case tsb_float:

                if (nodo.getNodoLiteral() != null) {

                    // int b =3;
                    // float a = b;
                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_int
                            && nodo.getNodoLiteral().getTipo() != Tipo.tsb_float) {
                        parser.report_error("Estas asignando un valor incorrecto, no es int", nodo);
                    }

                    float val;
                    val = Float.parseFloat(nodo.getNodoLiteral().getValor());

                    if (val < dt.getLimiteInferior() && val > dt.getLimiteSuperior()) {
                        parser.report_error("Has excedido los limites", nodo);
                    }

                } else {

                    // set a=b
                    Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());
                    if (d3 == null) {
                        parser.report_error("Estas asignando un valor incorrecto la variable no existe", nodo);
                    }

                    if (d3.getTipus() != Tipo.tsb_int && d3.getTipus() != Tipo.tsb_float) {
                        parser.report_error("Estas asignando un valor incorrecto, no es float", nodo);
                    }

                }

                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_float);
                ts.poner(id.getNombre(), d, nodo);

                break;

            case tsb_str:
                if (nodo.getNodoLiteral() != null) {
                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_str) {
                        parser.report_error("Estas asignando un valor incorrecto, no es str", nodo);
                    }
                } else {

                    Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());
                    if (d3 == null) {
                        parser.report_error("Estas asignando un valor incorrecto la variable no existe", nodo);
                    }

                    if (d3.getTipus() != dt.getTsb()) {
                        parser.report_error("Estas asignando un valor incorrecto, no es str", nodo);
                    }

                }

                // EL 0 es provisional este 0 se tendra que pasar con c3a
                d = new Dvar(0, Tipo.tsb_str);
                ts.poner(id.getNombre(), d, nodo);

                break;
            default:
                System.out.println("Hay un error ctrlAsignNormal");
                break;
        }

    }

    public void ctrlDeclListFunciones(NodoDeclFunc funcList) {

        while (funcList != null) {
            ctrlFunc(funcList.getNodoFunc());
            funcList = funcList.getNodoDeclFunc();
        }
    }

    public void ctrlFunc(NodoFunc func) {

        // comprobar el tipo
        System.out.println("INIT CTRL FUNC");
        NodoTipo tipo = func.getNodoTipo(); // cojo el tipo del nodo que me llega
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString()); // consulto dicho tipo en la tabla de simbolos
        // si el tipo no es null, es que es un tipo de los que tengo en la tabla de
        // simbolos, si no error.
        if (dt == null) { // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("No existe el tipo", func);
        }

        if(tipo.getTipo() != Tipo.tsb_void && func.getNodoReturn() == null){
            parser.report_error("Tienes que hacer el return", func);
        }

        // comrpobar si es una funcion que devuelve un array
        NodoDeclArray declArray = func.getNodoDeclArray();
        if (declArray != null && !declArray.isEmpty()) { // signifca que tenemos una declaracion de array
            // ctrlDeclArray(declArray);
        }
        System.out.println("adios");
        
        // compruebo el id
        NodoId id = func.getNodoId();
        // Este id no tiene que existir ya
        if (ts.consultarTD(id.getNombre()) != null) {
            parser.report_error("El id ya ha sido declarado", func.getNodoId());
        }
        ts.entrarBloque();
        // Antes de comprobar las declaraciones de las variables tenemos que poner en la
        // tabla de simbolos
        // las variables de los parametros
        // comprobar la declaracion de los parametros de las funciones
        
        NodoDeclFuncP funcP = func.getNodoDeclFuncP();
        if (funcP != null && !funcP.isEmpty()) {
            ctrlDeclFuncP(funcP);
        }
        

        // comprobar las declaraciones de las variables
        
        NodoDeclVars declVars = func.getNodoDeclVars();
        if (declVars != null && !declVars.isEmpty()) {
            ctrlDeclListVariables(declVars);
        }
        

        // comprobar las sentencias
        
        if (func.getNodoSents() != null) {
            ctrlSents(func.getNodoSents());
        }
        
        // comprobar el return
        NodoReturn ret = func.getNodoReturn();
        if(tipo.getTipo() == Tipo.tsb_void && ret != null){
            parser.report_error("No se debe devolver nada!", ret);
        }
        if (ret != null && !ret.isEmpty()) {
            ctrlReturn(ret, func.getNodoTipo());
        }

        ts.salirBloque();

        // añadir la funcion a la tabla de simbolos
        ts.poner(id.getNombre(), new DFunc(2, func.getNodoTipo().getTipo()), func);
    }

    public void ctrlReturn(NodoReturn ret, NodoTipo tipo) {

        NodoReturnParam paramReturn = ret.getNodoReturnParam();
        if(paramReturn.getNodoId() != null){
            //Primero miramos que exista
            Descripcion d = ts.consultarTD(paramReturn.getNodoId().getNombre());
            if(d == null){
                parser.report_error("No existe la variable", paramReturn.getNodoId());
            }
            //Si existe miramos que el tipo sea el mismo que el del return
            if(d.getTDescripcion() == Descripcion.TDesc.dvar.toString()){
                Dvar d1 = (Dvar) d;
                if(d1.getTipus() != tipo.getTipo()){
                    parser.report_error("Tipos incompatibles", paramReturn.getNodoId());
                }
            }else{ // Es un array
                Darray d1 = (Darray) d;
                if(d1.getTipus() != tipo.getTipo()){
                    parser.report_error("Tipos incompatibles", paramReturn.getNodoId());
                }
            }
        }else{  //Es un literal
            //Si es un literal basta con coger su tipo
            if(paramReturn.getNodoLiteral().getTipo() != tipo.getTipo()){
                parser.report_error("Tipos incompatibles", paramReturn.getNodoLiteral());
            }
        }
    }

    private void consultarValidez(NodoTipo tipo, Tipo t, NodoReturn ret) {
        switch (tipo.getTipo()) {
            // si la funcion tiene un tipo booleano el rreturn tambien debe ser booleano
            case tsb_bool:
                // compruebo las posibles partes boleanas, si no es true o false error
                if (t != Tipo.tsb_true || t != Tipo.tsb_false) {
                    // No es un bool error
                    parser.report_error("La funcion es booleana y el return no es booleano", ret);
                }

                break;

            case tsb_char:
                if (t != Tipo.tsb_char) {
                    // No es un char error
                    parser.report_error("La funcion es de tipo character y el return no es character", ret);
                }

                break;

            case tsb_float:

                if (t != Tipo.tsb_float) {
                    // No es un float error
                    parser.report_error("La funcion es de tipo float y el return no es float", ret);
                }
                break;

            case tsb_int:

                if (t != Tipo.tsb_int) {
                    // No es un int error
                    parser.report_error("La funcion es de tipo int y el return no es int", ret);
                }
                break;

            case tsb_str:

                if (t != Tipo.tsb_str) {
                    // No es un string error
                    parser.report_error("La funcion es de tipo string y el return no es string", ret);
                }
                break;

            case tsb_void:

                if (!ret.isEmpty()) {
                    parser.report_error("La funcion es de tipo void y el return no es void", ret);
                }

                break;

            default:
                break;
        }
    }

    public void ctrlSents(NodoSents sents) {

        while (sents != null) {
            ctrlSent(sents.getNodoSent());
            sents = sents.getNodoSents();
        }
    }

    public void ctrlSent(NodoSent sent) {

        if (sent.getNodoOtrasSent() != null) {
            System.out.println("HOLA ENTRO EN OTRAS SENT");
            ctrlOtrasSent(sent.getNodoOtrasSent());
        }

        if (sent.getNodoRealAsign() != null) {
            System.out.println("HOLA ENTRO EN REAL ASIGN");
            ctrlRealAsign(sent.getNodoRealAsign());
        }

        // if (sent.getNodoRealArrAsign() != null) {
        // System.out.println("HOLA ENTRO EN REAL ARRAY ASIGN");
        // ctrlRealArrayAsign(sent.getNodoRealArrAsign());
        // }

    }

    public void ctrlRealArrayAsign(NodoRealArrAsign nodoRealArrAsign) {

        NodoId id = nodoRealArrAsign.getNodoId();
        NodoDimArray dimension = nodoRealArrAsign.getNodoDimArray();
        NodoExpresion expresion = nodoRealArrAsign.getNodoExpresion();

        // AQUI HACEMOS set arr[2] = valor;

        NodoDimArray antiguo;
        // System.out.println("Evaluando el array: "+var.getNodoId().getNombre());

        int dim = 0;

        do {
            dim++;
            if (dimension.getNodoExpresion().getNodoLiteral() != null) {
                if (dimension.getNodoExpresion().getNodoLiteral().getTipo() != Tipo.tsb_int) {
                    parser.report_error("No se pueden declarar array de esta forma",
                            dimension.getNodoExpresion().getNodoLiteral());
                }
            } else if (dimension.getNodoExpresion().getNodoId() != null) {
                // System.out.println("ENtro en id " +
                // dimension.getNodoExpresion().getNodoId().getNombre());
                Dvar d = (Dvar) ts
                        .consultarTD(dimension.getNodoExpresion().getNodoId().getNombre());

                if (d == null) {
                    parser.report_error("La variable utilizada para asignar en el array no existe",
                            dimension.getNodoExpresion().getNodoId());
                }

                if (d.getTipus() != Tipo.tsb_int) {
                    parser.report_error(
                            "La variable utilizada para asignar en un array no es del tipo correcto",
                            dimension.getNodoExpresion().getNodoId());
                }
            }

            antiguo = dimension;

            if (dimension.getNodoDimensiones() != null) {
                dimension = dimension.getNodoDimensiones().getNodoDimArray();
            }

        } while (antiguo.getNodoDimensiones() != null);

        // hay que mirar si estamos haciendo la asignacion con las dimensiones correctas
        Darray d = (Darray) ts.consultarTD(id.getNombre());
        // System.out.println(d.getDimensiones());
        // System.out.println(dim);
        if (d.getDimensiones() != dim) {
            parser.report_error("Estas asignando valores a un array con las dimensiones incorrectas",
                    id);
        }
        // comprobar que el id existe en la tabla de simbolos
        if (ts.consultarTD(id.getNombre()) == null) {
            parser.report_error("No existe el id", id);
        }
        Dvar dt = (Dvar) ts.consultarTD(id.getNombre().toString()); // tipo del id
        Tipo var = dt.getTipus();
        if (expresion.getNodoLiteral() != null) {
            Tipo tipoExpr = expresion.getNodoLiteral().getTipo();

            if (var == Tipo.tsb_int || var == Tipo.tsb_float) {
                if (tipoExpr != Tipo.tsb_int && var != Tipo.tsb_float) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var == Tipo.tsb_char) {
                if (tipoExpr != Tipo.tsb_char && tipoExpr != Tipo.tsb_int) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var != tipoExpr) {
                parser.report_error("Tipos incompatibles", expresion);
            }

        } else if (expresion.getNodoId() != null) {

            Dvar tipoExpr = (Dvar) ts.consultarTD(expresion.getNodoId().getNombre());

            if (tipoExpr.getTipus() != dt.getTipus()) {
                parser.report_error("Tipos incompatibles", expresion.getNodoId());
            }

            if (var == Tipo.tsb_int || var == Tipo.tsb_float) {
                if (tipoExpr.getTipus() != Tipo.tsb_int && var != Tipo.tsb_float) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var == Tipo.tsb_char) {
                if (tipoExpr.getTipus() != Tipo.tsb_char && tipoExpr.getTipus() != Tipo.tsb_int) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var != tipoExpr.getTipus()) {
                parser.report_error("Tipos incompatibles", expresion);
            }

        } else if (expresion.getNodoLlamadaFunc() != null) {

            DFunc tipoExpr = (DFunc) ts.consultarTD(expresion.getNodoLlamadaFunc().getNodoId().getNombre());

            if (tipoExpr.getTipo() != dt.getTipus()) {
                parser.report_error("Tipos incompatibles", expresion.getNodoLlamadaFunc().getNodoId());
            }

            if (var == Tipo.tsb_int || var == Tipo.tsb_float) {
                if (tipoExpr.getTipo() != Tipo.tsb_int && var != Tipo.tsb_float) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var == Tipo.tsb_char) {
                if (tipoExpr.getTipo() != Tipo.tsb_char && tipoExpr.getTipo() != Tipo.tsb_int) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var != tipoExpr.getTipo()) {
                parser.report_error("Tipos incompatibles", expresion);
            }
        }

    }

    public void ctrlOtrasSent(NodoOtrasSent otras) { // bucles ,while ,switch

        switch (otras.getIdentificador()) {

            case 0: // if
                System.out.println("Detectamos un if");

                if (otras.getNodoExpresion() != null) {
                    ctrlExp(otras.getNodoExpresion());
                }
                

                // comprobamos las sentencias
                if (otras.getNodoSents() != null) {
                    ctrlSents(otras.getNodoSents());
                }

                // comprobamos el else
                if (otras.getNodoElse() != null) {
                    ctrlElseSent(otras.getNodoElse());
                }

                break;

            case 1: // while

                if(otras.getNodoExpresion() != null){
                    ctrlExp(otras.getNodoExpresion());
                }
                

                // comprobamos las sentencias
                if (otras.getNodoSents() != null) {
                    ctrlSents(otras.getNodoSents());
                }

                break;

            case 2: // for

                // compruebo que el id existe en la tabla de simbolos
                if (ts.consultarTD(otras.getNodoId().getNombre()) == null) {
                    parser.report_error("No existe el id", otras);
                }

                // comprobamos que exp sera bool
                ctrlExp(otras.getNodoExpresion());

                if (otras.getNodoOpRapidos() != null) {
                    ctrlOpRapidos(otras.getNodoOpRapidos());
                }

                // comprobamos las sentencias
                if (otras.getNodoSents() != null) {
                    ctrlSents(otras.getNodoSents());
                }

                break;

            case 3: // switch

                // compruebo que el id existe en la tabla de simbolos
                if (ts.consultarTD(otras.getNodoId().getNombre()) == null) {
                    parser.report_error("No existe el id", otras);
                }

                if (otras.getNodoCase() != null) {
                    ctrlCase(otras.getNodoCase(), otras.getNodoId());
                }

                break;

            case 4: // print
                System.out.println("Detectamos un print");
                if (otras.getNodoExpresion().getNodoLiteral() == null) {

                }

                // codigo 3d

                break;

            case 5: // println
                System.out.println("Detectamos un println");
                // ctrlPrintln(otras.getNodoPrintln());

                break;

            case 6: // llamada_func

                ctrl_LlamadaFunc(otras.getNodoLlamadaFunc());

                break;

            case 7: // In

                // ¿?¿?¿?¿?¿?¿?

                // codigo 3d

                break;

            default:
                break;
        }

    }

    public void ctrlExp(NodoExpresion exp) {

        System.out.println("EXP log: "+exp.getNodoExpresionLog());
        System.out.println("EXP id: "+exp.getNodoId());
        // si tenemos un literal comprobamos que sea booleano
        System.out.println("Entro ctrlExp");
        System.out.println("");
        if (exp.getNodoLiteral() != null && exp.getNodoLiteral().getTipo() != Tipo.tsb_bool) {
            
            parser.report_error("El parametro literal no es booleano", exp.getNodoLiteral());

        } else if (exp.getNodoId() != null) {// tenemos un id
            System.out.println("Entramos en id");
            // comprobamos que el id exista en la tabla de simbolos
            if (ts.consultarTD(exp.getNodoId().getNombre()) == null) {
                parser.report_error("El parametro id no existe", exp.getNodoId());
            }

            Dvar d3 = (Dvar) ts.consultarTD(exp.getNodoId().getNombre());

            // si existe comprobamos que sea booleano
            if (d3.getTipus() != Tipo.tsb_bool) {
                parser.report_error("El parametro id no es booleano", exp.getNodoId());
            }

        } else if (exp.getNodoExpresionArit() != null) {

            parser.report_error("La expresion no es booleana", exp.getNodoExpresionArit());

        } else if (exp.getNodoExpresionLog() != null) {
            // Habria que comprobar que el tipo en conjunto sea logico
            ctrlExpLog(exp.getNodoExpresionLog());

        } else if (exp.getNodoExpresionConNegacion() != null) {
            ctrlExp(exp.getNodoExpresionConNegacion());

        } else if (exp.getNodoLlamadaFunc() != null) {
            ctrl_LlamadaFunc(exp.getNodoLlamadaFunc());
        }
    }

    // public void ctrlParams(NodoExpresion params) {

    //     System.out.println("Entro ctrlParams");
    //     System.out.println(params.getNodoExpresionLog());
    //     if (params.getNodoParamSimple() != null) {// significa que tenemos solo un parametro

    //         // si tenemos un literal comprobamos que sea booleano
    //         if (params.getNodoParamSimple().getNodoExpresion().getNodoLiteral() != null && params.getNodoParamSimple().getNodoExpresion().getNodoLiteral().getTipo() != Tipo.tsb_bool) {

    //             parser.report_error("El parametro literal no es booleano",params.getNodoParamSimple().getNodoExpresion().getNodoLiteral());

    //         } else if (params.getNodoParamSimple().getNodoExpresion().getNodoId() != null) {

    //             System.out.println("Entramos en id");
    //             // tenemos un id
    //             // comprobamos que el id exista en la tabla de simbolos
    //             if (ts.consultarTD(params.getNodoParamSimple().getNodoExpresion().getNodoId().getNombre()) == null) {

    //                 parser.report_error("El parametro id no existe", params.getNodoParamSimple().getNodoExpresion().getNodoId());
    //             }

    //             Dvar d3 = (Dvar) ts.consultarTD(params.getNodoParamSimple().getNodoExpresion().getNodoId().getNombre());

    //             // si existe comprobamos que sea booleano
    //             if (d3.getTipus() != Tipo.tsb_bool) {
    //                 parser.report_error("El parametro id no es booleano",
    //                         params.getNodoParamSimple().getNodoExpresion().getNodoId());
    //             }

    //         } else if (params.getNodoParamSimple().getNodoExpresion().getNodoExpresionArit() != null) {

    //             parser.report_error("La expresion no es booleana",
    //                     params.getNodoParamSimple().getNodoExpresion().getNodoExpresionArit());

    //         } else if (params.getNodoParamSimple().getNodoExpresion().getNodoExpresionLog() != null) {
                
    //             // Habria que comprobar que el tipo en conjunto sea logico
    //             ctrlExpLog(params.getNodoParamSimple().getNodoExpresion().getNodoExpresionLog());

    //         } else if (params.getNodoParamSimple().getNodoExpresion().getNodoExpresionConNegacion() != null) {

    //             ctrlExp(params.getNodoParamSimple().getNodoExpresion().getNodoExpresionConNegacion());

    //         } else if (params.getNodoParamSimple().getNodoExpresion().getNodoExpresionConParentesis() != null) {
    //             // Parece ser una troleada espectacular
    //         } else if (params.getNodoParamSimple().getNodoExpresion().getNodoLlamadaFunc() != null) {

    //             ctrl_LlamadaFunc(params.getNodoParamSimple().getNodoExpresion().getNodoLlamadaFunc());

    //         }
            
    //     } else if (params.getNodoParamCompuesto() != null) { // parametros compuestos, no dejamos

    //         parser.report_error("No se pueden usar parametros compuestos", params.getNodoParamCompuesto());

    //     }
    // }

    public void ctrlExpLog(NodoExpresionLog nodoLog) {
        // Habria que comprobar que el tipo en conjunto sea logico
        // AND OR
        // si o si T1 y T2 tienen que ser booleanos
        // comprobar que el tipo
        if (nodoLog.getOpLog().getTipusOpLog() == TipoLog.AND || nodoLog.getOpLog().getTipusOpLog() == TipoLog.OR) {

            if (nodoLog.getTermino1().getNodoId() != null) {
                // verificamos que exista el id
                if (ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre()) == null) {
                    parser.report_error("El parametro id no existe", nodoLog.getTermino1().getNodoId());
                }

                // si existe comprobamos que sea booleano
                Dvar d3 = (Dvar) ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre());

                // si existe comprobamos que sea booleano
                if (d3.getTipus() != Tipo.tsb_bool) {
                    parser.report_error("El parametro id no es booleano", nodoLog.getTermino1().getNodoId());
                }
            }

            if (nodoLog.getTermino1().getNodoLlamadaFunc() != null) {
                // termino 1 es una llamada a funcion

                // tengo que verificar que esta funcion existe en la ts y que el tipo de retorno
                // es booleano
                if (ts.consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("La funcion no existe", nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }

                // si existe comprobamos que sea su return sea booleano
                DFunc d3 = (DFunc) ts.consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre());

                if (d3.getTipo() != Tipo.tsb_bool) {
                    parser.report_error("El return de la llamada a función no es booleana ",
                            nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }
            }

            if (nodoLog.getTermino2().getNodoId() != null) {
                // verificamos que exista el id
                if (ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre()) == null) {
                    parser.report_error("El parametro id no existe", nodoLog.getTermino2().getNodoId());
                }

                // si existe comprobamos que sea booleano
                Dvar d3 = (Dvar) ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre());

                // si existe comprobamos que sea booleano
                if (d3.getTipus() != Tipo.tsb_bool) {
                    parser.report_error("El parametro id no es booleano", nodoLog.getTermino2().getNodoId());
                }
            }

            if (nodoLog.getTermino2().getNodoLlamadaFunc() != null) {
                // termino 1 es una llamada a funcion

                // tengo que verificar que esta funcion existe en la ts y que el tipo de retorno
                // es booleano
                if (ts.consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("La funcion no existe", nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId());
                }

                // si existe comprobamos que sea su return sea booleano
                DFunc d3 = (DFunc) ts.consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre());

                if (d3.getTipo() != Tipo.tsb_bool) {
                    parser.report_error("El return de la llamada a función no es booleana ",
                            nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId());
                }
            }

            // si llegamos aqui es porque el termino 1 y el termino 2 son bool

            // creo que lo que sea necesario

        } else if (nodoLog.getOpLog().getTipusOpLog() == TipoLog.MAYOR
                || nodoLog.getOpLog().getTipusOpLog() == TipoLog.MENOR
                || nodoLog.getOpLog().getTipusOpLog() == TipoLog.IGUALMAYOR
                || nodoLog.getOpLog().getTipusOpLog() == TipoLog.IGUALMENOR) {// si es < > <= >= solo puede ser int o
                                                                              // float o char

            // caso de que sean id
            if (nodoLog.getTermino1().getNodoId() != null) {
                // comprobamos que existan los id
                System.out.println("El id es: " + nodoLog.getTermino1().getNodoId().getNombre());
                
                if (ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino1().getNodoId());
                }

                Dvar termino1 = (Dvar) ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre());
                // verificamos que el tipo del id sea int o float
                if (termino1.getTipus() != Tipo.tsb_int || termino1.getTipus() != Tipo.tsb_float) {
                    parser.report_error("El termino introducido no puede compararse",
                            nodoLog.getTermino1().getNodoId());
                }
            } else if (nodoLog.getTermino1().getNodoLlamadaFunc() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }

                DFunc termino1 = (DFunc) ts
                        .consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre());
                // verificamos que el retorno de la función sea int o float
                if (termino1.getTipo() != Tipo.tsb_int || termino1.getTipo() != Tipo.tsb_float) {
                    parser.report_error("El termino introducido no puede compararse",
                            nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }
            }

            if (nodoLog.getTermino2().getNodoId() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino2().getNodoId());
                }

                Dvar termino2 = (Dvar) ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre());
                // verificamos que el tipo del id sea int o float
                if (termino2.getTipus() != Tipo.tsb_int || termino2.getTipus() != Tipo.tsb_float) {
                    parser.report_error("El termino introducido no puede compararse",
                            nodoLog.getTermino2().getNodoId());
                }
            } else if (nodoLog.getTermino2().getNodoLlamadaFunc() != null) {
                // comprobamos que existan las llamadas a función
                if (ts.consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId());
                }

                DFunc termino2 = (DFunc) ts
                        .consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre());
                // verificamos que el retorno de la función sea int o float
                if (termino2.getTipo() != Tipo.tsb_int || termino2.getTipo() != Tipo.tsb_float) {
                    parser.report_error("El termino introducido no puede compararse",
                            nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }
            }

            // Termino t1 y t2 son int o float
        } else if (nodoLog.getOpLog().getTipusOpLog() == TipoLog.IGUALIGUAL) {

            Dvar termino1 = null;
            DFunc termino1Func = null;
            Dvar termino2 = null;
            DFunc termino2Func = null;
            // caso de que sean id
            if (nodoLog.getTermino1().getNodoId() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino1().getNodoId());
                }

                termino1 = (Dvar) ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre());

            } else if (nodoLog.getTermino1().getNodoLlamadaFunc() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("La funcion no existe", nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }

                termino1Func = (DFunc) ts
                        .consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre());
            }

            if (nodoLog.getTermino2().getNodoId() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino2().getNodoId());
                }

                termino2 = (Dvar) ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre());

            } else if (nodoLog.getTermino2().getNodoLlamadaFunc() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("La funcion no existe", nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId());
                }

                termino2Func = (DFunc) ts
                        .consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre());
            }

            if (termino1 != null) {

                if (termino2 != null) {

                    if (termino1.getTipus() != termino2.getTipus()) {

                        parser.report_error("Los tipos de los terminos no son iguales", nodoLog);

                    }

                    // codigo 3d

                } else if (termino2Func != null) {

                    if (termino1.getTipus() != termino2Func.getTipo()) {

                        parser.report_error("Los tipos de los terminos no son iguales", nodoLog);

                    }

                    // codigo 3d

                }

            } else if (termino1Func != null) {

                if (termino2 != null) {

                    if (termino1Func.getTipo() != termino2.getTipus()) {

                        parser.report_error("Los tipos de los terminos no son iguales", nodoLog);

                    }

                    // codigo 3d

                } else if (termino2Func != null) {

                    if (termino1Func.getTipo() != termino2Func.getTipo()) {

                        parser.report_error("Los tipos de los terminos no son iguales", nodoLog);

                    }

                    // codigo 3d

                }

            }

        } else if (nodoLog.getOpLog().getTipusOpLog() == TipoLog.DIFERENTE) {

            Dvar termino1 = null;
            DFunc termino1f = null;
            Dvar termino2 = null;
            DFunc termino2f = null;

            if (nodoLog.getTermino1().getNodoId() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino1().getNodoId());
                }

                termino1 = (Dvar) ts.consultarTD(nodoLog.getTermino1().getNodoId().getNombre());
            } else if (nodoLog.getTermino1().getNodoLlamadaFunc() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }
                termino1f = (DFunc) ts.consultarTD(nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId().getNombre());
            }

            if (nodoLog.getTermino2().getNodoId() != null) {
                // comprobamos que existan los id
                if (ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino2().getNodoId());
                }

                termino2 = (Dvar) ts.consultarTD(nodoLog.getTermino2().getNodoId().getNombre());
            } else if (nodoLog.getTermino2().getNodoLlamadaFunc() != null) {
                // comprobamos que existan las llamadas a función
                if (ts.consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre()) == null) {
                    parser.report_error("El id no existe", nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId());
                }

                termino2f = (DFunc) ts.consultarTD(nodoLog.getTermino2().getNodoLlamadaFunc().getNodoId().getNombre());
            }
            if (termino1 != null && termino2 != null) {
                if (termino1.getTipus() != termino2.getTipus()) {
                    parser.report_error("Los terminos no son iguales", nodoLog.getTermino1().getNodoId());
                }
            } else if (termino1f != null && termino2 != null) {
                if (termino1f.getTipo() != termino2.getTipus()) {
                    parser.report_error("Los terminos no son iguales",
                            nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }
            } else if (termino1 != null && termino2f != null) {
                if (termino1.getTipus() != termino2f.getTipo()) {
                    parser.report_error("Los terminos no son iguales", nodoLog.getTermino1().getNodoId());
                }
            } else {
                if (termino1f.getTipo() != termino2f.getTipo()) {
                    parser.report_error("Los terminos no son iguales",
                            nodoLog.getTermino1().getNodoLlamadaFunc().getNodoId());
                }
            }
        }
    }

    public void ctrlOpLog(NodoOpLog nodoOpLog) {

        switch (nodoOpLog.getTipusOpLog()) { // supongo que es crear lo del c3d
            case AND:
                break;
            case DIFERENTE:
                break;
            case IGUALIGUAL:
                break;
            case IGUALMAYOR:
                break;
            case IGUALMENOR:
                break;
            case MAYOR:
                break;
            case MENOR:
                break;
            case OR:
                break;
            default:
                break;

        }

    }

    public void ctrl_LlamadaFunc(NodoLlamadaFunc llamadaFunc) {

        // comprobar que el id de la funcion existe
        if (ts.consultarTD(llamadaFunc.getNodoId().getNombre()) == null) {
            parser.report_error("La funcion no existe", llamadaFunc);
        }

        // comprobar que los parametros pasados son iguales a los parametros aceptados
        // por la funcion
        // ?¿?¿?¿?¿?¿?

        // un poco trol que haya q tener parametros si o si en una funcion
        if (llamadaFunc.getNodoParams() != null) {
            // comprobar que el numero de parametros pasados es igual al de la funcion
            int nParamsFunc = 0;

            // si el parametro es simple solo compruebo uno y si es compuesto compruebo
            // todos
            // el numero de parametros pasados tiene que ser igual al numero de parametros
            // de la funcion
            if (llamadaFunc.getNodoParams().getNodoParamSimple() != null) {
                // Solo hay un parametro
                // es una expresion

                // si es un literal
                if (llamadaFunc.getNodoParams().getNodoParamSimple().getNodoExpresion().getNodoLiteral() != null) {

                    Tipo llamada = llamadaFunc.getNodoParams().getNodoParamSimple().getNodoExpresion().getNodoLiteral()
                            .getTipo();

                    // comprobar que el tipo de de la llamada es igual al tipo de la funcion

                }

                // si es un id
                if (llamadaFunc.getNodoParams().getNodoParamSimple().getNodoExpresion().getNodoId() != null) {
                    Dvar d = (Dvar) ts.consultarTD(llamadaFunc.getNodoParams().getNodoParamSimple().getNodoExpresion()
                            .getNodoId().getNombre());
                    // Si es nulo no existe
                    if (d == null) {
                        parser.report_error("El id no existe",
                                llamadaFunc.getNodoParams().getNodoParamSimple().getNodoExpresion().getNodoId());
                    }
                }

            } else if (llamadaFunc.getNodoParams().getNodoParamCompuesto() != null) {
                // Hay mas de un parametro

            }
        }

    }

    public void ctrlCase(NodoCase nodoCase, NodoId idSwitch) {

        // Descripcion del switch
        Dvar d3 = (Dvar) ts.consultarTD(idSwitch.getNombre());

        // si hay mas casos los recorro
        if (nodoCase.getNodoCase() != null) {
            ctrlCase(nodoCase.getNodoCase(), idSwitch);
        }

        // comrpobar que el id del switch es igual al id del case
        if (d3.getTipus() != nodoCase.getNodoInitCases().getTipo()) {
            parser.report_error("El tipo del id del switch no es igual al tipo del case", nodoCase);
        }
        // gestionar sents
        if (nodoCase.getNodoSents() != null) {
            ctrlSents(nodoCase.getNodoSents());
        }

        // gestionar el default
        if (nodoCase.getNodoCaseDefault() != null) {
            // si hay sents las gestiono
            if (nodoCase.getNodoCaseDefault().getNodoSents() != null) {
                ctrlSents(nodoCase.getNodoCaseDefault().getNodoSents());
            }
        }

    }

    public void ctrlOpRapidos(NodoOpRapidos opRapidos) {

        if (opRapidos.getNodoOpRapidosSuma() != null) {

            // compruebo que el id existe en la tabla de simbolos
            if (ts.consultarTD(opRapidos.getNodoOpRapidosSuma().getNodoId().getNombre()) == null) {
                parser.report_error("No existe el id", opRapidos);
            }

            // compruebo que el id sea int

            Dvar d3 = (Dvar) ts.consultarTD(opRapidos.getNodoOpRapidosSuma().getNodoId().getNombre());

            if (d3.getTipus() != Tipo.tsb_int) {
                parser.report_error("No se puede aplicar un incremento a un no int", opRapidos);
            }

        } else {

            // compruebo que el id existe en la tabla de simbolos
            if (ts.consultarTD(opRapidos.getNodoOpRapidosResta().getNodoId().getNombre()) == null) {
                parser.report_error("No existe el id", opRapidos);
            }

            // compruebo que el id sea int

            Dvar d3 = (Dvar) ts.consultarTD(opRapidos.getNodoOpRapidosResta().getNodoId().getNombre());

            if (d3.getTipus() != Tipo.tsb_int) {
                parser.report_error("No se puede aplicar un decremento a un no int", opRapidos);
            }
        }
    }

    public void ctrlElseSent(NodoElse elseSent) {

        // else
        if (elseSent.getNodoExpresion() == null) {
            System.out.println("Detectamos un else");
            if (elseSent.getNodoSents() != null) {
                ctrlSents(elseSent.getNodoSents());
            }

        } else {// elseif

            System.out.println("Detectamos un elseif");
            // verifico que expresion sea bool
            if (elseSent.getNodoExpresion().getNodoLiteral() != null
                    && (elseSent.getNodoExpresion().getNodoLiteral().getTipo() != Tipo.tsb_bool)) {
                parser.report_error("El parametro no es booleano", elseSent);

            }else{
                System.out.println("otro tipo param");
                
            }

            // compruebo las sentencias
            if (elseSent.getNodoSents() != null) {
                ctrlSents(elseSent.getNodoSents());
            }

            // compruebo el else
            if (elseSent.getNodoElse() != null) {
                ctrlElseSent(elseSent.getNodoElse());
            }

        }
    }

    public void ctrlRealAsign(NodoRealAsign realAsign) {
        // id = expresion ;
        NodoId id = realAsign.getNodoId();
        NodoExpresion expresion = realAsign.getNodoExpresion();

        // comprobar que el id existe en la tabla de simbolos
        if (ts.consultarTD(id.getNombre()) == null) {
            parser.report_error("No existe el id", realAsign);
        }

        Descripcion d = ts.consultarTD(id.getNombre().toString());
        Tipo var;

        if (d.getTDescripcion() == Descripcion.TDesc.darray.toString()) {
            Darray dt = (Darray) ts.consultarTD(id.getNombre().toString());
            var = dt.getTipus();
        } else {
            Dvar dt = (Dvar) ts.consultarTD(id.getNombre().toString()); // tipo del id
            var = dt.getTipus();
        }
        // comprobar que el tipo de la expresion es compatible con el tipo del id y ver
        // si es una constante o una variable

        if (expresion.getNodoLiteral() != null) {
            Tipo tipoExpr = expresion.getNodoLiteral().getTipo();

            if (var == Tipo.tsb_int || var == Tipo.tsb_float) {
                if (tipoExpr != Tipo.tsb_int && var != Tipo.tsb_float) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var == Tipo.tsb_char) {
                if (tipoExpr != Tipo.tsb_char && tipoExpr != Tipo.tsb_int) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var != tipoExpr) {
                parser.report_error("Tipos incompatibles", expresion);
            }

        } else if (expresion.getNodoId() != null) {

            Descripcion d1 = ts.consultarTD(expresion.getNodoId().getNombre().toString());
            Tipo tipoExpr;

            if (d1.getTDescripcion() == Descripcion.TDesc.dconst.toString()) {
                DConst dt = (DConst) ts.consultarTD(expresion.getNodoId().getNombre().toString());
                tipoExpr = dt.getTipo();
            } else {
                Dvar dt = (Dvar) ts.consultarTD(expresion.getNodoId().getNombre().toString()); // tipo del id
                tipoExpr = dt.getTipus();
            }

            if (tipoExpr != var) {
                parser.report_error("Tipos incompatibles", expresion.getNodoId());
            }

            if (var == Tipo.tsb_int || var == Tipo.tsb_float) {
                if (tipoExpr != Tipo.tsb_int && var != Tipo.tsb_float) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var == Tipo.tsb_char) {
                if (tipoExpr != Tipo.tsb_char && tipoExpr != Tipo.tsb_int) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var != tipoExpr) {
                parser.report_error("Tipos incompatibles", expresion);
            }

        } else if (expresion.getNodoLlamadaFunc() != null) {

            DFunc tipoExpr = (DFunc) ts.consultarTD(expresion.getNodoLlamadaFunc().getNodoId().getNombre());

            if (tipoExpr.getTipo() != var) {
                parser.report_error("Tipos incompatibles", expresion.getNodoLlamadaFunc().getNodoId());
            }

            if (var == Tipo.tsb_int || var == Tipo.tsb_float) {
                if (tipoExpr.getTipo() != Tipo.tsb_int && var != Tipo.tsb_float) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var == Tipo.tsb_char) {
                if (tipoExpr.getTipo() != Tipo.tsb_char && tipoExpr.getTipo() != Tipo.tsb_int) {
                    parser.report_error("Tipos incompatibles", expresion);
                }
            } else if (var != tipoExpr.getTipo()) {
                parser.report_error("Tipos incompatibles", expresion);
            }
        }
    }

    public void ctrlDeclFuncP(NodoDeclFuncP funcP) {

        // Puede ser que declare parametros o no
        NodoDeclFuncParams n = funcP.getNodoDeclFuncParams();
        while(n != null){
            ctrlParam(n.getNodoDeclFuncParam());
            n = n.getNodoDeclFuncParams();
        }
    }

    public void ctrlParam(NodoDeclFuncParam n){
        //Los metemos en la tabla de simbolos
        if(n.getNodoDeclArray() == null){
            System.out.println("Pongo la variable en ts: "+n.getNodoId().getNombre());
            ts.poner(n.getNodoId().getNombre(),new Dvar(0, n.getNodoTipo().getTipo()), n);
        }else{
            ts.poner(n.getNodoId().getNombre(),new Darray(0,n.getNodoTipo().getTipo(),dimensionArr(n.getNodoDeclArray()),false), n);
        }
    }

    public void ctrlDeclFuncParams(NodoDeclFuncParams funcParams) {

        // mete un solo parametro o mete mas de uno
        if (funcParams.getNodoDeclFuncParam() != null) {
            ctrlDeclFunParam(funcParams.getNodoDeclFuncParam());
        }
        if (funcParams.getNodoDeclFuncParams() != null) {
            ctrlDeclFuncParams(funcParams.getNodoDeclFuncParams());
        }
    }

    public void ctrlDeclFunParam(NodoDeclFuncParam funParam) {

        // compruebo tipo
        // si el tipo no es null, es que es un tipo de los que tengo en la tabla de
        // simbolos, si no error.
        if ((DTipus) ts.consultarTD(funParam.getNodoTipo().getTipo().toString()) == null) { // NO EXISTE EL TIPO O NO ES
                                                                                            // UN TIPO
            parser.report_error("No existe el tipo", funParam);
        }

        // Compruebo si es void, en ese caso error
        if (funParam.getNodoTipo().getTipo() == Tipo.tsb_void) {
            parser.report_error("No se puede utilizar parametros void", funParam);
        }

        // miro si hay un array o no
        if (funParam.getNodoDeclArray() != null) {
            // ctrlDeclArray(funParam.getNodoDeclArray());
        }

        // si todo bien meto el param en la ts

    }

    public void ctrlAsignArray() {

    }

    public void ctrlDimAray() {

    }

    public static TablaSimbolos getTs() {
        return ts;
    }
}
