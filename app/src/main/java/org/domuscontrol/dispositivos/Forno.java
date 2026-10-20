package org.domuscontrol.dispositivos;

public class Forno extends Dispositivo {

    private int temperatura;
    private int valorTemperaturaMinimo;
    private int valorTemperaturaMaximo;

    public Forno() {
        super();
        this.temperatura = 0;
        this.valorTemperaturaMinimo = 0;
        this.valorTemperaturaMaximo = 0;
    }

    public Forno(String marca, String modelo, boolean estado,
                   double consumoLigado, double consumoTotal,
                   double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                   int temperatura, int valorTemperaturaMinimo, int valorTemperaturaMaximo) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.temperatura = temperatura;
        this.valorTemperaturaMinimo = valorTemperaturaMinimo;
        this.valorTemperaturaMaximo = valorTemperaturaMaximo;
    }

    public Forno(Forno f) {
        super(f);
        this.temperatura = f.getTemperatura();
        this.valorTemperaturaMinimo = f.getValorTemperaturaMinimo();
        this.valorTemperaturaMaximo = f.getValorTemperaturaMaximo();
    }

    @Override
    public double consumoLigadoReal() {
        if (this.valorTemperaturaMaximo == 0) {
            return 0;
        }
        return super.getConsumoLigado() * (this.temperatura / (double) this.valorTemperaturaMaximo);
    }

    public int getTemperatura() {
        return this.temperatura;
    }

    public int getValorTemperaturaMinimo() {
        return this.valorTemperaturaMinimo;
    }

    public int getValorTemperaturaMaximo() {
        return this.valorTemperaturaMaximo;
    }

    public void setTemperatura(int temperatura) {
        if (temperatura <= this.valorTemperaturaMinimo) {
            this.temperatura = this.valorTemperaturaMinimo;
            setEstado(false);
        } else if (temperatura > this.valorTemperaturaMaximo) {
            this.temperatura = this.valorTemperaturaMaximo;
        } else {
            this.temperatura = temperatura;
        }
    }

    public void setValorTemperaturaMinimo(int valorTemperaturaMinimo) {
        this.valorTemperaturaMinimo = valorTemperaturaMinimo;
    }

    public void setValorTemperaturaMaximo(int valorTemperaturaMaximo) {
        this.valorTemperaturaMaximo = valorTemperaturaMaximo;
    }

    @Override
    public String toString() {
        return "Forno{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", temperatura=" + temperatura +
                ", valorTemperaturaMinimo=" + valorTemperaturaMinimo +
                ", valorTemperaturaMaximo=" + valorTemperaturaMaximo +
                '}';
    }

    @Override
    public Forno clone() {
        return new Forno(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forno)) return false;

        Forno f = (Forno) o;

        return this.getMarca().equals(f.getMarca()) &&
               this.getModelo().equals(f.getModelo()) &&
               this.valorTemperaturaMinimo == f.getValorTemperaturaMinimo() &&
               this.valorTemperaturaMaximo == f.getValorTemperaturaMaximo();
    }
}