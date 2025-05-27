package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionAPI {
    public Double conectarAPI(String monedaBase, String monedaDestino){
        var direcccion = URI.create("https://v6.exchangerate-api.com/v6/ea53538a7170746b7cf35a66/pair/"+monedaBase+"/"+monedaDestino);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direcccion)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //Parsear el response.body(texto plano) obtenido y convertirlo a Json otra vez.
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            //Obtener clave especifica () del JSON completo.
            Double tasaDeconversion = json.get("conversion_rate").getAsDouble();
            //retornamos la lista de conversiones como un JsonObject
            return tasaDeconversion;
        } catch (Exception e) {
            throw new RuntimeException("Error en la conexión con la API"+e.getMessage());
        }
    }
}
