package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CondicaoDispositivoLigadoTest {

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
        CondicaoDispositivoLigado cond = new CondicaoDispositivoLigado(1, 2);
        assertEquals("Dispositivo 2 (divisão 1) ligado", cond.descricao());
    }

    @Test
    void testClone() {
        CondicaoDispositivoLigado cond1 = new CondicaoDispositivoLigado(1, 2);
        CondicaoAutomacao cond2 = cond1.clone();
        assertEquals(cond1.descricao(), cond2.descricao());
    }

    @Test
    void testAvaliarDispositivoDesligado() {
        CondicaoDispositivoLigado cond = new CondicaoDispositivoLigado(divisao.getIdentificador(), dispositivo.getId());
        // Por defeito, os dispositivos nascem desligados
        assertFalse(cond.avaliar(casa));
    }

    @Test
    void testAvaliarDispositivoLigado() throws Exception {
        // Criar um novo dispositivo já ligado para adicionar a uma casa
        DummyDispositivo d2 = new DummyDispositivo();
        d2.ligar();
        
        Divisao div2 = new Divisao();
        div2.adicionarDispositivo(d2);
        
        Casa c2 = new Casa();
        c2.adicionarDivisao(div2);

        CondicaoDispositivoLigado cond = new CondicaoDispositivoLigado(div2.getIdentificador(), d2.getId());
        assertTrue(cond.avaliar(c2));
    }

    @Test
    void testAvaliarInexistente() {
        CondicaoDispositivoLigado cond = new CondicaoDispositivoLigado(999, 999);
        // Mesmo atirando exceções, o método avaliar captura-as num catch e devolve false
        assertFalse(cond.avaliar(casa), "Deve retornar falso quando aponta para dispositivo/divisão inexistente.");
    }
}
