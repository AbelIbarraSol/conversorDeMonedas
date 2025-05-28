package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.ConversionDeMoneda;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            ConversionDeMoneda conversionDeMoneda = new ConversionDeMoneda();
            System.out.print("""
                ***********************************************
                Sea bienvenido/a al Conversor de Moneda =]
                ***********************************************
                OPCIONES:
                1) Dólar =>> Peso argentino
                2) Peso argentino =>> Dólar
                3) Dólar =>> Real brasileño
                4) Real Brasileño =>> Dólar
                5) Dólar =>> Peso colombiano
                6) Peso colombiano ==> Dólar
                7) Salir
                ***********************************************
                Elija una opción valida: """);
            conversionDeMoneda.seleccionarOpcion(scanner.nextInt());
            System.out.print("\nIngrese el monto que desea convertir: ");
            Double monto = scanner.nextDouble();
            System.out.println(conversionDeMoneda.conversion(monto));
        }
    }
}