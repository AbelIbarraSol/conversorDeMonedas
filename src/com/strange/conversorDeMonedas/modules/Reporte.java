package com.strange.conversorDeMonedas.modules;

import java.util.ArrayList;
import java.util.List;

public class Reporte {
    private List<String> listaDeConversiones = new ArrayList<>();
    public void almacenarReporte(Conversor conversor,double valorACambiar){
        //listaDeConversiones.add(conversor.convertirMoneda(valorACambiar));
    }

    public void generarReporte(){
        for (String conversion : listaDeConversiones){
            System.out.println(conversion);
        }
    }
}
