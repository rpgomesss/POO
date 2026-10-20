package org.domuscontrol.mecanismos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Interacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime momento;
    private int idCasa;
    private int idDivisao;
    private int idDispositivo;
    private String tipoAcao;

    public Interacao(LocalDateTime momento, int idCasa, int idDivisao, int idDispositivo, String tipoAcao) {
        this.momento = momento;
        this.idCasa = idCasa;
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
        this.tipoAcao = tipoAcao;
    }

    public Interacao(Interacao outra) {
        this.momento = outra.momento;
        this.idCasa = outra.idCasa;
        this.idDivisao = outra.idDivisao;
        this.idDispositivo = outra.idDispositivo;
        this.tipoAcao = outra.tipoAcao;
    }

    public LocalDateTime getMomento() { return momento; }
    public int getIdCasa() { return idCasa; }
    public int getIdDivisao() { return idDivisao; }
    public int getIdDispositivo() { return idDispositivo; }
    public String getTipoAcao() { return tipoAcao; }

    @Override
    public Interacao clone() { return new Interacao(this); }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interacao that = (Interacao) o;
        return idCasa == that.idCasa && idDivisao == that.idDivisao && idDispositivo == that.idDispositivo &&
               momento.equals(that.momento) && tipoAcao.equals(that.tipoAcao);
    }

    @Override
    public String toString() {
        return "Interacao{momento=" + momento + ", idCasa=" + idCasa + ", idDivisao=" + idDivisao + ", idDispositivo=" + idDispositivo + ", tipoAcao='" + tipoAcao + "'}";
    }
}
