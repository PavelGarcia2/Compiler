package lexico;

public enum Tokens {
    t_program(1),
    t_return(2),
    t_exec(3),
    t_fun(4),
    t_in(5),
    t_sys(6),
    t_and(7),
    t_or(8),
    t_not(9),
    t_asignacion(10),
    t_suma(11),
    t_resta(12),
    t_multiplicacion(13),
    t_division(14),
    t_modulo(15),
    t_mayor(16),
    t_menor(17),
    t_igualMayor(18),
    t_igualMenor(19),
    t_igual(20),
    t_new(21),
    t_case(22),
    t_break(23),
    t_default(24),
    t_if(25),
    t_else(26),
    t_elif(27),
    t_while(28),
    t_for(29),
    t_switch(30),
    t_char(31),
    t_float(32),
    t_int(33),
    t_bool(34),
    t_str(35),
    t_const(36),
    t_tupla(37),
    t_array(38),
    t_lineaCaracteres(39),
    t_caracter(40),
    t_decimal(41),
    t_true(42),
    t_false(43),
    t_print(44),
    t_println(45),
    t_id(46),
    t_numero(47),
    t_saltoLinea(48),
    t_rParentesis(49),
    t_lParentesis(50),
    t_rBracket(51),
    t_lBracket(52),
    t_rCorchete(53),
    t_lCorchete(53),
    t_coma(55),
    t_puntoComa(56),
    t_punto(57),
    t_dosPuntos(58),
    t_Error(59);


    private int valor;

    Tokens(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
