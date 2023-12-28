package tsimbolos;

import java.util.ArrayList;

public class TablaAmbitos {

    private ArrayList<Integer> ambitos = new ArrayList<>();
    int nivel;

    public TablaAmbitos() {
        nivel = 0;
    }

    public void nuevoAmbito() {
        ambitos.add(nivel, nivel);
        nivel++;
    }

    public void setAmbito(int n, int valor) {
        ambitos.add(n, valor);
    }

    public int getAmbito(int n) {
        return ambitos.get(n);
    }
}
