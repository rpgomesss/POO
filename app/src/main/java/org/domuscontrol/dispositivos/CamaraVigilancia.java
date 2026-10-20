package org.domuscontrol.dispositivos;

public class CamaraVigilancia extends Dispositivo {

    private boolean visaoNoturna;
    private boolean visaoInfravermelho;

    public CamaraVigilancia() {
        super();
        this.visaoNoturna = false;
        this.visaoInfravermelho = false;
    }

    public CamaraVigilancia(String marca, String modelo, boolean estado,
                           double consumoLigado, double consumoTotal,
                           double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal,
                           boolean visaoNoturna, boolean visaoInfravermelho) {

        super(marca, modelo, estado, consumoLigado, consumoTotal, consumoPeriodo, marcadorTemporal, numeroAtivacoes, tempoAtivoTotal);

        this.visaoNoturna = visaoNoturna;
        this.visaoInfravermelho = visaoInfravermelho;
    }

    public CamaraVigilancia(CamaraVigilancia c) {
        super(c);

        this.visaoNoturna = c.getVisaoNoturna();
        this.visaoInfravermelho = c.getVisaoInfravermelho();
    }

    @Override
    public double consumoLigadoReal() {
        if(this.visaoNoturna){
            return super.getConsumoLigado() * (1.5);
        }
        else if(this.visaoInfravermelho){
            return super.getConsumoLigado() * (1.2);
        }
        return super.getConsumoLigado();
    }

    public boolean getVisaoNoturna() {
        return this.visaoNoturna;
    }

    public boolean getVisaoInfravermelho() {
        return this.visaoInfravermelho;
    }

    public void setVisaoNoturna(boolean visaoNoturna) throws IllegalStateException {
        if (visaoNoturna && this.visaoInfravermelho) {
            throw new IllegalStateException("Não pode ativar visão noturna com infravermelho ativo");
        }
        this.visaoNoturna = visaoNoturna;
    }

    public void setVisaoInfravermelho(boolean visaoInfravermelho) throws IllegalStateException {
        if (visaoInfravermelho && this.visaoNoturna) {
            throw new IllegalStateException("Não pode ativar infravermelho com visão noturna ativa");
        }
        this.visaoInfravermelho = visaoInfravermelho;
    }

    @Override
    public String toString() {
        return "CamaraVigilancia{" +
                "id=" + getId() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", estado=" + getEstado() +
                ", visaoNoturna=" + visaoNoturna +
                ", visaoInfravermelho=" + visaoInfravermelho +
                '}';
    }

    @Override
    public CamaraVigilancia clone() {
        return new CamaraVigilancia(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CamaraVigilancia)) return false;

        CamaraVigilancia c = (CamaraVigilancia) o;

        return this.getMarca().equals(c.getMarca()) &&
               this.getModelo().equals(c.getModelo());
    }
}