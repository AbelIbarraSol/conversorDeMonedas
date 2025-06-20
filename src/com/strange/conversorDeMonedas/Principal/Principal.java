package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.ConexionAPI;
import com.strange.conversorDeMonedas.modules.Menus;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new Menus("menuPrincipal").iniciarProyecto(scanner);
    }
}