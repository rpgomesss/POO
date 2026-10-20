package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutomacaoTest {

    static class DummyCondicao implements CondicaoAutomacao {
        boolean resultado;
        
        public DummyCondicao(boolean resultado) {
            this.resultado = resultado;
        }

        @Override
        public boolean avaliar(Casa casa) {
            return resultado;
        }

        @Override
        public String descricao() {
            return "Dummy Condicao";
        }

        @Override
        public CondicaoAutomacao clone() {
            return new DummyCondicao(resultado);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return resultado == ((DummyCondicao) o).resultado;
        }
    }

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
            return "Dummy Acao";
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
    void testConstrutorVazioRetornaNull() {
        Automacao a1 = new Automacao();
        // O construtor vazio não inicializa a condição, logo devolve null
        assertNull(a1.getCondicao());
    }

    @Test
    void testVerificarCondicaoSatisfeita() throws Exception {
        boolean[] executada = {false};
        DummyAcao acao = new DummyAcao(executada);
        DummyCondicao cond = new DummyCondicao(true);

        Automacao a = new Automacao("Auto", cond, new ArrayList<>(List.of(acao)));
        
        a.verificar(casa);
        assertTrue(executada[0], "Ação deve ser executada quando a condição é satisfeita");

        // Verificar novamente não deve executar de novo porque a condição já está ativa
        executada[0] = false;
        a.verificar(casa);
        assertFalse(executada[0], "Ação não deve ser executada se a condição já estava ativa");
    }

    @Test
    void testVerificarCondicaoNaoSatisfeita() throws Exception {
        boolean[] executada = {false};
        DummyAcao acao = new DummyAcao(executada);
        DummyCondicao cond = new DummyCondicao(false);

        Automacao a = new Automacao("Auto", cond, new ArrayList<>(List.of(acao)));
        
        a.verificar(casa);
        assertFalse(executada[0]);
    }

    @Test
    void testResetCondicao() throws Exception {
        boolean[] executada = {false};
        DummyAcao acao = new DummyAcao(executada);
        DummyCondicao cond = new DummyCondicao(true);

        Automacao a = new Automacao("Auto", cond, new ArrayList<>(List.of(acao)));
        
        a.verificar(casa);
        assertTrue(executada[0]);

        // Simula condição deixar de ser satisfeita
        a.setCondicao(new DummyCondicao(false));
        a.verificar(casa); // condicaoAtiva deve passar a false

        // Condição volta a ser satisfeita
        executada[0] = false;
        a.setCondicao(new DummyCondicao(true));
        a.verificar(casa);
        assertTrue(executada[0], "Ação deve ser executada novamente após a condição ter sido falsa");
    }
}
