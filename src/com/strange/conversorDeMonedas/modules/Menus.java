package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Menus {
    JsonObject jsonObject;
    private void cargarMenu(){
        try {
            //Buscar Json con los menus almacenados y transformarlos en un JsonObject
            String jsonMenus = Files.readString(Paths.get("src/com/strange/conversorDeMonedas/resources/menus.json"));
            jsonObject = JsonParser.parseString(jsonMenus).getAsJsonObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exhibirMenus(String clave){
        cargarMenu();
        JsonArray jsonArray = jsonObject.getAsJsonArray(clave);
        List<String> menu = new ArrayList<>();
        for (JsonElement e : jsonArray) menu.add(e.getAsString());
        for (String linea : menu) System.out.println(linea);
    }
}
