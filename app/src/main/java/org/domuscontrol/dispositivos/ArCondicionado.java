package org.domuscontrol.dispositivos;

public class ArCondicionado extends Dispositivo {

    private int temperatura;
    private int valorTemperaturaMinimo;
    private int valorTemperaturaMaximo;

    public ArCondicionado() {
        super();
        this.temperatura = 0;
        this.valorTemperaturaMinimo = 0;
        this.valorTemperaturaMaximo = 0;
    }

    public ArCondicionado(String marca, String modelo, boolean estado,
                   double consumoLigado, double consumoTotal,
                   double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                   int temperatura, int valorTemperaturaMinimo, int valorTemperaturaMaximo) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.temperatura = temperatura;
        this.valorTemperaturaMinimo = valorTemperaturaMinimo;
        this.valorTemperaturaMaximo = valorTemperaturaMaximo;
    }

    public ArCondicionado(ArCondicionado a) {
        super(a);
        this.temperatura = a.getTemperatura();
        this.valorTemperaturaMinimo = a.getValorTemperaturaMinimo();
        this.valorTemperaturaMaximo = a.getValorTemperaturaMaximo();
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
        return "ArCondicionado{" +
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
    public ArCondicionado clone() {
        return new ArCondicionado(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArCondicionado)) return false;

        ArCondicionado a = (ArCondicionado) o;

        return this.getMarca().equals(a.getMarca()) &&
               this.getModelo().equals(a.getModelo()) &&
               this.valorTemperaturaMinimo == a.getValorTemperaturaMinimo() &&
               this.valorTemperaturaMaximo == a.getValorTemperaturaMaximo();
    }
}