package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CondicaoConsumoMaiorQueTest {

    static class DummyDispositivo extends Dispositivo {
        public DummyDispositivo() { super(); }
        public DummyDispositivo(DummyDispositivo outro) { super(outro); }
        @Override public String toString() { return "Dummy"; }
        @Override public Dispositivo clone() { return new DummyDispositivo(this); }
        @Override public boolean equals(Object o) { return false; }
        @Override public double consumoLigadoReal() { return 10.0; }
        
        // Simular um consumo de 150 para testes
        @Override
        public double totalConsumo() {
            return 150.0; 
        }
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
        CondicaoConsumoMaiorQue cond = new CondicaoConsumoMaiorQue(1, 2, 100.5);
        assertEquals("Consumo total do dispositivo 2 (divisão 1) maior que 100.5", cond.descricao());
    }

    @Test
    void testClone() {
        CondicaoConsumoMaiorQue cond1 = new CondicaoConsumoMaiorQue(1, 2, 100.5);
        CondicaoAutomacao cond2 = cond1.clone();
        assertEquals(cond1.descricao(), cond2.descricao());
    }

    @Test
    void testAvaliarConsumoMaior() {
        CondicaoConsumoMaiorQue cond = new CondicaoConsumoMaiorQue(divisao.getIdentificador(), dispositivo.getId(), 100.0);
        assertTrue(cond.avaliar(casa)); // 150 > 100
    }

    @Test
    void testAvaliarConsumoMenor() {
        CondicaoConsumoMaiorQue cond = new CondicaoConsumoMaiorQue(divisao.getIdentificador(), dispositivo.getId(), 200.0);
        assertFalse(cond.avaliar(casa)); // 150 não é > 200
    }

    @Test
    void testAvaliarInexistente() {
        CondicaoConsumoMaiorQue cond = new CondicaoConsumoMaiorQue(999, 999, 50.0);
        assertFalse(cond.avaliar(casa));
    }
}
