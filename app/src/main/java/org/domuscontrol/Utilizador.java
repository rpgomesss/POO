package org.domuscontrol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.mecanismos.Interacao;
import org.domuscontrol.mecanismos.Mecanismo;
import org.domuscontrol.mecanismos.AcaoDispositivo;
import org.domuscontrol.mecanismos.AcaoLigar;
import org.domuscontrol.mecanismos.AcaoDesligar;
import org.domuscontrol.mecanismos.AcaoMudarTemperatura;
import org.domuscontrol.mecanismos.AcaoMudarIntensidadeLampada;
import org.domuscontrol.mecanismos.Escalonamento;

public class Utilizador implements Serializable {

    private int identificador;
    private static int proxId = 0;

    public static void setProxId(int id) {
        proxId = id;
    }
    private String nome;
    private Map<Integer,Casa> casas;
    private Map<Integer,Casa> casasAdmin;
    private List<Interacao> historicoInteracoes;

    private static final long serialVersionUID = 1L;

    public Utilizador() {
        this.identificador = proxId++;
        this.nome = "";
        this.casas = new HashMap<>();
        this.casasAdmin = new HashMap<>();
        this.historicoInteracoes = new ArrayList<>();
    }

    public Utilizador(String nome, Map<Integer, Casa> casas, Map<Integer, Casa> casasAdmin) {
        this.identificador = proxId++;
        this.nome = nome;
        this.setCasas(casas);
        this.setCasasAdmin(casasAdmin);
        this.historicoInteracoes = new ArrayList<>();
    }

    public Utilizador(Utilizador umUtilizador) {
        this.identificador = umUtilizador.getIdentificador();
        this.nome = umUtilizador.getNome();
        this.casas = umUtilizador.getCasas();
        this.casasAdmin = umUtilizador.getCasasAdmin();
        this.historicoInteracoes = umUtilizador.getHistoricoInteracoes();
    }

    public int getIdentificador() {
        return this.identificador;
    }

    public String getNome() {
        return this.nome;
    }

    public Map<Integer,Casa> getCasas() {
        Map<Integer,Casa> novo = new HashMap<>(this.casas.size());

        Set<Integer> chaves = this.casas.keySet();
        for(Integer id : chaves){
            novo.put(id, this.casas.get(id).clone());
        }
        return novo;
    }
 
    public Map<Integer, Casa> getCasasAdmin() {
        Map<Integer,Casa> novo = new HashMap<>(this.casasAdmin.size());

        Set<Integer> chaves = this.casasAdmin.keySet();
        for(Integer id : chaves){
            novo.put(id, this.casasAdmin.get(id).clone());
        }
        return novo;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCasas(Map<Integer, Casa> casas) {
        this.casas = new HashMap<>(casas.size());
        Set<Integer> chaves = casas.keySet();
        for(Integer id : chaves){
            this.casas.put(id, casas.get(id).clone());
        }
    }

    public void setCasasAdmin(Map<Integer, Casa> casasAdmin) {
        this.casasAdmin = new HashMap<>(casasAdmin.size());
        Set<Integer> chaves = casasAdmin.keySet();
        for(Integer id : chaves){
            this.casasAdmin.put(id, casasAdmin.get(id).clone());
        }
    }

    public List<Interacao> getHistoricoInteracoes() {
        return this.historicoInteracoes.stream().map(Interacao::clone).collect(Collectors.toList());
    }

    public void setHistoricoInteracoes(List<Interacao> historico) {
        this.historicoInteracoes = historico.stream().map(Interacao::clone).collect(Collectors.toList());
    }

    public void registarInteracao(Interacao i) {
        this.historicoInteracoes.add(i.clone());
    }

    public List<Mecanismo> gerarSugestoes() {
        List<Mecanismo> sugestoes = new ArrayList<>();
        
        if (this.historicoInteracoes == null || this.historicoInteracoes.isEmpty()) return sugestoes;

        Map<String, List<Interacao>> agrupadas = this.historicoInteracoes.stream().collect(Collectors.groupingBy(
            i -> i.getIdCasa() + "-" + i.getIdDivisao() + "-" + i.getIdDispositivo() + "-" + i.getTipoAcao() + "-" + i.getMomento().getHour()
        ));

        for (Map.Entry<String, List<Interacao>> entry : agrupadas.entrySet()) {
            if (entry.getValue().size() >= 3) {
                Interacao rep = entry.getValue().get(0);
                
                AcaoDispositivo acao = null;
                if ("LIGAR".equals(rep.getTipoAcao())) {
                    acao = new AcaoLigar(rep.getIdDivisao(), rep.getIdDispositivo());
                } else if ("DESLIGAR".equals(rep.getTipoAcao())) {
                    acao = new AcaoDesligar(rep.getIdDivisao(), rep.getIdDispositivo());
                } else if (rep.getTipoAcao().startsWith("TEMP_")) {
                    try {
                        int temp = Integer.parseInt(rep.getTipoAcao().split("_")[1]);
                        acao = new AcaoMudarTemperatura(rep.getIdDivisao(), rep.getIdDispositivo(), temp);
                    } catch (Exception ignored) {}
                } else if (rep.getTipoAcao().startsWith("INTENS_")) {
                    try {
                        int intens = Integer.parseInt(rep.getTipoAcao().split("_")[1]);
                        acao = new AcaoMudarIntensidadeLampada(rep.getIdDivisao(), rep.getIdDispositivo(), intens);
                    } catch (Exception ignored) {}
                }
                
                if (acao != null) {
                    List<AcaoDispositivo> acoes = new ArrayList<>();
                    acoes.add(acao);
                    
                    int horaMinutos = rep.getMomento().getHour() * 60; 
                    
                    Escalonamento esc = new Escalonamento("Sugestão Automática (" + rep.getTipoAcao() + ")", horaMinutos, acoes);
                    sugestoes.add(esc);
                }
            }
        }
        
        return sugestoes;
    }

    public void adicionarCasa(Casa c) {
        if (c == null) throw new IllegalArgumentException("Casa inválida.");
        if (this.casas.containsKey(c.getIdentificador()))
            throw new IllegalStateException("Casa com id " + c.getIdentificador() + " já existe.");
        this.casas.put(c.getIdentificador(), c);
    }

    public void adicionarCasaAdmin(Casa c) {
        if (c == null) throw new IllegalArgumentException("Casa inválida.");
        if (this.casasAdmin.containsKey(c.getIdentificador()))
            throw new IllegalStateException("Casa com id " + c.getIdentificador() + " já existe como admin.");
        this.casasAdmin.put(c.getIdentificador(), c);
    }

    public void removerCasa(int idCasa) {
        if (!this.casas.containsKey(idCasa))
            throw new IllegalStateException("Casa com id " + idCasa + " não encontrada.");
        this.casas.remove(idCasa);
    }

    public void removerCasaAdmin(int idCasa) {
        if (!this.casasAdmin.containsKey(idCasa))
            throw new IllegalStateException("Casa com id " + idCasa + " não encontrada.");
        this.casasAdmin.remove(idCasa);
    }

    public Casa getCasa(int idCasa) {
        if (this.casas.containsKey(idCasa))
            return this.casas.get(idCasa);
        if (this.casasAdmin.containsKey(idCasa))
            return this.casasAdmin.get(idCasa);
        throw new IllegalStateException("Casa com id " + idCasa + " não encontrada.");
    }

    public int numeroCasas() {
        return this.casas.size();
    }

    public int numeroCasasAdmin() {
        return this.casasAdmin.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("=== Utilizador ===\n");
        sb.append("ID: ").append(this.identificador).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
    
        sb.append("\nCasas (utilizador):\n");
        if (this.casas.isEmpty()) {
            sb.append("  (nenhuma)\n");
        } else {
            for (Casa c : this.casas.values()) {
                sb.append("  - [").append(c.getIdentificador())
                  .append("] ").append(c.getNome()).append("\n");
            }
        }
    
        sb.append("\nCasas (administrador):\n");
        if (this.casasAdmin.isEmpty()) {
            sb.append("  (nenhuma)\n");
        } else {
            for (Casa c : this.casasAdmin.values()) {
                sb.append("  - [").append(c.getIdentificador())
                  .append("] ").append(c.getNome()).append("\n");
            }
        }
    
        return sb.toString();
    }

    @Override
    public Utilizador clone() {
        return new Utilizador(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilizador u = (Utilizador) o;

        return this.identificador == u.identificador &&
               this.nome.equals(u.getNome()) &&
               this.casas.equals(u.getCasas()) &&
               this.casasAdmin.equals(u.getCasasAdmin()) &&
               this.historicoInteracoes.equals(u.getHistoricoInteracoes());
    }
}