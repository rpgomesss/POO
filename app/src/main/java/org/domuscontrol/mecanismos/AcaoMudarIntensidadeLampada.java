package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Lampada;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class AcaoMudarIntensidadeLampada implements AcaoDispositivo {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;
    private int novaIntensidade;

    public AcaoMudarIntensidadeLampada(int idDivisao, int idDispositivo, int novaIntensidade) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
        this.novaIntensidade = novaIntensidade;
    }

    public AcaoMudarIntensidadeLampada(AcaoMudarIntensidadeLampada outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
        this.novaIntensidade = outra.novaIntensidade;
    }

    @Override
    public void executar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        Divisao d = casa.getDivisao(this.idDivisao);  
        Dispositivo disp = d.getDispositivo(this.idDispositivo);
        
        if (disp instanceof Lampada) {
            ((Lampada) disp).setIntensidade(this.novaIntensidade);
        }
    }

    @Override
    public String descricao() {
        return "Mudar intensidade da Lâmpada " + idDispositivo + " na divisão " + idDivisao + " para " + novaIntensidade;
    }

    @Override
    public AcaoDispositivo clone() {
        return new AcaoMudarIntensidadeLampada(this);
    }

    @Override
    public String toString() {
        return "AcaoMudarIntensidadeLampada{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + ", novaIntensidade=" + novaIntensidade + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcaoMudarIntensidadeLampada acao = (AcaoMudarIntensidadeLampada) o;
        return idDivisao == acao.idDivisao && idDispositivo == acao.idDispositivo && novaIntensidade == acao.novaIntensidade;
    }
}
