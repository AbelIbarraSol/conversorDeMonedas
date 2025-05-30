package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.Conversor;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conversor conversor = new Conversor();

        boolean opcion = true;
        while (opcion){
            Conversor.exhibirMenu();
            opcion = conversor.seleccionarOpcion(scanner);
        }
        scanner.close();
    }
}