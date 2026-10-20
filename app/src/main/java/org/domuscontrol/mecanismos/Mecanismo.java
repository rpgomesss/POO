package org.domuscontrol.mecanismos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class Mecanismo implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int proxId = 0;

    public static void setProxId(int id) {
        proxId = id;
    }

    private int identificador;
    private String nome;
    private String descricao;
    private List<AcaoDispositivo> acoes;

    public Mecanismo() {
        this.identificador = proxId++;
        this.nome = "";
        this.descricao = "";
        this.acoes = new ArrayList<>();
    }

    public Mecanismo(String nome, String descricao, List<AcaoDispositivo> acoes) {
        this.identificador = proxId++;
        this.nome = nome;
        this.descricao = descricao;
        this.setAcoes(acoes);
    }

    public Mecanismo(Mecanismo outro) {
        this.identificador = outro.identificador;
        this.nome = outro.nome;
        this.descricao = outro.descricao;
        this.setAcoes(outro.acoes);
    }

    public int getIdentificador() { 
        return this.identificador; 
    }

    public String getNome() { 
        return this.nome; 
    }

    public String getDescricao() {
        return this.descricao;
    }

    public List<AcaoDispositivo> getAcoes() {
        return new ArrayList<>(this.acoes);
    }

    public void setNome(String nome){ 
        this.nome = nome; 
    }

    public void setDescricao(String descricao){ 
        this.descricao = descricao;
    }

    public void setAcoes(List<AcaoDispositivo> acoes) {
        this.acoes = new ArrayList<>(acoes);
    }

    public void adicionarAcao(AcaoDispositivo acao) {
        this.acoes.add(acao);
    }

    public void removerAcao(AcaoDispositivo acao) {
        this.acoes.remove(acao);
    }

    public void executarAcoes(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        for (AcaoDispositivo acao : this.acoes) {
            acao.executar(casa);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mecanismo m = (Mecanismo) o;
        return this.identificador == m.identificador
                && this.nome.equals(m.nome)
                && this.acoes.equals(m.acoes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mecanismo #").append(identificador)
          .append(" | Nome: ").append(nome)
          .append(" | Descrição: ").append(descricao)
          .append("\n  Ações:\n");
        for (AcaoDispositivo a : acoes) {
            sb.append("    - ").append(a.descricao()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public Mecanismo clone() {
        return new Mecanismo(this);
    }
}