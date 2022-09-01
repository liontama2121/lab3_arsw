/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread implements Runnable{
    private int num1, num2;

    /**
     * Metodo el cual asigna a las variable num1 y num2, valores para ser los limites que va a generar en el thread
     * @param num1 limite inferior
     * @param num2 limite superior
     */
    public CountThread(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    /**
     * Ejecuta los threads por medio de la función run, e imprime los valores ordenados ascdendentemente dentro del limite
     */

    @Override
    public void run(){
        for (int i=num1; i<=num2; i++){
            System.out.println(i);
        }
    }
}