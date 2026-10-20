package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MecanismoTest {

    static class DummyAcao implements AcaoDispositivo {
        boolean[] sideEffect;
        String id;

        public DummyAcao(boolean[] sideEffect, String id) {
            this.sideEffect = sideEffect;
            this.id = id;
        }

        @Override
        public void executar(Casa casa) {
            sideEffect[0] = true;
        }

        @Override
        public String descricao() {
            return id;
        }

        @Override
        public AcaoDispositivo clone() {
            return new DummyAcao(sideEffect, id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            DummyAcao that = (DummyAcao) o;
            return id.equals(that.id);
        }
    }

    private Casa casa;

    @BeforeEach
    void setUp() {
        casa = new Casa();
    }

    @Test
    void testConstrutorVazio() {
        Mecanismo m = new Mecanismo();
        assertEquals("", m.getNome());
        assertEquals("", m.getDescricao());
        assertTrue(m.getAcoes().isEmpty());
    }

    @Test
    void testConstrutorParametrizado() {
        List<AcaoDispositivo> acoes = new ArrayList<>();
        boolean[] ef1 = { false };
        acoes.add(new DummyAcao(ef1, "A1"));

        Mecanismo m = new Mecanismo("M1", "Desc", acoes);
        assertEquals("M1", m.getNome());
        assertEquals("Desc", m.getDescricao());
        assertEquals(1, m.getAcoes().size());
    }

    @Test
    void testConstrutorCopia() {
        Mecanismo m1 = new Mecanismo();
        m1.setNome("M1");
        m1.setDescricao("Desc");

        Mecanismo m2 = new Mecanismo(m1);
        assertEquals("M1", m2.getNome());
        assertEquals("Desc", m2.getDescricao());
        assertEquals(m1.getIdentificador(), m2.getIdentificador());
    }

    @Test
    void testAdicionarRemoverAcao() {
        Mecanismo m = new Mecanismo();
        boolean[] ef = { false };
        DummyAcao acao = new DummyAcao(ef, "A1");

        m.adicionarAcao(acao);
        assertEquals(1, m.getAcoes().size());

        m.removerAcao(acao);
        assertTrue(m.getAcoes().isEmpty());
    }

    @Test
    void testExecutarAcoes() throws Exception {
        Mecanismo m = new Mecanismo();
        boolean[] ef = { false };
        m.adicionarAcao(new DummyAcao(ef, "A1"));

        assertFalse(ef[0]);
        m.executarAcoes(casa);
        assertTrue(ef[0]);
    }

    @Test
    void testCloneEEquals() {
        Mecanismo m1 = new Mecanismo();
        m1.setNome("Nome");
        m1.setDescricao("Desc");

        Mecanismo m2 = m1.clone();

        assertEquals(m1, m2);
        assertEquals(m1.getIdentificador(), m2.getIdentificador());
        assertEquals(m1.getNome(), m2.getNome());
    }
}
