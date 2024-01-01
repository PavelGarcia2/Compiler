package tsimbolos.visual;

import javax.swing.SwingUtilities;

import tsimbolos.TablaExpansion;
import tsimbolos.TablaSimbolos;
import tsimbolos.auxi.DatosTE;
import tsimbolos.descripciones.Descripcion;
import tsimbolos.descripciones.Descripcion.TDesc;

public class pruebas {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TablaSimbolos ts = new TablaSimbolos();
                TablaExpansion te = ts.getTe();
                for (int i = 0; i < 100; i++) {
                    te.put(i, new DatosTE(i, "prueba"+i, new Descripcion(TDesc.dcamp), 10-i+100));
       
                }

                ts.setTe(te);
                new mainJframe(ts);
            }
        });
    }
}
