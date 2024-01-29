package tsimbolos;

import java.util.Arrays;
import java.util.HashMap;

import tsimbolos.auxi.Datos;

public class TablaDescripcion {

    private HashMap<String,Datos> td;

    public TablaDescripcion(){
        td = new HashMap<>();
    }

    public void insertar(String id, Datos datos){
        td.put(id, datos);
    }

    public Datos getElemento(String id){
        return td.get(id);
    }

    public boolean existe(String id){
        return td.containsKey(id);
    }


    public String toString() {
        String tabla=" Tabla de descripcion\n.............................................\n";
        tabla+=Arrays.asList(td);
        return tabla+"\n";
    }
}
