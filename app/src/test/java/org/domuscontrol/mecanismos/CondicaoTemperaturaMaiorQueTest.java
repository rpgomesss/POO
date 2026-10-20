package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.ArCondicionado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CondicaoTemperaturaMaiorQueTest {

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
        ac.setTemperatura(25);
        
        divisao.adicionarDispositivo(ac);
        casa.adicionarDivisao(divisao);
    }

    @Test
    void testConstrutorEDescricao() {
        CondicaoTemperaturaMaiorQue cond = new CondicaoTemperaturaMaiorQue(1, 2, 24);
        assertEquals("Temperatura do Ar Condicionado 2 (divisão 1) maior que 24°C", cond.descricao());
    }

    @Test
    void testClone() {
        CondicaoTemperaturaMaiorQue cond1 = new CondicaoTemperaturaMaiorQue(1, 2, 24);
        CondicaoAutomacao cond2 = cond1.clone();
        assertEquals(cond1.descricao(), cond2.descricao());
    }

    @Test
    void testAvaliarTemperaturaMaior() {
        CondicaoTemperaturaMaiorQue cond = new CondicaoTemperaturaMaiorQue(divisao.getIdentificador(), ac.getId(), 24);
        assertTrue(cond.avaliar(casa)); // 25 > 24
    }

    @Test
    void testAvaliarTemperaturaMenor() {
        CondicaoTemperaturaMaiorQue cond = new CondicaoTemperaturaMaiorQue(divisao.getIdentificador(), ac.getId(), 26);
        assertFalse(cond.avaliar(casa)); // 25 is not > 26
    }
    
    @Test
    void testAvaliarTemperaturaIgual() {
        CondicaoTemperaturaMaiorQue cond = new CondicaoTemperaturaMaiorQue(divisao.getIdentificador(), ac.getId(), 25);
        assertFalse(cond.avaliar(casa)); // 25 is not > 25
    }

    @Test
    void testAvaliarInexistente() {
        CondicaoTemperaturaMaiorQue cond = new CondicaoTemperaturaMaiorQue(999, 999, 20);
        assertFalse(cond.avaliar(casa));
    }
}
