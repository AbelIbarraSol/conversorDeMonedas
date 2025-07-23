package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIOpenExchangeRates implements APIServices{
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse response;
    private String direccion;
    private  JSONManager jsonManager = new JSONManager("apiConfiguration.json");
    @Override
    public boolean buscarMoneda(String moneda){
        String key= jsonManager.comprobarAPIKeyAlmacenada("OpenExchangeRates");
        direccion = "https://openexchangerates.org/api/currencies.json?app_id="+key;
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        JsonObject jsonObject = JsonParser.parseString((String) response.body()).getAsJsonObject();
        return jsonObject.has(moneda.toUpperCase());
    }

    @Override
    public Double obtenerTasaDeConversion(String monedaBase, String monedaDestino) {
        String key= jsonManager.comprobarAPIKeyAlmacenada("OpenExchangeRates");
        direccion = "https://openexchangerates.org/api/latest.json?app_id="+key+"&base="+monedaBase+"&symbols="+monedaDestino;
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Error en la conexión con API OpenExchangeRates"+e.getMessage());
        }
        JsonObject jsonObject = JsonParser.parseString((String) response.body()).getAsJsonObject();
        JsonObject tasa = jsonObject.get("rates").getAsJsonObject();
        return tasa.get(monedaDestino).getAsDouble();
    }

    @Override
    public boolean buscarAPIKey(String key) {
        direccion = "https://openexchangerates.org/api/usage.json?app_id="+key;
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new RuntimeException("Error en la conexión con API OpenExchangeRates"+e.getMessage());
        }
        JsonObject jsonObject = JsonParser.parseString((String) response.body()).getAsJsonObject();
        String resultado = jsonObject.get("status").getAsString();

        if(resultado.equals("200")){
            return true;
        } else{
            return false;
        }
    }
}
