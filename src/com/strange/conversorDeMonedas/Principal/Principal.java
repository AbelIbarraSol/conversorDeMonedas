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
            opcion = conversor.seleccionarOpcion(scanner.nextInt());
            if (opcion){
                System.out.print("\nIngrese el monto que desea convertir: ");
                System.out.println(conversor.conversion(scanner.nextDouble()));
            }
        }
        scanner.close();
    }
}