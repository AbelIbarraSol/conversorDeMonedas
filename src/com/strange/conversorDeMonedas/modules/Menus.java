package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
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

    private boolean iniciarMenuAPI(Scanner scanner){
        String api, mensaje, marco;
        while (true){
            System.out.print("Ingresa una opcion: ");
            String opcionMenuAPI = scanner.nextLine();
            if (opcionMenuAPI.equalsIgnoreCase("atras"))  break;
            switch (opcionMenuAPI){
                case "1" -> {
                    while (true){
                        api = "ExchangeRate";
                        new Menus("menuAPIER");
                        System.out.print("Ingresa una opción: ");
                        String opcionesMenuAPIER = scanner.nextLine();
                        if (opcionesMenuAPIER.equalsIgnoreCase("atras")) break;
                        switch (opcionesMenuAPIER){
                            case "1" -> {
                                mensaje = "🔑 KEY: %-40s".formatted(new JSONManager("apiConfiguration.json").comprobarAPIKeyAlmacenada(api));
                                marco = "-".repeat(mensaje.length());
                                System.out.println(marco+"\n"+mensaje+"\n"+marco);
                            }
                            case "2" -> new JSONManager("apiConfiguration.json").agregarAPIKey(1, api, scanner);
                            case "3" -> new JSONManager("apiConfiguration.json").seleccionarAPIPredeterminada(api);
                            default -> System.out.println("¡Opcion no encontrada! Ingrese una de las opciones de la lista.");
                        }
                    }
                }
                case "2" -> {
                    while (true){
                        api = "OpenExchangeRates";
                        new Menus("menuAPIOER");
                        System.out.print("Ingresa una opción: ");
                        String opcionesMenuAPIOER = scanner.nextLine();
                        if (opcionesMenuAPIOER.equalsIgnoreCase("atras")) break;
                        switch (opcionesMenuAPIOER){
                            case "1" -> {
                                mensaje = "🔑 KEY: %-40s".formatted(new JSONManager("apiConfiguration.json").comprobarAPIKeyAlmacenada(api));
                                marco = "-".repeat(mensaje.length());
                                System.out.println(marco+"\n"+mensaje+"\n"+marco);
                            }
                            case "2" -> new JSONManager("apiConfiguration.json").agregarAPIKey(2, api, scanner);
                            case "3" -> new JSONManager("apiConfiguration.json").seleccionarAPIPredeterminada(api);
                        }
                    }
                }
                case "3" -> {
                    String apiSeleccionada = new JSONManager("apiConfiguration.json").obtenerAPISeleccionada();
                    if (!apiSeleccionada.isEmpty()){
                        mensaje = "La API predeterminada es la de %s.".formatted(apiSeleccionada);
                        marco = "-".repeat(mensaje.length());
                        System.out.println(marco+"\n"+mensaje+"\n"+marco);
                    }
                }
                default -> System.out.println("¡Opcion no encontrada! Ingrese una opcion de la lista.");
            }
            new Menus("menuApis");
        }
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
                    case 4 ->{
                        if (!new Menus("menuApis").iniciarMenuAPI(scanner)) seguir = false;
                    }
                    default -> {
                        System.out.println("No se encontro la opción ingresada. Ingrese una opción de la lista.");
                        seguir = false;
                    }
                }
            }
            new Menus("menuPrincipal");
        } scanner.close();
    }
}
