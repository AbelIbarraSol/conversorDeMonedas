package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.Conversor;
import com.strange.conversorDeMonedas.modules.Menus;
import com.strange.conversorDeMonedas.modules.Reporte;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menus menus = new Menus();
        boolean continuar = true;

        while (continuar){
            menus.exhibirMenus(0);
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
                        if(!new Conversor().iniciarConversor(scanner)) seguir = false;
                    }
                    case 2 -> {}
                    case 3 -> {}
                    default -> System.out.println("hola");
                }
            }
        } scanner.close();
    }
}