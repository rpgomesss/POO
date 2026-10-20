package org.domuscontrol.dispositivos;

public class Portao extends Dispositivo {

    private int percentagemAbertura;

    public Portao() {
        super();
        this.percentagemAbertura = 0;
    }

    public Portao(String marca, String modelo, boolean estado,
                   double consumoLigado, double consumoTotal,
                   double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                   int percentagemAbertura) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.percentagemAbertura = percentagemAbertura;
    }

    public Portao(Portao p) {
        super(p);
        this.percentagemAbertura = p.getPercentagemAbertura();
    }

    @Override
    public double consumoLigadoReal() {
        return super.getConsumoLigado();
    }

    public int getPercentagemAbertura() {
        return this.percentagemAbertura;
    }

    public void setPercentagemAbertura(int percentagemAbertura) {
        if (percentagemAbertura < 0) {
            this.percentagemAbertura = 0;
        } else if (percentagemAbertura > 100) {
            this.percentagemAbertura = 100;
        } else {
            this.percentagemAbertura = percentagemAbertura;
        }
    }

    @Override
    public String toString() {
        return "Portao{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", percentagemAbertura=" + percentagemAbertura +
                '}';
    }

    @Override
    public Portao clone() {
        return new Portao(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Portao)) return false;

        Portao p = (Portao) o;

        return this.getMarca().equals(p.getMarca()) &&
               this.getModelo().equals(p.getModelo());
    }
}