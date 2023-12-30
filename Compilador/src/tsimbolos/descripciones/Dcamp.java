package tsimbolos.descripciones;

public class Dcamp extends Descripcion{
    
    private final String tipus;
    private final String nom;

    public Dcamp(String tipus, String nom) {
        super(TDesc.dcamp);
        this.tipus = tipus;
        this.nom = nom;
    }

    public String getTipus() {
        return tipus;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "DCamp{" + "tipus=" + tipus + ", name=" + nom + '}';
    }
    
}