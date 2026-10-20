package org.domuscontrol;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.domuscontrol.dispositivos.*;
import org.domuscontrol.casa.*;
import org.domuscontrol.exceptions.DispositivoDuplicadoException;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.junit.jupiter.api.Test;

public class DivisaoTest {

    @Test
    public void testConstrutorDefault() {
        Divisao d = new Divisao();

        assertEquals("", d.getNome());
        assertEquals(0, d.numeroDispositivos());
    }

    @Test
    public void testSettersGetters() {
        Divisao d = new Divisao();

        d.setNome("Sala");

        assertEquals("Sala", d.getNome());
    }

    @Test
    public void testAdicionarDispositivo() throws Exception {
        Divisao d = new Divisao();

        Dispositivo disp = new Lampada();

        d.adicionarDispositivo(disp);

        assertEquals(1, d.numeroDispositivos());
        assertEquals(disp, d.getDispositivo(disp.getId()));
    }

    @Test
    public void testAdicionarDispositivoDuplicado() throws Exception {
        Divisao d = new Divisao();

        Dispositivo disp = new Lampada();

        d.adicionarDispositivo(disp);

        assertThrows(
            DispositivoDuplicadoException.class,
            () -> d.adicionarDispositivo(disp)
        );
    }

    @Test
    public void testRemoverDispositivo() throws Exception {
        Divisao d = new Divisao();

        Dispositivo disp = new Lampada();

        d.adicionarDispositivo(disp);
        d.removerDispositivo(disp.getId());

        assertEquals(0, d.numeroDispositivos());
    }

    @Test
    public void testRemoverDispositivoInexistente() {
        Divisao d = new Divisao();

        assertThrows(
            DispositivoInexistenteException.class,
            () -> d.removerDispositivo(999)
        );
    }

    @Test
    public void testClone() throws Exception {
        Divisao d1 = new Divisao();
        d1.setNome("Sala");

        d1.adicionarDispositivo(new Lampada());

        Divisao d2 = d1.clone();

        assertEquals(d1, d2);
        assertNotSame(d1, d2);
    }

    @Test
    public void testEquals() {
        Divisao d1 = new Divisao();
        Divisao d2 = d1;

        assertEquals(d1, d2);
    }

    @Test
    public void testGetDispositivosDefensiveCopy() throws Exception {
        Divisao d = new Divisao();

        Dispositivo disp = new Lampada();

        d.adicionarDispositivo(disp);

        Map<Integer, Dispositivo> copia = d.getDispositivos();

        copia.clear();

        assertEquals(1, d.numeroDispositivos());
    }

    @Test
    public void testSetDispositivosDefensiveCopy() {
        Divisao d = new Divisao();

        Map<Integer, Dispositivo> mapa = new HashMap<>();

        Dispositivo disp = new Lampada();

        mapa.put(disp.getId(), disp);

        d.setDispositivos(mapa);

        mapa.clear();

        assertEquals(1, d.numeroDispositivos());
    }
}