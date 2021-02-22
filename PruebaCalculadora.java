/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author Brayansito
 */
public class PruebaCalculadora {
    public static void main (String [] args){
        Calculadora cal = new Calculadora (" ( 2 + 5 ) / 9.5 - -5 * 4.66");
        
        System.out.println (cal.resuelveExpresion(cal.expresionCalculadora()));
    }
}
