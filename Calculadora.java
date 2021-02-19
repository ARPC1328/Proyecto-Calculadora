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
        int i, total, cero;
        PilaArre<Character> operadores = new PilaArre();
        PilaArre<Character> parentesis = new PilaArre();
        PilaArre<Character> parentesis2 = new PilaArre();
        PilaArre<Character> puntos = new PilaArre();
        PilaArre<Character> signos = new PilaArre();
        PilaArre<Character> division = new PilaArre();
        PilaArre<Character> casoCero = new PilaArre();
        char simbolo;
        i = 0;
        while (i < expresion.length() && res) {
            simbolo = expresion.charAt(i);
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
    
    public static void main(String[] args) {
        Calculadora cal = new Calculadora("5/0.0");
        System.out.println(cal.revisionSintaxis());
    }   
}
