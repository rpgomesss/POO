package org.domuscontrol;

import static org.junit.jupiter.api.Assertions.*;

import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.UtilizadorInexistenteException;
import org.domuscontrol.model.DomusControl;
import org.junit.jupiter.api.Test;

public class DomusControlTest {

    @Test
    public void testConstrutorDefault() {
        DomusControl dc = new DomusControl();

        assertEquals(0, dc.numeroCasas());
        assertEquals(0, dc.numeroUtilizadores());
    }

    @Test
    public void testAdicionarUtilizador() throws Exception {
        DomusControl dc = new DomusControl();

        dc.adicionarUtilizador("João");

        assertEquals(1, dc.numeroUtilizadores());
    }

    @Test
    public void testRemoverUtilizador() throws Exception {
        DomusControl dc = new DomusControl();

        dc.adicionarUtilizador("João");

        int id = dc.getUtilizadores()
                .values()
                .iterator()
                .next()
                .getIdentificador();

        dc.removerUtilizador(id);

        assertEquals(0, dc.numeroUtilizadores());
    }

    @Test
    public void testRemoverUtilizadorInexistente() {
        DomusControl dc = new DomusControl();

        assertThrows(
                UtilizadorInexistenteException.class,
                () -> dc.removerUtilizador(999));
    }

    @Test
    public void testAdicionarCasa() throws Exception {
        DomusControl dc = new DomusControl();

        dc.adicionarCasa("Casa1", "Porto");

        assertEquals(1, dc.numeroCasas());
    }

    @Test
    public void testRemoverCasa() throws Exception {
        DomusControl dc = new DomusControl();

        dc.adicionarCasa("Casa1", "Porto");

        int id = dc.getCasas()
                .values()
                .iterator()
                .next()
                .getIdentificador();

        dc.removerCasa(id);

        assertEquals(0, dc.numeroCasas());
    }

    @Test
    public void testRemoverCasaInexistente() {
        DomusControl dc = new DomusControl();

        assertThrows(
                CasaInexistenteException.class,
                () -> dc.removerCasa(999));
    }

    @Test
    public void testClone() throws Exception {
        DomusControl dc1 = new DomusControl();

        dc1.adicionarCasa("Casa1", "Porto");

        DomusControl dc2 = dc1.clone();

        assertEquals(dc1, dc2);
        assertNotSame(dc1, dc2);
    }

    @Test
    public void testEquals() {
        DomusControl dc1 = new DomusControl();
        DomusControl dc2 = dc1;

        assertEquals(dc1, dc2);
    }
}