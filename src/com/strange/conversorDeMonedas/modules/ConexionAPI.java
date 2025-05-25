package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionAPI {
    public JsonObject conectarAPI(String moneda){
        var direcccion = URI.create("https://v6.exchangerate-api.com/v6/ea53538a7170746b7cf35a66/latest/"+moneda);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direcccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            //Parsear el JSON obtenido.
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            //Obtener clave especifica (sub objeto Json) del JSON completo.
            JsonObject divisas = json.getAsJsonObject("conversion_rates");
            //retornamos la lista de conversiones como un JsonObject
            return divisas;
        } catch (Exception e) {
            throw new RuntimeException("Error en la conexión con la API");
        }
    }
}
