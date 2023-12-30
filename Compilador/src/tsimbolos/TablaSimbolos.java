package tsimbolos;

import sintactico.Parser;
import tsimbolos.auxi.DatosTE;
import tsimbolos.auxi.DatosTD;
import tsimbolos.descripciones.*;

public class TablaSimbolos {

    int n; // Nivel actual (indicador de ambito) apunta al nivel de la tabla de ambitos
    TablaAmbitos ta;
    TablaExpansion te;
    TablaDescripcion td;
    private final Parser parser;

    /**
     * Constructora de la tabla de símbolos
     * 
     * @param parser
     */
    public TablaSimbolos(Parser parser) {
        n = 0;
        ta = new TablaAmbitos();
        te = new TablaExpansion();
        td = new TablaDescripcion();
        this.parser = parser;
        ta.setAmbito(n, 0);
        n++;
        ta.setAmbito(n, -1);
    }

    /**
     * Vacía la tabla de símbolos
     */
    public void vaciar() {
        n = 0;
        // Reseteamos la tabla de descripcion
        td = new TablaDescripcion();
        // Reseteamos la tabla de ambitos
        ta = new TablaAmbitos();
        ta.setAmbito(n, 0);
        n++;
        ta.setAmbito(n, -1);
    }

    /**
     * Mete un identificador en la tabla de símbolos
     * 
     * @param id
     * @return
     */
    public boolean poner(String id, Descripcion d) {
        // Si el identificador ya existe en el nivel actual, error
        if (td.getElemento(id).getnp() == n) {
            System.out.println("ERROR!");
            return false;
        }

        if (td.getElemento(id) != null) {
            // existe en otro nivel distinto al actual
            int idxe = ta.getAmbito(n);
            idxe++;
            ta.setAmbito(n, idxe);
            te.put(idxe, new DatosTE(-1, id, d, td.getElemento(id).getnp()));
        }
        td.insertar(id, new DatosTD(-1, n, d));
        return true;
    }

    /**
     * Mete las dimensiones del array en la tabla de símbolos
     * 
     * @param id
     * @param d
     * @return si se ha podido insertar
     * 
     */
    public boolean ponerIndice(String id, Descripcion d) {
        Descripcion da = td.getElemento(id).getDescripcion();
        // Si no és una taula és un error
        if (da.getTDescripcion() != "darray") {
            throw new UnsupportedOperationException("TablaSimbolos.java: Error del compilador");
        }
        int idxe = td.getElemento(id).getFirst();
        // idxep és l’element anterior a idxe
        int idxep = 0;
        while (idxe != 0) {
            idxep = idxe;
            idxe = te.get(idxep).getNext();
        }
        idxe = ta.getAmbito(n);
        idxe++;
        ta.setAmbito(n, idxe);
        // No és un camp, no té id
        te.put(idxe, new DatosTE(0, "id_nul", d, -1));
        // És el primer índex?
        if (idxep == 0) {
            td.getElemento(id).setFirst(idxe);
        } else {
            // L’índex anterior s’enllaça
            te.get(idxep).setNext(idxe);
        }
        return true;
    }

    public void entrarBloque() {
        n++;
        ta.setAmbito(n, ta.getAmbito(n - 1));
    }

    public void salirBloque() {
        if (n == 0) {
            throw new UnsupportedOperationException("TablaSimbolos.java: Error del compilador");
        } else {
            int idxi = ta.getAmbito(n);
            n--;
            int idxfi = ta.getAmbito(n);
            while (idxi != idxfi) {
                if (te.get(idxi).getNp() != -1) {
                    String id = te.get(idxi).getIdcamp();
                    td.getElemento(id).setnp(te.get(idxi).getNp());
                    td.getElemento(id).setDescripcion(te.get(idxi).getD());
                    td.getElemento(id).setFirst(te.get(idxi).getNext()); // next de l’entrada de la tupla a te és first
                }
                idxi = idxi - 1;
            }
        }
    }

    //checked
    public void ponerParam(String idPr, String idParam, Descripcion d) {
        d = td.getElemento(idPr).getDescripcion();

        if (d.getTDescripcion() != "dproc") {
            throw new UnsupportedOperationException("No existe el procedimiento/función con este nombre: " + idPr);
        }
        
    }
        

    

    public Descripcion consultarTD(String id) {
        return td.getElemento(id).getDescripcion();
    }

    public Descripcion consultarTE(int idx) {
        return te.get(idx).getD();
    }

    public int consultarFirst(String id) {
        if (td.getElemento(id).getDescripcion().getTDescripcion() != "darray") {
            throw new UnsupportedOperationException("TablaSimbolos.java: Error del compilador");
        }
        return td.getElemento(id).getFirst();
    }

    public int consultarNext(int idx) {
        if(te.get(idx).getNext()==0){
            throw new UnsupportedOperationException("TablaSimbolos.java: Error del compilador");
        }
        return te.get(idx).getNext();
    }

    public boolean last(int idx) {
        return te.get(idx).getNext() == 0;
    }

    public String toString() {
        String tabla = "TABLA DE SIMBOLOS\n.............................................\n";
        tabla += ta; // mostramos la tabla de ambitos
        tabla += te; // mostramos la tabla de expansion
        tabla += td; // mostramos la tabla de descripcion
        return tabla;

    }
}
