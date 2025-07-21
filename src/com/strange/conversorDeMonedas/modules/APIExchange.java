package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIExchange implements APIServices {
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;
    private String direccion;
    private String key = new JSONManager("apiConfiguration.json").comprobarAPIKeyAlmacenada("ExchangeRate");

    @Override
    public boolean buscarMoneda(String moneda) {
        boolean codigoExiste = false;
        direccion = "https://v6.exchangerate-api.com/v6/"+key+"/codes";
        client= HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new RuntimeException("Error en la comprobacion de la moneda. ");
        }
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray jsonObjectDivisas = jsonObject.getAsJsonArray("supported_codes");

        for(JsonElement element : jsonObjectDivisas){
            JsonArray monedas = element.getAsJsonArray();
            String codigo = monedas.get(0).getAsString();
            if (codigo.equals(moneda)){
                codigoExiste = true;
                break;
            }
        }
        return codigoExiste;
    }

    @Override
    public Double obtenerTasaDeConversion(String monedaBase, String monedaDestino){
        direccion = "https://v6.exchangerate-api.com/v6/"+key+"/pair/"+monedaBase+"/"+monedaDestino;
        client = HttpClient.newHttpClient();
        //Solicitud
        request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        try {
            //Obtener la RESPUESTA como string
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Error en la conexión con la API ExchangeRate"+e.getMessage());
        }

        //Convertir el response.body(string) obtenido y convertirlo a Json otra vez.
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

        //Obtener clave especifica () del JSON completo.
        Double tasaDeconversion = jsonObject.get("conversion_rate").getAsDouble();

        //retornamos la lista de conversiones como un JsonObject
        return tasaDeconversion;
    }

    @Override
    public boolean buscarAPIKey(String key) {
        direccion = "https://v6.exchangerate-api.com/v6/"+key+"/codes";
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
        String resultado = jsonObject.get("result").getAsString();

        if(resultado.equals("success")){
            return true;
        } else {
            return false;
        }
    }
}

