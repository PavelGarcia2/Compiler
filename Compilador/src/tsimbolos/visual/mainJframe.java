package tsimbolos.visual;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import tsimbolos.TablaExpansion;
import tsimbolos.TablaSimbolos;
import tsimbolos.auxi.DatosTE;
import tsimbolos.descripciones.Descripcion;
import tsimbolos.descripciones.Descripcion.TDesc;

public class mainJframe extends JFrame {
    private TablaSimbolos TS = new TablaSimbolos();

    public mainJframe(TablaSimbolos TS) {
        this.TS = TS;
        // headers for the table
        String[] columns = new String[] {
                "next", "Idcamp", "d", "np", "idTd"
        };

        // actual data for the table in a 2d array

        // leemos los datos de la tabla de simbolos la tabla de expansion

        TablaExpansion te = TS.getTe();
        int tam = te.getSize();
        Object[][] data = new Object[tam][columns.length];
        for (int i = 0; i < tam; i++) {
            DatosTE row = te.get(i);
            Object[] aux = new Object[] { row.getNext(), row.getIdcamp(), row.getD(), row.getNp(), "idTD??" };
            data[i] = aux;

        }
        // create table with data
        JTable table = new JTable(data, columns);

        // add the table to the frame
        this.add(new JScrollPane(table));

        this.setTitle("Tabla de simbolos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public TablaSimbolos getTS() {
        return TS;
    }

    public void setTS(TablaSimbolos tS) {
        TS = tS;
    }

   
}
