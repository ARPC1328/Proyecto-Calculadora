/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author Usuario
 */
public class Calculadora {
    private String expresion;

    public Calculadora() {
    }

    public Calculadora(String expresion) {
        this.expresion = expresion;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    } 
    
    public <T> int contarPila(PilaArre<T> pila) {
        PilaArre<T> aux = new PilaArre();
        int total = 0;
        while(!pila.isEmpty()) {
            aux.push(pila.pop());
            total++;
        }
        while(!aux.isEmpty())
            pila.push(aux.pop());
        return total;
    }
    
    public boolean revisionSintaxis() {
        boolean res = true;
        int i;
        PilaArre<Character> operadores = new PilaArre();
        PilaArre<Character> parentesis = new PilaArre();
        PilaArre<Character> puntos = new PilaArre();
        PilaArre<Character> signos = new PilaArre();
        char simbolo;
        i = 0;
        while (i < expresion.length() && res) {
            simbolo = expresion.charAt(i);
            switch (simbolo) {
                case '(':
                    if(!signos.isEmpty())     
                        if(signos.peek().equals(')'))
                            res = false;
                    parentesis.push(simbolo);                    
                    signos.push(simbolo);                    
                    break;
                case ')':
                    if(parentesis.isEmpty())
                        res = false;
                    else 
                        parentesis.pop();                        
                    if(!signos.isEmpty()) 
                        if(signos.peek().equals('('))
                            res = false;
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
                        signos.push(simbolo);
                    }
                    break;
                case '-':
                    if(!operadores.isEmpty())
                        if(operadores.peek().equals('+') || operadores.peek().equals('-') || operadores.peek().equals('/') || operadores.peek().equals('*'))
                            res = false;
                    else {
                        if(!puntos.isEmpty())
                            puntos.pop();                      
                        operadores.push(simbolo);
                        signos.push(simbolo);
                    }                  
                    break;
                case '*':
                    if(operadores.isEmpty() || operadores.peek().equals('+') || operadores.peek().equals('-') || operadores.peek().equals('/') || operadores.peek().equals('*'))
                        res = false;
                    else {
                        if(!puntos.isEmpty())
                            puntos.pop();                       
                        operadores.push(simbolo); 
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
                        signos.push(simbolo);
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
            }
            i++;
        }
        if(!operadores.isEmpty())
            if(operadores.peek().equals('+') || operadores.peek().equals('-') || operadores.peek().equals('/') || operadores.peek().equals('*'))
                res = false;
        if(!parentesis.isEmpty())
            res = false;
        return res;
    }  
    
    public static void main(String[] args) {
        Calculadora cal = new Calculadora("+((5/2+3)+((5/2)))");
        System.out.println(cal.revisionSintaxis());
    }   
}