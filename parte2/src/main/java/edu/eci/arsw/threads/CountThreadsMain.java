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
public class CountThreadsMain {
    /**
     * Inicializa los threads y los ejecuta
             * @param a cadena de inicio del main
     */
    public static void main(String a[]){
        CountThread countThread1 = new CountThread(0,99);
        CountThread countThread2 = new CountThread(99,199);
        CountThread countThread3 = new CountThread(200,299);

        Thread Thread1 = new Thread(countThread1);
        Thread Thread2 = new Thread(countThread2);
        Thread Thread3 = new Thread(countThread3);

        /*Thread1.run();
        Thread2.run();
        Thread3.run();*/

        Thread1.start();
        Thread2.start();
        Thread3.start();

    }
}