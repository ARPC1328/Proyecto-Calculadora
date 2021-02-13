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
public class PilaArre<T> implements PilaADT<T>{

    private T datos [];
    private int tope;
    private final int MAX = 10;
    
    public PilaArre (){
       datos = (T[]) new Object[MAX];
       tope = -1;
    }
    
    public PilaArre (int tamano){
        datos = (T[]) new Object[tamano];
        this.tope = -1; //Palabra Vacia
    }
    
    public void push(T nuevo) { //Agrega elemento
        
        if (tope+1 == datos.length){
            aumentaPila();
        }
        tope++;
        datos[tope] = nuevo;
    }
    
    private void aumentaPila(){
       T [] masGrande = (T[]) new Object[datos.length*2];
       
       for (int i=0; i <=tope; i++){
           masGrande[i] = datos[i];
       }
       datos = masGrande;
    }

    @Override
    public boolean isEmpty() { //¿Está vacía? 
        return tope == -1;
    }
    
    public T peek(){ //Version A (¿Que hay en el tope de la pila?
        T resp = null; 
        
        if (!isEmpty()){
            resp = datos[tope];
        }
        
        return resp;
    }

    public T pop(){
        T dato;
        
        if (isEmpty()){
            throw new ExcepcionColeccionVacia ("Esta vacía la pila!!!!");
        }
        else {
           dato = datos[tope];
           datos[tope] = null;
           tope--;  
           return dato;
        }
    }
      
}
