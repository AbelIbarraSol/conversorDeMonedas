package com.strange.conversorDeMonedas.modules;


public class APIFactory {
    public APIServices getAPISeleccionada(){
        String apiSelected = new JSONManager("apiConfiguration.json").obtenerAPISeleccionada();
        switch (apiSelected){
            case "ExchangeRate":    return new APIExchange();
            case "OpenExchangeRates":   return new APIOpenExchangeRates();
            default: throw new RuntimeException("¡API no seleccionada!");
        }
    }
}
