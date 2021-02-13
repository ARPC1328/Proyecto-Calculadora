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
    
    public Calculadora(){
        
    }
    
    public Calculadora (String expresion){
        this.expresion = expresion;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
    
    public int jerarquiaOperaciones(char simbolo){
        int res = -2; 
        
        if (simbolo == '+' || simbolo == '-'){
            res = -1;
        }
        else {
            if (simbolo == '*' || simbolo == '/'){
                res = 0;
            }
            else {
                if (simbolo == '^')
                    res = 1;
            }
        }
            
        return res;
    }
    
     public boolean balanceoParentesis(){
        boolean res = true;
        int i;
        PilaArre<Character> p = new PilaArre();
        char simbolo;
        
        i=0;
        while (i < expresion.length() && res){
            simbolo = expresion.charAt(i);
            switch (simbolo){
                case '(':
                    p.push(simbolo);
                break;
                case ')':
                    if (p.peek() == null){
                        res = false;
                    }    
                    else {
                        p.pop();
                    }
                break;        
            }
            i++;    
        }
        
        return res && p.isEmpty();
    }
  
    public String expresionCalculadora(){
        StringBuilder cad = new StringBuilder();
        char simbolo;
        PilaADT<Character> cal = new PilaArre();
        
        if (balanceoParentesis()){
           for (int i=0; i < expresion.length(); i++){
                simbolo = expresion.charAt(i);
                switch (simbolo) {
                    case '(': //Parentesis izquierdo
                        cal.push(simbolo);
                    break;    
                    case '+': //operadores
                    case '-':
                    case '*':
                    case '/':
                    case '^':    
                        while (!cal.isEmpty() && (jerarquiaOperaciones(simbolo) <= jerarquiaOperaciones(cal.peek())) ){
                            cad.append(cal.pop());
                        }
                        cal.push(simbolo);
                    break;
                    case ')': //Parentesis derecho
                        while (!cal.isEmpty() && cal.peek() != '('){
                            cad.append(cal.pop());
                        }
                        cal.pop();
                    break;    
                    default: //operando
                        cad.append(simbolo);
                    break;           
                }
            } 
        }
        else {
            cad.append("Hay parentesis de más");
        }
 
        return cad.toString();
    }
    
    public static void main(String[] args) {
        Calculadora cal = new Calculadora("((2/5)))*(5+9)");
        
        System.out.println (cal.expresionCalculadora());
    }
    
}
