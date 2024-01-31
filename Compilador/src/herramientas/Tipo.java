package herramientas;

public enum Tipo {
    tsb_int,
    tsb_char,
    tsb_str,
    tsb_bool,
    tsb_void,
    tsb_float,
    tsb_true,
    tsb_false;

    public int getBytes(){
        switch (this){
            case tsb_int:
                return Integer.BYTES;
            case tsb_char:
                return Character.BYTES;
            case tsb_str:
                return 128;
            case tsb_bool:
                return 1;
            case tsb_void:
                return -1;
            case tsb_float:
                return Float.BYTES;
            case tsb_true:
                return 1;
            case tsb_false:
                return 1;
        }
        return -2;
    }
}
