package tsimbolos;

import java.util.*;
import sintactico.Parser;


public class TablaSimbolos {
    

    int n; // Nivel actual (indicador de ambito) apunta al nivel de la tabla de ambitos
    TablaAmbitos ta;
    TablaExpansion te;
    TablaDescripcion td;
    private final Parser parser;

    public TablaSimbolos(Parser parser){
        n=0;
        ta = new TablaAmbitos();
        te = new TablaExpansion();
        td = new TablaDescripcion();
        this.parser = parser;
        ta.setAmbito(n,0);
        n++;
        ta.setAmbito(n,-1);
    }

    public void vaciar(){
        int n=0;
        for(){
            //vaciar tabla de descripción
            
        }
        //Reseteamos la tabla de ambitos
        ta = new TablaAmbitos();

    }

    public void poner(){
        
    }

    public void consultar(){

    }

    public void entrarBloque(){

    }

    public void salirBloque(){

    }
}
