package com.strange.conversorDeMonedas.modules;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JSONManager {
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    private String ruta = "src/com/strange/conversorDeMonedas/resources/";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JSONManager(String archivoJson){
        try {
            //Leer archivo Json
            String contenido = Files.readString(Paths.get(ruta+archivoJson));
            jsonObject = JsonParser.parseString(contenido).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonArray convertirAJsonArray(String key){
        //Extraer array key del jsonObject y convertirlo en un JsonArray
        return jsonArray = jsonObject.getAsJsonArray(key);
    }

    public boolean agregarMonedas(Scanner scanner){
        while(true){
            System.out.print("🔸 Ingrese el codigo de divisa: ");
            String input = scanner.nextLine();
            if (input.matches(".*\\d.*")) { // Expresión regular que detecta cualquier número en el texto
                System.out.println("""
                ┌─────────────────────────────────────────────────┐
                         🚨 Error: No se permiten números.          
                                Ingresa solo texto.
                └─────────────────────────────────────────────────┘
                """);
                continue;
            }

            if (input.equalsIgnoreCase("atras")){
                return false;
            }

            if(!new APIFactory().getAPISeleccionada().buscarMoneda(input.toUpperCase())){
                System.out.println("🚨 El codigo ingresado no pertenece a una moneda real");
                continue;
            }

            JsonArray listaMonedas = convertirAJsonArray("monedas");
            boolean monedaExiste = false;
            for (JsonElement moneda : listaMonedas) {
                if (moneda.getAsString().equalsIgnoreCase(input)) {
                    monedaExiste = true;
                    System.out.printf("""
                    ┌─────────────────────────────────────────────────┐
                            🚨 %s ya se encuentra registrado 🚨 
                    └─────────────────────────────────────────────────┘\n
                    """,input);
                    break;
                }
            }

            if (!monedaExiste){
                listaMonedas.add(input.toUpperCase());
                jsonObject.add("monedas",listaMonedas);

                //Almacenar en el JsonObject
                String jsonActualizado = gson.toJson(jsonObject);

                //Almacenar en el archivo
                try {
                    Files.writeString(Paths.get(ruta+"monedas.json"), jsonActualizado);
                    System.out.println("""
                    ┌─────────────────────────────────────────────────┐
                                   ✅¡Moneda Registrada!✅
                    └─────────────────────────────────────────────────┘
                    """);
                    return false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void almacenarReporte(String conversionRealizada, LocalDateTime horaDeConversion){
        //Formato
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        horaDeConversion.format(formato);
        jsonArray = convertirAJsonArray("conversiones");

        JsonArray nuevaConversion = new JsonArray();
        nuevaConversion.add(conversionRealizada);
        nuevaConversion.add(horaDeConversion.format(formato));

        jsonArray.add(nuevaConversion);

        jsonObject.add("conversiones",jsonArray);

        String jsonActualizado = gson.toJson(jsonObject);

        try {
            //INSERTAR jsonobject en archivo
            Files.writeString(Paths.get(ruta+"historial.json"), jsonActualizado );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void generarReporte(){
        jsonArray = convertirAJsonArray("conversiones");
        for (int i = 0; i < jsonArray.size(); i++){
            JsonArray elements = jsonArray.get(i).getAsJsonArray();
            String descripcion = elements.get(0).getAsString();
            String fecha = elements.get(1).getAsString();
            System.out.printf("%-50s %-50s\n",descripcion,fecha);
        }
    }

    public void almacenarKEYEnJSON(String api,String key){
        JsonObject listaDeAPIs = jsonObject.getAsJsonObject("api-key");
        listaDeAPIs.addProperty(api, key);
        jsonObject.add("api-key",listaDeAPIs);
        String jsonObjectActualizado = gson.toJson(jsonObject);
        try {
            Files.writeString(Paths.get(ruta+"apiConfiguration.json"), jsonObjectActualizado );
            System.out.println("✅ ¡API Key guardada!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean agregarAPIKey(int opcion, String api, Scanner scanner){
        while (true){
            System.out.print("Ingrese el API Key de "+api+": ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("atras")) return false;
            switch (opcion){
                case 1 ->{
                    if (new APIExchange().buscarAPIKey(input)){
                        almacenarKEYEnJSON(api, input);
                        return false;
                    } else {
                        System.out.println("🚨 La API KEY ingresada no es valida. 🚨\nVuelve a ingresar la API KEY correcta o comunicate con %s".formatted(api));
                    }
                }
                case 2 ->{
                    if (new APIOpenExchangeRates().buscarAPIKey(input)){
                        almacenarKEYEnJSON(api, input);
                        return false;
                    } else {
                        System.out.println("🚨 La API KEY ingresada no es valida. 🚨\nVuelve a ingresar la API KEY correcta o comunicate con %s".formatted(api));
                    }
                }
            }
        }
    }

    public String comprobarAPIKeyAlmacenada(String api){
        String key = obtenerAPIKey(api);
        if (key == null || key.isEmpty()){
            throw new IllegalStateException("No se encontro una API key valida para "+api+". Registra una api en el programa antes de continuar.");
        }
        return key;
    }

    public String obtenerAPIKey(String api){
        JsonObject listaDeAPIs = jsonObject.getAsJsonObject("api-key");
        return listaDeAPIs.get(api).getAsString();
    }

    public String obtenerAPISeleccionada(){
        JsonElement jsonElement = jsonObject.get("api-selected");
        String apiSeleccionada = jsonElement.getAsString();
        if (apiSeleccionada.isEmpty()) {
            System.out.println("""
        🚨¡API NO SELECCIONADA!🚨
        🟣 Pasos para seleccionar una API predeterminada:
        1. Dirígete al Menú Principal.
        2. Selecciona la opción [4. Lista de APIs disponibles].
        3. Elige la API que desees utilizar.
        4. Finalmente, selecciona la opción [3.Establecer como API predeterminada].""");
        }
        return apiSeleccionada;
    }
    public void seleccionarAPIPredeterminada(String api){
        jsonObject.addProperty("api-selected", api);
        String jsonObjectActualizado = gson.toJson(jsonObject);
        try {
            Files.writeString(Paths.get(ruta+"apiConfiguration.json"), jsonObjectActualizado );
            System.out.println("✅ ¡Se establecio la API de "+api+" como predeterminada!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
