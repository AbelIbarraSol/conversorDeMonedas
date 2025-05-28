package com.strange.conversorDeMonedas.modules;

import java.util.Arrays;
import java.util.List;

public class ConversionDeMoneda {
    List<String> listaDeMonedas = Arrays.asList("monedaBase", "monedaDestino");
    public void seleccionarOpcion(int opcion){
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
                System.exit(0);
            }
        }
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
