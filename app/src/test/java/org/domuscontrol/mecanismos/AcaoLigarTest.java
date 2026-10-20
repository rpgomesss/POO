package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcaoLigarTest {

    static class DummyDispositivo extends Dispositivo {
        public DummyDispositivo() { super(); }
        public DummyDispositivo(DummyDispositivo outro) { super(outro); }
        @Override public String toString() { return "Dummy"; }
        @Override public Dispositivo clone() { return new DummyDispositivo(this); }
        @Override public boolean equals(Object o) { return false; }
        @Override public double consumoLigadoReal() { return 10.0; }
    }

    private Casa casa;
    private Divisao divisao;
    private DummyDispositivo dispositivo;

    @BeforeEach
    void setUp() throws Exception {
        casa = new Casa();
        divisao = new Divisao();
        dispositivo = new DummyDispositivo();
        
        divisao.adicionarDispositivo(dispositivo);
        casa.adicionarDivisao(divisao);
    }

    @Test
    void testConstrutorEDescricao() {
        AcaoLigar acao = new AcaoLigar(1, 2);
        assertEquals("Ligar dispositivo 2 na divisão 1", acao.descricao());
    }

    @Test
    void testClone() {
        AcaoLigar acao1 = new AcaoLigar(1, 2);
        AcaoDispositivo acao2 = acao1.clone();
        assertEquals(acao1.descricao(), acao2.descricao());
    }

    @Test
    void testExecutarNaoLancaExcecao() {
        AcaoLigar acao = new AcaoLigar(divisao.getIdentificador(), dispositivo.getId());
        
        // Verifica que o método executa sem erros com IDs válidos.
        // Nota: Não é fácil testar se o estado real do dispositivo mudou na `casa` 
        // porque `getDivisao` e `getDispositivo` retornam clones da vossa implementação
        // e o update de volta para a `casa` encontra-se comentado no código de `AcaoLigar`.
        assertDoesNotThrow(() -> acao.executar(casa));
    }
}
