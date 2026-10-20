package org.domuscontrol;

import static org.junit.jupiter.api.Assertions.*;

import org.domuscontrol.casa.*;
import org.domuscontrol.exceptions.DivisaoDuplicadaException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.junit.jupiter.api.Test;

public class CasaTest {

    @Test
    public void testConstrutorDefault() {
        Casa c = new Casa();

        assertEquals("", c.getNome());
        assertEquals("", c.getLocalizacao());
        assertEquals(0, c.numeroDivisoes());
    }

    @Test
    public void testSettersGetters() {
        Casa c = new Casa();

        c.setNome("Casa1");
        c.setLocalizacao("Braga");

        assertEquals("Casa1", c.getNome());
        assertEquals("Braga", c.getLocalizacao());
    }

    @Test
    public void testAdicionarDivisao() throws Exception {
        Casa c = new Casa();

        Divisao d = new Divisao();
        d.setNome("Sala");

        c.adicionarDivisao(d);

        assertEquals(1, c.numeroDivisoes());
        assertEquals(d, c.getDivisao(d.getIdentificador()));
    }

    @Test
    public void testAdicionarDivisaoDuplicada() throws Exception {
        Casa c = new Casa();

        Divisao d = new Divisao();

        c.adicionarDivisao(d);

        assertThrows(
            DivisaoDuplicadaException.class,
            () -> c.adicionarDivisao(d)
        );
    }

    @Test
    public void testRemoverDivisao() throws Exception {
        Casa c = new Casa();

        Divisao d = new Divisao();

        c.adicionarDivisao(d);

        c.removerDivisao(d);

        assertEquals(0, c.numeroDivisoes());
    }

    @Test
    public void testRemoverDivisaoInexistente() {
        Casa c = new Casa();

        Divisao d = new Divisao();

        assertThrows(
            DivisaoInexistenteException.class,
            () -> c.removerDivisao(d)
        );
    }

    @Test
    public void testClone() throws Exception {
        Casa c1 = new Casa();

        c1.setNome("Casa1");

        Divisao d = new Divisao();

        c1.adicionarDivisao(d);

        Casa c2 = c1.clone();

        assertEquals(c1, c2);
        assertNotSame(c1, c2);
    }

    @Test
    public void testEquals() {
        Casa c1 = new Casa();
        Casa c2 = c1;

        assertEquals(c1, c2);
    }
}