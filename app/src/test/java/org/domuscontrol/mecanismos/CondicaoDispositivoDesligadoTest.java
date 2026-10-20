package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CondicaoDispositivoDesligadoTest {

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
        dispositivo = new DummyDispositivo(); // estado por defeito = desligado (false)
        
        divisao.adicionarDispositivo(dispositivo);
        casa.adicionarDivisao(divisao);
    }

    @Test
    void testConstrutorEDescricao() {
        CondicaoDispositivoDesligado cond = new CondicaoDispositivoDesligado(1, 2);
        assertEquals("Dispositivo 2 (divisão 1) desligado", cond.descricao());
    }

    @Test
    void testClone() {
        CondicaoDispositivoDesligado cond1 = new CondicaoDispositivoDesligado(1, 2);
        CondicaoAutomacao cond2 = cond1.clone();
        assertEquals(cond1.descricao(), cond2.descricao());
    }

    @Test
    void testAvaliarDispositivoDesligado() {
        CondicaoDispositivoDesligado cond = new CondicaoDispositivoDesligado(divisao.getIdentificador(), dispositivo.getId());
        assertTrue(cond.avaliar(casa));
    }

    @Test
    void testAvaliarDispositivoLigado() throws Exception {
        DummyDispositivo d2 = new DummyDispositivo();
        d2.ligar();
        
        Divisao div2 = new Divisao();
        div2.adicionarDispositivo(d2);
        
        Casa c2 = new Casa();
        c2.adicionarDivisao(div2);

        CondicaoDispositivoDesligado cond = new CondicaoDispositivoDesligado(div2.getIdentificador(), d2.getId());
        assertFalse(cond.avaliar(c2));
    }

    @Test
    void testAvaliarInexistente() {
        CondicaoDispositivoDesligado cond = new CondicaoDispositivoDesligado(999, 999);
        assertFalse(cond.avaliar(casa));
    }
}
