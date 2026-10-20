package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CamaraVigilanciaTest {

    @Test
    public void testConstrutorDefault() {
        CamaraVigilancia c = new CamaraVigilancia();

        assertFalse(c.getVisaoNoturna());
        assertFalse(c.getVisaoInfravermelho());
        assertFalse(c.getEstado());
    }

    @Test
    public void testAtivarVisaoNoturna() {
        CamaraVigilancia c = new CamaraVigilancia();

        c.setVisaoNoturna(true);

        assertTrue(c.getVisaoNoturna());
    }

    @Test
    public void testAtivarInfravermelho() {
        CamaraVigilancia c = new CamaraVigilancia();

        c.setVisaoInfravermelho(true);

        assertTrue(c.getVisaoInfravermelho());
    }

    @Test
    public void testErroAtivarNoturnaComInfravermelho() {
        CamaraVigilancia c = new CamaraVigilancia();

        c.setVisaoInfravermelho(true);

        assertThrows(IllegalStateException.class, () -> {
            c.setVisaoNoturna(true);
        });
    }

    @Test
    public void testErroAtivarInfravermelhoComNoturna() {
        CamaraVigilancia c = new CamaraVigilancia();

        c.setVisaoNoturna(true);

        assertThrows(IllegalStateException.class, () -> {
            c.setVisaoInfravermelho(true);
        });
    }

    @Test
    public void testConsumoNormal() {
        CamaraVigilancia c = new CamaraVigilancia();

        c.setConsumoLigado(100);

        assertEquals(100.0, c.consumoLigadoReal());
    }

    @Test
    public void testConsumoVisaoNoturna() {
        CamaraVigilancia c = new CamaraVigilancia();

        c.setConsumoLigado(100);
        c.setVisaoNoturna(true);

        assertEquals(150.0, c.consumoLigadoReal());
    }

    @Test
    public void testConsumoInfravermelho() {
        CamaraVigilancia c = new CamaraVigilancia();

        c.setConsumoLigado(100);
        c.setVisaoInfravermelho(true);

        assertEquals(120.0, c.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        CamaraVigilancia c1 = new CamaraVigilancia();
        c1.setMarca("TP-Link");
        c1.setVisaoNoturna(true);

        CamaraVigilancia c2 = c1.clone();

        assertEquals(c1.getMarca(), c2.getMarca());
        assertEquals(c1.getVisaoNoturna(), c2.getVisaoNoturna());

        assertNotSame(c1, c2);
    }

    @Test
    public void testEquals() {
        CamaraVigilancia c1 = new CamaraVigilancia();
        CamaraVigilancia c2 = c1;

        assertEquals(c1, c2);
    }

    @Test
    public void testToString() {
        CamaraVigilancia c = new CamaraVigilancia();
        c.setMarca("Xiaomi");
        c.setModelo("CamPro");
        c.setVisaoNoturna(true);

        String s = c.toString();

        assertTrue(s.contains("Xiaomi"));
        assertTrue(s.contains("CamPro"));
        assertTrue(s.contains("true"));
    }
}