package org.domuscontrol.dispositivos;

public class DispositivoFalso extends Dispositivo {

    public DispositivoFalso() {
        super();
    }

    public DispositivoFalso(String marca, String modelo, boolean estado,
                            double consumoLigado, double consumoTotal,
                            double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal) {
        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);
    }

    public DispositivoFalso(Dispositivo umDispositivo) {
        super(umDispositivo);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", consumoLigado=" + getConsumoLigado() +
                ", consumoTotal=" + getConsumoTotal() +
                ", consumoPeriodo=" + getConsumoPeriodo() +
                ", marcadorTemporal=" + getMarcadorTemporal() +
                ", numeroAtivacoes=" + getNumeroAtivacoes() +
                ", tempoAtivoTotal=" + getTempoAtivoTotal() +
                '}';
    }

    @Override
    public DispositivoFalso clone() {
        return new DispositivoFalso(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DispositivoFalso)) return false;
        DispositivoFalso d = (DispositivoFalso) o;
        return this.getId() == d.getId();
    }

    @Override
    public double consumoLigadoReal() {
        return getConsumoLigado();
    }
}