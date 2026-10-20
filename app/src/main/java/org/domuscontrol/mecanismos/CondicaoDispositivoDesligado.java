package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class CondicaoDispositivoDesligado implements CondicaoAutomacao {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;

    public CondicaoDispositivoDesligado(int idDivisao, int idDispositivo) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
    }

    public CondicaoDispositivoDesligado(CondicaoDispositivoDesligado outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
    }

    @Override
    public boolean avaliar(Casa casa) {
        try {
            Divisao d = casa.getDivisao(this.idDivisao);
            Dispositivo disp = d.getDispositivo(this.idDispositivo);

            return !disp.getEstado();

        } catch (DivisaoInexistenteException | DispositivoInexistenteException e) {
            return false;
        }
    }

    @Override
    public String descricao() {
        return "Dispositivo " + idDispositivo + " (divisão " + idDivisao + ") desligado";
    }

    @Override
    public CondicaoAutomacao clone() {
        return new CondicaoDispositivoDesligado(this);
    }

    @Override
    public String toString() {
        return "CondicaoDispositivoDesligado{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CondicaoDispositivoDesligado that = (CondicaoDispositivoDesligado) o;
        return idDivisao == that.idDivisao && idDispositivo == that.idDispositivo;
    }
}
