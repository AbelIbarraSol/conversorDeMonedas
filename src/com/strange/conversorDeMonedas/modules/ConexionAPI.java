package com.strange.conversorDeMonedas.modules;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionAPI {
    public Divisa conectarAPI(String moneda){
        var direcccion = URI.create("https://v6.exchangerate-api.com/v6/ea53538a7170746b7cf35a66/latest/"+moneda);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direcccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(),Divisa.class);
        } catch (Exception e) {
            throw new RuntimeException("Error en la conexión con la API");
        }
    }
}
