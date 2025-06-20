package com.strange.conversorDeMonedas.modules;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

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

    public Set<String> obtenerClaves() {
        return jsonObject.keySet();
    }

    public boolean agregarDatosAlJson(Scanner scanner){
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

            if(!new ConexionAPI().comprobarExistenciaDeMoneda(input.toUpperCase())){
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
}
