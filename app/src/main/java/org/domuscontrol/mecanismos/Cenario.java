package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

import java.util.List;

public class Cenario extends Mecanismo {

    private static final long serialVersionUID = 1L;

    private List<AcaoDispositivo> acoesDesativacao;
    private boolean ativo;

    public Cenario() {
        super();
        this.acoesDesativacao = new java.util.ArrayList<>();
        this.ativo = false;
    }

    public Cenario(String nome, String descricao, List<AcaoDispositivo> acoesAtivacao) {
        super(nome, descricao, acoesAtivacao);
        this.acoesDesativacao = new java.util.ArrayList<>();
        this.ativo = false;
    }

    public Cenario(String nome, String descricao,
                   List<AcaoDispositivo> acoesAtivacao,
                   List<AcaoDispositivo> acoesDesativacao) {
        super(nome, descricao, acoesAtivacao);
        this.setAcoesDesativacao(acoesDesativacao);
        this.ativo = false;
    }

    public Cenario(Cenario outro) {
        super(outro);
        this.setAcoesDesativacao(outro.acoesDesativacao);
        this.ativo = outro.ativo;
    }

    public boolean isAtivo() { 
        return ativo; 
    }

    public List<AcaoDispositivo> getAcoesDesativacao() {
        return new java.util.ArrayList<>(this.acoesDesativacao);
    }

    public void setAcoesDesativacao(List<AcaoDispositivo> acoes) {
        this.acoesDesativacao = new java.util.ArrayList<>(acoes);
    }

    public void adicionarAcaoDesativacao(AcaoDispositivo acao) {
        this.acoesDesativacao.add(acao);
    }

    public void ativar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        if (!this.ativo) {
            this.executarAcoes(casa);
            this.ativo = true;
        }
    }

    public void desativar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        if (this.ativo) {
            for (AcaoDispositivo acao : this.acoesDesativacao) {
                acao.executar(casa);
            }
            this.ativo = false;
        }
    }

    public void alternar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        if (this.ativo) desativar(casa);
        else ativar(casa);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Cenario c = (Cenario) o;
        return this.ativo == c.ativo && this.acoesDesativacao.equals(c.acoesDesativacao);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Cenário ===\n").append(super.toString());
        sb.append("  Estado: ").append(ativo ? "ATIVO" : "inativo").append("\n");
        if (!acoesDesativacao.isEmpty()) {
            sb.append("  Ações de desativação:\n");
            for (AcaoDispositivo a : acoesDesativacao) {
                sb.append("    - ").append(a.descricao()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public Cenario clone() {
        return new Cenario(this);
    }
}