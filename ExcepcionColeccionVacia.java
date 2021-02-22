/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 * Clase para manejar el caso en que se trata de llamar Pop en una pila vacia, Extiende RuntimeException
 * @author Anthoan, Andrés, Alejandro, Brayan, Fernando.
 * @version 1.0
 */
public class ExcepcionColeccionVacia extends RuntimeException{

/** 
* Constructor vacio que  regresa "E R R O R".
*/
    public ExcepcionColeccionVacia() {
        super("E R R O R");
    }
    
/** 
* Constructor que regresa el mensaje especificado como parámetro
* @param mensaje: El mensaje a regresar.
*/  
     public ExcepcionColeccionVacia(String mensaje) {
        super(mensaje);
    }
}
