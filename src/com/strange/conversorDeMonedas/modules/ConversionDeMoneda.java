package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.List;

public class ConversionDeMoneda {
    List<String> listaDeMonedas = Arrays.asList("monedaBase", "monedaDestino");
    String monedaBase, monedaDestino;
    double valorEquivalente = 0.0;

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
            case 7 -> System.out.println("El programa finalizo");
        }

        monedaBase = listaDeMonedas.get(0);
        monedaDestino= listaDeMonedas.get(1);

        ConexionAPI conexionAPI = new ConexionAPI();

        JsonObject json = JsonParser.parseString(conexionAPI.conectarAPI(monedaBase).toString()).getAsJsonObject();
        //System.out.println(json);
        valorEquivalente = json.get(monedaDestino).getAsDouble();
    }

    public Double conversion(double valorACambiar){
        Double montoFinal =  valorACambiar * valorEquivalente;
        Double montoFinalRedondeado = Math.round(montoFinal * 100.0) / 100.0;
        //System.out.println("1 "+monedaBase+" equivale a "+String.format("%.8f",valorEquivalente)+" "+monedaDestino);
        //System.out.print(valorACambiar+" x "+valorEquivalente+" = ");
        return montoFinalRedondeado;
    }


}
