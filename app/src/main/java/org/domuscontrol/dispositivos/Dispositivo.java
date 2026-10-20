package org.domuscontrol.dispositivos;

import java.io.Serializable;

public abstract class Dispositivo implements Serializable{
    private int id; 
    private static int proxId = 0;

    public static void setProxId(int id) {
        proxId = id;
    }
    private String marca;
    private String modelo;
    private boolean estado;
    private double consumoLigado;
    private double consumoTotal;
    private double consumoPeriodo;
    private long marcadorTemporal;
    private int numeroAtivacoes;
    private long tempoAtivoTotal;

    private static final long serialVersionUID = 1L;

    public Dispositivo() {
        this.id = proxId ++;
        this.marca = "";
        this.modelo = "";
        this.estado = false;
        this.consumoLigado = 0.0;
        this.consumoTotal = 0.0;
        this.consumoPeriodo = 0.0;
        this.marcadorTemporal = 0;
        this.numeroAtivacoes = 0;
        this.tempoAtivoTotal = 0;
    }

    public Dispositivo(String marca, String modelo, boolean estado, 
                       double consumoLigado, double consumoTotal, double consumoPeriodo, long marcadorTemporal, int numeroAtivacoes, long tempoAtivoTotal) {
        this.id = proxId++;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.consumoLigado = consumoLigado;
        this.consumoTotal = consumoTotal;
        this.consumoPeriodo = consumoPeriodo;
        this.marcadorTemporal = marcadorTemporal;
        this.numeroAtivacoes = numeroAtivacoes;
        this.tempoAtivoTotal = tempoAtivoTotal;
    }

    public Dispositivo(Dispositivo umDispositivo) {
        this.id = umDispositivo.getId();
        this.marca = umDispositivo.getMarca();
        this.modelo = umDispositivo.getModelo();
        this.estado = umDispositivo.getEstado();
        this.consumoLigado = umDispositivo.getConsumoLigado();
        this.consumoTotal = umDispositivo.getConsumoTotal();
        this.consumoPeriodo = umDispositivo.getConsumoPeriodo();
        this.marcadorTemporal = umDispositivo.getMarcadorTemporal();
        this.numeroAtivacoes = umDispositivo.getNumeroAtivacoes();
        this.tempoAtivoTotal = umDispositivo.getTempoAtivoTotal();
    }

    //getters

    public int getId() {
        return this.id;
    }

    public String getMarca() {
        return this.marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public double getConsumoLigado() {
        return this.consumoLigado;
    }

    public double getConsumoTotal() {
        return this.consumoTotal;
    }

    public double getConsumoPeriodo() {
        return this.consumoPeriodo;
    }

    public long getMarcadorTemporal() {
        return this.marcadorTemporal;
    }

    public int getNumeroAtivacoes() {
        return this.numeroAtivacoes;
    }

    public long getTempoAtivoTotal() {
        if (this.estado) {
            long agora = System.currentTimeMillis();
            return this.tempoAtivoTotal + (agora - this.marcadorTemporal);
        }
        return this.tempoAtivoTotal;
    }

    //setters

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setConsumoLigado(double consumoLigado) {
        this.consumoLigado = consumoLigado;
    }

    public void setConsumoTotal(double consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public void setConsumoPeriodo(double consumoPeriodo) {
        this.consumoPeriodo = consumoPeriodo;
    }

    public void setMarcadorTemporal(long marcadorTemporal) {
        this.marcadorTemporal = marcadorTemporal;
    }

    public void setNumeroAtivacoes(int numeroAtivacoes) {
        this.numeroAtivacoes = numeroAtivacoes;
    }

    public void setTempoAtivoTotal(long tempoAtivoTotal) {
        this.tempoAtivoTotal = tempoAtivoTotal;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract Dispositivo clone();

    @Override
    public abstract boolean equals(Object o);

    public void ligar(){
        if(!estado){
            this.numeroAtivacoes++;
            this.estado = true;
            this.marcadorTemporal = System.currentTimeMillis();
        }
    }

    public void desligar(){
        if (this.estado) {
            long agora = System.currentTimeMillis();
            long periodo = agora - this.marcadorTemporal;

            this.tempoAtivoTotal += periodo;

            atualizaConsumo();
            this.estado = false;
        }
    }

    public double totalConsumo(){
        atualizaConsumo();
        return this.consumoTotal;
    }

    public double periodoConsumo(){
        atualizaConsumo();
        return this.consumoPeriodo;
    }

    public abstract double consumoLigadoReal();

    private void atualizaConsumo(){
        long marcadorTemporalAtual = System.currentTimeMillis();
        long periodo = marcadorTemporalAtual - this.marcadorTemporal;
        
        double consumo = consumoLigadoReal(); //por causa por exemplo da intensidade da lampada, o consumo pode variar, por isso chamo o getter

        if(this.estado){
            this.consumoTotal += consumo*periodo;
            this.consumoPeriodo += consumo*periodo;
        }

        this.marcadorTemporal = marcadorTemporalAtual;
    }

    public void resetPeriodo(){
        this.consumoPeriodo = 0.0;
    }
}