package semantico;


import java.util.ArrayList;

import semantico.Operador3Direcciones.TipoI;

public class Optimizaciones {

    private final ArrayList<Intruccion3Direcciones> intrucciones;

    public Optimizaciones(Codigo3Direcciones c3d){
        this.intrucciones = new ArrayList<>(c3d.getIntrucciones());
    }

    public boolean bracamentsAdjacents(){
        boolean cambio=false;
        for(int i =0; i< intrucciones.size(); i++){
            //si nos encontramos con un if de cualquier tipo lo modificamos 

        }

        return cambio;

    }

    
    
    public boolean bracamentsSobreBrancaments(){
        return false;
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
