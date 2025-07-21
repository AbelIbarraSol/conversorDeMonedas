package com.strange.conversorDeMonedas.modules;

public interface APIServices {
    boolean buscarMoneda(String moneda);
    Double obtenerTasaDeConversion(String monedaBase, String monedaDestino);
    boolean buscarAPIKey(String key);
}
