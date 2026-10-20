package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LampadaTest {

    @Test
    public void testConstrutorDefault() {
        Lampada l = new Lampada();

        assertEquals(0, l.getIntensidade());
        assertEquals(0, l.getValorIntensidadeMinimo());
        assertEquals(100, l.getValorIntensidadeMaximo());
        assertFalse(l.getEstado());
    }

    @Test
    public void testSetIntensidadeDentroLimites() {
        Lampada l = new Lampada();

        l.setIntensidade(50);

        assertEquals(50, l.getIntensidade());
    }

    @Test
    public void testIntensidadeAbaixoMinimo() {
        Lampada l = new Lampada();
        l.setValorIntensidadeMinimo(20);

        l.setIntensidade(10);

        assertEquals(20, l.getIntensidade());
    }

    @Test
    public void testIntensidadeAcimaMaximo() {
        Lampada l = new Lampada();
        l.setValorIntensidadeMaximo(80);

        l.setIntensidade(100);

        assertEquals(80, l.getIntensidade());
    }

    @Test
    public void testConsumoLigadoReal() {
        Lampada l = new Lampada();

        l.setConsumoLigado(100);
        l.setIntensidade(50);

        assertEquals(50.0, l.consumoLigadoReal());
    }

    @Test
    public void testConsumoMaximo() {
        Lampada l = new Lampada();

        l.setConsumoLigado(100);
        l.setIntensidade(100);

        assertEquals(100.0, l.consumoLigadoReal());
    }

    @Test
    public void testConsumoMinimo() {
        Lampada l = new Lampada();

        l.setConsumoLigado(100);
        l.setIntensidade(0);

        assertEquals(0.0, l.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        Lampada l1 = new Lampada();
        l1.setMarca("Philips");
        l1.setIntensidade(70);
        l1.setValorIntensidadeMinimo(10);
        l1.setValorIntensidadeMaximo(90);

        Lampada l2 = l1.clone();

        assertEquals(l1.getMarca(), l2.getMarca());
        assertEquals(l1.getIntensidade(), l2.getIntensidade());
        assertEquals(l1.getValorIntensidadeMinimo(), l2.getValorIntensidadeMinimo());
        assertEquals(l1.getValorIntensidadeMaximo(), l2.getValorIntensidadeMaximo());

        assertNotSame(l1, l2);
    }

    @Test
    public void testEquals() {
        Lampada l1 = new Lampada();
        Lampada l2 = l1;

        assertEquals(l1, l2);
    }

    @Test
    public void testToString() {
        Lampada l = new Lampada();
        l.setMarca("Xiaomi");
        l.setModelo("Smart");
        l.setValorIntensidadeMinimo(10);
        l.setValorIntensidadeMaximo(90);
        l.setIntensidade(80);

        String s = l.toString();

        assertTrue(s.contains("Xiaomi"));
        assertTrue(s.contains("Smart"));
        assertTrue(s.contains("80"));
        assertTrue(s.contains("10"));
        assertTrue(s.contains("90"));
    }
}