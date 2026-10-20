package org.domuscontrol.model.services;

import java.util.ArrayList;
import java.util.List;

import org.domuscontrol.model.DomusControl;

public class AutomacoesModel {
    
    private DomusControl domus;

    public AutomacoesModel(DomusControl d){
        this.domus = d;
    }

    public List<String> listarCondicoes(int idCasa) {

        List<String> condicoes = new ArrayList<>();

        condicoes.add("Dispositivo ligado");

        return condicoes;
    }
}
