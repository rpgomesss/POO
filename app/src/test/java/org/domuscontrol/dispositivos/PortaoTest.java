package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PortaoTest {

    @Test
    public void testConstrutorDefault() {
        Portao p = new Portao();

        assertEquals(0, p.getPercentagemAbertura());
        assertFalse(p.getEstado());
    }

    @Test
    public void testSetPercentagemDentroLimites() {
        Portao p = new Portao();

        p.setPercentagemAbertura(40);

        assertEquals(40, p.getPercentagemAbertura());
    }

    @Test
    public void testSetPercentagemAbaixoZero() {
        Portao p = new Portao();

        p.setPercentagemAbertura(-20);

        assertEquals(0, p.getPercentagemAbertura());
    }

    @Test
    public void testSetPercentagemAcimaCem() {
        Portao p = new Portao();

        p.setPercentagemAbertura(150);

        assertEquals(100, p.getPercentagemAbertura());
    }

    @Test
    public void testConsumoLigadoReal() {
        Portao p = new Portao();
        p.setConsumoLigado(60);

        assertEquals(60.0, p.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        Portao p1 = new Portao();
        p1.setMarca("Nice");
        p1.setModelo("GateX");
        p1.setPercentagemAbertura(75);

        Portao p2 = p1.clone();

        assertEquals(p1.getMarca(), p2.getMarca());
        assertEquals(p1.getModelo(), p2.getModelo());
        assertEquals(p1.getPercentagemAbertura(), p2.getPercentagemAbertura());

        assertNotSame(p1, p2);
    }

    @Test
    public void testEqualsTrue() {
        Portao p1 = new Portao();
        p1.setMarca("BFT");
        p1.setModelo("Ultra");

        Portao p2 = new Portao();
        p2.setMarca("BFT");
        p2.setModelo("Ultra");

        assertEquals(p1, p2);
    }

    @Test
    public void testEqualsFalse() {
        Portao p1 = new Portao();
        p1.setMarca("BFT");
        p1.setModelo("Ultra");

        Portao p2 = new Portao();
        p2.setMarca("BFT");
        p2.setModelo("Mini");

        assertNotEquals(p1, p2);
    }

    @Test
    public void testToString() {
        Portao p = new Portao();
        p.setMarca("Nice");
        p.setModelo("Pro");
        p.setPercentagemAbertura(55);

        String s = p.toString();

        assertTrue(s.contains("Nice"));
        assertTrue(s.contains("Pro"));
        assertTrue(s.contains("55"));
    }
}