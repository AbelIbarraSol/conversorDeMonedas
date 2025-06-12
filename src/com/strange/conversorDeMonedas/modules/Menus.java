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
import java.util.Arrays;
import java.util.List;

public class Menus {
    private JsonObject jsonObject;
    private List<String> listaDeMenus = Arrays.asList("menuPrincipal","menuConversor","menuHistorial","menuAgregarDivisas");
    public Menus(){
        //Constructor vacio el try catch se ejecuta al instanciar la clase.
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

    public void exhibirMenus(int index){
        JsonArray jsonArray = jsonObject.getAsJsonArray(listaDeMenus.get(index));
        List<String> menu = new ArrayList<>();
        for (JsonElement e : jsonArray) menu.add(e.getAsString());
        for (String linea : menu) System.out.println(linea);
    }
}
