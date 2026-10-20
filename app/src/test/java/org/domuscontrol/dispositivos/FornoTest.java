package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FornoTest {

    @Test
    public void testConstrutorDefault() {
        Forno f = new Forno();

        assertEquals(0, f.getTemperatura());
        assertEquals(0, f.getValorTemperaturaMinimo());
        assertEquals(0, f.getValorTemperaturaMaximo());
        assertFalse(f.getEstado());
    }

    @Test
    public void testSetTemperaturaDentroLimites() {
        Forno f = new Forno();
        f.setValorTemperaturaMinimo(50);
        f.setValorTemperaturaMaximo(250);

        f.setTemperatura(100);

        assertEquals(100, f.getTemperatura());
    }

    @Test
    public void testTemperaturaAbaixoMinimoDesliga() {
        Forno f = new Forno();
        f.setValorTemperaturaMinimo(50);
        f.setValorTemperaturaMaximo(250);

        f.setEstado(true);
        f.setTemperatura(10);

        assertEquals(50, f.getTemperatura());
        assertFalse(f.getEstado());
    }

    @Test
    public void testTemperaturaAcimaMaximo() {
        Forno f = new Forno();
        f.setValorTemperaturaMinimo(50);
        f.setValorTemperaturaMaximo(250);

        f.setTemperatura(300);

        assertEquals(250, f.getTemperatura());
    }

    @Test
    public void testConsumoLigadoReal() {
        Forno f = new Forno();

        f.setConsumoLigado(100);
        f.setValorTemperaturaMaximo(200);
        f.setTemperatura(100); // 50%

        assertEquals(50.0, f.consumoLigadoReal());
    }

    @Test
    public void testConsumoMaximo() {
        Forno f = new Forno();

        f.setConsumoLigado(100);
        f.setValorTemperaturaMaximo(200);
        f.setTemperatura(200);

        assertEquals(100.0, f.consumoLigadoReal());
    }

    @Test
    public void testConsumoTemperaturaMinima() {
        Forno f = new Forno();

        f.setConsumoLigado(100);
        f.setValorTemperaturaMinimo(50);
        f.setValorTemperaturaMaximo(200);

        f.setTemperatura(50);

        assertEquals(25.0, f.consumoLigadoReal());
    }

    @Test
    public void testDivisaoPorZeroProtecao() {
        Forno f = new Forno();

        f.setConsumoLigado(100);
        f.setValorTemperaturaMaximo(0);
        f.setTemperatura(0);

        // evita crash — comportamento esperado: 0
        assertDoesNotThrow(() -> f.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        Forno f1 = new Forno();
        f1.setMarca("Bosch");
        f1.setTemperatura(180);
        f1.setValorTemperaturaMinimo(50);
        f1.setValorTemperaturaMaximo(250);

        Forno f2 = f1.clone();

        assertEquals(f1.getMarca(), f2.getMarca());
        assertEquals(f1.getTemperatura(), f2.getTemperatura());
        assertEquals(f1.getValorTemperaturaMinimo(), f2.getValorTemperaturaMinimo());
        assertEquals(f1.getValorTemperaturaMaximo(), f2.getValorTemperaturaMaximo());

        assertNotSame(f1, f2);
    }

    @Test
    public void testEquals() {
        Forno f1 = new Forno();
        Forno f2 = f1;

        assertEquals(f1, f2);
    }

    @Test
    public void testToString() {
        Forno f = new Forno();
        f.setMarca("Teka");
        f.setModelo("Pro");
        f.setValorTemperaturaMinimo(50);
        f.setValorTemperaturaMaximo(250);
        f.setTemperatura(180);

        String s = f.toString();

        assertTrue(s.contains("Teka"));
        assertTrue(s.contains("Pro"));
        assertTrue(s.contains("180"));
        assertTrue(s.contains("50"));
        assertTrue(s.contains("250"));
    }
}