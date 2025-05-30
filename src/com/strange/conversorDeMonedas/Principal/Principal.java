package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.Conversor;
import com.strange.conversorDeMonedas.modules.Reporte;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conversor conversor = new Conversor();
        Reporte reporte = new Reporte();
        boolean opcion = true;
        while (opcion){
            Conversor.exhibirMenu();
            opcion = conversor.seleccionarOpcion(scanner.nextInt());
            if (opcion){
                System.out.print("\nIngrese el monto que desea convertir: ");

                reporte.almacenarReporte(conversor,scanner.nextDouble());
                reporte.generarReporte();
            }
        }
        scanner.close();
    }
}