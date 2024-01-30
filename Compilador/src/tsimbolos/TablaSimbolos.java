package tsimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import sintactico.Parser;
import tsimbolos.auxi.Datos;
import tsimbolos.descripciones.*;
import sintactico.arbol.Nodo;

public class TablaSimbolos {

    int n; // Nivel actual (indicador de ambito) apunta al nivel de la tabla de ambitos
    private final int maxCap = 50;
    private NodoExpansion[] ta; // Tabla de ambitos contiene apuntadores hacia NodosExpansion de la tabla de
                                // expansion
    private LinkedList<NodoExpansion> te; // Tabla de expansion contiene las declaraciones visibles y no visibles
    private HashMap<String, Datos> td; // Tabla de descripcion contiene las descripciones visibles
    private ArrayList<ArrayList<String>> toRemove; // Lista de listas de ids a eliminar

    private final Parser parser;

    /**
     * Constructora de la tabla de símbolos
     * 
     * @param parser
     */
    public TablaSimbolos(Parser parser) {
        n = 0;
        ta = new NodoExpansion[maxCap]; // Init tabla de ambitos
        te = new LinkedList<>(); // Init tabla de expansion
        td = new HashMap<>(500); // Init tabla de descripcion
        toRemove = new ArrayList<>(maxCap);
        for (int i = 0; i < maxCap; i++) {
            toRemove.add(new ArrayList<String>());
        }
        this.parser = parser;
    }
    /**
     * Vacía la tabla de símbolos
     */
    public void vaciar() {
        td.clear();
        te.clear();
        for(int i = n ; i >= 0 ; i--){
            ta[i] = null;
        }
        n=0;
    }

    /**
     * Mete un identificador en la tabla de símbolos
     * 
     * @param id
     * @return
     */
    public void poner(String id, Descripcion d, Nodo nodo) {

        Datos elementoAntiguo = td.get(id);
        Datos elementoNuevo = new Datos(d, id,n);
        NodoExpansion nodoExp;
        boolean cambio= false;

        if(elementoAntiguo != null){
            if(elementoAntiguo.getNp() == 1){
                parser.report_error("Estas intentando sobreescribir una variable global", nodo);
            }
            if(elementoAntiguo.getNp() == n){
                //Error semántico
                parser.report_error("El simbolo ya esta declarado en el nivel actual", nodo);
            }else{
                nodoExp = new NodoExpansion(elementoAntiguo,elementoNuevo);
                te.add(nodoExp);
                ta[n]= nodoExp;
                cambio = true;
            }
            
        }

        if(!cambio){
            toRemove.get(n).add(id);
        }
        System.out.println("Hemos añadido a la tabla de simbolos "+id);
        td.put(id, elementoNuevo);
    }

    public void entrarBloque() {
        n++;
        ta[n] = ta[n-1];
    }

    public void salirBloque() {
        if(n==0){
            //Error del compilador
        }

        for(String eliminar : toRemove.get(n)){
            td.remove(eliminar);
        }

        toRemove.get(n).clear();

        Iterator<NodoExpansion> iter = te.descendingIterator();
        if(iter.hasNext()){
            NodoExpansion actual = iter.next();
            ta[n--] = null;

            while(ta[n] != actual){
                actual.visible.setNp(actual.notVisible.getNp());
                actual.visible.setDescripcion(actual.notVisible.getDescripcion());
                iter.remove();
            
                if(iter.hasNext()){
                    actual = iter.next();
                }else{
                    break;
                }
            
            }
        }else{
            n--;
        }
    }

    public void salirBloqueFunc() {

    }

    public void ponerParam(String idPr, String idParam, Descripcion d) {

    }

    public Descripcion consultarTD(String id) {
        System.out.println("Consultamos la tabla de simbolos "+id);
        if(td.get(id) != null){
            return td.get(id).getDescripcion();
        }
        return null;
    }

    // public Descripcion consultarTE(int idx) {

    // }

    // public int consultarFirst(String id) {

    // }

    // public int consultarNext(int idx) {

    // }

    // public boolean last(int idx) {

    // }

    // public String toString() {

    // }

    // public TablaExpansion getTe() {

    // }

    // public void setTe(TablaExpansion te) {

    // }

    // /**
    //  * Mete las dimensiones del array en la tabla de símbolos
    //  * 
    //  * @param id
    //  * @param d
    //  * @return si se ha podido insertar
    //  * 
    //  */
    // public boolean ponerIndice(String id, Descripcion d) {
    // }
}
