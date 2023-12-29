package tsimbolos;

public class test {
    public static void main(String[] args) {

        TablaAmbitos ta = new TablaAmbitos();

        // insertar un ambito
        for (int i = 0; i < 200; i++) {
            ta.nuevoAmbito(i);
        }
        System.out.println(ta);

        ta.nuevoAmbito(0);
        System.out.println("Ambito: "+ta.getAmbito(60));

        // que pasa si queremos acceder a una posicion no existente
        ta.setAmbito(0, 100);
        System.out.println("Ambito: "+ta.getAmbito(0));

        // eliminar un ambito
        ta = new TablaAmbitos();
        ta.nuevoAmbito(44);
        System.out.println("Ambito: "+ta.getAmbito(2));
    }
}
