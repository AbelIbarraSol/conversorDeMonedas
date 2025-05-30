package com.strange.conversorDeMonedas.modules;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Conversor {
    public static void exhibirMenu(){
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
    }

    List<String> listaDeMonedas = Arrays.asList("monedaBase", "monedaDestino");
    public boolean seleccionarOpcion(Scanner scanner){
        int opcion = scanner.nextInt();
        switch (opcion){
            case 1 -> {
                listaDeMonedas.set(0,"USD");
                listaDeMonedas.set(1,"ARS");
            }
            case 2 ->{
                listaDeMonedas.set(0,"ARS");
                listaDeMonedas.set(1,"USD");
            }
            case 3 ->{
                listaDeMonedas.set(0,"USD");
                listaDeMonedas.set(1,"BRL");
            }
            case 4 ->{
                listaDeMonedas.set(0,"BRL");
                listaDeMonedas.set(1,"USD");
            }
            case 5 ->{
                listaDeMonedas.set(0,"USD");
                listaDeMonedas.set(1,"COP");
            }
            case 6 ->{
                listaDeMonedas.set(0,"COP");
                listaDeMonedas.set(1,"USD");
            }
            case 7 -> {
                System.out.println("El programa finalizo");
                return false;
            } default -> {
                System.out.println("Opcion invalida, ingrese una opcion");
                return true;
            }
        }

        System.out.print("\nIngrese el monto que desea convertir: ");
        double monto = scanner.nextDouble();
        System.out.println(conversion(monto));
        return true;
    }

    public String conversion(double valorACambiar){
        String monedaBase = listaDeMonedas.get(0);
        String monedaDestino = listaDeMonedas.get(1);
        Double valorEquivalente = new ConexionAPI().conectarAPI(monedaBase, monedaDestino);
        double montoFinal =  valorACambiar * valorEquivalente;
        double montoFinalRedondeado = Math.round(montoFinal * 100.0) / 100.0;
        return "El valor "+valorACambiar+" ["+monedaBase+"] corresponde al valor final de ==> "+montoFinalRedondeado+" ["+monedaDestino+"]";
    }


}
