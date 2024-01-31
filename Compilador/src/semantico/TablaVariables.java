package semantico;

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

}