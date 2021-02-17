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

    public int jerarquiaOperaciones(String simbolo) {
        int res = -2;

        if (simbolo.equals("+") || simbolo.equals("-")) {
            res = -1;
        } else {
            if (simbolo.equals("*") || simbolo.equals("/")) {
                res = 0;
            } else {
                if (simbolo.equals("^")) {
                    res = 1;
                }
            }
        }

        return res;
    }

    public boolean balanceoParentesis() {
        boolean res = true;
        int i;
        PilaArre<Character> p = new PilaArre();
        char simbolo;

        i = 0;
        while (i < expresion.length() && res) {
            simbolo = expresion.charAt(i);
            switch (simbolo) {
                case '(':
                    p.push(simbolo);
                    break;
                case ')':
                    if (p.isEmpty()) {
                        res = false;
                    } else {
                        p.pop();
                    }
                    break;
            }
            i++;
        }

        return res && p.isEmpty();
    }

    public PilaArre<String> expresionCalculadora() {
        PilaArre cad = new PilaArre();
        String simbolo;
        PilaADT<String> cal = new PilaArre();

        if (balanceoParentesis()) {
            String[] arre = expresion.split(" ");
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
        } else {
            cad.push("Hay parentesis de más");
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

    public boolean esSigno(String X) {
        return (X.equals("+") || X.equals("-") || X.equals("*") || X.equals("/") || X.equals("^"));
    }

    public String resuelve() {
        PilaArre<String> post = expresionCalculadora();
        PilaArre<String> aux = new PilaArre();
        PilaArre<String> temp = new PilaArre();
        String elem = "", var1, var2;

        while (!post.isEmpty()) {
            if (esSigno(post.peek())) {
                elem = post.pop();
                if (esSigno(post.peek())) {
                    aux.push(elem);
                    elem = post.pop();
                } else {
                    temp.push(post.pop());
                }

            } else {
                var2 = temp.peek();
                var1 = post.pop();
                switch (elem) {
                    case "+":
                        post.push(Double.toString(Double.parseDouble(var1) + (Double.parseDouble(var2))));
                        break;
                    case "-":
                        post.push(Double.toString(Double.parseDouble(var1) - (Double.parseDouble(var2))));
                        break;
                    case "*":
                        post.push(Double.toString(Double.parseDouble(var1) * (Double.parseDouble(var2))));
                        break;
                    case "/":
                        post.push(Double.toString(Double.parseDouble(var1) / (Double.parseDouble(var2))));
                        break;

                }
                if(!aux.isEmpty()){
                    elem = aux.pop();
                }else {
                    elem= post.pop();
                }

            }

        }
        return elem;

    }

    public static void main(String[] args) {
        Calculadora cal = new Calculadora("( ( 2 / 5 ) ) * ( -5.0 + 25 )");
        System.out.println(imprime(cal.expresionCalculadora()));

        System.out.println(cal.resuelve());

    }

}
