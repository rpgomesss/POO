package org.domuscontrol.dispositivos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColunaTest {

    @Test
    public void testConstrutorDefault() {
        Coluna c = new Coluna();

        assertEquals(0, c.getVolume());
        assertEquals(0, c.getBateria());
        assertFalse(c.getEstado());
    }

    @Test
    public void testSetVolume() {
        Coluna c = new Coluna();

        c.setVolume(75);

        assertEquals(75, c.getVolume());
    }

    @Test
    public void testSetBateria() {
        Coluna c = new Coluna();

        c.setBateria(90);

        assertEquals(90, c.getBateria());
    }

    @Test
    public void testConsumoVolumeZero() {
        Coluna c = new Coluna();
        c.setConsumoLigado(100);
        c.setVolume(0);

        assertEquals(0.0, c.consumoLigadoReal());
    }

    @Test
    public void testConsumoVolumeMedio() {
        Coluna c = new Coluna();
        c.setConsumoLigado(100);
        c.setVolume(50);

        assertEquals(50.0, c.consumoLigadoReal());
    }

    @Test
    public void testConsumoVolumeMaximo() {
        Coluna c = new Coluna();
        c.setConsumoLigado(100);
        c.setVolume(100);

        assertEquals(100.0, c.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        Coluna c1 = new Coluna();
        c1.setMarca("Sony");
        c1.setModelo("SRS");
        c1.setVolume(80);
        c1.setBateria(70);

        Coluna c2 = c1.clone();

        assertEquals(c1.getMarca(), c2.getMarca());
        assertEquals(c1.getVolume(), c2.getVolume());
        assertEquals(c1.getBateria(), c2.getBateria());

        assertNotSame(c1, c2);
    }

    @Test
    public void testEquals() {
        Coluna c1 = new Coluna();
        Coluna c2 = c1;

        assertEquals(c1, c2);
    }

    @Test
    public void testToString() {
        Coluna c = new Coluna();
        c.setMarca("JBL");
        c.setModelo("Flip");
        c.setVolume(60);
        c.setBateria(85);

        String s = c.toString();

        assertTrue(s.contains("JBL"));
        assertTrue(s.contains("Flip"));
        assertTrue(s.contains("60"));
        assertTrue(s.contains("85"));
    }
}