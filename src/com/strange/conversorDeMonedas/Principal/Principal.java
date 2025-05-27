package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.ConexionAPI;
import com.strange.conversorDeMonedas.modules.ConversionDeMoneda;
import com.strange.conversorDeMonedas.modules.ConversionDeMoneda;

public class Principal {
    public static void main(String[] args) {

        ConversionDeMoneda conversionDeMoneda = new ConversionDeMoneda();

        var conexionAPI = new ConexionAPI().conectarAPI("USD","BRL");
        System.out.println(conexionAPI);

    }
}