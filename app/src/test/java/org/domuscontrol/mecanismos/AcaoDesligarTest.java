package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcaoDesligarTest {

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
        dispositivo.ligar(); 
        
        divisao.adicionarDispositivo(dispositivo);
        casa.adicionarDivisao(divisao);
    }

    @Test
    void testConstrutorEDescricao() {
        AcaoDesligar acao = new AcaoDesligar(1, 2);
        assertEquals("Desligar dispositivo 2 na divisão 1", acao.descricao());
    }

    @Test
    void testClone() {
        AcaoDesligar acao1 = new AcaoDesligar(1, 2);
        AcaoDispositivo acao2 = acao1.clone();
        assertEquals(acao1.descricao(), acao2.descricao());
    }

    @Test
    void testExecutarNaoLancaExcecao() {
        AcaoDesligar acao = new AcaoDesligar(divisao.getIdentificador(), dispositivo.getId());
        assertDoesNotThrow(() -> acao.executar(casa));
    }
}
