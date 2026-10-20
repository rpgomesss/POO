package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.ArCondicionado;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class CondicaoTemperaturaMaiorQue implements CondicaoAutomacao {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;
    private int limiteTemperatura;

    public CondicaoTemperaturaMaiorQue(int idDivisao, int idDispositivo, int limiteTemperatura) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
        this.limiteTemperatura = limiteTemperatura;
    }

    public CondicaoTemperaturaMaiorQue(CondicaoTemperaturaMaiorQue outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
        this.limiteTemperatura = outra.limiteTemperatura;
    }

    @Override
    public boolean avaliar(Casa casa) {
        try {
            Divisao d = casa.getDivisao(this.idDivisao);
            Dispositivo disp = d.getDispositivo(this.idDispositivo);
            
            if (disp instanceof ArCondicionado) {
                return ((ArCondicionado) disp).getTemperatura() > this.limiteTemperatura;
            }
            return false;
            
        } catch (DivisaoInexistenteException | DispositivoInexistenteException e) {
            return false;
        }
    }

    @Override
    public String descricao() {
        return "Temperatura do Ar Condicionado " + idDispositivo + " (divisão " + idDivisao + ") maior que " + limiteTemperatura + "°C";
    }

    @Override
    public CondicaoAutomacao clone() {
        return new CondicaoTemperaturaMaiorQue(this);
    }

    @Override
    public String toString() {
        return "CondicaoTemperaturaMaiorQue{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + ", limiteTemperatura=" + limiteTemperatura + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CondicaoTemperaturaMaiorQue that = (CondicaoTemperaturaMaiorQue) o;
        return idDivisao == that.idDivisao && idDispositivo == that.idDispositivo && limiteTemperatura == that.limiteTemperatura;
    }
}
