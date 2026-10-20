package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

import java.time.LocalDateTime;
import java.util.List;

public class Escalonamento extends Mecanismo {

    private static final long serialVersionUID = 1L;

    private int horaDisparoMinutos;
    private boolean[] diasSemana;
    private boolean todosDias;
    private int ultimoTempoDisparado;


    public Escalonamento() {
        super();
        this.horaDisparoMinutos = 0;
        this.diasSemana = new boolean[7];
        this.todosDias = true;
        this.ultimoTempoDisparado = -1;
    }

    public Escalonamento(String nome, int horaDisparoMinutos, List<AcaoDispositivo> acoes) {
        super(nome, "Escalonamento diário às " + formatarHora(horaDisparoMinutos), acoes);
        this.horaDisparoMinutos = horaDisparoMinutos;
        this.diasSemana = new boolean[7];
        this.todosDias = true;
        this.ultimoTempoDisparado = -1;
    }

    public Escalonamento(String nome, int horaDisparoMinutos,
                         boolean[] diasSemana, List<AcaoDispositivo> acoes) {
        super(nome, "Escalonamento semanal às " + formatarHora(horaDisparoMinutos), acoes);
        this.horaDisparoMinutos = horaDisparoMinutos;
        this.diasSemana = diasSemana.clone();
        this.todosDias = false;
        this.ultimoTempoDisparado = -1;
    }

    public Escalonamento(Escalonamento outro) {
        super(outro);
        this.horaDisparoMinutos = outro.horaDisparoMinutos;
        this.diasSemana = outro.diasSemana.clone();
        this.todosDias = outro.todosDias;
        this.ultimoTempoDisparado = outro.ultimoTempoDisparado;
    }

    public int getHoraDisparoMinutos(){ 
        return horaDisparoMinutos; 
    }

    public boolean isTodosDias(){ 
        return todosDias; 
    }

    public boolean[] getDiasSemana(){ 
        return diasSemana.clone(); 
    }

    public void setHoraDisparoMinutos(int h) { 
        this.horaDisparoMinutos = h; 
    }

    public void setTodosDias(boolean v){
        this.todosDias = v; 
    }

    public void setDiasSemana(boolean[] d){ 
        this.diasSemana = d.clone(); 
    }

    public void tentar(Casa casa, int tempoAtual, int diaSemana) throws DivisaoInexistenteException, DispositivoInexistenteException {
        if (tempoAtual == this.horaDisparoMinutos) {
            if (tempoAtual != this.ultimoTempoDisparado) {
                boolean diaValido = this.todosDias
                        || (diaSemana >= 0 && diaSemana < 7 && this.diasSemana[diaSemana]);

                if (diaValido) {
                    this.executarAcoes(casa);
                    this.ultimoTempoDisparado = tempoAtual;
                }
            }
        } else {
            this.ultimoTempoDisparado = -1;
        }
    }

    public void tentar(Casa casa, LocalDateTime data)  throws DivisaoInexistenteException, DispositivoInexistenteException {
        int tempoAtual = data.getHour() * 60 + data.getMinute();
        int diaSemana = data.getDayOfWeek().getValue() % 7;
        tentar(casa, tempoAtual, diaSemana);
    }

    private static String formatarHora(int minutos) {
        return String.format("%02d:%02d", minutos / 60, minutos % 60);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Escalonamento e = (Escalonamento) o;
        return this.horaDisparoMinutos == e.horaDisparoMinutos && this.todosDias == e.todosDias;
    }

    @Override
    public String toString() {
        return "=== Escalonamento ===\n" + super.toString()
                + "  Dispara às: " + formatarHora(horaDisparoMinutos) + "\n"
                + "  Todos os dias: " + todosDias + "\n";
    }

    @Override
    public Escalonamento clone() {
        return new Escalonamento(this);
    }
}