/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculadora;

import java.util.ArrayList;

/**
 * Clase del objeto Calculadora usado para resolver la expresión incertada en la interfaz visual.
 * @author Anthoan, Andrés, Alejandro, Brayan, Fernando.
 * @version 1.0
 */
public class Calculadora {

 private String expresion;

 /**
 * Constructor vacio para la clase Calculadora.
 */
    public Calculadora() {

    }

 /**
 * Constructor para la clase Calculadora.
 * @param expresion: la expresión a calcular expresada como String.
 */
    public Calculadora(String expresion) {
        this.expresion = expresion;
    }

 /**
 * Getter para obtener la expresión
 * @return la expresión en formato String.
 */
    public String getExpresion() {
        return expresion;
    }
 
/**
 * Setter para establecer la expresión
 * @param expresion: la expresión en formato String.
 */
    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
    
 /**
 * Función para determinar la jerarquia de operaciones
 * @param simbolo: un símbolo de operación +,-,*,/
 * @return <pre>
 * -2: Si el símbolo no es +,-,*,/.
 * -1: Si el símbolo es + o -.
 * 0: Si el símbolo es * o /.
 * </pre>
 */
    public int jerarquiaOperaciones(String simbolo) {
        int res = -2;

        if (simbolo.equals("+") || simbolo.equals("-")) {
            res = -1;
        } else {
            if (simbolo.equals("*") || simbolo.equals("/")) {
                res = 0;
            } 
        }

        return res;
    }

 /**
 * Cuenta los elementos que contiene una pila
 * @param pila: la pila a la que queremos contar sus elementos.
 * @return <pre> 
 * Un entero indicado el número de elementos que contiene la pila.
 * </pre>
 */
 public <T> int contarPila(PilaADT<T> pila) {
        PilaADT<T> aux = new PilaArre();
        int total = 0;
        while(!pila.isEmpty()) {
            aux.push(pila.pop());
            total++;
        }
        while(!aux.isEmpty())
            pila.push(aux.pop());
        return total;
    }
    
 /**
 * Revisa si la sintaxis de la expresión es correcta
 * @see contarPila.
 * @return true: Si la sintaxis es correcta.
 * false: Si la sintaxis es incorrecta.
 */
    public boolean revisionSintaxis() {
        boolean res = true;
        int i, total, cero;
        PilaADT<Character> operadores = new PilaArre();
        PilaADT<Character> parentesis = new PilaArre();
        PilaADT <Character> parentesis2 = new PilaArre();
        PilaADT <Character> puntos = new PilaArre();
        PilaADT <Character> signos = new PilaArre();
        PilaADT <Character> division = new PilaArre();
        PilaADT <Character> casoCero = new PilaArre();
        char simbolo;
        
        i = 0;
        while (i < expresion.length() && res) {
            simbolo = expresion.charAt(i);
            if(simbolo != ' ') {
                switch (simbolo) {
                    case '(':
                        if(!signos.isEmpty())     
                            if(signos.peek().equals(')') && parentesis2.isEmpty())
                                res = false;
                        if(!parentesis2.isEmpty())
                            while(!parentesis2.isEmpty())
                                parentesis2.pop();
                        parentesis.push(simbolo);                    
                        signos.push(simbolo);                    
                        break;
                    case ')':
                        if(parentesis.isEmpty())
                            res = false;
                        else 
                            parentesis.pop();                        
                        if(!signos.isEmpty()) 
                            if(signos.peek().equals('(') && parentesis2.isEmpty())
                                res = false;
                        if(!parentesis2.isEmpty())
                            while(!parentesis2.isEmpty())
                                parentesis2.pop();
                        if(!division.isEmpty()) {
                            cero = contarPila(casoCero);
                            total = 0;
                            while(!casoCero.isEmpty()) 
                                if(casoCero.pop().equals('0'))
                                    total++;
                            if(cero == total)
                                res = false;
                            division.pop();                        
                        }
                        signos.push(simbolo);
                        break;
                    case '+':
                        if(!operadores.isEmpty())
                            if(operadores.peek().equals('+') || operadores.peek().equals('-') || operadores.peek().equals('/') || operadores.peek().equals('*'))
                                res = false;
                        else {
                            if(!puntos.isEmpty()) 
                                puntos.pop();                     
                            operadores.push(simbolo);
                            if(!signos.isEmpty())
                                if(signos.peek().equals('/'))
                                    if(!division.isEmpty()) {
                                        cero = contarPila(casoCero);
                                        total = 0;
                                        while(!casoCero.isEmpty()) 
                                            if(casoCero.pop().equals('0'))
                                                total++;
                                        if(cero == total)
                                            res = false;
                                        division.pop();
                            }  
                            signos.push(simbolo);
                        }
                        break;
                    case '-':
                        if(!puntos.isEmpty())
                                puntos.pop();                      
                        operadores.push(simbolo);
                        signos.push(simbolo);                 
                        break;
                    case '*':
                        if(operadores.isEmpty() || operadores.peek().equals('+') || operadores.peek().equals('-') || operadores.peek().equals('/') || operadores.peek().equals('*'))
                            res = false;
                        else {
                            if(!puntos.isEmpty())
                                puntos.pop();                       
                            operadores.push(simbolo); 
                            if(!division.isEmpty()) {
                                cero = contarPila(casoCero);
                                total = 0;
                                while(!casoCero.isEmpty()) 
                                    if(casoCero.pop().equals('0'))
                                        total++;
                                if(cero == total)
                                    res = false;
                                division.pop();
                            }  
                            signos.push(simbolo);
                        }                  
                        break;
                    case '/':
                        if(operadores.isEmpty() || operadores.peek().equals('+') || operadores.peek().equals('-') || operadores.peek().equals('/') || operadores.peek().equals('*'))
                            res = false;
                        else {
                            if(!puntos.isEmpty())
                                puntos.pop();
                            operadores.push(simbolo);
                            if(!division.isEmpty()) {
                                cero = contarPila(casoCero);
                                total = 0;
                                while(!casoCero.isEmpty()) 
                                    if(casoCero.pop().equals('0'))
                                        total++;
                                if(cero == total)
                                    res = false;
                                division.pop();
                            }     
                            signos.push(simbolo);                        
                            division.push(simbolo);
                        }                
                        break;
                    case '.':
                        if(puntos.isEmpty())
                            puntos.push(simbolo);
                        else
                            res = false;
                        operadores.push(simbolo);
                        break;
                    default: 
                        operadores.push(simbolo);
                        if(!signos.isEmpty())
                            if(signos.peek().equals('('))
                                parentesis2.push(simbolo);
                        if(!division.isEmpty())
                            casoCero.push(simbolo);
                }
            }
            i++;
        }
        if(!operadores.isEmpty())
            if(operadores.peek().equals('+') || operadores.peek().equals('-') || operadores.peek().equals('/') || operadores.peek().equals('*'))
                res = false;
        if(!parentesis.isEmpty())
            res = false;
        if(!casoCero.isEmpty()) {
            cero = contarPila(casoCero);
            total = 0;
            while(!casoCero.isEmpty()) 
                if(casoCero.pop().equals('0'))
                    total++;
            if(cero == total)
                res = false;
        }
        return res;
    }  

  /**
 * Esta función convierte en expresión post-fija si el sintaxis es correcto.
 * @see jerarquiaOperaciones
 * @return <pre>
 * Si la expresión tiene un error de Sintaxis la función regresará "E R R O R S I N T A X I S".
 * Si la expresión no tiene error de sintaxis, regresará una Pila con la expresión Post-Fija.
 *  </pre>
 */
    public PilaADT<String> expresionCalculadora() {
        PilaADT cad = new PilaArre();
        String simbolo;
        PilaADT<String> cal = new PilaArre();
        String[] arre;

        if (revisionSintaxis()) {
            arre = expresion.split(" ");
            for (int i = 0; i < arre.length; i++) {
                simbolo = arre[i];
                switch (simbolo) {
                    case "(": //Parentesis izquierdo
                        cal.push(simbolo);
                        break;
                    case "+": //operadores
                    case "-":
                    case "*":
                    case "/":
                    case "^":
                        while (!cal.isEmpty() && (jerarquiaOperaciones(simbolo) <= jerarquiaOperaciones(cal.peek()))) {
                            cad.push(cal.pop());
                        }
                        cal.push(simbolo);
                    break;
                    case ")": //Parentesis derecho
                        while (!cal.isEmpty() && !cal.peek().equals("(")) {
                            cad.push(cal.pop());
                        }
                        cal.pop();
                    break;   
                    default: //operando
                        cad.push(simbolo);
                    break;
                }
            }
            while (!cal.isEmpty()){
                arre = expresion.split(" ");
                cad.push(cal.pop());
            }
        } else {
            cad.push("E R R O R S I N T A X I S");
        }

        return cad;
    }
    
 /**
 * Realiza las operaciónes 
 * @param pila: Una Pila conteniendo una expresión Post-Fija con el sintaxis correcto.
 * @see revisionSintaxis.
 * @return <pre> 
 * Si existe algún error regresa "E R R O R"
 * Si no existe algún error, regresa un String con el resultado de la operación.
 * </pre>
 */
    public String resuelveExpresion(PilaADT<String> pila){
            double res = 0;
            String res2;
            PilaADT <String> aux = new PilaArre();
            PilaADT <String> aux1 = new PilaArre();
            PilaADT <String> aux2 = new PilaArre();
            String simbolo;

            if(revisionSintaxis()){
                while (!pila.isEmpty()){
                    aux.push(pila.pop());
                }

            while (!aux.isEmpty()){
                simbolo = aux.pop();
                switch (simbolo){
                    case "+":
                        res = Double.parseDouble(aux1.pop()) + Double.parseDouble(aux1.pop());
                        aux1.push(String.valueOf(res));
                    break;    
                    case "-":
                        aux2.push(aux1.pop());
                        res = Double.parseDouble(aux1.pop()) - Double.parseDouble(aux2.pop());
                        aux1.push(String.valueOf(res));
                    break;
                    case "*":    
                        res = Double.parseDouble(aux1.pop()) * Double.parseDouble(aux1.pop());
                        aux1.push(String.valueOf(res));
                    break;
                    case "/":
                        aux2.push(aux1.pop());
                        res = Double.parseDouble(aux1.pop()) / Double.parseDouble(aux2.pop());  
                        aux1.push(String.valueOf(res));
                    break;
                    default:
                        aux1.push(simbolo);
                    break;    
                }
            }

                res= Double.parseDouble(aux1.peek());
                res2=String.valueOf(res);
            }
            else{
                res2="E R R O R";
            }
           
            return  res2;
        }            
}
