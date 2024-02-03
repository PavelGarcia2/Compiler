package main;

/**
 * Assignatura 21742 - Compiladors I 
 * Estudis: Grau en Informàtica 
 * Itinerari: Computació 
 * Curs: 2018-2019
 *
 * Professor: Pere Palmer
 */
import lexico.Scanner;
import semantico.Semantico;
import sintactico.Parser;

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

/**
 * classe principal. Permet llegir una expressió des de teclat o des d'un fitxer
 * i l'analitza. Si és correcte en dóna el resultat.
 * 
 * @author Pere Palmer
 */
public class Main {

    /**
     * @param args arguments de línia de comanda
     */
    public static void main(String[] args) {
        Reader input;
        try {
            // Get the current working directory
            String workingDirectory = System.getProperty("user.dir");
            // Print the current working directory
            System.out.println("Current Working Directory: " + workingDirectory);
            if (args.length > 0) {
                
                System.out.println(args[0]);
                input = new FileReader(args[0]);
                //si existe el fichero tokens.txt lo borramos
                File fichero = new File("tokens.txt");
                if (fichero.exists()) {
                    fichero.delete();
                }

                SymbolFactory sf = new ComplexSymbolFactory();
                Scanner scanner = new Scanner(input);
                Parser parser = new Parser(scanner, sf);
                Symbol result = parser.parse();
                //new mainJframe(Semantico.getTs());
                //System.out.println("Resultat: " + result.value);
                //System.out.println(Semantico.getTs());
                
                
            } else {
                System.out.println("No has introducido ningun archivo!");
            }

        } catch (Exception e) {
            System.err.println("error: " + e);
        }
    }

}
