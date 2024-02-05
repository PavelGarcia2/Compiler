package semantico;


import java.util.ArrayList;

import herramientas.Tipo;
import semantico.Operador3Direcciones.TipoCambio;
import semantico.Operador3Direcciones.TipoI;

public class Optimizaciones {

    private final ArrayList<Intruccion3Direcciones> intrucciones;

    public Optimizaciones(Codigo3Direcciones c3d){
        this.intrucciones = new ArrayList<>(c3d.getIntrucciones());
    }

    public boolean bracamentsAdjacents(){
        System.out.println("Bracaments Adjacents");
        boolean cambio=false;
        for(int i =0; i< intrucciones.size(); i++){
            //si nos encontramos con un if de cualquier tipo lo modificamos 
            if(logico(intrucciones.get(i).getTipoIntruccion())){
                System.out.println("Intruccion If Encontrada:"+intrucciones.get(i).toString());
                Operador3Direcciones op3 = intrucciones.get(i).getOperadores()[2]; //tipo de if
                int posicionIf = i;
                i++;


                System.out.println("Intruccion IfTrueGoto:"+intrucciones.get(i).toString());
                Operador3Direcciones op1 = intrucciones.get(i).getOperadores()[2]; //if true goto
                System.out.println("Valor res del if:"+op3.toString()); 
                int posicionIfGoto = i;
                i++;

                

                if(i < intrucciones.size() && intrucciones.get(i).getTipoIntruccion() == TipoInstruccion.GOTO){
                    System.out.println("Intruccion Goto Encontrada:"+intrucciones.get(i).toString());
                    Operador3Direcciones op2 = intrucciones.get(i).getOperadores()[2]; // siguiente salto goto
                    int posicionGoto = i;
                    i++;

                    System.out.println("Intruccion Aumentada++:"+intrucciones.get(i).toString());


                    System.out.println("Vakir etiqueta1: "+op1.getEtiqueta() );
                    System.out.println("Vakir op2Eti: "+intrucciones.get(i).getOperadores()[2].getEtiqueta() );
                    if(i < intrucciones.size() && intrucciones.get(i).getTipoIntruccion() == TipoInstruccion.SKIP && intrucciones.get(i).getOperadores()[2].getEtiqueta().equals(op1.getEtiqueta())){

                        System.out.println("etnbro");
                        intrucciones.get(posicionIf).setTipoIntruccion(opuesto(intrucciones.get(posicionIf).getTipoIntruccion())); //niego el if
                        System.out.println("Intruccion cambiada if:"+intrucciones.get(posicionIf).toString());
                        //intrucciones.get(posicionIfGoto).setOperador(new Operador3Direcciones("", op1.getValString(), TipoCambio.BOOL), 0);
                        intrucciones.get(posicionIfGoto).setOperador(op2, 2);
                        System.out.println("Intruccion ifgoto:"+intrucciones.get(posicionIfGoto).toString());
                        intrucciones.remove(posicionGoto);
                        System.out.println("Intruccion eliminada posgoto:"+intrucciones.get(posicionGoto).toString());
                        cambio = true;

                        //miramos si podemos borrar el skip
                        boolean borrar = true;
                        for(int j =0; j< intrucciones.size(); j++){

                            System.out.println("Intruccion anlizanda:"+intrucciones.get(j).toString());
                            System.out.println("valor arit: "+aritmetico(intrucciones.get(j).getTipoIntruccion()) );
                            System.out.println("Valor comp: "+ (intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.GOTO));
                            System.out.println("Vakir etiqueta1: "+op1.getEtiqueta() );
                            System.out.println("Valor etiq2: "+intrucciones.get(j).getOperadores()[2]);
                            System.out.println("Valor op1: "+op1.getEtiqueta());


                            if((aritmetico(intrucciones.get(j).getTipoIntruccion()) || intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.GOTO) && (intrucciones.get(j).getOperadores()[2].getEtiqueta() != null && intrucciones.get(j).getOperadores()[2].getEtiqueta().equals(op1.getEtiqueta()))){
                                borrar = false;
                                break;
                            }
                            System.out.println("SIguiente intruccion");
                        }

                        if(borrar){
                            intrucciones.remove(posicionGoto);
                        }
                    }
                }
            }

            System.out.println("Intruccion:"+intrucciones.get(i).toString()); 
        }
        return cambio;
    }

    
    
    public boolean bracamentsSobreBrancaments(){
        System.out.println("Bracaments Sobre Brancaments");
        boolean cambio = false;
        for (int i = 0; i < intrucciones.size(); i++) {
            if (logico(intrucciones.get(i).getTipoIntruccion())) {
                int posIf = i;
                Operador3Direcciones e1 = intrucciones.get(i).getOperadores()[2];
                for (int j = i + 1; j < intrucciones.size(); j++) {
                    if (intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.SKIP && intrucciones.get(j).getOperadores()[2].getEtiqueta().equals(e1.getEtiqueta())) {
                        if (j + 1 < intrucciones.size() && intrucciones.get(j + 1).getTipoIntruccion() == TipoInstruccion.GOTO) {
                            intrucciones.get(posIf).setOperador(intrucciones.get(j + 1).getOperadores()[2],2);
                            boolean borrar = true;
                            for (int k = 0; k < intrucciones.size(); k++) {
                                if ((logico(intrucciones.get(k).getTipoIntruccion()) || intrucciones.get(k).getTipoIntruccion() == TipoInstruccion.GOTO) && intrucciones.get(k).getOperadores()[2].getEtiqueta().equals(e1.getEtiqueta())) {
                                    borrar = false;
                                    break;
                                }
                            }
                            if (borrar) {
                                cambio = true;
                                intrucciones.remove(j);
                            }
                        }
                    }
                }
            }
        }
        return cambio;
    }
    
    public boolean asignacioDeBooleanos(){
        return false;
    }
    
    public boolean operacionesConstantes(){

        return false;
    }

    public boolean eliminacionCodigoInaccesible(){
        return false;
    }
    
    public boolean desplazamientoDeConstantes(){
        return false;
    }
    
    public boolean normalizacionOperaciones(){

        return false;
        
    }
    
    public boolean asignacionesDiferidas(){
        System.out.println("Asignaciones Diferidas");
        boolean cambio = false;
        for(int i=0; i< intrucciones.size();i++){
            if(aritmetico(intrucciones.get(i).getTipoIntruccion()) || 
            intrucciones.get(i).getTipoIntruccion() == TipoInstruccion.COPIA ||
            intrucciones.get(i).getTipoIntruccion() == TipoInstruccion.NEGACION ||
            intrucciones.get(i).getTipoIntruccion() == TipoInstruccion.IND_VAL){

                int referencia = intrucciones.get(i).getOperadores()[2].getReferencia();
                int contador=0;
                int posicion = -1;
                for(int j=0; j < intrucciones.size();j++){

                    if(j != i){
                        if( (intrucciones.get(j).getOperadores()[0] != null && intrucciones.get(j).getOperadores()[0].getTipo() == TipoI.REFERENCIA && intrucciones.get(j).getOperadores()[0].getReferencia() == referencia) ||
                            (intrucciones.get(j).getOperadores()[1] != null && intrucciones.get(j).getOperadores()[1].getTipo() == TipoI.REFERENCIA && intrucciones.get(j).getOperadores()[1].getReferencia() == referencia) ||
                            (intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.PARAM_SIMPLE && intrucciones.get(j).getOperadores()[2] != null && intrucciones.get(j).getOperadores()[2].getReferencia() == referencia) ||
                            (intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.RETURN && intrucciones.get(j).getOperadores()[2] != null && intrucciones.get(j).getOperadores()[2].getReferencia() == referencia) ){

                            if(contador == 1){
                                contador++;
                                break;
                            }else{
                                contador++;
                                posicion = j;
                            }
                        }

                        if((aritmetico(intrucciones.get(j).getTipoIntruccion()) || 
                           intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.COPIA ||
                           intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.NEGACION ||
                           intrucciones.get(j).getTipoIntruccion() == TipoInstruccion.IND_VAL) && 
                           intrucciones.get(j).getOperadores()[2].getReferencia() == referencia){

                            contador=2;
                            break;

                        }
                    }
                }

                if(contador == 1){

                    if(intrucciones.get(i).getTipoIntruccion() == TipoInstruccion.COPIA || intrucciones.get(i).getTipoIntruccion() == TipoInstruccion.NEGACION) {

                        Operador3Direcciones op = intrucciones.get(i).getOperadores()[0];

                        if(intrucciones.get(posicion).getTipoIntruccion() == TipoInstruccion.COPIA || 
                        intrucciones.get(posicion).getTipoIntruccion() == TipoInstruccion.NEGACION || 
                        intrucciones.get(posicion).getTipoIntruccion() == TipoInstruccion.IN){

                            intrucciones.get(posicion).setOperador(op, 0);
                            intrucciones.remove(i);
                            cambio = true;
                            i--;

                        }else if(intrucciones.get(posicion).getTipoIntruccion() == TipoInstruccion.PRINT || intrucciones.get(posicion).getTipoIntruccion() == TipoInstruccion.PRINTLN){
                            //no hacer nada
                        }else{

                            if(intrucciones.get(posicion).getOperadores()[0] != null && intrucciones.get(posicion).getOperadores()[0].getTipo() == TipoI.REFERENCIA && intrucciones.get(posicion).getOperadores()[0].getReferencia() == referencia){

                                intrucciones.get(posicion).setOperador(op, 0);
                            }else{
                                intrucciones.get(posicion).setOperador(op, 1);
                            }
                            intrucciones.remove(i);
                            cambio = true;
                            i--;
                        }

                    }else{
                        Operador3Direcciones op1 = intrucciones.get(i).getOperadores()[0];
                        Operador3Direcciones op2 = intrucciones.get(i).getOperadores()[1];
                        if(intrucciones.get(posicion).getTipoIntruccion() == TipoInstruccion.COPIA){

                            intrucciones.get(posicion).setTipoIntruccion(intrucciones.get(i).getTipoIntruccion());
                            intrucciones.get(posicion).setOperador(op1, 0);
                            intrucciones.get(posicion).setOperador(op2, 1);
                            intrucciones.remove(i);
                            cambio = true;
                            i--;
                        }
                    }
                }

            }
        }
        return cambio;
        
    }

    
    private boolean logico(TipoInstruccion tipo) {
        return tipo == TipoInstruccion.IFIGUALI || tipo == TipoInstruccion.IFMAYORIGUAL || tipo == TipoInstruccion.IFMAYOR
                || tipo == TipoInstruccion.IFMENORIGUAL || tipo == TipoInstruccion.IFMENOR || tipo == TipoInstruccion.IFDIFERENTE;
    }
    
    private boolean aritmetico(TipoInstruccion tipo) {
        return tipo == TipoInstruccion.RESTA || tipo == TipoInstruccion.SUMA || tipo == TipoInstruccion.DIVISION
                || tipo == TipoInstruccion.MULTIPLICACION || tipo == TipoInstruccion.MODULO;
    }
    
    private TipoInstruccion opuesto(TipoInstruccion tipo) {
        if (tipo == TipoInstruccion.IFIGUALI) {
            return TipoInstruccion.IFDIFERENTE;
    
        } else if (tipo == TipoInstruccion.IFDIFERENTE) {
            return TipoInstruccion.IFIGUALI;
    
        } else if (tipo == TipoInstruccion.IFMAYOR) {
            return TipoInstruccion.IFMENORIGUAL;
    
        } else if (tipo == TipoInstruccion.IFMAYORIGUAL) {
            return TipoInstruccion.IFMENOR;
    
        } else if (tipo == TipoInstruccion.IFMENOR) {
            return TipoInstruccion.IFMAYORIGUAL;
    
        } else if (tipo == TipoInstruccion.IFMENORIGUAL) {
            return TipoInstruccion.IFMAYOR;
    
        } else if (tipo == TipoInstruccion.SUMA) {
            return TipoInstruccion.RESTA;
        }
    
        return null;
    }

    public ArrayList<Intruccion3Direcciones> getIntrucciones() {
        return intrucciones;
    }
}
