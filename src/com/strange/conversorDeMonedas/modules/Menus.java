package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Scanner;

public class Menus {
    private JsonArray jsonArray;

    public Menus(String clave){
        jsonArray = new JSONManager("menus.json").convertirAJsonArray(clave);
        for (JsonElement e : jsonArray) {
            System.out.println(e.getAsString());
        }
    }

    private boolean iniciarMenuConversor(Scanner scanner){
        Conversor  conversor = new Conversor();
        while (true){
            if (conversor.escogerMonedaBase(scanner)){
                return false;
            }else {
                conversor.convertirMoneda(scanner);
            } return true;
        }
    }

    private boolean iniciarMenuAgregarMonedas(Scanner scanner){
        while (true){
            System.out.print("Ingrese una opcion: ");
            switch (scanner.nextLine().toLowerCase()){
                case "1" -> {
                    new Menus("menuListarDivisas");
                    new Conversor().listarMonedas();
                    return true;
                }
                case "2" -> {
                    new Menus("menuAgregarDivisas");
                    if(!new JSONManager("monedas.json").agregarMonedas(scanner)){
                        return true;
                    }
                }
                case "atras" -> {
                    return false;
                }
                default -> System.out.println("¡No se encontro la opcion! Vuelva a ingresar una opcion de la lista.");
            }
        }
    }

    private boolean iniciarMenuHistorial(Scanner scanner){
        new JSONManager("historial.json").generarReporte();
        return false;
    }

    public void iniciarProyecto(Scanner scanner){
        boolean continuar = true;
        while (continuar){
            System.out.print("Ingrese una opcion: ");
            int seleccion = Integer.parseInt(scanner.nextLine());
            if (seleccion == 0) {
                System.out.println("El programa finalizo");
                continuar = false;
                break;
            }
            boolean seguir = true;
            while (seguir){
                switch (seleccion) {
                    case 1 -> {
                        if(!new Menus("menuConversor").iniciarMenuConversor(scanner)) seguir = false;
                    }
                    case 2 -> {
                        if(!new Menus("menuDivisas").iniciarMenuAgregarMonedas(scanner)) seguir = false;
                    }
                    case 3 -> {
                        if (!new Menus("menuHistorial").iniciarMenuHistorial(scanner)) seguir = false;
                    }
                    default -> {
                        System.out.println("hola");
                        seguir = false;
                    }
                }
            }
            new Menus("menuPrincipal");
        } scanner.close();
    }
}
