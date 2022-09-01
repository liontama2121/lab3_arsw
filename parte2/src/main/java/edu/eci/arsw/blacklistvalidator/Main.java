/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 * Clase principal, en la cual se envia el host, se calcula el tiempo y revisa si es un black host
 * @author hcadavid
 */
public class Main {

    /**
     * Meotodo que envia el host y la cantidad de hilos a HostBlackListsValidator, para realizar la validación de si es un
     * Black Host y que mide el tiempo en que se tarda en solucionarse el problema
     * @param a argumentos de entrada del metodo
     * @throws InterruptedException controla la excepción que se pueda generar en HostBlackListsValidator
     */
    public static void main(String a[]) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        HostBlackListsValidator HBLV=new HostBlackListsValidator();
        List<Integer> blacklisted=HBLV.checkHost("202.24.34.55",100);
        long endTime = System.currentTimeMillis();
        System.out.println("The host was found in the following blacklists:"+blacklisted);
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " milisegundos.");
        
    }
    
}
