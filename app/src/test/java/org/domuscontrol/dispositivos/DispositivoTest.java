package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DispositivoTest {

    @Test
    public void testConstrutorDefault() {
        Dispositivo d = new DispositivoFalso();

        assertEquals("", d.getMarca());
        assertEquals("", d.getModelo());
        assertFalse(d.getEstado());
        assertEquals(0.0, d.getConsumoLigado());
    }

    @Test
    public void testSettersGetters() {
        Dispositivo d = new DispositivoFalso();

        d.setMarca("LG");
        d.setModelo("X1");
        d.setEstado(true);
        d.setConsumoLigado(5.0);

        assertEquals("LG", d.getMarca());
        assertEquals("X1", d.getModelo());
        assertTrue(d.getEstado());
        assertEquals(5.0, d.getConsumoLigado());
    }

    @Test
    public void testLigarDesligar() throws InterruptedException {
        Dispositivo d = new DispositivoFalso();
        d.setConsumoLigado(1.0);

        d.ligar();
        assertTrue(d.getEstado());

        Thread.sleep(10);

        d.desligar();
        assertFalse(d.getEstado());

        assertTrue(d.getConsumoTotal() > 0);
    }

    @Test
    public void testConsumoPeriodo() throws InterruptedException {
        Dispositivo d = new DispositivoFalso();
        d.setConsumoLigado(2.0);

        d.ligar();
        Thread.sleep(10);
        double consumo = d.periodoConsumo();

        assertTrue(consumo > 0);
    }

    @Test
    public void testResetPeriodo() throws InterruptedException {
        Dispositivo d = new DispositivoFalso();
        d.setConsumoLigado(2.0);

        d.ligar();
        Thread.sleep(10);
        d.periodoConsumo();

        d.resetPeriodo();

        assertEquals(0.0, d.getConsumoPeriodo());
    }

    @Test
    public void testClone() {
        Dispositivo d1 = new DispositivoFalso();
        d1.setMarca("Sony");

        Dispositivo d2 = d1.clone();

        assertEquals(d1.getMarca(), d2.getMarca());
        assertNotSame(d1, d2);
    }

    @Test
    public void testEquals() {
        Dispositivo d1 = new DispositivoFalso();
        Dispositivo d2 = d1;

        assertEquals(d1, d2);
    }
}