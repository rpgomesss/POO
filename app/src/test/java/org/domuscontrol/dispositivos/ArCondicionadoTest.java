package org.domuscontrol.dispositivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ArCondicionadoTest {

    @Test
    public void testConstrutorDefault() {
        ArCondicionado a = new ArCondicionado();

        assertEquals(0, a.getTemperatura());
        assertEquals(0, a.getValorTemperaturaMinimo());
        assertEquals(0, a.getValorTemperaturaMaximo());
        assertFalse(a.getEstado());
    }

    @Test
    public void testSetTemperaturaDentroLimites() {
        ArCondicionado a = new ArCondicionado();
        a.setValorTemperaturaMinimo(16);
        a.setValorTemperaturaMaximo(30);

        a.setTemperatura(22);

        assertEquals(22, a.getTemperatura());
    }

    @Test
    public void testTemperaturaAbaixoMinimoDesliga() {
        ArCondicionado a = new ArCondicionado();
        a.setValorTemperaturaMinimo(16);
        a.setValorTemperaturaMaximo(30);

        a.setEstado(true);
        a.setTemperatura(10);

        assertEquals(16, a.getTemperatura());
        assertFalse(a.getEstado());
    }

    @Test
    public void testTemperaturaAcimaMaximo() {
        ArCondicionado a = new ArCondicionado();
        a.setValorTemperaturaMinimo(16);
        a.setValorTemperaturaMaximo(30);

        a.setTemperatura(40);

        assertEquals(30, a.getTemperatura());
    }

    @Test
    public void testConsumoLigadoReal() {
        ArCondicionado a = new ArCondicionado();

        a.setConsumoLigado(100); // base
        a.setValorTemperaturaMaximo(50);
        a.setTemperatura(25); // 50%

        double consumo = a.consumoLigadoReal();

        assertEquals(50.0, consumo);
    }

    @Test
    public void testConsumoMaximo() {
        ArCondicionado a = new ArCondicionado();

        a.setConsumoLigado(100);
        a.setValorTemperaturaMaximo(50);
        a.setTemperatura(50);

        assertEquals(100.0, a.consumoLigadoReal());
    }

    @Test
    public void testClone() {
        ArCondicionado a1 = new ArCondicionado();
        a1.setMarca("Daikin");
        a1.setTemperatura(24);
        a1.setValorTemperaturaMinimo(16);
        a1.setValorTemperaturaMaximo(30);

        ArCondicionado a2 = a1.clone();

        assertEquals(a1.getMarca(), a2.getMarca());
        assertEquals(a1.getTemperatura(), a2.getTemperatura());
        assertEquals(a1.getValorTemperaturaMinimo(), a2.getValorTemperaturaMinimo());
        assertEquals(a1.getValorTemperaturaMaximo(), a2.getValorTemperaturaMaximo());

        assertNotSame(a1, a2);
    }

    @Test
    public void testEquals() {
        ArCondicionado a1 = new ArCondicionado();
        ArCondicionado a2 = a1;

        assertEquals(a1, a2);
    }

    @Test
    public void testToString() {
        ArCondicionado a = new ArCondicionado();
        a.setMarca("LG");
        a.setModelo("Cool");
        a.setValorTemperaturaMinimo(16);
        a.setValorTemperaturaMaximo(30);
        a.setTemperatura(20);

        String s = a.toString();

        assertTrue(s.contains("LG"));
        assertTrue(s.contains("Cool"));
        assertTrue(s.contains("20"));
        assertTrue(s.contains("16"));
        assertTrue(s.contains("30"));
    }
}