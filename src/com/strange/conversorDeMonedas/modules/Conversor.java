package com.strange.conversorDeMonedas.modules;

import com.google.gson.JsonArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Conversor {
    private Map<String,String> listaDeMonedas = new HashMap<>();
    private JsonArray jsonArray;
    private String divisor = "──────────────────────────────────────────────────\n";
    private String atras = "⬅️ Atras";

    public Conversor(){
        jsonArray = new JSONManager("monedas.json").convertirAJsonArray("monedas");
    }

    public void listarMonedas(){
        String elemento;
        for (int i = 0; i < jsonArray.size(); i++){
            elemento = (i + 1)+". "+jsonArray.get(i).getAsString();
            System.out.println(elemento);
        }

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

    public boolean escogerMonedaBase(Scanner scanner) {
        while(true){
            System.out.println(divisor+atras);
            listarMonedas();
            System.out.print("🔸 Ingrese una opcion: ");
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
                return false;
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Ingresa un número válido.");
            }
        }
    }

    public boolean convertirMoneda(Scanner scanner){
        while (true){
            System.out.print("💰 Ingrese un monto a convertir: ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Entrada vacía, intenta nuevamente.");
                continue;
            }
            try{
                double valorACambiar = Double.valueOf(input);
                String monedaBase = listaDeMonedas.get("MonedaBase");
                String monedaDestino = listaDeMonedas.get("MonedaDestino");
                //Llamar metodo segun la api seleccionada
                Double valorEquivalente = new APIFactory().getAPISeleccionada().obtenerTasaDeConversion(monedaBase,monedaDestino);
                double montoFinal =  valorACambiar * valorEquivalente;
                double montoFinalRedondeado = Math.round(montoFinal * 100.0) / 100.0;

                String conversion = "%.2f [%s] ➡ %.2f [%s]".formatted(valorACambiar,monedaBase,montoFinalRedondeado,monedaDestino);

                // Captura el instante actual
                LocalDateTime horaActual= LocalDateTime.now();
                new JSONManager("historial.json").almacenarReporte(conversion,horaActual);
                String mensaje = "💱 El valor %.2f [%s] corresponde al valor final de ==> %.2f [%s]".formatted(valorACambiar,monedaBase,montoFinalRedondeado,monedaDestino);
                String marco = "═".repeat(mensaje.length());
                System.out.println(marco+"\n"+mensaje+"\n"+marco);
                return false;
            }catch (NumberFormatException e){
                throw new RuntimeException("Formato inválido. Ingresa un número válido. "+e);
            }
        }
    }
}
