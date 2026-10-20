package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Lampada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcaoMudarIntensidadeLampadaTest {

    private Casa casa;
    private Divisao divisao;
    private Lampada lampada;

    @BeforeEach
    void setUp() throws Exception {
        casa = new Casa();
        divisao = new Divisao();
        lampada = new Lampada();
        lampada.setValorIntensidadeMinimo(0);
        lampada.setValorIntensidadeMaximo(100);
        lampada.setIntensidade(50);
        
        divisao.adicionarDispositivo(lampada);
        casa.adicionarDivisao(divisao);
    }

    @Test
    void testConstrutorEDescricao() {
        AcaoMudarIntensidadeLampada acao = new AcaoMudarIntensidadeLampada(1, 2, 80);
        assertEquals("Mudar intensidade da Lâmpada 2 na divisão 1 para 80", acao.descricao());
    }

    @Test
    void testClone() {
        AcaoMudarIntensidadeLampada acao1 = new AcaoMudarIntensidadeLampada(1, 2, 90);
        AcaoDispositivo acao2 = acao1.clone();
        assertEquals(acao1.descricao(), acao2.descricao());
    }

    @Test
    void testExecutarNaoLancaExcecao() {
        AcaoMudarIntensidadeLampada acao = new AcaoMudarIntensidadeLampada(divisao.getIdentificador(), lampada.getId(), 100);
        assertDoesNotThrow(() -> acao.executar(casa));
    }
}
