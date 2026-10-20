package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.*;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class AcaoLigar implements AcaoDispositivo {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;

    public AcaoLigar(int idDivisao, int idDispositivo) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
    }

    public AcaoLigar(AcaoLigar outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
    }

    @Override
    public void executar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException{
        Divisao d = casa.getDivisao(this.idDivisao);  
        Dispositivo disp = d.getDispositivo(this.idDispositivo);
        
        disp.ligar();
    }

    @Override
    public String descricao() {
        return "Ligar dispositivo " + idDispositivo + " na divisão " + idDivisao;
    }

    @Override
    public AcaoDispositivo clone() {
        return new AcaoLigar(this);
    }

    @Override
    public String toString() {
        return "AcaoLigar{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcaoLigar acao = (AcaoLigar) o;
        return idDivisao == acao.idDivisao && idDispositivo == acao.idDispositivo;
    }
}