    package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class AcaoDesligar implements AcaoDispositivo {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;

    public AcaoDesligar(int idDivisao, int idDispositivo) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
    }

    public AcaoDesligar(AcaoDesligar outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
    }

    @Override
    public void executar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        Divisao d = casa.getDivisao(this.idDivisao);  
        Dispositivo disp = d.getDispositivo(this.idDispositivo);
        
        disp.desligar();
    }

    @Override
    public String descricao() {
        return "Desligar dispositivo " + idDispositivo + " na divisão " + idDivisao;
    }

    @Override
    public AcaoDispositivo clone() {
        return new AcaoDesligar(this);
    }

    @Override
    public String toString() {
        return "AcaoDesligar{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcaoDesligar acao = (AcaoDesligar) o;
        return idDivisao == acao.idDivisao && idDispositivo == acao.idDispositivo;
    }
}
