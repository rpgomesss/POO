package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TelevisaoTest {

    @Test
    public void testConstrutorDefault() {
        Televisao t = new Televisao();

        assertEquals(0, t.getVolume());
        assertEquals(0, t.getBrilho());
        assertFalse(t.getEstado());
    }

    @Test
    public void testSetVolumeDentroLimites() {
        Televisao t = new Televisao();

        t.setVolume(50);

        assertEquals(50, t.getVolume());
    }

    @Test
    public void testSetVolumeLimites() {
        Televisao t = new Televisao();

        t.setVolume(-10);
        assertEquals(0, t.getVolume());

        t.setVolume(200);
        assertEquals(100, t.getVolume());
    }

    @Test
    public void testSetBrilhoDentroLimites() {
        Televisao t = new Televisao();

        t.setBrilho(70);

        assertEquals(70, t.getBrilho());
    }

    @Test
    public void testSetBrilhoLimites() {
        Televisao t = new Televisao();

        t.setBrilho(-5);
        assertEquals(0, t.getBrilho());

        t.setBrilho(150);
        assertEquals(100, t.getBrilho());
    }

    @Test
    public void testConsumoBrilhoZero() {
        Televisao t = new Televisao();
        t.setConsumoLigado(100);
        t.setBrilho(0);

        assertEquals(0.0, t.consumoLigadoReal());
    }

    @Test
    public void testConsumoBrilhoMedio() {
        Televisao t = new Televisao();
        t.setConsumoLigado(100);
        t.setBrilho(50);

        assertEquals(50.0, t.consumoLigadoReal());
    }

    @Test
    public void testConsumoBrilhoMaximo() {
        Televisao t = new Televisao();
        t.setConsumoLigado(100);
        t.setBrilho(100);

        assertEquals(100.0, t.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        Televisao t1 = new Televisao();
        t1.setMarca("LG");
        t1.setModelo("OLED");
        t1.setVolume(80);
        t1.setBrilho(60);

        Televisao t2 = t1.clone();

        assertEquals(t1.getMarca(), t2.getMarca());
        assertEquals(t1.getVolume(), t2.getVolume());
        assertEquals(t1.getBrilho(), t2.getBrilho());

        assertNotSame(t1, t2);
    }

    @Test
    public void testEqualsTrue() {
        Televisao t1 = new Televisao();
        t1.setMarca("Samsung");
        t1.setModelo("QLED");

        Televisao t2 = new Televisao();
        t2.setMarca("Samsung");
        t2.setModelo("QLED");

        assertEquals(t1, t2);
    }

    @Test
    public void testEqualsFalse() {
        Televisao t1 = new Televisao();
        t1.setMarca("Samsung");
        t1.setModelo("QLED");

        Televisao t2 = new Televisao();
        t2.setMarca("Sony");
        t2.setModelo("Bravia");

        assertNotEquals(t1, t2);
    }

    @Test
    public void testToString() {
        Televisao t = new Televisao();
        t.setMarca("LG");
        t.setModelo("NanoCell");
        t.setVolume(40);
        t.setBrilho(80);

        String s = t.toString();

        assertTrue(s.contains("LG"));
        assertTrue(s.contains("NanoCell"));
        assertTrue(s.contains("40"));
        assertTrue(s.contains("80"));
    }
}