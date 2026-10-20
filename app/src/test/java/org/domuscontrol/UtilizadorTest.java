package org.domuscontrol;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.domuscontrol.casa.Casa;
import org.junit.jupiter.api.Test;

public class UtilizadorTest {

    @Test
    public void testConstrutorDefault() {
        Utilizador u = new Utilizador();

        assertEquals("", u.getNome());
        assertEquals(0, u.numeroCasas());
        assertEquals(0, u.numeroCasasAdmin());
    }

    @Test
    public void testSettersGetters() {
        Utilizador u = new Utilizador();

        u.setNome("João");

        assertEquals("João", u.getNome());
    }

    @Test
    public void testAdicionarCasa() {
        Utilizador u = new Utilizador();
        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");

        u.adicionarCasa(c);

        assertEquals(1, u.numeroCasas());
        assertEquals(c, u.getCasa(c.getIdentificador()));
    }

    @Test
    public void testAdicionarCasaAdmin() {
        Utilizador u = new Utilizador();
        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");

        u.adicionarCasaAdmin(c);

        assertEquals(1, u.numeroCasasAdmin());
        assertEquals(c, u.getCasa(c.getIdentificador()));
    }

    @Test
    public void testRemoverCasa() {
        Utilizador u = new Utilizador();
        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");

        u.adicionarCasa(c);
        u.removerCasa(c.getIdentificador());

        assertEquals(0, u.numeroCasas());
    }

    @Test
    public void testRemoverCasaAdmin() {
        Utilizador u = new Utilizador();
        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");

        u.adicionarCasaAdmin(c);
        u.removerCasaAdmin(c.getIdentificador());

        assertEquals(0, u.numeroCasasAdmin());
    }

    @Test
    public void testGetCasaPrioridade() {
        Utilizador u = new Utilizador();
        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");

        u.adicionarCasa(c);

        assertEquals(c, u.getCasa(c.getIdentificador()));
    }

    @Test
    public void testClone() {
        Utilizador u1 = new Utilizador();
        u1.setNome("Ana");

        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");
        u1.adicionarCasa(c);

        Utilizador u2 = u1.clone();

        assertEquals(u1.getNome(), u2.getNome());
        assertEquals(u1.numeroCasas(), u2.numeroCasas());

        assertNotSame(u1, u2); // objetos diferentes
    }

    @Test
    public void testEquals() {
        Utilizador u1 = new Utilizador();
        Utilizador u2 = u1;

        assertEquals(u1, u2);
    }

    @Test
    public void testSetCasasDefensiveCopy() {
        Utilizador u = new Utilizador();

        Map<Integer, Casa> mapa = new HashMap<>();
        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");
        mapa.put(c.getIdentificador(), c);

        u.setCasas(mapa);

        mapa.clear(); // modificar fora

        assertEquals(1, u.numeroCasas()); // não deve afetar
    }

    @Test
    public void testGetCasasDefensiveCopy() {
        Utilizador u = new Utilizador();
        Casa c = new Casa();
        c.setNome("Casa1");
        c.setLocalizacao("Porto");

        u.adicionarCasa(c);

        Map<Integer, Casa> copia = u.getCasas();
        copia.clear();

        assertEquals(1, u.numeroCasas()); // não deve afetar original
    }
}