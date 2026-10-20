package org.domuscontrol.dispositivos;

public class Aspirador extends Dispositivo {

    private int intensidade;
    private int valorIntensidadeMinimo;
    private int valorIntensidadeMaximo;
    private int bateria;

    public Aspirador() {
        super();
        this.intensidade = 0;
        this.valorIntensidadeMinimo = 0;
        this.valorIntensidadeMaximo = 0;
        this.bateria = 0;
    }

    public Aspirador(String marca, String modelo, boolean estado,
                     double consumoLigado, double consumoTotal,
                     double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                     int intensidade, int valorIntensidadeMinimo, int valorIntensidadeMaximo, int bateria) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.intensidade = intensidade;
        this.valorIntensidadeMinimo = valorIntensidadeMinimo;
        this.valorIntensidadeMaximo = valorIntensidadeMaximo;
        this.bateria = bateria;
    }

    public Aspirador(Aspirador a) {
        super(a);
        this.intensidade = a.getIntensidade();
        this.valorIntensidadeMinimo = a.getValorIntensidadeMinimo();
        this.valorIntensidadeMaximo = a.getValorIntensidadeMaximo();
        this.bateria = a.getBateria();
    }

    @Override
    public double consumoLigadoReal() {
        if (this.valorIntensidadeMaximo == 0) {
            return 0;
        }
        return super.getConsumoLigado() * (this.intensidade / (double) this.valorIntensidadeMaximo);
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

    public int getBateria() {
        return this.bateria;
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

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    @Override
    public String toString() {
        return "Aspirador{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", intensidade=" + intensidade +
                ", valorIntensidadeMinimo=" + valorIntensidadeMinimo +
                ", valorIntensidadeMaximo=" + valorIntensidadeMaximo +
                ", bateria=" + bateria +
                '}';
    }

    @Override
    public Aspirador clone() {
        return new Aspirador(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aspirador)) return false;

        Aspirador a = (Aspirador) o;

        return  this.getMarca().equals(a.getMarca()) &&
                this.getModelo().equals(a.getModelo()) &&
                this.valorIntensidadeMinimo == a.getValorIntensidadeMinimo() &&
                this.valorIntensidadeMaximo == a.getValorIntensidadeMaximo();
    }
}