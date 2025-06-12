package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Conversor {
    private Map<String,String> listaDeMonedas = new HashMap<>();
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    private String divisor = "──────────────────────────────────────────────────\n";
    private String atras = "⬅️ Atras";

    public Conversor(){
        try {
            String jsonMonedas = Files.readString(Paths.get("src/com/strange/conversorDeMonedas/resources/monedas.json"));
            //Convertir json en jsonObject
            jsonObject = JsonParser.parseString(jsonMonedas).getAsJsonObject();
            //Extraer array "monedas" del jsonObject
            jsonArray = jsonObject.getAsJsonArray("monedas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean iniciarConversor(Scanner scanner){
        while (true){
            new Menus().exhibirMenus(1);
            if (escogerMonedaBase(scanner)){
                return false;
            }else {
                System.out.println(convertirMoneda(scanner));
            } return true;
        }
    }

    public void agregarMonedas(String monedaNueva){
        //No agrega al archivo json y falta poder comprobar con la api si es valido la moneda
        jsonArray.add(monedaNueva);
        jsonObject.add("monedas",jsonArray);
    }

    private void listarMonedas(){
        String elemento;
        System.out.println(atras);
        for (int i = 0; i < jsonArray.size(); i++){
            elemento = (i + 1)+". "+jsonArray.get(i).getAsString();
            System.out.println(elemento);
        }
        System.out.print("🔸 Ingrese una opcion: ");
    }

    private void listarMonedasExcluyendo(String excluir) {
        System.out.println(divisor + "🔹 Escoge una Moneda de Destino\n" + atras);
        int contador = 1;
        for (int i = 0; i < jsonArray.size(); i++) {
            String moneda = jsonArray.get(i).getAsString();
            if (!moneda.equals(excluir)) {
                System.out.println(contador + ". " + moneda);
                contador++;
            }
        }
    }

    private boolean validarOpcion(int opcion) {
        if (opcion < 1 || opcion > jsonArray.size()) {
            System.out.println("Número fuera de rango. Intenta nuevamente.");
            return false;
        }
        return true;
    }

    private boolean escogerMonedaBase(Scanner scanner) {
        while(true){
            listarMonedas();

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Entrada vacía, intenta nuevamente.");
                continue; // permite reintentar
            }

            if (input.equalsIgnoreCase("atras")) {
                return true;
            }

            try {
                int monedaBase = Integer.parseInt(input);
                if (!validarOpcion(monedaBase)) {
                    continue; // Permite reintentar si la opción no es válida
                }

                String monedaBaseSeleccionada = jsonArray.get(monedaBase - 1).getAsString();

                listarMonedasExcluyendo(monedaBaseSeleccionada);

                if (escogerMonedaDestino(scanner, monedaBaseSeleccionada)){
                    continue; //reintentar la seleccion de moneda base
                }

                return false;

            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Ingresa un número válido.");
                return true; // reintentar
            }
        }
    }

    private boolean escogerMonedaDestino(Scanner scanner, String monedaBase) {
        // Lista temporal con las monedas excluyendo la Moneda Base
        List<String> monedasDestino = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String moneda = jsonArray.get(i).getAsString();
            if (!moneda.equals(monedaBase)) {
                monedasDestino.add(moneda);
            }
        }

        while (true) {
            System.out.print(" 🔹 Ingresa una opción: ");
            String seleccion = scanner.nextLine().trim();

            if (seleccion.equalsIgnoreCase("atras")) {
                return true; // Regresa a la selección de moneda base
            }

            try {
                int opcion = Integer.parseInt(seleccion);
                if (!validarOpcion(opcion)) {
                    continue;
                }

                String monedaDestinoSeleccionada = monedasDestino.get(opcion - 1);

                listaDeMonedas.put("MonedaBase", monedaBase);
                listaDeMonedas.put("MonedaDestino", monedaDestinoSeleccionada);

                return false; // Finaliza correctamente y permite avanzar
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Ingresa un número válido.");
            }
        }
    }

    private String convertirMoneda(Scanner scanner){
        System.out.print("💰 Ingrese un monto a convertir: ");
        double valorACambiar = Double.valueOf(scanner.nextLine());
        String monedaBase = listaDeMonedas.get("MonedaBase");
        String monedaDestino = listaDeMonedas.get("MonedaDestino");
        Double valorEquivalente = new ConexionAPI().conectarAPI(monedaBase, monedaDestino);
        double montoFinal =  valorACambiar * valorEquivalente;
        double montoFinalRedondeado = Math.round(montoFinal * 100.0) / 100.0;
        String resultado = """
                ═════════════════════════════════════════════════════════════════════
                  💱 El valor %.2f [%s] corresponde al valor final de ==> %.2f [%s].
                ═════════════════════════════════════════════════════════════════════
                """.formatted(valorACambiar,monedaBase,montoFinalRedondeado,monedaDestino);
        return resultado;
    }
}
