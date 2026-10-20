package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EscalonamentoTest {

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
            return "Dummy";
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
    void testConstrutorDiario() {
        boolean[] executada = {false};
        Escalonamento e = new Escalonamento("Diario", 600, new ArrayList<>(List.of(new DummyAcao(executada))));
        assertTrue(e.isTodosDias());
        assertEquals(600, e.getHoraDisparoMinutos());
    }

    @Test
    void testConstrutorSemanal() {
        boolean[] executada = {false};
        boolean[] dias = {true, false, true, false, false, false, false};
        Escalonamento e = new Escalonamento("Semanal", 700, dias, new ArrayList<>(List.of(new DummyAcao(executada))));
        assertFalse(e.isTodosDias());
        assertArrayEquals(dias, e.getDiasSemana());
    }

    @Test
    void testTentarDiario() throws Exception {
        boolean[] executada = {false};
        Escalonamento e = new Escalonamento("Diario", 600, new ArrayList<>(List.of(new DummyAcao(executada))));

        e.tentar(casa, 599, 0);
        assertFalse(executada[0], "Não deve disparar se a hora não corresponder");

        e.tentar(casa, 600, 0);
        assertTrue(executada[0], "Deve disparar na hora certa");

        executada[0] = false;
        e.tentar(casa, 600, 0);
        assertFalse(executada[0], "Não deve disparar duas vezes no mesmo minuto");
    }

    @Test
    void testTentarSemanal() throws Exception {
        boolean[] executada = {false};
        boolean[] dias = {false, true, false, false, false, false, false}; // Apenas dia 1 (ex. 0=Seg, 1=Ter...)
        Escalonamento e = new Escalonamento("Semanal", 600, dias, new ArrayList<>(List.of(new DummyAcao(executada))));

        e.tentar(casa, 600, 0);
        assertFalse(executada[0], "Não deve disparar no dia errado");

        e.tentar(casa, 600, 1);
        assertTrue(executada[0], "Deve disparar no dia certo e hora certa");
    }

    @Test
    void testTentarLocalDateTime() throws Exception {
        boolean[] executada = {false};
        Escalonamento e = new Escalonamento("Diario", 10 * 60 + 30, new ArrayList<>(List.of(new DummyAcao(executada))));

        LocalDateTime dataCerta = LocalDateTime.of(2023, 10, 10, 10, 30);
        LocalDateTime dataErrada = LocalDateTime.of(2023, 10, 10, 10, 31);

        e.tentar(casa, dataErrada);
        assertFalse(executada[0]);

        e.tentar(casa, dataCerta);
        assertTrue(executada[0]);
    }
}
