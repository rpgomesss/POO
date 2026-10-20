package org.domuscontrol.mecanismos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InteracaoTest {

    private LocalDateTime momento;
    private Interacao interacaoBase;

    @BeforeEach
    void setUp() {
        momento = LocalDateTime.of(2026, 5, 10, 19, 0);
        interacaoBase = new Interacao(momento, 1, 2, 5, "LIGAR");
    }

    @Test
    void testConstrutorEGetters() {
        assertEquals(momento, interacaoBase.getMomento());
        assertEquals(1, interacaoBase.getIdCasa());
        assertEquals(2, interacaoBase.getIdDivisao());
        assertEquals(5, interacaoBase.getIdDispositivo());
        assertEquals("LIGAR", interacaoBase.getTipoAcao());
    }

    @Test
    void testConstrutorDeCopia() {
        Interacao copia = new Interacao(interacaoBase);
        
        assertEquals(interacaoBase.getMomento(), copia.getMomento());
        assertEquals(interacaoBase.getIdCasa(), copia.getIdCasa());
        assertEquals(interacaoBase.getIdDivisao(), copia.getIdDivisao());
        assertEquals(interacaoBase.getIdDispositivo(), copia.getIdDispositivo());
        assertEquals(interacaoBase.getTipoAcao(), copia.getTipoAcao());
    }

    @Test
    void testClone() {
        Interacao clone = interacaoBase.clone();
        
        assertNotSame(interacaoBase, clone); // Devem ser instâncias diferentes em memória
        assertEquals(interacaoBase, clone);  // Mas iguais em conteúdo
    }

    @Test
    void testEquals() {
        Interacao interacao1 = new Interacao(momento, 1, 2, 5, "LIGAR");
        Interacao interacaoIgual = new Interacao(momento, 1, 2, 5, "LIGAR");
        Interacao interacaoDiferenteAcao = new Interacao(momento, 1, 2, 5, "DESLIGAR");
        Interacao interacaoDiferenteDisp = new Interacao(momento, 1, 2, 9, "LIGAR");
        Interacao interacaoDiferenteHora = new Interacao(momento.plusHours(1), 1, 2, 5, "LIGAR");

        assertEquals(interacao1, interacao1);
        
        assertEquals(interacao1, interacaoIgual);
        assertEquals(interacaoIgual, interacao1);
        
        assertNotEquals(interacao1, interacaoDiferenteAcao);
        assertNotEquals(interacao1, interacaoDiferenteDisp);
        assertNotEquals(interacao1, interacaoDiferenteHora);
        assertNotEquals(interacao1, null);
        assertNotEquals(interacao1, new Object());
    }

    @Test
    void testToString() {
        String esperado = "Interacao{momento=2026-05-10T19:00, idCasa=1, idDivisao=2, idDispositivo=5, tipoAcao='LIGAR'}";
        assertEquals(esperado, interacaoBase.toString());
    }
}
