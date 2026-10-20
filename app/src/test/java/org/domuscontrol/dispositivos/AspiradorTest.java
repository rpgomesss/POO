package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AspiradorTest {

    @Test
    public void testConstrutorDefault() {
        Aspirador a = new Aspirador();

        assertEquals(0, a.getIntensidade());
        assertEquals(0, a.getValorIntensidadeMinimo());
        assertEquals(0, a.getValorIntensidadeMaximo());
        assertEquals(0, a.getBateria());
    }

    @Test
    public void testSetIntensidadeDentroDosLimites() {
        Aspirador a = new Aspirador();
        a.setValorIntensidadeMinimo(0);
        a.setValorIntensidadeMaximo(100);

        a.setIntensidade(60);

        assertEquals(60, a.getIntensidade());
    }

    @Test
    public void testSetIntensidadeAbaixoMinimo() {
        Aspirador a = new Aspirador();
        a.setValorIntensidadeMinimo(10);
        a.setValorIntensidadeMaximo(100);

        a.setIntensidade(5);

        assertEquals(10, a.getIntensidade());
    }

    @Test
    public void testSetIntensidadeAcimaMaximo() {
        Aspirador a = new Aspirador();
        a.setValorIntensidadeMinimo(0);
        a.setValorIntensidadeMaximo(100);

        a.setIntensidade(200);

        assertEquals(100, a.getIntensidade());
    }

    @Test
    public void testConsumoLigadoReal() {
        Aspirador a = new Aspirador();
        a.setConsumoLigado(100);
        a.setValorIntensidadeMaximo(100);
        a.setIntensidade(50);

        assertEquals(50.0, a.consumoLigadoReal());
    }

    @Test
    public void testBateria() {
        Aspirador a = new Aspirador();

        a.setBateria(80);

        assertEquals(80, a.getBateria());
    }

    @Test
    public void testClone() {
        Aspirador a1 = new Aspirador();
        a1.setMarca("iRobot");
        a1.setModelo("Roomba");
        a1.setValorIntensidadeMaximo(100);
        a1.setIntensidade(70);
        a1.setBateria(90);

        Aspirador a2 = a1.clone();

        assertEquals(a1.getMarca(), a2.getMarca());
        assertEquals(a1.getIntensidade(), a2.getIntensidade());
        assertEquals(a1.getBateria(), a2.getBateria());

        assertNotSame(a1, a2);
    }

    @Test
    public void testEquals() {
        Aspirador a1 = new Aspirador();
        Aspirador a2 = a1;

        assertEquals(a1, a2);
    }

    @Test
    public void testToString() {
        Aspirador a = new Aspirador();
        a.setMarca("Xiaomi");
        a.setModelo("Vacuum");
        a.setValorIntensidadeMaximo(100);
        a.setIntensidade(80);
        a.setBateria(75);

        String s = a.toString();

        assertTrue(s.contains("Xiaomi"));
        assertTrue(s.contains("Vacuum"));
        assertTrue(s.contains("80"));
        assertTrue(s.contains("100"));
        assertTrue(s.contains("75"));
    }
}