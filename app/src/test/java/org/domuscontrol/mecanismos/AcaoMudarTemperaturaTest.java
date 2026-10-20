package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.ArCondicionado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcaoMudarTemperaturaTest {

    private Casa casa;
    private Divisao divisao;
    private ArCondicionado ac;

    @BeforeEach
    void setUp() throws Exception {
        casa = new Casa();
        divisao = new Divisao();
        ac = new ArCondicionado();
        ac.setValorTemperaturaMinimo(16);
        ac.setValorTemperaturaMaximo(30);
        ac.setTemperatura(20);
        
        divisao.adicionarDispositivo(ac);
        casa.adicionarDivisao(divisao);
    }

    @Test
    void testConstrutorEDescricao() {
        AcaoMudarTemperatura acao = new AcaoMudarTemperatura(1, 2, 22);
        assertEquals("Mudar temperatura do Ar Condicionado 2 na divisão 1 para 22°C", acao.descricao());
    }

    @Test
    void testClone() {
        AcaoMudarTemperatura acao1 = new AcaoMudarTemperatura(1, 2, 24);
        AcaoDispositivo acao2 = acao1.clone();
        assertEquals(acao1.descricao(), acao2.descricao());
    }

    @Test
    void testExecutarNaoLancaExcecao() {
        AcaoMudarTemperatura acao = new AcaoMudarTemperatura(divisao.getIdentificador(), ac.getId(), 25);
        assertDoesNotThrow(() -> acao.executar(casa));
    }
}
