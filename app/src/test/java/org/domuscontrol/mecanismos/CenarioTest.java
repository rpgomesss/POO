package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CenarioTest {

    static class DummyAcao implements AcaoDispositivo {
        boolean[] executada;

        public DummyAcao(boolean[] executada) {
            this.executada = executada;
        }

        @Override
        public void executar(Casa casa) {
            executada[0] = true;
        }

        @Override
        public String descricao() {
            return "Dummy";
        }

        @Override
        public AcaoDispositivo clone() {
            return new DummyAcao(executada);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof DummyAcao && ((DummyAcao)o).executada == this.executada;
        }
    }

    private Casa casa;

    @BeforeEach
    void setUp() {
        casa = new Casa();
    }

    @Test
    void testConstrutor() {
        Cenario c = new Cenario();
        assertFalse(c.isAtivo());
        assertTrue(c.getAcoesDesativacao().isEmpty());
    }

    @Test
    void testAtivar() throws Exception {
        boolean[] ativada = {false};
        boolean[] desativada = {false};

        List<AcaoDispositivo> acoesAtivacao = new ArrayList<>();
        acoesAtivacao.add(new DummyAcao(ativada));

        List<AcaoDispositivo> acoesDesativacao = new ArrayList<>();
        acoesDesativacao.add(new DummyAcao(desativada));

        Cenario c = new Cenario("C1", "Desc", acoesAtivacao, acoesDesativacao);
        
        assertFalse(c.isAtivo());
        
        c.ativar(casa);
        assertTrue(c.isAtivo());
        assertTrue(ativada[0]);
        assertFalse(desativada[0]);
        
        // Chamar ativar de novo não deve executar acoesAtivacao novamente
        ativada[0] = false;
        c.ativar(casa);
        assertFalse(ativada[0]);
    }

    @Test
    void testDesativar() throws Exception {
        boolean[] ativada = {false};
        boolean[] desativada = {false};

        List<AcaoDispositivo> acoesAtivacao = new ArrayList<>();
        acoesAtivacao.add(new DummyAcao(ativada));

        List<AcaoDispositivo> acoesDesativacao = new ArrayList<>();
        acoesDesativacao.add(new DummyAcao(desativada));

        Cenario c = new Cenario("C1", "Desc", acoesAtivacao, acoesDesativacao);
        c.ativar(casa);
        assertTrue(c.isAtivo());

        c.desativar(casa);
        assertFalse(c.isAtivo());
        assertTrue(desativada[0]);
    }

    @Test
    void testAlternar() throws Exception {
        boolean[] ativada = {false};
        boolean[] desativada = {false};

        Cenario c = new Cenario("C1", "Desc", 
                new ArrayList<>(List.of(new DummyAcao(ativada))),
                new ArrayList<>(List.of(new DummyAcao(desativada))));

        c.alternar(casa);
        assertTrue(c.isAtivo());
        assertTrue(ativada[0]);

        c.alternar(casa);
        assertFalse(c.isAtivo());
        assertTrue(desativada[0]);
    }
}
