package org.domuscontrol.dispositivos;

public class Coluna extends Dispositivo {

    private int volume;
    private int bateria;

    public Coluna() {
        super();
        this.volume = 0;
        this.bateria = 0;
    }

    public Coluna(String marca, String modelo, boolean estado,
                   double consumoLigado, double consumoTotal,
                   double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                   int volume, int bateria) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.volume = volume;
        this.bateria = bateria;
    }

    public Coluna(Coluna c) {
        super(c);
        this.volume = c.getVolume();
        this.bateria = c.getBateria();
    }

    @Override
    public double consumoLigadoReal() {
        if (this.volume == 0) {
            return 0;
        }
        return super.getConsumoLigado() * (this.volume / 100.0);
    }

    public int getVolume() {
        return this.volume;
    }

    public int getBateria() {
        return this.bateria;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    @Override
    public String toString() {
        return "Coluna{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", volume=" + volume +
                ", bateria=" + bateria +
                '}';
    }

    @Override
    public Coluna clone() {
        return new Coluna(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coluna)) return false;

        Coluna c = (Coluna) o;

        return this.getMarca().equals(c.getMarca()) &&
               this.getModelo().equals(c.getModelo());
    }
}