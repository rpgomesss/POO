package org.domuscontrol.dispositivos;

public class Televisao extends Dispositivo {

    private int volume;
    private int brilho;

    public Televisao() {
        super();
        this.volume = 0;
        this.brilho = 0;
    }

    public Televisao(String marca, String modelo, boolean estado,
                      double consumoLigado, double consumoTotal,
                      double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                      int volume, int brilho) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.volume = volume;
        this.brilho = brilho;
    }

    public Televisao(Televisao t) {
        super(t);

        this.volume = t.getVolume();
        this.brilho = t.getBrilho();
    }

    @Override
    public double consumoLigadoReal() {    //isto ou uma funcao que mude a variavel de instancia para o consumo real dependendo da intensidade
        if (this.brilho == 0) {
            return 0;
        }
        return super.getConsumoLigado() * (this.brilho / 100.0);
    }

    public int getVolume() {
        return this.volume;
    }

    public int getBrilho() {
        return this.brilho;
    }

    public void setVolume(int volume) {
        if (volume < 0) {
            this.volume = 0;
        } else if (volume > 100) {
            this.volume = 100;
        } else {
            this.volume = volume;
        }
    }

    public void setBrilho(int brilho) {
        if (brilho < 0) {
            this.brilho = 0;
        } else if (brilho > 100) {
            this.brilho = 100;
        } else {
            this.brilho = brilho;
        }
    }

    @Override
    public String toString() {
        return "Televisao{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", volume=" + volume +
                ", brilho=" + brilho +
                '}';
    }

    @Override
    public Televisao clone() {
        return new Televisao(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Televisao)) return false;

        Televisao t = (Televisao) o;

        return  this.getMarca().equals(t.getMarca()) &&
                this.getModelo().equals(t.getModelo());
    }
}