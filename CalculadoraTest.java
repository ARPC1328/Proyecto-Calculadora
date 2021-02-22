/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brayansito
 */
public class CalculadoraTest {
    
    public CalculadoraTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getExpresion method, of class Calculadora.
     */
    @Test
    public void testGetExpresion() {
        System.out.println("getExpresion");
        Calculadora instance = new Calculadora("2 + 5 / 8");
        String expResult = "2 + 5 / 8";
        String result = instance.getExpresion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setExpresion method, of class Calculadora.
     */
    @Test
    public void testSetExpresion() {
        System.out.println("setExpresion");
        String expresion = "7 - 8";
        Calculadora instance = new Calculadora("2 + 5 ");
        instance.setExpresion(expresion);
    }

    /**
     * Test of jerarquiaOperaciones method, of class Calculadora.
     */
    @Test
    public void testJerarquiaOperaciones() {
        System.out.println("jerarquiaOperaciones");
        String simbolo = "+";
        Calculadora instance = new Calculadora();
        int expResult = -1;
        int result = instance.jerarquiaOperaciones(simbolo);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of revisionSintaxis method, of class Calculadora.
     */
    @Test
    public void testRevisionSintaxis() {
        System.out.println("revisionSintaxis");
        Calculadora instance = new Calculadora("( 2 - 8 ) * 8.5 / 7.45");
        boolean expResult = true;
        boolean result = instance.revisionSintaxis();
        assertEquals(expResult, result);
    }

    /**
     * Test of resuelveExpresion method, of class Calculadora.
     */
    @Test
    public void testResuelveExpresion() {
        System.out.println("resuelveExpresion");
        PilaADT<String> pila = null;
        Calculadora instance = new Calculadora("2 + 8 * 9");
        String expResult = "74.0";
        String result = instance.resuelveExpresion(instance.expresionCalculadora());
        assertEquals(expResult, result);
    }
    
}
