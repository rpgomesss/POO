package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class CondicaoConsumoMaiorQue implements CondicaoAutomacao {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;
    private double limiteConsumo;

    public CondicaoConsumoMaiorQue(int idDivisao, int idDispositivo, double limiteConsumo) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
        this.limiteConsumo = limiteConsumo;
    }

    public CondicaoConsumoMaiorQue(CondicaoConsumoMaiorQue outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
        this.limiteConsumo = outra.limiteConsumo;
    }

    @Override
    public boolean avaliar(Casa casa) {
        try {
            Divisao d = casa.getDivisao(this.idDivisao);
            Dispositivo disp = d.getDispositivo(this.idDispositivo);
            
            // Avalia se o consumo total já excedeu um limite definido
            return disp.totalConsumo() > this.limiteConsumo;
            
        } catch (DivisaoInexistenteException | DispositivoInexistenteException e) {
            return false;
        }
    }

    @Override
    public String descricao() {
        return "Consumo total do dispositivo " + idDispositivo + " (divisão " + idDivisao + ") maior que " + limiteConsumo;
    }

    @Override
    public CondicaoAutomacao clone() {
        return new CondicaoConsumoMaiorQue(this);
    }

    @Override
    public String toString() {
        return "CondicaoConsumoMaiorQue{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + ", limiteConsumo=" + limiteConsumo + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CondicaoConsumoMaiorQue that = (CondicaoConsumoMaiorQue) o;
        return idDivisao == that.idDivisao && idDispositivo == that.idDispositivo && Double.compare(that.limiteConsumo, limiteConsumo) == 0;
    }
}
