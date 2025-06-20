package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionAPI {
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public Double obtenerTasaDeConversion(String monedaBase, String monedaDestino){
        String direccion = "https://v6.exchangerate-api.com/v6/ea53538a7170746b7cf35a66/pair/";
        client = HttpClient.newHttpClient();
        //Solicitud
        request = HttpRequest.newBuilder()
                .uri(URI.create(direccion+monedaBase+"/"+monedaDestino))
                .build();
        try {
            //Obtener la RESPUESTA como string
            HttpResponse<String> repuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Error en la conexión con la API"+e.getMessage());
        }

        //Convertir el response.body(string) obtenido y convertirlo a Json otra vez.
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

        //Obtener clave especifica () del JSON completo.
        Double tasaDeconversion = jsonObject.get("conversion_rate").getAsDouble();

        //retornamos la lista de conversiones como un JsonObject
        return tasaDeconversion;
    }

    public boolean comprobarExistenciaDeMoneda(String codigoNuevo){
        boolean codigoExiste = false;
        var direccion = URI.create("https://v6.exchangerate-api.com/v6/ea53538a7170746b7cf35a66/codes");
        client= HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(direccion)
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

            if (codigo.equals(codigoNuevo)){
                codigoExiste = true;
                break;
            }
        }

        return codigoExiste;

    }

}
