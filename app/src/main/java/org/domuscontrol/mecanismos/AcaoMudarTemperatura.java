package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.ArCondicionado;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class AcaoMudarTemperatura implements AcaoDispositivo {

    private static final long serialVersionUID = 1L;

    private int idDivisao;
    private int idDispositivo;
    private int novaTemperatura;

    public AcaoMudarTemperatura(int idDivisao, int idDispositivo, int novaTemperatura) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
        this.novaTemperatura = novaTemperatura;
    }

    public AcaoMudarTemperatura(AcaoMudarTemperatura outra) {
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
        this.novaTemperatura = outra.novaTemperatura;
    }

    @Override
    public void executar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        Divisao d = casa.getDivisao(this.idDivisao);  
        Dispositivo disp = d.getDispositivo(this.idDispositivo);
        
        if (disp instanceof ArCondicionado) {
            ((ArCondicionado) disp).setTemperatura(this.novaTemperatura);
        }
    }

    @Override
    public String descricao() {
        return "Mudar temperatura do Ar Condicionado " + idDispositivo + " na divisão " + idDivisao + " para " + novaTemperatura + "°C";
    }

    @Override
    public AcaoDispositivo clone() {
        return new AcaoMudarTemperatura(this);
    }

    @Override
    public String toString() {
        return "AcaoMudarTemperatura{divisão=" + idDivisao + ", dispositivo=" + idDispositivo + ", novaTemperatura=" + novaTemperatura + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcaoMudarTemperatura acao = (AcaoMudarTemperatura) o;
        return idDivisao == acao.idDivisao && idDispositivo == acao.idDispositivo && novaTemperatura == acao.novaTemperatura;
    }
}
