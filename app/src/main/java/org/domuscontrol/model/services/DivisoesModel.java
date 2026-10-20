package org.domuscontrol.model.services;

import java.util.ArrayList;
import java.util.List;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.DivisaoDuplicadaException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.model.DomusControl;

public class DivisoesModel {

    private DomusControl domus;

    public DivisoesModel(DomusControl d){
        this.domus = d;
    }

        // ================= Divisões =================

    public void adicionarDivisaoAuxiliar(String nome, int idC) throws CasaInexistenteException, DivisaoDuplicadaException{
        Casa c = domus.getCasa(idC);

        Divisao d = new Divisao();
        d.setNome(nome);
        c.adicionarDivisao(d);
    }

    public List<String> listarDivisoes(int idC) throws IllegalArgumentException, CasaInexistenteException {
        List<String> res = new ArrayList<>();

        Casa c = domus.getCasa(idC);

        for (Divisao d : c.getDivisoes().values()) {
            res.add(d.getIdentificador() + " - " + d.getNome());
        }

        if(res.isEmpty()) throw new IllegalArgumentException (c.getNome() + " não tem divisões!");

        return res;
    }

    public void removerDivisaoAuxiliar(int idC, int idD) throws IllegalArgumentException, CasaInexistenteException, DivisaoInexistenteException{
        Casa c = domus.getCasa(idC);
        Divisao d = c.getDivisao(idD);

        if(d == null) throw new IllegalArgumentException("Divisão não existe!");

        else c.removerDivisao(d);
    }
}