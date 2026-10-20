package org.domuscontrol.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.domuscontrol.model.services.*;
import org.domuscontrol.Utilizador;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.CasaDuplicadaException;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.CenarioInexistenteException;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.exceptions.UtilizadorDuplicadoException;
import org.domuscontrol.exceptions.UtilizadorInexistenteException;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.mecanismos.Mecanismo;

public class DomusControl implements Serializable{
    
    private Map<Integer,Utilizador> utilizadores;
    private Map<Integer,Casa> casas;
    private LocalDateTime tempoAtual;

    private static final long serialVersionUID = 1L;

    private transient UtilizadoresECasasModel utilizadoresECasas;
    private transient DispositivoModel dispositivos;
    private transient DivisoesModel divisoes;
    private transient FicheirosModel ficheiros;
    private transient AutomacoesModel automacoes;
    private transient EscalonamentosModel escalonamentos;
    private transient CenariosModel cenarios;
    private transient TopsModel tops;

    // ================= CONSTRUTORES =================

    public DomusControl() {
        this.utilizadores = new HashMap<>();
        this.casas = new HashMap<>();
        this.tempoAtual = null;

        this.utilizadoresECasas = new UtilizadoresECasasModel(this);
        this.dispositivos = new DispositivoModel(this);
        this.divisoes = new DivisoesModel(this);
        this.ficheiros = new FicheirosModel(this);
        this.automacoes = new AutomacoesModel(this);
        this.escalonamentos = new EscalonamentosModel(this);
        this.cenarios = new CenariosModel(this);
        this.tops = new TopsModel(this);
    }

    public DomusControl(Map<Integer,Utilizador> utilizadores, 
                        Map<Integer,Casa> casas, LocalDateTime tempoAtual) {
        this.setUtilizadores(utilizadores);
        this.setCasas(casas);
        this.tempoAtual = tempoAtual;
    }

    public DomusControl(DomusControl dc) {

        this.utilizadores = dc.getUtilizadores();
        this.casas = dc.getCasas();
        this.tempoAtual = dc.getTempoAtual();

        this.utilizadoresECasas = new UtilizadoresECasasModel(this);
        this.dispositivos = new DispositivoModel(this);
        this.divisoes = new DivisoesModel(this);
        this.ficheiros = new FicheirosModel(this);
        this.automacoes = new AutomacoesModel(this);
        this.escalonamentos = new EscalonamentosModel(this);
        this.cenarios = new CenariosModel(this);
        this.tops = new TopsModel(this);
    }

    //-----------------

    public UtilizadoresECasasModel getUtilizadoresECasas() {
        return utilizadoresECasas;
    }

    public DispositivoModel getDispositivos() {
        return dispositivos;
    }

    public DivisoesModel getDivisoes() {
        return divisoes;
    }

    public FicheirosModel getFicheiros() {
        return ficheiros;
    }

    public AutomacoesModel getAutomacoes() {
        return automacoes;
    }

    public EscalonamentosModel getEscalonamentos() {
        return escalonamentos;
    }

    public CenariosModel getCenarios() {
        return cenarios;
    }

    public TopsModel getTops() {
        return tops;
    }

    // ================= GETTERS & SETTERS =================

    public Map<Integer,Utilizador> getUtilizadores() {
        Map<Integer,Utilizador> novo = new HashMap<>(this.utilizadores.size());

        Set<Integer> chaves = this.utilizadores.keySet();
        for(Integer id : chaves){
            novo.put(id, this.utilizadores.get(id));
        }
        return novo;
    }

    public Map<Integer,Casa> getCasas() {
        Map<Integer,Casa> novo = new HashMap<>(this.casas.size());

        Set<Integer> chaves = this.casas.keySet();
        for(Integer id : chaves){
            novo.put(id, this.casas.get(id));
        }
        return novo;
    }

    public LocalDateTime getTempoAtual() {
        return this.tempoAtual;
    }

    public void setUtilizadores(Map<Integer,Utilizador> utilizadores) {
        this.utilizadores = new HashMap<>(utilizadores.size());

        Set<Integer> chaves = utilizadores.keySet();
        for(Integer id : chaves){
            this.utilizadores.put(id, utilizadores.get(id).clone());
        }
    }

    public void setCasas(Map<Integer,Casa> casas) {
        this.casas = new HashMap<>(casas.size());

        Set<Integer> chaves = casas.keySet();
        for(Integer id : chaves){
            this.casas.put(id, casas.get(id).clone());
        }
    }

    public void setTempoAtual(LocalDateTime tempoAtual) {
        this.tempoAtual = tempoAtual;
    }

    public void adicionarUtilizador(String nome) throws UtilizadorDuplicadoException{
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome inválido.");
        Utilizador u = new Utilizador();
        u.setNome(nome);
        if (this.utilizadores.containsKey(u.getIdentificador()))
            throw new UtilizadorDuplicadoException(u.getIdentificador());
        this.utilizadores.put(u.getIdentificador(),u);
    }

    public void removerUtilizador(int id) throws UtilizadorInexistenteException {
        if (!this.utilizadores.containsKey(id))
            throw new UtilizadorInexistenteException(id);
        this.utilizadores.remove(id);
    }

    public Utilizador getUtilizador(int id) throws UtilizadorInexistenteException {
        Utilizador u = this.utilizadores.get(id);
        if (u == null) throw new UtilizadorInexistenteException(id);
        return u.clone();
    }

    public boolean existeUtilizador(int id) {
        return this.utilizadores.containsKey(id);
    }

    public int numeroUtilizadores() {
        return this.utilizadores.size();
    }

    public void adicionarCasa(String nome, String localizacao) throws CasaDuplicadaException { 
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome inválido.");
        Casa c = new Casa();
        c.setNome(nome);
        c.setLocalizacao(localizacao);
        if (this.casas.containsKey(c.getIdentificador()))
            throw new CasaDuplicadaException(c.getIdentificador());
        this.casas.put(c.getIdentificador(),c);
    }

    public void removerCasa(int id) throws CasaInexistenteException {
        if (!this.casas.containsKey(id))
            throw new CasaInexistenteException(id);
        this.casas.remove(id);
    }

    public Casa getCasa(int id) throws CasaInexistenteException {
        Casa c = this.casas.get(id);
        if (c == null) throw new CasaInexistenteException(id);
        return c;
    }

    public boolean existeCasa(int id) {
        return this.casas.containsKey(id);
    }

    public int numeroCasas() {
        return this.casas.size();
    }

    // ================= MÉTODOS DE INSTÂNCIA =================
    public void avancarTempo(int minutos) throws DivisaoInexistenteException, DispositivoInexistenteException{
        if (this.tempoAtual == null) {
            this.tempoAtual = LocalDateTime.now();
        }

        this.tempoAtual = this.tempoAtual.plusMinutes(minutos);

        this.executarEscalonamentos();
    }

    private void executarEscalonamentos() throws DivisaoInexistenteException, DispositivoInexistenteException {
        for (Casa c : this.casas.values()) {
            c.executarEscalonamentos(this.tempoAtual);
        }
    }

    public void ativarCenario(int idCasa, int idCenario) throws CasaInexistenteException, CenarioInexistenteException, DivisaoInexistenteException, DispositivoInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null) throw new CasaInexistenteException(idCasa);
        c.ativarCenario(idCenario);
    }

    public void avaliarAutomacoes(int idCasa) throws CasaInexistenteException, DivisaoInexistenteException, DispositivoInexistenteException{
        Casa c = this.casas.get(idCasa);
        if (c == null) throw new CasaInexistenteException(idCasa);
        c.avaliarAutomacoes();
    }



    @Override
    public DomusControl clone() {
        return new DomusControl(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        DomusControl domusControl = (DomusControl) o;

        return this.utilizadores.equals(domusControl.getUtilizadores()) &&
               this.casas.equals(domusControl.getCasas()) &&
               (this.tempoAtual == null
                   ? domusControl.getTempoAtual() == null
                   : this.tempoAtual.equals(domusControl.getTempoAtual()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== DomusControl ===\n");
        sb.append("Tempo atual: ").append(this.tempoAtual).append("\n");

        sb.append("\nUtilizadores:\n");
        for (Utilizador u : this.utilizadores.values()) {
            sb.append(u.toString()).append("\n");
        }

        sb.append("\nCasas:\n");
        for (Casa c : this.casas.values()) {
            sb.append(c.toString()).append("\n");
        }

        return sb.toString();
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.utilizadoresECasas = new UtilizadoresECasasModel(this);
        this.dispositivos = new DispositivoModel(this);
        this.divisoes = new DivisoesModel(this);
        this.ficheiros = new FicheirosModel(this);
        this.automacoes = new AutomacoesModel(this);
        this.escalonamentos = new EscalonamentosModel(this);
        this.cenarios = new CenariosModel(this);
        this.tops = new TopsModel(this);

        // Sincronizar proxIds
        int maxU = utilizadores.keySet().stream().max(Integer::compare).orElse(-1);
        Utilizador.setProxId(maxU + 1);

        int maxC = casas.keySet().stream().max(Integer::compare).orElse(-1);
        Casa.setProxId(maxC + 1);

        int maxDiv = -1;
        int maxDisp = -1;
        int maxMec = -1;

        for (Casa c : casas.values()) {
            for (Divisao d : c.getDivisoes().values()) {
                maxDiv = Math.max(maxDiv, d.getIdentificador());
                for (Dispositivo disp : d.getDispositivos().values()) {
                    maxDisp = Math.max(maxDisp, disp.getId());
                }
            }
            for (Mecanismo m : c.getAutomacoes()) maxMec = Math.max(maxMec, m.getIdentificador());
            for (Mecanismo m : c.getEscalonamentos()) maxMec = Math.max(maxMec, m.getIdentificador());
            for (Mecanismo m : c.getCenarios()) maxMec = Math.max(maxMec, m.getIdentificador());
        }
        Divisao.setProxId(maxDiv + 1);
        Dispositivo.setProxId(maxDisp + 1);
        Mecanismo.setProxId(maxMec + 1);
    }
}