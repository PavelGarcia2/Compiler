package semantico;

import sintactico.arbol.*;

import java.util.ArrayList;
import java.util.Stack;

import herramientas.Tipo;
import semantico.Operador3Direcciones.TipoCambio;
import semantico.Variable.TipoVar;
import sintactico.Parser;
import tsimbolos.*;
import tsimbolos.descripciones.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Semantico
 */
public class Semantico {
    private final Parser parser;
    private final NodoPrograma arbol;
    private static TablaSimbolos ts;
    private final TablaVariables tablaVariables;
    private final TablaProcedimientos tablaProcedimientos;
    private Codigo3Direcciones g;

    public Semantico(NodoPrograma arbol, Parser parser) {
        this.arbol = arbol;
        // creamos la tabla de simbolos y la inicializamos
        this.parser = parser;
        this.ts = new TablaSimbolos(parser);
        this.tablaVariables = new TablaVariables();
        this.tablaProcedimientos = new TablaProcedimientos();
        this.g = new Codigo3Direcciones(tablaVariables, tablaProcedimientos);

        inicializarTablaSimbolos();
        // runProgram();
    }

    private void inicializarTablaSimbolos() {
        // System.out.println("INIT TS");

        DTipus dv = new DTipus(Tipo.tsb_void, 0, 0);
        ts.poner("tsb_void", dv, null);

        // Inicializamos el bool
        DTipus d = new DTipus(Tipo.tsb_bool, -1, 0);
        ts.poner("tsb_bool", d, null);

        // Inicializamos el valor true
        DConst dC = new DConst(Tipo.tsb_bool, -1, "true", null);
        ts.poner("true", dC, null);

        // Inicializamos el valor false
        dC = new DConst(Tipo.tsb_bool, 0, "false", null);
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
        // System.out.println("HEMOS INIT TS");
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

            // obtenemos todas las declaraciones de procedures
            NodoDeclFunc funcList = arbol.getNodoDeclaracionFunciones();
            if (funcList != null && !funcList.isEmpty()) {
                ctrlDeclListFunciones(funcList);
            }

            g.genIntruccion(TipoInstruccion.CALL, new Operador3Direcciones("main"), null, null);
            ctrlMain(main);

            AtomicInteger i = new AtomicInteger(0);
            g.getIntrucciones().forEach(ins -> {
                System.out.println(i.getAndIncrement() + "\t" + ins.toString());
            });

        } else {
            parser.report_error("No hemos encontrado el main", main);
        }
    }

    public void ctrlDeclConstantes(NodoDeclConst constList) {

        while (constList != null) {
            ctrlConst(constList.getNodoConst());
            constList = constList.getNodoDeclConst();
        }
    }

    public void ctrlConst(NodoConst constante) {

        NodoSigno nodoSigno = constante.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                .getNodoExpresion().getNodoLiteral().getSigno();
        NodoTipo tipo = constante.getNodoTipo();
        NodoId id = constante.getNodoId();
        NodoAsignacion asignacion = constante.getNodoAsignacion();
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString());

        // Comprovar que tipo es un tipo
        if (dt == null) { // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("No existe el tipo", constante);
        }

        // Comprovar que el tipo es adecuado
        if ((dt.getTsb() != Tipo.tsb_int) && (dt.getTsb() != Tipo.tsb_char) && (dt.getTsb() != Tipo.tsb_bool)) {
            parser.report_error("No se pueden crear constantes de este tipo", constante);
        }

        // Primero miramos que nodo literal no sea null
        if (asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral() == null) {
            parser.report_error("Estas asignando un valor incorrecto", constante);
        }

        // Cogemos el tipo de literal
        Tipo type = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral()
                .getTipo();
        String valor = asignacion.getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion().getNodoLiteral()
                .getValor();

        // Mirar si el tipo de la izquierda y derecha son iguales
        if (type == Tipo.tsb_int) {
            if (dt.getTsb() == Tipo.tsb_bool) {
                parser.report_error("Estas asignando un valor de otro tipo", constante);
            }
        } else if (dt.getTsb() != type) {
            parser.report_error("Estas asignando un valor de otro tipo", constante);
        }

        // Si es un entero cogemos el signo
        int signo = 1;
        if (nodoSigno != null && type == Tipo.tsb_int) {
            signo = nodoSigno.getSigno();
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

        switch (dt.getTsb()) {
            case tsb_char:
                rango = rango * signo;
                if (rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()) {
                    parser.report_error("Has excedido los limites, char de dimensiones incorrectas.", constante);
                }
                break;
            case tsb_bool:
                if (rango != -1 && rango != 0) {
                    parser.report_error("Has excedido los limites, no es bool.", constante);
                }
                break;
            case tsb_int:
                rango = rango * signo;
                if (rango < dt.getLimiteInferior() || rango > dt.getLimiteSuperior()) {
                    parser.report_error("Has excedido los limites, int de dimensiones incorrectas.", constante);
                }
                break;
            default:
                parser.report_error("No permitimos crear constantes de esta forma", constante);
                break;
        }

        // C3@
        DConst dc = new DConst(dt.getTsb(), rango, id.getNombre(), id);
        ts.poner(id.getNombre(), dc, constante);
        System.out.println("GENERO NUEVA VARIABLE rango: " + rango);
        int nv = g.nuevaVariable(TipoVar.VARIABLE, dt.getTsb(), false);
        System.out.println("GENERO NUEVA VARIABLE nv: " + nv);
        System.out.println("GENERO NUEVA VARIABLE id: " + id.getNombre());
        id.setNv(nv);
        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(rango), null,
                new Operador3Direcciones(nv, false));
    }

    public void ctrlMain(NodoMain nodo) {

        // ?¿?¿?¿?¿
        ts.entrarBloque();
        // ts.entrarBloque();

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
                    int nv = g.nuevaVariable(TipoVar.VARIABLE, tipo.getTipo(), false);
                    Dvar d;
                    d = new Dvar(nv, dt.getTsb(), id);
                    id.setNv(nv);
                    ts.poner(id.getNombre(), d, id);
                    if(tipo.getTipo() == Tipo.tsb_bool || tipo.getTipo() == Tipo.tsb_int || tipo.getTipo() == Tipo.tsb_char){
                        g.genIntruccion(TipoInstruccion.COPIA,new Operador3Direcciones(0), null ,new Operador3Direcciones(nv,false));
                    }else if(tipo.getTipo() == Tipo.tsb_float){
                        g.genIntruccion(TipoInstruccion.COPIA,new Operador3Direcciones(0.0f), null ,new Operador3Direcciones(nv,false));
                    }else{
                         g.genIntruccion(TipoInstruccion.COPIA,new Operador3Direcciones("",0.0f), null ,new Operador3Direcciones(nv,false));
                    }
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
                        if (tipoDer != Tipo.tsb_char && tipoDer != Tipo.tsb_int) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    } else if (tipoIzq == Tipo.tsb_float || tipoIzq == Tipo.tsb_int) {
                        if (tipoDer != Tipo.tsb_float && tipoDer != Tipo.tsb_int) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    } else {
                        if (tipoIzq != tipoDer) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    }
                    String valor = var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                            .getNodoExpresion()
                            .getNodoLiteral().getValor();

                    // id.setNv(nv);
                    switch (tipoDer) {
                        case tsb_int:
                            int vali = Integer.parseInt(valor);
                            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(vali), null,
                                    new Operador3Direcciones(d.getnv(), false));
                            break;
                        case tsb_bool:
                            int valb = Integer.parseInt(valor);
                            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valb), null,
                                    new Operador3Direcciones(d.getnv(), false));
                            break;
                        case tsb_char:
                            int valc;
                            if (tipoDer != Tipo.tsb_int) {
                                valc = (int) (valor.charAt(1));
                            } else {
                                valc = Integer.parseInt(valor);
                            }
                            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valc), null,
                                    new Operador3Direcciones(d.getnv(), false));
                            break;
                        case tsb_float:
                            Float valf = Float.parseFloat(valor);
                            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valf), null,
                                    new Operador3Direcciones(d.getnv(), false));
                            break;
                        case tsb_str:
                            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valor, 0f), null,
                                    new Operador3Direcciones(d.getnv(), false));
                            break;
                    }

                } else if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                        .getNodoExpresion1() != null) {
                    Tipo tipoDer = ctrlExp(var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                            .getNodoExpresion(), false, true);
                    // si char izq , solo puede ser char o int der
                    if (tipoIzq == Tipo.tsb_char) {
                        if (tipoDer != Tipo.tsb_char && tipoDer != Tipo.tsb_int) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    } else if (tipoIzq == Tipo.tsb_float || tipoIzq == Tipo.tsb_int) {
                        if (tipoDer != Tipo.tsb_float && tipoDer != Tipo.tsb_int) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    } else {
                        if (tipoIzq != tipoDer) {
                            parser.report_error("Error estas asignando un valor incorrecto", var.getNodoAsignacion());
                        }
                    }
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                    .getNodoExpresion().getNv(), false), null,
                                    new Operador3Direcciones(d.getnv(), false));
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
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(dId.getnv(), false), null,
                                    new Operador3Direcciones(d.getnv(), false));
                }

            } else {
                // AQUI HACEMOS set arr[2] = valor;
                // marcador set array
                NodoDimArray dimension = var.getNodoDimArray();
                NodoDimArray antiguo;
                // System.out.println("Evaluando el array: "+var.getNodoId().getNombre());

                int dim = 0;

                do {
                    dim++;
                    if (dimension.getNodoExpresion().getNodoLiteral() != null) {
                        if (dimension.getNodoExpresion().getNodoLiteral().getTipo() != Tipo.tsb_int) {
                            parser.report_error("No se pueden asignar array de esta forma",
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
                    } else if (dimension.getNodoExpresion().getNodoExpresion1() != null) {
                        Tipo asign = ctrlExp(dimension.getNodoExpresion(), false, true);
                        if (asign != Tipo.tsb_int) {
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
                } else if (var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal().getNodoExpresion()
                        .getNodoExpresion1() != null) {
                    Tipo tipo = ctrlExp(var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                            .getNodoExpresion(), false, false);
                    if (tipo != d.getTipus()) {
                        parser.report_error("Valores no compatibles",
                                var.getNodoAsignacion().getNodoTipoAsignacion().getNodoAsignacionNormal()
                                        .getNodoExpresion()
                                        .getNodoExpresion1());
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
        int nv = g.nuevaVariable(TipoVar.VARIABLE, dt.getTsb(), true);
        Darray da = new Darray(nv, dt.getTsb(), dimIzq, true, bounds);
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
        int nv = 0;
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

                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_int, false);
                    id.setNv(nv);
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valor), null,
                            new Operador3Direcciones(nv, false));

                } else if (nodo.getNodoId() != null) {
                    // a = b, primero consultar tabla de simbolos b tiene que existir
                    // Si es un array no se puede

                    Descripcion desc = ts.consultarTD(nodo.getNodoId().getNombre());
                    if (desc == null) {
                        parser.report_error("Estas asignando un valor incorrecto, no existe", nodo);
                    }

                    if (desc.getTDescripcion() == Descripcion.TDesc.darray.toString()) {
                        parser.report_error("Estas asignando un valor incorrecto", nodo);
                    } else if (desc.getTDescripcion() == Descripcion.TDesc.dconst.toString()) {
                        DConst d3 = (DConst) ts.consultarTD(nodo.getNodoId().getNombre());

                        if (d3.getTipo() != Tipo.tsb_float && d3.getTipo() != Tipo.tsb_int) {
                            parser.report_error("Estas asignando un valor incorrecto, no es int", nodo);
                        }
                        nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_int, false);
                        id.setNv(nv);
                        g.genIntruccion(TipoInstruccion.COPIA,
                                new Operador3Direcciones(d3.getNodoId().getNv(), false), null,
                                new Operador3Direcciones(nv, false));
                    } else if (desc.getTDescripcion() == Descripcion.TDesc.dvar.toString()) {
                        Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());

                        if (d3.getTipus() != Tipo.tsb_float && d3.getTipus() != Tipo.tsb_int) {
                            parser.report_error("Estas asignando un valor incorrecto, no es int", nodo);
                        }
                        nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_int, false);
                        id.setNv(nv);
                        g.genIntruccion(TipoInstruccion.COPIA,
                                new Operador3Direcciones(d3.getnv(), false), null,
                                new Operador3Direcciones(nv, false));
                    }

                } else if (nodo.getNodoExpresion1() != null) {

                    // Tenemos que obtener el valor de esta expresion
                    Tipo tipo = ctrlExp(nodo, false, true);

                    if (tipo != Tipo.tsb_float && tipo != Tipo.tsb_int) {
                        parser.report_error("Estas asignando un valor incorrecto, no es int", nodo);
                    }

                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_int, false);
                    id.setNv(nv);
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(nodo.getNv(), false), null,
                            new Operador3Direcciones(nv, false));
                }

                d = new Dvar(nv, Tipo.tsb_int, id);
                ts.poner(id.getNombre(), d, id);
                break;

            case tsb_bool:
                // System.out.println("LLAMO A CTRL EXP CON NODO");
                // ctrlExp(nodo, false, true);
                System.out.println("ENTRO EN UNA ASIGNACION BOOLEANA");
                // si tipo de izquierda es bool comprobamos deerecha
                if (nodo.getNodoLiteral() != null) {
                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_bool) {
                        parser.report_error("Estas asignando un valor incorrecto, no es bool", nodo);
                    }

                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_bool, false);
                    id.setNv(nv);
                    d = new Dvar(nv, Tipo.tsb_bool, id);
                    ts.poner(id.getNombre(), d, id);
                    g.genIntruccion(TipoInstruccion.COPIA,
                            new Operador3Direcciones(Integer.parseInt(nodo.getNodoLiteral().getValor())), null,
                            new Operador3Direcciones(nv, false));

                } else if (nodo.getNodoId() != null) {
                    Descripcion d1 = ts.consultarTD(nodo.getNodoId().getNombre());
                    if (d1 == null) {
                        parser.report_error("Estas asignando un valor incorrecto, no existe", nodo);
                    }
                    if (d1.getTDescripcion() == Descripcion.TDesc.dvar.toString()) {
                        Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());

                        if (d3.getTipus() != dt.getTsb()) {
                            parser.report_error("Estas asignando un valor incorrecto, no es booleano",
                                    nodo);
                        }
                        nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_bool, false);
                        id.setNv(nv);
                        d = new Dvar(nv, Tipo.tsb_bool, id);
                        ts.poner(id.getNombre(), d, id);
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(d3.getnv(), false), null,
                                new Operador3Direcciones(nv, false));
                    } else {
                        DConst d3 = (DConst) ts.consultarTD(nodo.getNodoId().getNombre());

                        if (d3.getTipo() != dt.getTsb()) {
                            parser.report_error("Estas asignando un valor incorrecto, no es booleano",
                                    nodo);
                        }
                        nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_bool, false);
                        id.setNv(nv);
                        d = new Dvar(nv, Tipo.tsb_bool, id);
                        ts.poner(id.getNombre(), d, id);
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(d3.getNodoId().getNv(), false),
                                null,
                                new Operador3Direcciones(nv, false));
                    }

                } else if (nodo.getNodoExpresion1() != null) {
                    System.out.println("LLAMO A CTRL EXP");
                    ctrlExp(nodo, false, true);
                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_bool, false);
                    id.setNv(nv);
                    d = new Dvar(nv, Tipo.tsb_bool, id);
                    ts.poner(id.getNombre(), d, id);
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(nodo.getNv(), false), null,
                            new Operador3Direcciones(nv, false));
                }

                // EL 0 es provisional este 0 se tendra que pasar con c3a

                break;

            case tsb_char:

                if (nodo.getNodoLiteral() != null) {
                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_int
                            && nodo.getNodoLiteral().getTipo() != Tipo.tsb_char) {
                        parser.report_error("Estas asignando un valor incorrecto, no es char", nodo);
                    }

                    System.out.println("ERROR");
                    if (nodo.getNodoLiteral().getValor().length() > 1) {
                        valor = (int) (nodo.getNodoLiteral().getValor().charAt(1));
                    } else {
                        valor = Integer.parseInt(nodo.getNodoLiteral().getValor());
                    }

                    if (valor < dt.getLimiteInferior() || valor > dt.getLimiteSuperior()) {
                        parser.report_error("Has excedido los limites", nodo);
                    }

                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_char, false);
                    id.setNv(nv);
                    d = new Dvar(nv, Tipo.tsb_char, id);
                    ts.poner(id.getNombre(), d, id);
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valor), null,
                            new Operador3Direcciones(nv, false));

                } else if (nodo.getNodoId() != null) {
                    Descripcion d1 = ts.consultarTD(nodo.getNodoId().getNombre());
                    if (d1 == null) {
                        parser.report_error("Estas asignando un valor incorrecto la variable no existe", nodo);
                    }

                    if (d1.getTDescripcion() == Descripcion.TDesc.dvar.toString()) {

                        Dvar d3 = (Dvar) d1;
                        if (d3.getTipus() != Tipo.tsb_int && d3.getTipus() != Tipo.tsb_char) {
                            parser.report_error("Estas asignando un valor incorrecto, no es int o char", nodo);
                        }

                        nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_char, false);
                        d = new Dvar(nv, Tipo.tsb_char, id);
                        d.getNodoId().setNv(nv);
                        ts.poner(id.getNombre(), d, id);
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(d3.getnv(), false),
                                null,
                                new Operador3Direcciones(nv, false));

                    } else if (d1.getTDescripcion() == Descripcion.TDesc.dconst.toString()) {
                        DConst d3 = (DConst) d1;
                        if (d3.getTipo() != Tipo.tsb_int && d3.getTipo() != Tipo.tsb_char) {
                            parser.report_error("Estas asignando un valor incorrecto, no es int o char", nodo);
                        }

                        nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_char, false);
                        d = new Dvar(nv, Tipo.tsb_char, id);
                        d.getNodoId().setNv(nv);
                        ts.poner(id.getNombre(), d, id);
                        g.genIntruccion(TipoInstruccion.COPIA,
                                new Operador3Direcciones(d3.getNodoId().getNv(), false),
                                null,
                                new Operador3Direcciones(nv, false));

                    }

                } else if (nodo.getNodoExpresion1() != null) {

                    Tipo tipo = ctrlExp(nodo, false, true);
                    if (tipo != Tipo.tsb_int && tipo != Tipo.tsb_char) {
                        parser.report_error("Estas asignando un valor incorrecto", nodo);
                    }

                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_char, false);
                    d = new Dvar(nv, Tipo.tsb_char, id);
                    d.getNodoId().setNv(nv);
                    ts.poner(id.getNombre(), d, id);
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(nodo.getNv(), false),
                            null,
                            new Operador3Direcciones(nv, false));
                }

                break;

            case tsb_float:

                if (nodo.getNodoLiteral() != null) {
                    // int b =3;
                    // float a = b;
                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_int
                            && nodo.getNodoLiteral().getTipo() != Tipo.tsb_float) {
                        parser.report_error("Estas asignando un valor incorrecto, no es float", nodo);
                    }

                    float val;
                    val = Float.parseFloat(nodo.getNodoLiteral().getValor());

                    if (val < dt.getLimiteInferior() && val > dt.getLimiteSuperior()) {
                        parser.report_error("Has excedido los limites", nodo);
                    }

                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_float, false);
                    id.setNv(nv);
                    d = new Dvar(nv, Tipo.tsb_float, id);
                    ts.poner(id.getNombre(), d, id);
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(val), null,
                            new Operador3Direcciones(nv, false));

                } else if (nodo.getNodoId() != null) {

                    // set a=b
                    Descripcion d1 = ts.consultarTD(nodo.getNodoId().getNombre());
                    if (d1 == null) {
                        parser.report_error("Estas asignando un valor incorrecto la variable no existe", nodo);
                    } else if (d1.getTDescripcion() == Descripcion.TDesc.dvar.toString()) {
                        Dvar d3 = (Dvar) ts.consultarTD(nodo.getNodoId().getNombre());
                        System.out.println(nodo.getNodoId().getNombre());
                        System.out.println(d3.getTipus());
                        if (d3.getTipus() != Tipo.tsb_int && d3.getTipus() != Tipo.tsb_float) {
                            parser.report_error("Estas asignando un valor incorrecto, no es float", nodo);
                        }

                        nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_float, false);
                        d = new Dvar(nv, Tipo.tsb_float, id);
                        d.getNodoId().setNv(nv);
                        ts.poner(id.getNombre(), d, id);
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(d3.getnv(), false),
                                null,
                                new Operador3Direcciones(nv, false));
                    } else {
                        parser.report_error("Estas asignando un valor incorrecto", nodo);
                    }

                } else if (nodo.getNodoExpresion1() != null) {
                    Tipo tipo = ctrlExp(nodo, false, true);
                    if (tipo != Tipo.tsb_int && tipo != Tipo.tsb_float) {
                        parser.report_error("Estas asignando un valor incorrecto", nodo);
                    }
                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_float, false);
                    d = new Dvar(nv, Tipo.tsb_float, id);
                    d.getNodoId().setNv(nv);
                    ts.poner(id.getNombre(), d, id);
                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(nodo.getNv(), false),
                            null,
                            new Operador3Direcciones(nv, false));
                }

                break;

            case tsb_str:
                if (nodo.getNodoLiteral() != null) {
                    if (nodo.getNodoLiteral().getTipo() != Tipo.tsb_str) {
                        parser.report_error("Estas asignando un valor incorrecto, no es str", nodo);
                    }
                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_str, false);
                    id.setNv(nv);
                    System.out.println("NOMBRE: " + id.getNombre());
                    System.out.println("NOMBRE: " + id.getNv());
                    d = new Dvar(nv, Tipo.tsb_str, id);
                    ts.poner(id.getNombre(), d, id);
                    g.genIntruccion(TipoInstruccion.COPIA,
                            new Operador3Direcciones(nodo.getNodoLiteral().getValor(), 1.0f), null,
                            new Operador3Direcciones(nv, false));
                } else if (nodo.getNodoId() != null) {
                    Descripcion d1 = ts.consultarTD(nodo.getNodoId().getNombre());

                    if (d1 == null) {
                        parser.report_error("Estas asignando un valor incorrecto la variable no existe", nodo);
                    }
                    Dvar d3 = (Dvar) d1;
                    if (d3.getTipus() != dt.getTsb()) {
                        parser.report_error("Estas asignando un valor incorrecto, no es str", nodo);
                    }

                    nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_str, false);
                    id.setNv(nv);
                    d = new Dvar(nv, Tipo.tsb_str, id);
                    ts.poner(id.getNombre(), d, id);

                    g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(d3.getnv(), false), null,
                            new Operador3Direcciones(nv, false));

                } else if (nodo.getNodoExpresion1() != null) {
                    // Tipo tipo = ctrlExp(nodo, false, true);
                    // if (tipo != dt.getTsb()) {
                    // parser.report_error("Estas asignando un valor incorrecto", nodo);
                    // }
                }
                break;
            default:
                System.out.println("Hay un error ctrlAsignNormal");
                break;
        }

    }

    public void ctrlDeclListFunciones(NodoDeclFunc funcList) {
        //Enfoque iterativo
        // while (funcList != null) {
        //     ctrlFunc(funcList.getNodoFunc());
        //     funcList = funcList.getNodoDeclFunc();
        // }
        //Enfoque recursivo
        if(funcList.getNodoDeclFunc() != null){
            ctrlDeclListFunciones(funcList.getNodoDeclFunc());
        }
        if(funcList.getNodoFunc()!=null){
            ctrlFunc(funcList.getNodoFunc());
        }
    }

    public Tipo ctrlFunc(NodoFunc func) {

        // comprobar el tipo
        System.out.println("INIT CTRL FUNC");
        NodoTipo tipo = func.getNodoTipo(); // cojo el tipo del nodo que me llega
        DTipus dt = (DTipus) ts.consultarTD(tipo.getTipo().toString()); // consulto dicho tipo en la tabla de simbolos
        // si el tipo no es null, es que es un tipo de los que tengo en la tabla de
        // simbolos, si no error.
        g.pushFuncionActual(func.getNodoId().getNombre());
        String etiqueta = g.nuevaEtiqueta();
        int np = g.nuevoProcedimiento(func.getNodoId().getNombre(), ts.getAmbito(), etiqueta, 0);
       

        if (dt == null) { // NO EXISTE EL TIPO O NO ES UN TIPO
            parser.report_error("No existe el tipo", func);
        }

        if (tipo.getTipo() != Tipo.tsb_void && func.getNodoReturn() == null) {
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
        g.genIntruccion(TipoInstruccion.PREAMBULO, null, null, new Operador3Direcciones(id.getNombre()));
        g.genIntruccion(TipoInstruccion.SKIP, null, null, new Operador3Direcciones(etiqueta));

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
        if (tipo.getTipo() == Tipo.tsb_void && ret != null) {
            parser.report_error("No se debe devolver nada!", ret);
        }
        int retval = -1;
        if (ret != null && !ret.isEmpty()) {
            retval = ctrlReturn(ret, func.getNodoTipo(),np);
        }else{
            g.genIntruccion(TipoInstruccion.RETURN, null, null, new Operador3Direcciones(np,true));
        }

        ts.salirBloque();
        DFunc dfunc = new DFunc(np, func.getNodoTipo().getTipo(), func,retval);
        dfunc.setNp(np);
        ts.poner(id.getNombre(), dfunc, func);
        return tipo.getTipo() ;
    }

    public int ctrlReturn(NodoReturn ret, NodoTipo tipo, int np) {

        NodoReturnParam paramReturn = ret.getNodoReturnParam();
        int nv = -1;
        if (paramReturn.getNodoId() != null) {
            // Primero miramos que exista
            Descripcion d = ts.consultarTD(paramReturn.getNodoId().getNombre());
            if (d == null) {
                parser.report_error("No existe la variable", paramReturn.getNodoId());
            }
            // Si existe miramos que el tipo sea el mismo que el del return
            if (d.getTDescripcion() == Descripcion.TDesc.dvar.toString()) {
                Dvar d1 = (Dvar) d;
                if (d1.getTipus() != tipo.getTipo()) {
                    parser.report_error("Tipos incompatibles", paramReturn.getNodoId());
                }
                nv = d1.getNodoId().getNv();
            } else { // Es un array
                Darray d1 = (Darray) d;
                if (d1.getTipus() != tipo.getTipo()) {
                    parser.report_error("Tipos incompatibles", paramReturn.getNodoId());
                }
            }
            g.genIntruccion(TipoInstruccion.RETURN, new Operador3Direcciones(nv,false), null, new Operador3Direcciones(np,true));
            return nv;
        } else { // Es un literal
            // Si es un literal basta con coger su tipo
            // if (paramReturn.getNodoLiteral().getTipo() != tipo.getTipo()) {
            //     parser.report_error("Tipos incompatibles", paramReturn.getNodoLiteral());
            // }
            parser.report_error("Se debe devolver un id", paramReturn.getNodoLiteral());
        }
        return nv;
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
        //Enfoque iterativo
        // while (sents != null) {
        //     ctrlSent(sents.getNodoSent());
        //     sents = sents.getNodoSents();
        // }
        //Enfoque recursivo
        if(sents.getNodoSents() != null){
            ctrlSents(sents.getNodoSents());
        }
        if(sents.getNodoSent()!=null){
            ctrlSent(sents.getNodoSent());
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
                    ctrlExp(otras.getNodoExpresion(), false, true);
                }

               
                String etiqueta = g.nuevaEtiqueta();
                
                g.genIntruccion(TipoInstruccion.IFTRUEGOTO, 
                new Operador3Direcciones(otras.getNodoExpresion().getNv(),false),
                new Operador3Direcciones(0, TipoCambio.INT), 
                new Operador3Direcciones(etiqueta));

                String etiquetaS = g.nuevaEtiqueta();
                g.genIntruccion(TipoInstruccion.GOTO,null,null, new Operador3Direcciones(etiquetaS));
                 g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(etiqueta));
                // comprobamos las sentencias
                if (otras.getNodoSents() != null) {
                    ctrlSents(otras.getNodoSents());
                }
                
                g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(etiquetaS));
                // comprobamos el else
                if (otras.getNodoElse() != null) {
                    ctrlElseSent(otras.getNodoElse());
                }
                

                break;

            case 1: // while

                String etiquetaPreWhile = g.nuevaEtiqueta();
                g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(etiquetaPreWhile));
                
                if (otras.getNodoExpresion() != null) {
                    ctrlExp(otras.getNodoExpresion(), false, true);
                }

                String sentsWhile = g.nuevaEtiqueta();
                String etiquetaPostWhile = g.nuevaEtiqueta();
                g.genIntruccion(TipoInstruccion.IFTRUEGOTO, 
                new Operador3Direcciones(otras.getNodoExpresion().getNv(),false),
                new Operador3Direcciones(0, TipoCambio.INT), 
                new Operador3Direcciones(sentsWhile));

                g.genIntruccion(TipoInstruccion.GOTO,null,null, new Operador3Direcciones(etiquetaPostWhile));
                g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(sentsWhile));
                // comprobamos las sentencias
                if (otras.getNodoSents() != null) {
                    ctrlSents(otras.getNodoSents());
                }

                g.genIntruccion(TipoInstruccion.GOTO,null,null, new Operador3Direcciones(etiquetaPreWhile));
                g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(etiquetaPostWhile));

                break;

            case 2: // for

                // compruebo que el id existe en la tabla de simbolos
                if (ts.consultarTD(otras.getNodoId().getNombre()) == null) {
                    parser.report_error("No existe el id", otras);
                }

                Descripcion d2 = ts.consultarTD(otras.getNodoId().getNombre());

                int nv2 = -1;
                if(Descripcion.TDesc.dvar.toString() == d2.getTDescripcion()){
                    //caso variable
                    Dvar d1 = (Dvar) d2;
                    nv2= d1.getNodoId().getNv();
                }else{
                    //caso constante
                    DConst dconst = (DConst) d2;
                    nv2 = dconst.getNodoId().getNv();
                }
                

                String etiquetaPreFor = g.nuevaEtiqueta();
                g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(etiquetaPreFor));
                
                // comprobamos que exp sera bool
                ctrlExp(otras.getNodoExpresion(), false, true);

                String sentsFor = g.nuevaEtiqueta();
                String etiquetaPostFor = g.nuevaEtiqueta();
                g.genIntruccion(TipoInstruccion.IFTRUEGOTO, 
                new Operador3Direcciones(otras.getNodoExpresion().getNv(),false),
                new Operador3Direcciones(0, TipoCambio.INT), 
                new Operador3Direcciones(sentsFor));
                
                int op = -1;
                if (otras.getNodoOpRapidos() != null) {                    
                    op=ctrlOpRapidos(otras.getNodoOpRapidos());
                }
                
                g.genIntruccion(TipoInstruccion.GOTO,null,null, new Operador3Direcciones(etiquetaPostFor));
                g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(sentsFor));

                // comprobamos las sentencias
                if (otras.getNodoSents() != null) {
                    ctrlSents(otras.getNodoSents());
                }

                if(op==0){
                    //restando
                    g.genIntruccion(TipoInstruccion.RESTA,new Operador3Direcciones(nv2,false), new Operador3Direcciones(1), new Operador3Direcciones(nv2,false));                    
                }else if(op==1){
                    //sumando
                    g.genIntruccion(TipoInstruccion.SUMA,new Operador3Direcciones(nv2,false), new Operador3Direcciones(1), new Operador3Direcciones(nv2,false));
                    //i=i+1
                }

                g.genIntruccion(TipoInstruccion.GOTO,null,null, new Operador3Direcciones(etiquetaPreFor));
                g.genIntruccion(TipoInstruccion.SKIP,null,null, new Operador3Direcciones(etiquetaPostFor));

                break;

            case 3: // switch

                // compruebo que el id existe en la tabla de simbolos
                if (ts.consultarTD(otras.getNodoId().getNombre()) == null) {
                    parser.report_error("No existe el id", otras);
                }

                if (otras.getNodoCase() != null) {
                    ctrlCase(otras.getNodoCase(), otras.getNodoId());
                }

                if (otras.getNodoCasoDefault() != null) {
                    ctrlSents(otras.getNodoCasoDefault().getNodoSents());
                }

                break;

            case 4: // print
                System.out.println("Detectamos un print");

                // id todos los tipos, literales todos los tipos y llamada a funcion todos los
                // tipos
                //tipo = ctrlExp(otras.getNodoExpresion(),false,false);   
                ctrlPrints(otras, TipoInstruccion.PRINT);
              
                break;

            case 5: // println

                System.out.println("Detectamos un println");
                ctrlPrints(otras, TipoInstruccion.PRINTLN);
                break;

            case 6: // llamada_func
                
                ctrl_LlamadaFunc(otras.getNodoLlamadaFunc());

                break;

            case 7: // In

                // ¿?¿?¿?¿?¿?¿?

                // codigo 3d

                int nv = g.nuevaVariable(TipoVar.VARIABLE, Tipo.tsb_char, false);
                g.genIntruccion(TipoInstruccion.IN, null, null, new Operador3Direcciones(nv));
                otras.getNodoExpresion().setNv(nv);
                //?¿?¿¿? setTyp char

                break;

            default:
                break;
        }

    }

    public void ctrlPrints(NodoOtrasSent otras, TipoInstruccion PRINT) {        
    
        if(otras.getIdentificador() == 4|| otras.getIdentificador() == 5){

            if(otras.getNodoExpresion().getNodoId() != null){
                Descripcion d = ts.consultarTD(otras.getNodoExpresion().getNodoId().getNombre());
                if(d == null){
                    parser.report_error("El id no existe", otras.getNodoExpresion().getNodoId());
                }    

                int nv = -1;

                if(Descripcion.TDesc.dvar.toString() == d.getTDescripcion()){
                    //caso variable
                    Dvar d1 = (Dvar) d;
                    nv= d1.getNodoId().getNv();
                }else{
                    //caso constante
                    DConst dconst = (DConst) d;
                    nv = dconst.getNodoId().getNv();
                }
                // codigo 3d
                g.genIntruccion(PRINT, null,null, new Operador3Direcciones(nv,false));
            
            
            }else if(otras.getNodoExpresion().getNodoLiteral() != null){
                String valor = otras.getNodoExpresion().getNodoLiteral().getValor();
                switch(otras.getNodoExpresion().getNodoLiteral().getTipo()){
                    case tsb_int:
                        int vali = Integer.parseInt(valor);
                        g.genIntruccion(PRINT, null, null,new Operador3Direcciones(vali));
                    break;
                    case tsb_bool:
                            int valb = Integer.parseInt(valor);
                            g.genIntruccion(PRINT, null, null,new Operador3Direcciones(valb));
                        break;
                    case tsb_char:
                            int valc;
                            valc = (int) (valor.charAt(1));
                            g.genIntruccion(PRINT, null, null,new Operador3Direcciones(valc));
                        break;

                    case tsb_float:
                            Float valf = Float.parseFloat(valor);
                            g.genIntruccion(PRINT, null, null,new Operador3Direcciones(valf));
                        break;
                    case tsb_str:                                
                            g.genIntruccion(PRINT, null, null,new Operador3Direcciones(valor,0f));
                        break;
                    default:
                        break;
                }

            }else if(otras.getNodoExpresion().getNodoExpresion1() !=null){
                ctrlExp(otras.getNodoExpresion(),false,false);
                g.genIntruccion(TipoInstruccion.PRINT, null,null, new Operador3Direcciones(otras.getNodoExpresion().getNv(),false));
            }else if(otras.getNodoExpresion().getNodoLlamadaFunc() != null){
                Descripcion d = ts.consultarTD(otras.getNodoExpresion().getNodoLlamadaFunc().getNodoId().getNombre());
                if(d == null){
                    parser.report_error("No existe la funcion", otras.getNodoExpresion().getNodoLlamadaFunc());
                }
                DFunc dfunc = (DFunc) d;
                ctrl_LlamadaFunc(otras.getNodoExpresion().getNodoLlamadaFunc());
                g.genIntruccion(TipoInstruccion.PRINT, null,null, new Operador3Direcciones(dfunc.getRetVal(),false));
            }else{
                parser.report_error("Expresion no valida en print", otras.getNodoExpresion());
            }

        }
        
    }

    public Tipo ctrlExp(NodoExpresion exp, boolean op, boolean bool) {

        System.out.println("Entro ctrlExp");
        System.out.println("");

        if (exp.getNodoLiteral() != null) {
            if (exp.getNodoLiteral().getTipo() != Tipo.tsb_bool) {
                if (!op) {
                    parser.report_error("El parametro no es correcto", exp.getNodoLiteral());
                } else {
                    if (exp.getNodoLiteral().getTipo() != Tipo.tsb_bool
                            && exp.getNodoLiteral().getTipo() != Tipo.tsb_int
                            && exp.getNodoLiteral().getTipo() != Tipo.tsb_float) {
                        parser.report_error("El parametro no es correcto", exp.getNodoLiteral());
                    }
                }
            }
            int nv = g.nuevaVariable(TipoVar.VARIABLE, exp.getNodoLiteral().getTipo(), false);
            exp.setNv(nv);
            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(exp.getNodoLiteral().getValor(), true),
                    null, new Operador3Direcciones(nv, false));

            return exp.getNodoLiteral().getTipo();

        } else if (exp.getNodoId() != null) {// tenemos un id

            System.out.println("Entramos en id");

            // comprobamos que el id exista en la tabla de simbolos
            System.out.println(exp.getNodoId().getNombre());
            if (ts.consultarTD(exp.getNodoId().getNombre()) == null) {
                parser.report_error("El parametro id no existe", exp.getNodoId());
            }

            Descripcion d = ts.consultarTD(exp.getNodoId().getNombre());

            if (d.getTDescripcion() == Descripcion.TDesc.dconst.toString()) {
                System.out.println("HOLA");
                DConst d3 = (DConst) ts.consultarTD(exp.getNodoId().getNombre());
                // si existe comprobamos que sea booleano
                if (!op && d3.getTipo() != Tipo.tsb_bool) {
                    parser.report_error("El parametro id no es booleano", exp.getNodoId());
                }

                if (op && d3.getTipo() != Tipo.tsb_bool && d3.getTipo() != Tipo.tsb_int
                        && d3.getTipo() != Tipo.tsb_float) {
                    parser.report_error("El parametro no es correcto", exp.getNodoId());
                }
                System.out.println("EXP id: " + exp.getNodoId().getNombre());
                int nv = g.nuevaVariable(TipoVar.VARIABLE, d3.getTipo(), false);
                exp.setNv(nv);
                System.out.println("EXP nv: " + d3.getNodoId().getNv());
                g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(d3.getNodoId().getNv(), false), null,
                        new Operador3Direcciones(nv, false));

                return d3.getTipo();
            } else {
                System.out.println("HOLA2");
                Dvar d3 = (Dvar) ts.consultarTD(exp.getNodoId().getNombre());
                // si existe comprobamos que sea booleano
                if (!op && d3.getTipus() != Tipo.tsb_bool) {
                    parser.report_error("El parametro id no es booleano", exp.getNodoId());
                }

                if (op && d3.getTipus() != Tipo.tsb_bool && d3.getTipus() != Tipo.tsb_int
                        && d3.getTipus() != Tipo.tsb_float) {
                    parser.report_error("El parametro no es correcto", exp.getNodoId());
                }
                System.out.println("EXP id: " + exp.getNodoId().getNombre());
                System.out.println("EXP nv: " + d3.getNodoId().getNv());
                int nv = g.nuevaVariable(TipoVar.VARIABLE, d3.getTipus(), false);
                exp.setNv(nv);
                g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(d3.getNodoId().getNv(), false), null,
                        new Operador3Direcciones(nv, false));

                return d3.getTipus();
            }

        } else if (exp.getNodoExpresionConNegacion() != null) {
            Tipo tipo = ctrlExp(exp.getNodoExpresionConNegacion(), false, true);
            if (tipo != Tipo.tsb_bool) {
                parser.report_error("No se puede negar cosas que no son bool", exp.getNodoExpresionConNegacion());
            }
            //
            // obtenemos primero el id
            // int nv = g.nuevaVariable(TipoVar.VARIABLE, exp.getNodoLiteral().getTipo(),
            // false);
            // exp.setNv(nv);
            return Tipo.tsb_bool;

        } else if (exp.getNodoLlamadaFunc() != null) {
            ctrl_LlamadaFunc(exp.getNodoLlamadaFunc());
            DFunc dfunc = (DFunc) ts.consultarTD(exp.getNodoLlamadaFunc().getNodoId().getNombre());
            return dfunc.getTipo();

        } else if (exp.getNodoExpresion1() != null) {
            // Como tenemos una expresion en termino1 y en termino 2 podemos llamar a que se
            // evaluen
            TipoLog operador;
            TipoArit operador2;

            if (exp.getNodoOperador().getNodoOpLog() != null) {
                operador = exp.getNodoOperador().getNodoOpLog().getTipusOpLog();
                // Con op log
                System.out.println("ESTOY EN UNA OPERACION LOGICA");
                Tipo tipoTerm1 = ctrlExp(exp.getNodoExpresion1(), true, true);
                Tipo tipoTerm2 = ctrlExp(exp.getNodoExpresion2(), true, true);
                System.out.println("\n\nTipo termino1 " + tipoTerm1);
                System.out.println("Tipo termino2 " + tipoTerm2);

                if (operador == TipoLog.AND || operador == TipoLog.OR) {
                    if (tipoTerm1 != Tipo.tsb_bool || tipoTerm2 != Tipo.tsb_bool) {
                        parser.report_error("Operacion booleana incorrecta", exp.getNodoOperador().getNodoOpLog());
                    }
                }
                if (operador == TipoLog.IGUALMAYOR || operador == TipoLog.IGUALMENOR || operador == TipoLog.MAYOR
                        || operador == TipoLog.MENOR) {
                    if ((tipoTerm1 == Tipo.tsb_int || tipoTerm1 == Tipo.tsb_float) && tipoTerm1 != tipoTerm2) {
                        parser.report_error("Operacion comparativa incorrecta", exp.getNodoOperador().getNodoOpLog());
                    }
                }
                if (operador == TipoLog.IGUALIGUAL || operador == TipoLog.DIFERENTE) {
                    if (tipoTerm1 != tipoTerm2) {
                        parser.report_error("Operacion comparativa incorrecta", exp.getNodoOperador().getNodoOpLog());
                    }
                }

                if (tipoTerm1 != tipoTerm2) {
                    parser.report_error("Tipos incompatibles", exp.getNodoOperador());
                } else {
                    int nv = g.nuevaVariable(TipoVar.VARIABLE, tipoTerm2, false);
                    exp.setNv(nv);
                    // Tengo que coger los nv de la expresion 1 y expresion 2
                    g.generaInstruccionLogica(exp.getNodoOperador().getNodoOpLog().getTipusOpLog(),
                            new Operador3Direcciones(exp.getNodoExpresion1().getNv(), false),
                            new Operador3Direcciones(exp.getNodoExpresion2().getNv(), false),
                            new Operador3Direcciones(nv, false));
                    return Tipo.tsb_bool;
                }

            } else if (exp.getNodoOperador().getNodoOpArit() != null) {
                operador2 = exp.getNodoOperador().getNodoOpArit().getOpArit();
                Tipo tipoTerm1 = ctrlExp(exp.getNodoExpresion1(), true, true);
                Tipo tipoTerm2 = ctrlExp(exp.getNodoExpresion2(), true, true);

                System.out.println("\n\nTipo termino1 " + tipoTerm1);
                System.out.println("Tipo termino2 " + tipoTerm2);
                if (tipoTerm1 != tipoTerm2) {
                    parser.report_error("Tipos incompatibles", exp.getNodoOperador());
                }
                System.out.println("OPERADOR = " + exp.getNodoOperador().getNodoOpArit().getOpArit());
                int nv = g.nuevaVariable(TipoVar.VARIABLE, tipoTerm2, false);
                exp.setNv(nv);
                // Tengo que coger los nv de la expresion 1 y expresion 2
                g.generaInstruccionAritmetica(exp.getNodoOperador().getNodoOpArit().getOpArit(),
                        new Operador3Direcciones(exp.getNodoExpresion1().getNv(), false),
                        new Operador3Direcciones(exp.getNodoExpresion2().getNv(), false),
                        new Operador3Direcciones(nv, false));
                // System.out.println("adios");
                return tipoTerm1;
            }
        }
        return Tipo.tsb_void;
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

        // Vamos a pilar el nodo de la funcion correspondiente
        DFunc d = (DFunc) ts.consultarTD(llamadaFunc.getNodoId().getNombre());
        // Marcador
        // Aqui tenemos los parametros correctos
        int nParamsBuenos = 0;
        Stack<NodoDeclFuncParam> pila = new Stack();
        if (d.getNodoFunc().getNodoDeclFuncP() != null) {
            NodoDeclFuncParams n = d.getNodoFunc().getNodoDeclFuncP().getNodoDeclFuncParams();
            while (n != null) {
                nParamsBuenos++;
                pila.push(n.getNodoDeclFuncParam());
                n = n.getNodoDeclFuncParams();
            }
        }

        int nParamsLlamada = 0;
        if (llamadaFunc.getNodoParams() != null && nParamsBuenos == 0) {
            parser.report_error("Parametros incorrectos", llamadaFunc);
        } else if (llamadaFunc.getNodoParams() != null) {
            NodoPar n1 = llamadaFunc.getNodoParams().getNodoPar();
            NodoContParam n2;
            do {
                nParamsLlamada++;
                n2 = n1.getNodoContParam();
                if (n2 != null) {
                    n1 = n2.getParametro();
                }
            } while (n2 != null);
        }

        if (nParamsBuenos != nParamsLlamada) {
            parser.report_error("Numero de parametros incorrecto", llamadaFunc);
        }

        // Comprovacion de tipos
        if (nParamsBuenos != 0) {
            Tipo funcion;
            Tipo llamada;

            NodoDeclFuncParams n4 = d.getNodoFunc().getNodoDeclFuncP().getNodoDeclFuncParams();
            NodoPar n1 = llamadaFunc.getNodoParams().getNodoPar();
            NodoContParam n2 = n1.getNodoContParam();

            while (n4 != null) {

                NodoDeclFuncParam declFuncParam = n4.getNodoDeclFuncParam();
                System.out.println("hola");
                // funcion = declFuncParam.getNodoTipo().getTipo();
                funcion = pila.pop().getNodoTipo().getTipo();
                System.out.println("hola2");

                if (n1.getNodoExpresion().getNodoLiteral() == null &&
                        n1.getNodoExpresion().getNodoLlamadaFunc() == null &&
                        n1.getNodoExpresion().getNodoId() == null &&
                        n1.getNodoExpresion().getNodoExpresion1() == null) {

                    parser.report_error("Expresión indebida como parametro de entrada en la función",
                            n1.getNodoExpresion());

                } else if (n1.getNodoExpresion().getNodoLiteral() != null) {
                    // MIRAR que es tenemos un literal dentro de la llamada de la funcion
                    // Vamos a sacar el tipo del literal
                    llamada = n1.getNodoExpresion().getNodoLiteral().getTipo();
                    System.out.println("Tipo llamada: " + llamada);
                    System.out.println("Tipo funcion: " + funcion);
                    if (funcion != llamada) {
                        parser.report_error("Parametro incorrecto", n1.getNodoExpresion().getNodoLiteral());
                    }
                    String valor = n1.getNodoExpresion().getNodoLiteral().getValor();
                    switch (llamada) {
                        case tsb_int:
                            int vali = Integer.parseInt(valor);
                            //Generamos instruccion
                            g.genIntruccion(TipoInstruccion.PARAM_SIMPLE, null, null, new Operador3Direcciones(vali));
                            break;
                        case tsb_bool:
                            int valb = Integer.parseInt(valor);
                            g.genIntruccion(TipoInstruccion.PARAM_SIMPLE, null, null, new Operador3Direcciones(valb));
                            break;
                        case tsb_char:
                            int valc;
                            if (funcion != Tipo.tsb_int) {
                                valc = (int) (valor.charAt(1));
                            } else {
                                valc = Integer.parseInt(valor);
                            }
                            g.genIntruccion(TipoInstruccion.PARAM_SIMPLE, null, null, new Operador3Direcciones(valc));
                            break;
                        case tsb_float:
                            Float valf = Float.parseFloat(valor);
                            g.genIntruccion(TipoInstruccion.PARAM_SIMPLE, null, null, new Operador3Direcciones(valf));
                            break;
                        case tsb_str:
                            g.genIntruccion(TipoInstruccion.PARAM_SIMPLE, null, null, new Operador3Direcciones(valor,0f));
                            break;
                    }

                } else if (n1.getNodoExpresion().getNodoExpresion1() != null) {
                    // Si metemos una expresion
                    if (n1.getNodoExpresion().getNodoOperador().getNodoOpLog() != null) {
                        // Es bool
                        ctrlExp(n1.getNodoExpresion(), false, true);
                        if (funcion != Tipo.tsb_bool) {
                            System.out.println("HOLA");
                            parser.report_error("Parametro incorrecto", n1.getNodoExpresion().getNodoExpresion1());
                        }
                       
                    } else {
                        // Es aritmetica
                        // Tenemos que hacer el control de expresiones
                        ctrlExp(n1.getNodoExpresion(), false, true);
                        if (funcion != Tipo.tsb_int && funcion != Tipo.tsb_float) {
                            System.out.println("HOLA2");
                            parser.report_error("Parametro incorrecto", n1.getNodoExpresion().getNodoExpresion1());
                        }
                        
                    }
                    g.genIntruccion(TipoInstruccion.PARAM_SIMPLE, null, null, new Operador3Direcciones(n1.getNodoExpresion().getNv(),false));
                
                } else if (n1.getNodoExpresion().getNodoId() != null) {
                    System.out.println("COMPROBAMOS EL ID");
                    Descripcion d3 = ts.consultarTD(n1.getNodoExpresion().getNodoId().getNombre());
                    if (d3 == null) {
                        parser.report_error("No existe el id", n1.getNodoExpresion().getNodoId());
                    }
                    int nv;
                    if (d3.getTDescripcion() == Descripcion.TDesc.dconst.toString()) {
                        DConst d4 = (DConst) d3;
                        llamada = d4.getTipo();
                        nv = d4.getNodoId().getNv();
                    } else {
                        Dvar d4 = (Dvar) d3;
                        llamada = d4.getTipus();
                        nv = d4.getNodoId().getNv();
                    }

                    if (funcion != llamada) {
                        parser.report_error("Parametro incorrecto", n1.getNodoExpresion().getNodoId());
                    }
                    
                    g.genIntruccion(TipoInstruccion.PARAM_SIMPLE, null, null, new Operador3Direcciones(nv,false));
                }
                //System.out.println("hola3");
                // Mirar si tipollamada y tipo funcion son iguales si no error
                n2 = n1.getNodoContParam();
                if (n2 != null) {
                    n1 = n2.getParametro();
                }
                n4 = n4.getNodoDeclFuncParams();
            }
        }
        g.genIntruccion(TipoInstruccion.CALL, null, null, new Operador3Direcciones(d.getnp(),true));
    }

    public void gestionParam() {

    }

    public void ctrlCase(NodoCase nodoCase, NodoId idSwitch) {

        // Descripcion del switch
        Descripcion d = ts.consultarTD(idSwitch.getNombre());

        if (d.getTDescripcion() == Descripcion.TDesc.dconst.toString()) {
            parser.report_error("No debes utilizar constantes en switch", idSwitch);
        }

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

    }

    public int ctrlOpRapidos(NodoOpRapidos opRapidos) {

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
            return 1;

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
            return 0;
        }
    }

    public void ctrlElseSent(NodoElse elseSent) {

        // else
        if (elseSent.getNodoExpresion() == null) {

            
            //g.genIntruccion(TipoInstruccion.SKIP, null, null, new Operador3Direcciones(etiquetaE));
            
            System.out.println("Detectamos un else");
            if (elseSent.getNodoSents() != null) {
                ctrlSents(elseSent.getNodoSents());
            }

        } else {// elseif

            System.out.println("Detectamos un elseif");
            // verifico que expresion sea bool

            if (elseSent.getNodoExpresion() != null) {
                ctrlExp(elseSent.getNodoExpresion(), false, true);
            }

            String etiquetaExp = g.nuevaEtiqueta();
            String etiquetaFalse = g.nuevaEtiqueta();
                
            g.genIntruccion(TipoInstruccion.IFTRUEGOTO, new Operador3Direcciones(elseSent.getNodoExpresion().getNv(),false),new Operador3Direcciones(0, TipoCambio.INT), new Operador3Direcciones(etiquetaExp));
            
            g.genIntruccion(TipoInstruccion.GOTO, null, null, new Operador3Direcciones(etiquetaFalse));
            g.genIntruccion(TipoInstruccion.SKIP, null, null, new Operador3Direcciones(etiquetaExp));

            // compruebo las sentencias
           
            if (elseSent.getNodoSents() != null) {
                ctrlSents(elseSent.getNodoSents());
            }

            
            g.genIntruccion(TipoInstruccion.SKIP, null, null, new Operador3Direcciones(etiquetaFalse));

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
        // Tipo expr = ctrlExp(expresion, false, true);


        Descripcion d = ts.consultarTD(id.getNombre());
        if (d == null) {
            parser.report_error("No existe el id", id);
        }
        Dvar dvar = (Dvar) d;

        if (expresion.getNodoExpresion1() != null) {
            Tipo expr = ctrlExp(expresion, false, true);
            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(expresion.getNv(),false), null,new Operador3Direcciones(dvar.getNodoId().getNv(),false));
        } else if(expresion.getNodoLlamadaFunc() != null){
            //Tipo func = ctrlFunc(null);
            ctrl_LlamadaFunc(expresion.getNodoLlamadaFunc());
            DFunc dfunc = (DFunc) ts.consultarTD(expresion.getNodoLlamadaFunc().getNodoId().getNombre());
            g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(dfunc.getRetVal(),false), null,new Operador3Direcciones(dvar.getNodoId().getNv(),false));
        } else {
            System.out.println("ENTRO POR AQUI");
            // comprobar que el id existe en la tabla de simbolos
            //Descripcion d = ts.consultarTD(id.getNombre().toString());
            Tipo var = null;
            if (d.getTDescripcion() == Descripcion.TDesc.darray.toString()) {
                Darray dt = (Darray) ts.consultarTD(id.getNombre().toString());
                var = dt.getTipus();
            } else if (d.getTDescripcion() == Descripcion.TDesc.dvar.toString()) {
                Dvar dt = (Dvar) ts.consultarTD(id.getNombre().toString()); // tipo del id
                var = dt.getTipus();
            } else {
                parser.report_error("No se pueden asignar valores", realAsign);
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
                String valor = expresion.getNodoLiteral().getValor();
                switch (var) {
                    case tsb_int:
                        int vali = Integer.parseInt(valor);
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(vali), null,
                                new Operador3Direcciones(dvar.getNodoId().getNv(), false));
                        break;
                    case tsb_bool:
                        int valb = Integer.parseInt(valor);
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valb), null,
                                new Operador3Direcciones(dvar.getNodoId().getNv(), false));
                        break;
                    case tsb_char:
                        int valc;
                        if (tipoExpr != Tipo.tsb_int) {
                            valc = (int) (valor.charAt(1));
                        } else {
                            valc = Integer.parseInt(valor);
                        }
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valc), null,
                                new Operador3Direcciones(dvar.getNodoId().getNv(), false));
                        break;
                    case tsb_float:
                        Float valf = Float.parseFloat(valor);
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valf), null,
                                new Operador3Direcciones(dvar.getNodoId().getNv(), false));
                        break;
                    case tsb_str:
                        g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(valor, 0f), null,
                                new Operador3Direcciones(dvar.getNodoId().getNv(),false));
                        break;
                }
                
            } else if (expresion.getNodoId() != null) {

                Descripcion d1 = ts.consultarTD(expresion.getNodoId().getNombre().toString());
                Tipo tipoExpr;
                int nv;

                if (d1.getTDescripcion() == Descripcion.TDesc.dconst.toString()) {
                    DConst dt = (DConst) ts.consultarTD(expresion.getNodoId().getNombre().toString());
                    tipoExpr = dt.getTipo();
                    nv = dt.getNodoId().getNv();
                } else {
                    Dvar dt = (Dvar) ts.consultarTD(expresion.getNodoId().getNombre().toString()); // tipo del id
                    tipoExpr = dt.getTipus();
                    nv = dt.getNodoId().getNv();
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

                g.genIntruccion(TipoInstruccion.COPIA, new Operador3Direcciones(nv, false), null,
                                    new Operador3Direcciones(dvar.getNodoId().getNv(), false));

            } else if (expresion.getNodoLlamadaFunc() != null) {
                //System.out.println("HOLA");
                ctrl_LlamadaFunc(expresion.getNodoLlamadaFunc());
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

    }

    public void ctrlDeclFuncP(NodoDeclFuncP funcP) {

        // Puede ser que declare parametros o no
        NodoDeclFuncParams n = funcP.getNodoDeclFuncParams();
        while (n != null) {
            ctrlParam(n.getNodoDeclFuncParam());
            n = n.getNodoDeclFuncParams();
        }
    }

    public void ctrlParam(NodoDeclFuncParam n) {
        // Los metemos en la tabla de simbolos
        if (n.getNodoDeclArray() == null) {
            System.out.println("Pongo la variable en ts: " + n.getNodoId().getNombre());
            ts.poner(n.getNodoId().getNombre(), new Dvar(0, n.getNodoTipo().getTipo(), n.getNodoId()), n);
        } else {
            ts.poner(n.getNodoId().getNombre(),
                    new Darray(0, n.getNodoTipo().getTipo(), dimensionArr(n.getNodoDeclArray()), false), n);
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
