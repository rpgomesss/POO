package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.*;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;;

public class CondicaoDispositivoLigado implements CondicaoAutomacao {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;

    public CondicaoDispositivoLigado(int idDivisao, int idDispositivo) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
    }

    public CondicaoDispositivoLigado(CondicaoDispositivoLigado outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
    }

    @Override
    public boolean avaliar(Casa casa) {
        try {
            Divisao d = casa.getDivisao(this.idDivisao);
            Dispositivo disp = d.getDispositivo(this.idDispositivo);

            return disp.getEstado();

        } catch (DivisaoInexistenteException | DispositivoInexistenteException e) {
            return false;
        }
    }

    @Override
    public String descricao() {
        return "Dispositivo " + idDispositivo + " (divisão " + idDivisao + ") ligado";
    }

    @Override
    public CondicaoAutomacao clone() {
        return new CondicaoDispositivoLigado(this);
    }

    @Override
    public String toString() {
        return "CondicaoDispositivoLigado{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CondicaoDispositivoLigado that = (CondicaoDispositivoLigado) o;
        return idDivisao == that.idDivisao && idDispositivo == that.idDispositivo;
    }
}