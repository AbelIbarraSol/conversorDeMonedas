package com.strange.conversorDeMonedas.Principal;

import com.strange.conversorDeMonedas.modules.ConexionAPI;
import com.strange.conversorDeMonedas.modules.Divisa;

public class Principal {
    public static void main(String[] args) {
        Divisa conexionAPI = new ConexionAPI().conectarAPI("PEN");
        System.out.println(conexionAPI);
    }
}