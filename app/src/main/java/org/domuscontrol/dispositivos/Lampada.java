package org.domuscontrol.dispositivos;

public class Lampada extends Dispositivo {

    private int intensidade;
    private int valorIntensidadeMinimo;
    private int valorIntensidadeMaximo;

    public Lampada() {
        super();
        this.intensidade = 0;
        this.valorIntensidadeMinimo = 0;
        this.valorIntensidadeMaximo = 100;
    }

    public Lampada(String marca, String modelo, boolean estado,
                   double consumoLigado, double consumoTotal,
                   double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                   int intensidade, int valorIntensidadeMinimo, int valorIntensidadeMaximo) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.intensidade = intensidade;
        this.valorIntensidadeMinimo = valorIntensidadeMinimo;
        this.valorIntensidadeMaximo = valorIntensidadeMaximo;
    }

    public Lampada(Lampada l) {
        super(l);
        this.intensidade = l.getIntensidade();
        this.valorIntensidadeMinimo = l.getValorIntensidadeMinimo();
        this.valorIntensidadeMaximo = l.getValorIntensidadeMaximo();
    }

    @Override
    public double consumoLigadoReal() {    //isto ou uma funcao que mude a variavel de instancia para o consumo real dependendo da intensidade
        if (this.valorIntensidadeMaximo == 0) {
            return 0;
        }
        return super.getConsumoLigado() * (this.intensidade / 100.0);
    }

    public int getIntensidade() {
        return this.intensidade;
    }

    public int getValorIntensidadeMinimo() {
        return this.valorIntensidadeMinimo;
    }

    public int getValorIntensidadeMaximo() {
        return this.valorIntensidadeMaximo;
    }

    public void setIntensidade(int intensidade) {
        if (intensidade < this.valorIntensidadeMinimo) {
            this.intensidade = this.valorIntensidadeMinimo;
        } else if (intensidade > this.valorIntensidadeMaximo) {
            this.intensidade = this.valorIntensidadeMaximo;
        } else {
            this.intensidade = intensidade;
        }
    }

    public void setValorIntensidadeMinimo(int valorIntensidadeMinimo) {
        this.valorIntensidadeMinimo = valorIntensidadeMinimo;
    }

    public void setValorIntensidadeMaximo(int valorIntensidadeMaximo) {
        this.valorIntensidadeMaximo = valorIntensidadeMaximo;
    }

    @Override
    public String toString() {
        return "Lampada{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", intensidade=" + intensidade +
                ", valorIntensidadeMinimo=" + valorIntensidadeMinimo +
                ", valorIntensidadeMaximo=" + valorIntensidadeMaximo +
                '}';
    }

    @Override
    public Lampada clone() {
        return new Lampada(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lampada)) return false;

        Lampada l = (Lampada) o;

        return this.getMarca().equals(l.getMarca()) &&
               this.getModelo().equals(l.getModelo()) &&
               this.valorIntensidadeMinimo == l.getValorIntensidadeMinimo() &&
               this.valorIntensidadeMaximo == l.getValorIntensidadeMaximo();
    }
}