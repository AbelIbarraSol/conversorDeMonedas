package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.ConversionDeMoneda;
import com.strange.conversorDeMonedas.modules.ConversionDeMoneda;

public class Principal {
    public static void main(String[] args) {
        /*
        System.out.println("""
                ***********************************************
                Sea bienvenido/a al Conversor de Moneda =]
                1) Dólar =>> Peso argentino
                2) Peso argentino =>> Dólar
                3) Dólar =>> Real brasileño
                4) Real Brasileño =>> Dólar
                5) Dólar =>> Peso colombiano
                6) Peso colombiano ==> Dólar
                7) Salir
                ***********************************************
        """);*/

        ConversionDeMoneda conversionDeMoneda = new ConversionDeMoneda();
        conversionDeMoneda.seleccionarOpcion(5);
        System.out.println(conversionDeMoneda.conversion(10.00));
    }
}