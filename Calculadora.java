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
    
    public String agregaEspacios(){
        StringBuilder cad = new StringBuilder();
        int i=0;
        char simbolo;
        
       while (i < expresion.length()){
           simbolo = expresion.charAt(i);
           if (simbolo == '-'){
               if (expresion.charAt(i+1) == '-'){
                  cad.append(simbolo);
                  cad.append (" ");
                  cad.append (expresion.charAt(i+1));
                  cad.append (expresion.charAt(i+2));
                  i+=2;
               }
               else {
                  cad.append(simbolo);
                  cad.append(expresion.charAt(i+1));
                  cad.append (" ");
                  i++;
               }
           }
           else {
               cad.append (expresion.charAt(i));
               cad.append(" ");
           }
           i++;
       }
        
        return cad.toString();
    }
    
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
   
    public PilaADT<String> expresionCalculadora(String expresionD) {
        PilaArre cad = new PilaArre();
        String simbolo;
        PilaADT<String> cal = new PilaArre();
        String[] arre;

        if (revisionSintaxis()) {
            arre = expresionD.split(" ");
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
                arre = expresionD.split(" ");
                cad.push(cal.pop());
            }
        } else {
            cad.push("E R R O R S I N T A X I S");
        }

        return cad;
    }

    public static <T> String imprime(PilaADT<T> PilaArre) { //siempre ADT para irme a lo genereal y que pueda incluir todo 
        StringBuilder sb = new StringBuilder();
        PilaArre<T> aux = new PilaArre();
        
        while (!PilaArre.isEmpty()) {
            sb.append(PilaArre.peek().toString() + "\n");
            aux.push(PilaArre.pop());

        }
        while (!aux.isEmpty()) {
            PilaArre.push(aux.pop());
        }
        return sb.toString();

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
            if (simbolo != ' ') {
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
                        if (!puntos.isEmpty()){
                            puntos.pop();
                        }
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
    
    public double resuelveExpresion(PilaADT<String> pila){
        double res = 0;
        PilaADT <String> aux = new PilaArre();
        PilaADT <String> aux1 = new PilaArre();
        PilaADT <String> aux2 = new PilaArre();
        String simbolo;
        
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
        
        return Double.parseDouble (aux1.peek());
    }            
   
    public static void main(String[] args) {
        Calculadora cal = new Calculadora("9+-5/-4+9*8+(8*9)--5");
        
        System.out.println (cal.agregaEspacios());
        System.out.println (cal.resuelveExpresion(cal.expresionCalculadora(cal.agregaEspacios())));
       
    }
}   
