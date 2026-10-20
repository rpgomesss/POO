package org.domuscontrol.casa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.domuscontrol.exceptions.AutomacaoDuplicadaException;
import org.domuscontrol.exceptions.AutomacaoInexistenteException;
import org.domuscontrol.exceptions.CenarioDuplicadoException;
import org.domuscontrol.exceptions.CenarioInexistenteException;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoDuplicadaException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.exceptions.EscalonamentoDuplicadoException;
import org.domuscontrol.exceptions.EscalonamentoInexistenteException;
import org.domuscontrol.mecanismos.Automacao;
import org.domuscontrol.mecanismos.Cenario;
import org.domuscontrol.mecanismos.Escalonamento;


public class Casa implements Serializable{

    // ==================== ATRIBUTOS ====================

    private int identificador;
    private static int proxId = 0;

    public static void setProxId(int id) {
        proxId = id;
    }
    private String nome;
    private String localizacao;
    private double consumoTotal;
    private Map<Integer,Divisao> divisoes;
    private List<Automacao> automacoes;
    private List<Escalonamento> escalonamentos;
    private List<Cenario> cenarios;

    private static final long serialVersionUID = 1L;

    public Casa() {
        this.identificador = proxId++;
        this.nome = "";
        this.localizacao = "";
        this.consumoTotal = 0.0;
        this.divisoes = new HashMap<>();
        this.automacoes = new ArrayList<>();
        this.escalonamentos = new ArrayList<>();
        this.cenarios = new ArrayList<>();
    }


    public Casa(String nome, double consumoTotal, String localizacao,
                Map<Integer, Divisao> divisoes, List<Automacao> automacoes,
                List<Escalonamento> escalonamentos, List<Cenario> cenarios) {
        this.identificador = proxId++;
        this.nome = nome;
        this.localizacao = localizacao;
        this.consumoTotal = consumoTotal;
        this.setDivisoes(divisoes);
        this.setAutomacoes(automacoes);
        this.setEscalonamentos(escalonamentos);
        this.setCenarios(cenarios);
    }

    public Casa(Casa umaCasa) {
        this.identificador = umaCasa.getIdentificador();
        this.nome = umaCasa.getNome();
        this.localizacao = umaCasa.getLocalizacao();
        this.consumoTotal = umaCasa.getConsumoTotal();
        this.divisoes = umaCasa.getDivisoes();
        this.automacoes = umaCasa.getAutomacoes();
        this.escalonamentos = umaCasa.getEscalonamentos();
        this.cenarios = umaCasa.getCenarios();
    }


    public int getIdentificador() {
        return this.identificador;
    }

    public String getNome() {
        return this.nome;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public double getConsumoTotal() {
        return this.consumoTotal;
    }

    public Map<Integer,Divisao> getDivisoes() {
        Map<Integer, Divisao> copia = new HashMap<>(this.divisoes.size());

        Set<Integer> chaves = this.divisoes.keySet();
        for (Integer id : chaves) {
            copia.put(id, this.divisoes.get(id));
        }
        return copia;
    }

    public List<Automacao> getAutomacoes() {
        List<Automacao> copia = new ArrayList<>(this.automacoes.size());
        for (Automacao a : this.automacoes) {
            copia.add(a);
        }
        return copia;
    }


    public List<Escalonamento> getEscalonamentos() {
        List<Escalonamento> copia = new ArrayList<>(this.escalonamentos.size());
        for (Escalonamento e : this.escalonamentos) {
            copia.add(e);
        }
        return copia;
    }

    public List<Cenario> getCenarios() {
        List<Cenario> copia = new ArrayList<>(this.cenarios.size());
        for (Cenario c : this.cenarios) {
            copia.add(c);
        }
        return copia;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setConsumoTotal(double consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public void setDivisoes(Map<Integer, Divisao> divisoes) {
        this.divisoes = new HashMap<>(divisoes.size());

        Set<Integer> chaves = divisoes.keySet();
        for (Integer id : chaves) {
            this.divisoes.put(id, divisoes.get(id).clone());
        }
    }

    public void setAutomacoes(List<Automacao> automacoes) {
        this.automacoes = new ArrayList<>(automacoes.size());
        for (Automacao a : automacoes) {
            this.automacoes.add(a.clone());
        }
    }

    public void setEscalonamentos(List<Escalonamento> escalonamentos) {
        this.escalonamentos = new ArrayList<>(escalonamentos.size());
        for (Escalonamento e : escalonamentos) {
            this.escalonamentos.add(e.clone());
        }
    }

    public void setCenarios(List<Cenario> cenarios) {
        this.cenarios = new ArrayList<>(cenarios.size());
        for (Cenario c : cenarios) {
            this.cenarios.add(c.clone());
        }
    }

    public Divisao getDivisao(int id) throws DivisaoInexistenteException {
        Divisao d = this.divisoes.get(id);
        if (d == null) 
            throw new DivisaoInexistenteException(id);
        return this.divisoes.get(id);
    }

    public void adicionarDivisao(Divisao d) throws DivisaoDuplicadaException {
        if (d == null) throw new IllegalArgumentException("Divisão inválida.");
        if (this.divisoes.containsKey(d.getIdentificador()))
            throw new DivisaoDuplicadaException(d.getIdentificador());
        this.divisoes.put(d.getIdentificador(), d.clone());
    }

    public void removerDivisao(Divisao d) throws DivisaoInexistenteException {
        if (!this.divisoes.containsKey(d.getIdentificador()))
            throw new DivisaoInexistenteException(d.getIdentificador());
        this.divisoes.remove(d.getIdentificador());
    }

    public int numeroDivisoes() {
        return this.divisoes.size();
    }

    public void adicionarAutomacao(Automacao a) throws AutomacaoDuplicadaException {
        if (a == null) throw new IllegalArgumentException("Automação inválida.");
        boolean existe = this.automacoes.stream()
                                        .anyMatch(aut -> aut.getIdentificador() == a.getIdentificador());
        if (existe) 
            throw new AutomacaoDuplicadaException(a.getIdentificador());
        this.automacoes.add(a.clone());
    }

    public void removerAutomacao(int id) throws AutomacaoInexistenteException{
        boolean removeu = this.automacoes.removeIf(a -> a.getIdentificador() == id);
        if (!removeu) 
            throw new AutomacaoInexistenteException(id);
    }

    public void adicionarEscalonamento(Escalonamento e) throws EscalonamentoDuplicadoException {
        if (e == null) throw new IllegalArgumentException("Escalonamento inválido.");
        boolean existe = this.escalonamentos.stream()
                                            .anyMatch(esc -> esc.getIdentificador() == e.getIdentificador());
        if (existe) 
            throw new EscalonamentoDuplicadoException(e.getIdentificador());
        this.escalonamentos.add(e.clone());
    }

    public void removerEscalonamento(int id) throws EscalonamentoInexistenteException {
        boolean removeu = this.escalonamentos.removeIf(e -> e.getIdentificador() == id);
        if (!removeu) 
            throw new EscalonamentoInexistenteException(id);
    }

    public void adicionarCenario(Cenario c) throws CenarioDuplicadoException {
        if (c == null) throw new IllegalArgumentException("Cenário inválida.");
        boolean existe = this.cenarios.stream()
                                      .anyMatch(cenario -> cenario.getIdentificador() == c.getIdentificador());
        if (existe) 
            throw new CenarioDuplicadoException(c.getIdentificador());
        this.cenarios.add(c.clone());
    }

    public void removerCenario(int id) throws CenarioInexistenteException {
        boolean removeu = this.cenarios.removeIf(c -> c.getIdentificador() == id);
        if (!removeu) 
            throw new CenarioInexistenteException(id);
    }

    public void executarEscalonamentos(LocalDateTime tempoAtual) throws DivisaoInexistenteException, DispositivoInexistenteException {
        for (Escalonamento e : this.escalonamentos) {
            e.tentar(this, tempoAtual);
        }
    }

    public void ativarCenario(int idCenario) throws CenarioInexistenteException, DivisaoInexistenteException, DispositivoInexistenteException {
        for (Cenario c : this.cenarios) {
            if (c.getIdentificador() == idCenario) {
                c.ativar(this);
                return;
            }
        }
        throw new CenarioInexistenteException(idCenario);
    }

    public void avaliarAutomacoes() throws DivisaoInexistenteException, DispositivoInexistenteException {
    for (Automacao a : this.automacoes) {
        a.verificar(this);
    }
}

    public void calculaConsumoTotal() {
        double total = 0.0;
        
        for (Divisao d : this.divisoes.values()) {
            total += d.calculaConsumoTotal();
        }
    
        this.consumoTotal = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
    
        Casa casa = (Casa) o;
    
        return this.identificador == casa.identificador &&
               this.nome.equals(casa.getNome()) &&
               this.localizacao.equals(casa.getLocalizacao()) &&
               this.divisoes.equals(casa.getDivisoes()) &&
               this.automacoes.equals(casa.getAutomacoes()) &&
               this.escalonamentos.equals(casa.getEscalonamentos()) &&
               this.cenarios.equals(casa.getCenarios());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Casa ===\n");
        sb.append("ID: ").append(this.identificador).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Localização: ").append(this.localizacao).append("\n");

        sb.append("\nDivisões:\n");
        for (Divisao d : this.divisoes.values()) {
            sb.append(d.toString()).append("\n");
        }

        sb.append("\nAutomações:\n");
        for (Automacao a : this.automacoes) {
            sb.append(a.toString()).append("\n");
        }

        sb.append("\nEscalonamentos:\n");
        for (Escalonamento e : this.escalonamentos) {
            sb.append(e.toString()).append("\n");
        }

        sb.append("\nCenários:\n");
        for (Cenario c : this.cenarios) {
            sb.append(c.toString()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public Casa clone() {
        return new Casa(this);
    }
}