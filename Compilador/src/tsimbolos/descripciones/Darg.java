package tsimbolos.descripciones;

public class Darg extends Descripcion{

    String tipus;       // identificador del tipus del paràmetre
    Boolean tipusAcces; //segons l’accés, pot tenir dos valors:  
                        // * mode_in = True
                        // * mode_in_out = False
    String nom;         // nom de l’argument

    public Darg(String tipus, Boolean tipusAcces, String nom){
        super(TDesc.darg);
        this.tipus = tipus;
        this.tipusAcces = tipusAcces;
        this.nom = nom;
    }

    public String getTipus() {
        return tipus;
    }

    public Boolean getTipusAcces() {
        return tipusAcces;
    }

    public String getNom() {
        return nom;
    }


    @Override
    public String toString() {
        return "Darg{" +"tipus='" + tipus + '\'' +", tipusAcces=" + tipusAcces +", nom='" + nom + '\'' +'}';
    }
   
}
