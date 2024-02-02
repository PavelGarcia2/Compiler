package assembly;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;





import herramientas.Tipo;
import semantico.*;
import semantico.Operador3Direcciones.TipoI;
import tsimbolos.*;
import tsimbolos.descripciones.*;

public class GeneradorEnsamblado{

    StringBuilder ensamblado= new StringBuilder();
    TablaSimbolos tablaSimbolos;
    TablaVariables tablaVariables;
    TablaProcedimientos tablaProcedimientos;
    ArrayList<Intruccion3Direcciones> instrucciones;

    
    private String espacioEtiqueta= "\t";
    private String espacioNormal= " ";
    private ArrayList<Integer> variablesDeclaradas;
    private ArrayList<Integer> listaParametros;
    private String nombreArchivo;
    private int variableActual = -1;
    private boolean mainCreado = false;
    private final String main= "main";
    private String filename;
    private final String folder = "Compilador/src/main/pruebas/Tests_68K/"; 
    private final String extension = ".X68";
    private boolean isParam = false;
    private int actualParam = 1;
    private boolean isString = false;
    private int arrayCIdx = 0;
    int contador = 99;
    private int counter = 0;
    private int contadorStr = 0;


    public GeneradorEnsamblado(String nombreArchivo, TablaSimbolos tablaSimbolos, TablaVariables tablaVariables, TablaProcedimientos tablaProcedimientos, ArrayList<Intruccion3Direcciones> instrucciones){
        this.variablesDeclaradas=new ArrayList<>();
        this.listaParametros=new ArrayList<>();
        
        this.nombreArchivo=nombreArchivo;
        this.tablaSimbolos=tablaSimbolos;
        this.tablaVariables=tablaVariables;
        this.tablaProcedimientos=tablaProcedimientos;
        this.instrucciones=instrucciones;
    }

    public void generarCodigoMain(){

        ensamblado.append("\t"+"INCLUDE "+"library/MACRO.X68"+"\n");
        ensamblado.append("\t"+"INCLUDE "+"library/CONST.X68"+"\n");
        ensamblado.append("\t"+"INCLUDE "+"library/VAR.X68"+"\n");

        ensamblado.append(";-----------------------------------------------------------\n");
        ensamblado.append(";-----------------------------------------------------------\n");
        ensamblado.append("; Initial program lines, main                               \n");
        ensamblado.append(";-----------------------------------------------------------\n");
        ensamblado.append("; Labels to memory space reserved for variables:            \n");
        ensamblado.append(";-----------------------------------------------------------\n");

        for (int i = 0; i < tablaVariables.getNV(); i++) {
            if(tablaVariables.get(i).getTipo() == Tipo.tsb_str){
                ensamblado.append(tablaVariables.get(i).getNombre()+ espacioEtiqueta + "DC.B"+ espacioNormal+ getValorString(i) + ",0\n");
            }else{
                 if (tablaVariables.get(i).esArray()) {
                    ensamblado.append(tablaVariables.get(i).getNombre() + espacioEtiqueta + "DS.L" + espacioNormal + 15 + "\n");
                } else {
                    ensamblado.append(tablaVariables.get(i).getNombre() + espacioEtiqueta + "DS.L" + espacioNormal + "1\n");
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            ensamblado.append("SParam" + i + espacioEtiqueta + "DS.B" + espacioNormal + "128\n");
            ensamblado.append(espacioEtiqueta + "DC.B" + espacioNormal + "0\n");
        }
        for (int i = 0; i < 10; i++) {
            ensamblado.append("Param" + i + espacioEtiqueta + "DS.L" + espacioNormal + "1\n");
        }

        acabaTP();
        actulizarVariables();


        ensamblado.append(";-----------------------------------------------------------\n");
        ensamblado.append(espacioEtiqueta + "DS.W" + espacioNormal + "0\n");
        ensamblado.append(";-----------------------------------------------------------\n");
        ensamblado.append("globals:\n");

        boolean globales = true;
 
        for (Intruccion3Direcciones instruction : instrucciones) {
            ensamblado.append(header(instruction) + '\n');
            //If main not created and we don't have to do skip...
            if (!mainCreado && globales) {
                if (instruction.getTipoIntruccion() != TipoInstruccion.COPIA) {
                    globales = false;
                    ensamblado.append(espacioEtiqueta + "JMP" + espacioNormal + main + " ; Declared all globals\n");
                    generaInstruccion(instruction);
                } else {
                    generaInstruccion(instruction);
                }

            } else {

                generaInstruccion(instruction);
            }

        }

        ensamblado.append(espacioEtiqueta + "END" + espacioNormal + "globals");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(folder + nombreArchivo.substring(0, nombreArchivo.length() - 4) + extension)))) {
            bufferedWriter.write(ensamblado.toString());
            //For overwritting avoidance
            Thread.sleep(2000);
        } catch (IOException ex) {
            System.out.println("No file was generated, something went wrong...");
            Logger.getLogger(GeneradorEnsamblado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneradorEnsamblado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void generaInstruccion(Intruccion3Direcciones instruccion){

        switch (instruccion.getTipoIntruccion()) {
			case AND:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");

                ensamblado.append(espacioEtiqueta + "ANDM" + espacioNormal + "D0, D1\n");

                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(),instruccion.getOperadores()[2].getReferencia());
                
				break;

			case CALL:
            
                actualParam = 1;
                if (instruccion.getOperadores()[0].getEtiqueta() == main) { //Main case
                    ensamblado.append(main + ':' + "                                                       \n");
                    mainCreado = true;
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A7, " + "A6" + '\n'); //Mover la SP a EBP
                    break;
                }

                Procedimiento procedure = tablaProcedimientos.get(instruccion.getOperadores()[0].getEtiqueta());
                String label = procedure.getEtiquetaInicial();

                ensamblado.append(espacioEtiqueta + "JSR" + espacioNormal + label + " ; GOTO " + label + "\n"); //Make the jump, we have parameters in stack (From SIMPLEPARAM)

                if (instruccion.getOperadores()[2] != null) {
                    //D5 used for returning values
                    STORE("D5", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(), instruccion.getOperadores()[2].getReferencia());
                }

                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A6" + ", A7\n");
                counter = 0;
                contadorStr = 0;

				break;
			case COPIA:
                //System.out.println(instruccion.getOperadores()[0]);  
                System.out.println(instruccion.getOperadores()[2].getId());  

                Descripcion d = tablaSimbolos.consultarTD(instruccion.getOperadores()[2].getId());
                Tipo tipo = null;
                if(d instanceof Dvar){
                    LOAD(instruccion.getOperadores()[0],instruccion.getOperadores()[2].getReferencia(),null, "D0");
                    Dvar dvar = (Dvar) d;
                    tipo = dvar.getTipus();
                }else if( d instanceof DConst){
                    LOAD(instruccion.getOperadores()[0],instruccion.getOperadores()[2].getReferencia(),null, "D0");
                    DConst dcons = (DConst) d;
                    tipo = dcons.getTipo();
                }
                  
               // System.out.println("hola");

                if (((tipo == Tipo.tsb_str))) {

                    isString = true;
                }

                System.out.println("PETA");
                STORE("D0", null, instruccion.getOperadores()[2].getReferencia());
                isString = false;


				break;
			case DIVISION:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");

                ensamblado.append(espacioEtiqueta + "DIVM" + espacioNormal + "D1, D0\n");
                ensamblado.append(espacioEtiqueta + "AND.L" + espacioNormal + "#$0000FFFF" + ", D1\n");

                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(),instruccion.getOperadores()[2].getReferencia());

				break;
			case GOTO:

                ensamblado.append(espacioEtiqueta + "JMP" + espacioNormal + instruccion.getOperadores()[2].getEtiqueta() + '\n');

				break;

			case IFDIFERENTE:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "CMP.L" + espacioNormal + "D1, D0\n");
                ensamblado.append(espacioEtiqueta + "BEQ" + espacioNormal + instruccion.getOperadores()[2].getEtiqueta() + '\n');

				break;

			case IFTRUEGOTO:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "CMP.L" + espacioNormal + "D1, D0\n");
                ensamblado.append(espacioEtiqueta + "BNE" + espacioNormal + instruccion.getOperadores()[2].getEtiqueta() + '\n');

				break;

			case IFMAYOR:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "CMP.L" + espacioNormal + "D1, D0\n");
                ensamblado.append(espacioEtiqueta + "BLE" + espacioNormal + instruccion.getOperadores()[2].getEtiqueta() + '\n');

				break;

			case IFMAYORIGUAL:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "CMP.L" + espacioNormal + "D1, D0\n");
                ensamblado.append(espacioEtiqueta + "BLT" + espacioNormal + instruccion.getOperadores()[2].getEtiqueta() + '\n');

				break;
			case IFMENOR:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "CMP.L" + espacioNormal + "D1, D0\n");
                ensamblado.append(espacioEtiqueta + "BGE" + espacioNormal + instruccion.getOperadores()[2].getEtiqueta() + '\n');

				break;

			case IFMENORIGUAL:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "CMP.L" + espacioNormal + "D1, D0\n");
                ensamblado.append(espacioEtiqueta + "BGT" + espacioNormal + instruccion.getOperadores()[2].getEtiqueta() + '\n');

				break;

			case IN:

                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#5, D0 ; Prepare read\n");
                ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15 ; Expect input\n");
                ensamblado.append(espacioEtiqueta + "AND.L" + espacioNormal + "#$00FF, D1 ; Mask upper word (we read char = 2 bytes)\n");
                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(), instruccion.getOperadores()[2].getReferencia());

				break;

			case MODULO:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");

                ensamblado.append(espacioEtiqueta + "MODM" + espacioNormal + "D0, D1\n");

                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(),instruccion.getOperadores()[2].getReferencia());

				break;

			case MULTIPLICACION:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");

                ensamblado.append(espacioEtiqueta + "MULTM" + espacioNormal + "D0, D1\n");

                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(),instruccion.getOperadores()[2].getReferencia());

				break;

			case NEGACION:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "NEGM" + espacioNormal + "D1"); //If we already use D1 for result, optimal to get the value inside it
                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(), instruccion.getOperadores()[2].getReferencia());

				break;

			case NOT:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D1");
                ensamblado.append(espacioEtiqueta + "NOTM" + espacioNormal + "D1"); //If we already use D1 for result, optimal to get the value inside it
                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(), instruccion.getOperadores()[2].getReferencia());

				break;

			case OR:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");

                ensamblado.append(espacioEtiqueta + "ORM" + espacioNormal + "D0, D1\n");

                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(),instruccion.getOperadores()[2].getReferencia());

				break;

			case PREAMBULO:

                 //Preamble
                if (instruccion.getOperadores()[2].getEtiqueta() == main) {
                    ensamblado.append(";Preamble of MAIN ignored\n");
                    break;
                } else {
                    //We already have the parameters and return address. We just put in the DISP and BP
                    int prof4x = tablaProcedimientos.get(instruccion.getOperadores()[2].getEtiqueta()).getAmbito() * 4;
                    ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "DISP, A0\n");
                    //Antic DISP(prof)
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + prof4x + "(A0), -(A7)\n");
                    //Antic BP
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A6" + ", -(A7)\n");
                    //Update BP
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A7, " + "A6" + " ;BP = SP\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A6" + ", " + (prof4x - 4) + "(A7) ; DISP(prof) = BP\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A6" + ", D0 ; D0=EBP\n");

                    ArrayList<Dargin> params = tablaSimbolos.obtenerParams(instruccion.getOperadores()[2].getEtiqueta());
                    int contadorStrings = 0;
                    int contadorVars = 0;
                    for (int i = 0; i < params.size(); i++) {
                        //Dvar desc = (Dvar) tablaSimbolos.consultaId(params.get(i).getNombre());
                        if (params.get(i).getTipus() == Tipo.tsb_str) {
                            ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "SParam" + contadorStrings + ", " + "A0 ; Param gest\n");
                            ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + params.get(i).getnv() + ", " + "A1 ; Param gest\n");
                            ensamblado.append(".loop" + contadorStrings + ':' + '\n');
                            ensamblado.append(espacioEtiqueta + "MOVE.B" + espacioNormal + "(A0)+, (A1)+ \n");
                            ensamblado.append(espacioEtiqueta + "TST.B (A0) \n");
                            ensamblado.append(espacioEtiqueta + "BNE .loop" + contadorStrings + '\n');
                            ensamblado.append(espacioEtiqueta + "MOVE.B" + espacioNormal + "#0, (A1)+ \n");
                            contadorStrings++;
                        } else {
                            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(Param" + contadorVars++ + "), " + "(Variable" + params.get(i).getnv() + ") ; Param gest\n");
                        }
                    }
                }
                listaParametros = new ArrayList<>();

				break;

			case PRINT:

                 Variable variable = tablaVariables.get(instruccion.getOperadores()[0].getReferencia());

                if (variable.getTipo() == Tipo.tsb_str) {
                    ensamblado.append(espacioEtiqueta + "LEA" + espacioEtiqueta + "Variable" + instruccion.getOperadores()[0].getReferencia() + ", A0 ; Cargar la string\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A0, A1 ; Ready text\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#14, D0 ; Prepare display\n");
                    ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15\n ; Expect screen visualization\n");
                } else if (variable.getTipo() == Tipo.tsb_char) {
                    LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(),
                            tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D1");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#6, D0 ; Prepare display\n");
                    ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15\n ; Expect screen visualization\n");
                } else if (variable.getTipo() == Tipo.tsb_bool || variable.getTipo() == Tipo.tsb_int) {
                    LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(),tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D1");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#3, D0 ; Prepare display\n");
                    ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15\n ; Expect screen visualization\n");

                }

				break;

			case PRINTLN:

                variable = tablaVariables.get(instruccion.getOperadores()[0].getReferencia());
                
                    if (variable.getTipo() == Tipo.tsb_str) {
                        ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + instruccion.getOperadores()[0].getReferencia() + ", A0 ; Cargar la string\n");
                        ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A0, A1 ; Ready text\n");
                        ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#14, D0 ; Prepare display\n");
                        ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15\n ; Expect screen visualization\n");
                    } else if (variable.getTipo() == Tipo.tsb_char) {
                        LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D1");
                        ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#6, D0 ; Prepare display\n");
                        ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15\n ; Expect screen visualization\n");
                    } else if (variable.getTipo() == Tipo.tsb_bool || variable.getTipo() == Tipo.tsb_int) {
                        LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(),tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D1");
                        ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#3, D0 ; Prepare display\n");
                        ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15\n ; Expect screen visualization\n");
                    }
                

                ensamblado.append(espacioEtiqueta + "MOVE.B" + espacioNormal + "#11, D0 ; Next line prepare\n");
                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#$00FF, D1 ; Request current coordinates\n");
                ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15 ; Get them in D1.L (HIGH=COL, LOW = ROW)\n");
                ensamblado.append(espacioEtiqueta + "AND.L" + espacioNormal + "#$00FF, D1 ; We want always column = 0\n");
                ensamblado.append(espacioEtiqueta + "ADDQ.B" + espacioNormal + "#1, D1 ; We increment the current row by 1\n");
                ensamblado.append(espacioEtiqueta + "TRAP" + espacioNormal + "#15 ; Set new coordinates (next line ready)\n");

				break;

			case RESTA:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");

                ensamblado.append(espacioEtiqueta + "SUBM" + espacioNormal + "D0, D1\n");

                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(),instruccion.getOperadores()[2].getReferencia());

				break;

			case RETURN:

                if (instruccion.getOperadores()[0].getEtiqueta() == main) {
                    ensamblado.append(espacioEtiqueta + "SIMHALT\n");
                    break;
                }

                int prof4x = tablaProcedimientos.get(instruccion.getOperadores()[0].getEtiqueta()).getAmbito() * 4;

                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A6" + ", A7 ; SP = BP, return to state before PMB\n");
                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(A7)+, " + "A6" + " ; BP = old BP\n");
                ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "DISP, A0 ; A0 = @DISP\n");
                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(A7)+, " + prof4x + "(A0) ; DISP[prof] = old value\n");

                if (instruccion.getOperadores()[2] != null) {
                    LOAD(instruccion.getOperadores()[2], instruccion.getOperadores()[2].getReferencia(), tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(), "D5");
                }

                ensamblado.append(espacioEtiqueta + "RTS ; Return\n");

				break;

			case SKIP:

                ensamblado.append(instruccion.getOperadores()[2].getEtiqueta() + ":\n");

				break;
			case SUMA:

                LOAD(instruccion.getOperadores()[0], instruccion.getOperadores()[0].getReferencia(), tablaVariables.get(instruccion.getOperadores()[0].getReferencia()).getIdProcedimiento(), "D0");
                LOAD(instruccion.getOperadores()[1], instruccion.getOperadores()[1].getReferencia(), tablaVariables.get(instruccion.getOperadores()[1].getReferencia()).getIdProcedimiento(), "D1");

                ensamblado.append(espacioEtiqueta + "ADDM" + espacioNormal + "D0, D1\n");

                STORE("D1", tablaVariables.get(instruccion.getOperadores()[2].getReferencia()).getIdProcedimiento(),instruccion.getOperadores()[2].getReferencia());

				break;

        }

    }







    private String getValorString(int i) {
        for (Intruccion3Direcciones instruction : instrucciones) {
            if (instruction.getTipoIntruccion() == TipoInstruccion.COPIA) {
                if (instruction.getOperadores()[2].getReferencia() == i) {
                    if (instruction.getOperadores()[0].getEtiqueta() == null) {
                        return getValorString(instruction.getOperadores()[0].getReferencia());
                    }
                    return ((String) instruction.getOperadores()[0].getEtiqueta()).replace('"', '\'');
                }
            }
        }
        return "No se ha encontrado un string en dicha variable";
    }

    private void acabaTP() {
      
        for (String procedureId : tablaProcedimientos.keySet()) {
            Procedimiento procedure = tablaProcedimientos.get(procedureId);
            procedure.setSizeVariables(0);
            tablaProcedimientos.put(procedureId, procedure);
        }
    }


    private void actulizarVariables() {
        String actualProcedure = "noProc";
        int currentParam = 1;
        for (int variableId = 0; variableId < tablaVariables.getNV(); variableId++) {
            
            String procedureId = tablaVariables.get(variableId).getIdProcedimiento();

            if (tablaVariables.get(variableId).getTipoVar() == Variable.TipoVar.VARIABLE && tablaVariables.get(variableId).getTipo() != Tipo.tsb_str) {

                int variableSize = tablaVariables.get(variableId).getBytes();

                //Update procedure? (when not main)
                if (tablaProcedimientos.get(procedureId) != null) {
                    tablaProcedimientos.get(procedureId).setSizeVariables(variableSize + tablaProcedimientos.get(procedureId).getSizeVariables());
                }
                if (procedureId == actualProcedure) {
                    tablaVariables.get(variableId).setDimension(variableActual-- * (variableSize < 4 ? 4 : variableSize)); //Global variables be one after another in memory}
                } else if (actualProcedure == "noProc") {
                    actualProcedure = procedureId;
                    tablaVariables.get(variableId).setDimension(variableActual-- * (variableSize < 4 ? 4 : variableSize));
                } else {
                    actualProcedure = null;
                    variableActual = -1;
                    tablaVariables.get(variableId).setDimension(variableActual-- * (variableSize < 4 ? 4 : variableSize));
                }
                if (actualProcedure == null) {
                    System.out.println(tablaVariables.get(variableId) + ", de offset -> " + tablaVariables.get(variableId).getDimension() + '\n');
                }
            } else if (tablaVariables.get(variableId).getTipoVar() == Variable.TipoVar.PARAMETRO) {
                int variableSize = tablaVariables.get(variableId).getBytes();
                tablaVariables.get(variableId).setDimension(currentParam * (variableSize < 4 ? 2 : variableSize));
                if (currentParam <= tablaProcedimientos.get(procedureId).getNumParams()) {
                    currentParam++;
                } else {
                    currentParam = 1;
                }
            }
        }
    }


    private String header(Intruccion3Direcciones instruction) {

        String res = "; Instruction of type: " + instruction.getTipoIntruccion().toString() + "\n;";
        
        if (instruction.getOperadores()[0] != null) {
            res = res + "Operator 1 -> " + (instruction.getOperadores()[0].getReferencia());
        }
        if (instruction.getOperadores()[1] != null) {
            res = res + ", Operator 2 -> " + instruction.getOperadores()[1];     
        }
        if (instruction.getOperadores()[2] != null) {
            res = res + ", Store in -> " + (instruction.getOperadores()[2].getReferencia());
        }
        return res + '\n';
    }

    
    private void LOAD(Operador3Direcciones literal, int variableId, String procedureId, String register) {
        Variable variable = tablaVariables.get(variableId);
        System.out.println("LOADING... -> " + variable.toString());
        char size = 'L';
        //Default scope of both:
        int profx = 0;
        int profp = 0;
        if (tablaProcedimientos.get(variable.getIdProcedimiento()) != null) {
            profx = tablaProcedimientos.get(variable.getIdProcedimiento()).getAmbito();
            profp = tablaProcedimientos.get(procedureId).getAmbito();
        }
        
        int dx = tablaVariables.get(variableId).getDimension();
        if (isParam) {
            dx = tablaVariables.get(variableId).getBytes() * actualParam; //12 = DISP (4) + BP (4) + RETURN (4)
            actualParam++;
        }
        if (literal.getTipo() == TipoI.LITERAL) //If we have scalar value
        {
            switch (literal.getTipoCambio()) {
                case INT:
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + '#' + ((int) literal.getValor()) + ", " + register + " ; Load variable\n");
                    break;
                case CHAR:
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "#'" + ((char) literal.getValor()) + "', " + register + " ; Load variable\n");
                    break;
                case BOOL:
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + '#' + ((boolean) literal.getValor() ? '1' : '0') + ", " + register + " ; Load variable\n");
                    break;
                case STRING:
                    ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + variableId + ", A0\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A0, " + register + " ; Load variable\n");
                    break;
                default:
                    throw new UnsupportedOperationException("Trying to load something that is not a variable!");
            }
        } else if (profx == profp && dx < 0) { //Si profundidad de la variable es la misma que el procedure y está en negativo por debajo de BlockPointer
            if (variable.esArray()) {
                ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + variableId + ", A0 ; Load local variable\n");
                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A0, " + register + " ; Load local variable\n");
            } else {
                ensamblado.append(espacioEtiqueta + "MOVE." + size + espacioNormal + '(' + "Variable" + variableId + "), " + register + " ; Load local variable\n");
            }
        } else if (profx == profp && dx > 0) { //Si profundidad de la variable es la misma que el procedure y está en positivo por encima de BlockPointer
            //dx = tv.get(variableId).getOffset() + 4;
            if (true) {
                if (!(variable.getTipo() == Tipo.tsb_str)) {
                    ensamblado.append(espacioEtiqueta + "MOVE." + size + espacioNormal + "(Variable" + variableId + "), " + register + " ; Load local param\n");
                } else {
                    ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + variableId + ", A0 ; Load local param\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A0, " + register + " ; Load it to register \n");
                }
            }
        } else if (profx < profp && dx < 0) {
            ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "DISP, A0 ; @DISP in A0\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + profx + "(A0), A0 ; DISP[profx] in A0, it is BPx value\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + dx + "(A0), " + register + " ; Load outsider variable in " + register + '\n');
        } else if (profx < profp && dx > 0) {
            ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "DISP, A0 ; @DISP in A0\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + profx + "(A0), A0 ; DISP[profx] in A0, it is BPx value\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(A0), A0\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(A0), " + register + " ; Load outsider parameter in " + register + '\n');
        } else {
            if (dx == 0) { //es una string, chapó
                ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + variableId + ", " + "A0 ; Param gest\n");
                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "A0, " + register + " ; Param gest\n");
            } else {
                throw new UnsupportedOperationException("Variable or parameter not valid for loading!");
            }
        }
    }

    private void STORE(String register, String procedureId, int variableId) {

        Variable variable = tablaVariables.get(variableId);
        System.out.println("STORING... -> " + variable.toString());
        
       
        int profx = 0;
        int profp = 0;
        boolean notGlobal = tablaProcedimientos.get(variable.getIdProcedimiento()) != null;
        if (notGlobal) {
            profx = tablaProcedimientos.get(variable.getIdProcedimiento()).getAmbito();
            profp = tablaProcedimientos.get(procedureId).getAmbito();
        }
        //OFFSET DE LA VARIABLE
        int dx = variable.getDimension();
        if (isParam) {
            dx = 12 + tablaVariables.get(variableId).getBytes() * actualParam;
            actualParam++;
        }
        if (profx == profp && dx < 0) { //Local variable
            //Look up variable space, if sums correctly and add all the space to A7 and we win jejeje
            if (true) {
                if (variable.esArray()) {
                    int sizeArray = tablaVariables.get(variableId).getBytes();
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + register + ", " + "A0 ; Store local variable\n");
                    ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + variableId + ", " + "A1 ; Store local variable\n");
                    ensamblado.append(espacioEtiqueta + "CLR.L" + espacioNormal + "D1\n");
                    ensamblado.append(espacioEtiqueta + "CLR.L" + espacioNormal + "D2\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(Variable" + sizeArray + "), D2 \n");
                    ensamblado.append(espacioEtiqueta + "DIVS" + espacioNormal + "#4, D2 \n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "D2, D1 \n");
                    ensamblado.append(".arrayCopy" + arrayCIdx + ":\n");
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(A0)+, " + "(A1)+ ; Store local variable\n");
                    ensamblado.append(espacioEtiqueta + "DBRA" + espacioNormal + "D1, .arrayCopy" + arrayCIdx++ + '\n');
                }else {
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + register + ", " + "(Variable" + variableId + ") ; Store local variable\n");
                }
            }
            if (notGlobal) {
                tablaProcedimientos.get(variable.getIdProcedimiento()).setSizeVariables(tablaProcedimientos.get(variable.getIdProcedimiento()).getSizeVariables() + tablaVariables.get(variableId).getBytes());
            }
        } else if (profx == profp && dx > 0) { //Local parameter
            if (true) {
                ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + register + ", " + "(Variable" + variableId + ") ; Store local parameter\n");
            }
        } else if (profx < profp && dx < 0) { //Foreign variable
            ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "DISP, A0 ; Get DISP @\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + profx + "(A0), A0 ; A0 contains BPx, from DISP[profx]\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + register + ", " + dx + "(A0) ; Store foreign variable\n");
        } else if (profx < profp && dx > 0) { //Foreign parameter
            ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "DISP, A0 ; @DISP in A0\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + profx + "(A0), A0 ; DISP[profx] in A0, it is BPx value\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + "(A0), A0\n");
            ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + register + ", (A0) ; Store foreign parameter\n");
        } else {
            if (dx == 0) {
                if (isString) {
                    ensamblado.append(espacioEtiqueta + "MOVE.L" + espacioNormal + register + ", " + "A0 ; Param gest\n");
                    ensamblado.append(espacioEtiqueta + "LEA" + espacioNormal + "Variable" + variableId + ", " + "A1 ; Param gest\n");
                    ensamblado.append(".loop" + contador + ':' + '\n');
                    ensamblado.append(espacioEtiqueta + "MOVE.B" + espacioNormal + "(A0)+, (A1)+ \n");
                    ensamblado.append(espacioEtiqueta + "TST.B (A0) \n");
                    ensamblado.append(espacioEtiqueta + "BNE .loop" + contador-- + '\n');
                    ensamblado.append(espacioEtiqueta + "MOVE.B" + espacioNormal + "#0, (A1)+ \n");
                }
            } else {
                throw new UnsupportedOperationException("Variable or parameter not valid for storing!");
            }
        }
    }

    

}
