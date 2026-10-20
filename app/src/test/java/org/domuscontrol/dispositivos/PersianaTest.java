package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PersianaTest {

    @Test
    public void testConstrutorDefault() {
        Persiana p = new Persiana();

        assertEquals(0, p.getPercentagemAbertura());
        assertFalse(p.getEstado());
    }

    @Test
    public void testSetPercentagemDentroLimites() {
        Persiana p = new Persiana();

        p.setPercentagemAbertura(50);

        assertEquals(50, p.getPercentagemAbertura());
    }

    @Test
    public void testSetPercentagemAbaixoZero() {
        Persiana p = new Persiana();

        p.setPercentagemAbertura(-10);

        assertEquals(0, p.getPercentagemAbertura());
    }

    @Test
    public void testSetPercentagemAcimaCem() {
        Persiana p = new Persiana();

        p.setPercentagemAbertura(150);

        assertEquals(100, p.getPercentagemAbertura());
    }

    @Test
    public void testConsumoLigadoReal() {
        Persiana p = new Persiana();
        p.setConsumoLigado(75);

        assertEquals(75.0, p.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        Persiana p1 = new Persiana();
        p1.setMarca("Somfy");
        p1.setModelo("Smart");
        p1.setPercentagemAbertura(80);

        Persiana p2 = p1.clone();

        assertEquals(p1.getMarca(), p2.getMarca());
        assertEquals(p1.getModelo(), p2.getModelo());
        assertEquals(p1.getPercentagemAbertura(), p2.getPercentagemAbertura());

        assertNotSame(p1, p2);
    }

    @Test
    public void testEqualsTrue() {
        Persiana p1 = new Persiana();
        p1.setMarca("Ikea");
        p1.setModelo("BlindX");

        Persiana p2 = new Persiana();
        p2.setMarca("Ikea");
        p2.setModelo("BlindX");

        assertEquals(p1, p2);
    }

    @Test
    public void testEqualsFalse() {
        Persiana p1 = new Persiana();
        p1.setMarca("Ikea");
        p1.setModelo("BlindX");

        Persiana p2 = new Persiana();
        p2.setMarca("Ikea");
        p2.setModelo("BlindY");

        assertNotEquals(p1, p2);
    }

    @Test
    public void testToString() {
        Persiana p = new Persiana();
        p.setMarca("Somfy");
        p.setModelo("Ultra");
        p.setPercentagemAbertura(60);

        String s = p.toString();

        assertTrue(s.contains("Somfy"));
        assertTrue(s.contains("Ultra"));
        assertTrue(s.contains("60"));
    }
}