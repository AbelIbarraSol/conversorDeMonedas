package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.Conversor;
import com.strange.conversorDeMonedas.modules.Menus;
import com.strange.conversorDeMonedas.modules.Reporte;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        Menus menus = new Menus();
        boolean opcion = true;
        menus.exhibirMenus("menuConversor");
    }
}