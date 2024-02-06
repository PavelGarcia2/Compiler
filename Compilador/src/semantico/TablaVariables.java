package semantico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TablaVariables{

    private final ArrayList<Variable> variables;
    private int nv;

    public TablaVariables(){
        variables = new ArrayList<>();
        nv = 0;
    }

    public Variable get(int i){
        return variables.get(i);
    }

    public Variable put(Variable var){
        variables.add(var);
        nv++;
        return var;
    }

    public int getNV(){
        return nv;
    }

    public void decrementarNV(){
        nv--;
    }

    public void imprimirVariables(){

        resultadoTS.setLength(0);
        resultadoTS.append("Contenido de la tabla de variables\n\n");
        for (Variable var : variables) {
            resultadoTS.append(var.toString()).append("\n");
        }

        escribeTS(resultadoTS.toString());
    }

    private StringBuilder resultadoTS = new StringBuilder();

    private void escribeTS(String token){
        //mete el token leido en un txt 
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("Compilador/src/resultadosEjecucion/tablaVariables.txt", false));
            writer.write(token);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}