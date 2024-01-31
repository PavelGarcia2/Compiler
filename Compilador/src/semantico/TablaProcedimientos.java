package semantico;

import java.util.HashMap;
import java.util.Set;

public class TablaProcedimientos{

    private final HashMap<String, Procedimiento> tablaProcedimientos;
    private int np;

    public TablaProcedimientos(){
        tablaProcedimientos = new HashMap<>();
        np = 0;
    }

    public Procedimiento get(String id){
        return tablaProcedimientos.get(id);
    }

    public Set<String> keySet(){
        return tablaProcedimientos.keySet();
    }


    public boolean put(String id, Procedimiento procedimiento){

        if(tablaProcedimientos.containsKey(id)){
            return false;
        }

        tablaProcedimientos.put(id, procedimiento);
        np++;
        return true;
    }

    public int getNP(){
        return np;
    }

    public void decrementarNP(){
        np--;
    }
}