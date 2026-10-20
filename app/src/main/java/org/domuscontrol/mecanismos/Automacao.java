package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

import java.util.List;

public class Automacao extends Mecanismo {

    private static final long serialVersionUID = 1L;

    private CondicaoAutomacao condicao;
    private boolean condicaoAtiva;

    public Automacao() {
        super();
        this.condicaoAtiva = false;
    }

    public Automacao(String nome, CondicaoAutomacao condicao, List<AcaoDispositivo> acoes) {
        super(nome, "Automação", acoes);
        this.condicao = condicao;
        this.condicaoAtiva = false;
    }

    public Automacao(String nome, String descricao,
                     CondicaoAutomacao condicao, List<AcaoDispositivo> acoes) {
        super(nome, descricao, acoes);
        this.condicao = condicao;
        this.condicaoAtiva = false;
    }

    public Automacao(Automacao outra) {
        super(outra);
        this.condicao = outra.condicao;
        this.condicaoAtiva = outra.condicaoAtiva;
    }

    public CondicaoAutomacao getCondicao() { 
        return this.condicao; 
    }

    public void setCondicao(CondicaoAutomacao condicao) {
        this.condicao = condicao;
    }

    public void verificar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException {
        if (this.condicao == null) return;
        
        boolean satisfeita = this.condicao.avaliar(casa);

        if (satisfeita && !this.condicaoAtiva) {
            this.executarAcoes(casa);
            this.condicaoAtiva = true;
        } else if (!satisfeita) {
            this.condicaoAtiva = false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Automacao a = (Automacao) o;
        if (this.condicao == null && a.condicao == null) return true;
        if (this.condicao == null || a.condicao == null) return false;
        return this.condicao.equals(a.condicao);
    }

    @Override
    public String toString() {
        return "=== Automação ===\n" + super.toString()
                + "  Condição: " + condicao.descricao() + "\n"
                + "  Estado condição: " + (condicaoAtiva ? "ativa" : "inativa") + "\n";
    }

    @Override
    public Automacao clone() {
        return new Automacao(this);
    }
}