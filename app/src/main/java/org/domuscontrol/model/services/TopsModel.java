package org.domuscontrol.model.services;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.model.DomusControl;

public class TopsModel {

    private DomusControl domus;

    public TopsModel(DomusControl d){
        this.domus = d;
    }

    // ================= TOPS =================

    public String casaMaisConsome() throws CasaInexistenteException{
        if(domus.getCasas().isEmpty())
            throw new CasaInexistenteException("Não existem casas registadas");
        Comparator<Casa> mc = (c1,c2) -> 
            Double.compare(c2.getConsumoTotal(), c1.getConsumoTotal());
        Casa c = domus.getCasas().values()
                         .stream()
                         .sorted(mc)
                         .findFirst()
                         .orElse(null);
        if(c != null) return c.toString();
        return null;
    }

    public List<String> top3DivisoesMaisDispositivos()
        throws CasaInexistenteException, DivisaoInexistenteException {

        if(domus.getCasas().isEmpty())
            throw new CasaInexistenteException(
                    "Não existem casas registadas");

        List<Map.Entry<Casa, Divisao>> todas = new ArrayList<>();

        for(Casa c : domus.getCasas().values()) {

            for(Divisao d : c.getDivisoes().values()) {

                todas.add(
                    new AbstractMap.SimpleEntry<>(c, d));
            }
        }

        if(todas.isEmpty())
            throw new DivisaoInexistenteException(
                    "Não existem divisões registadas.");

        todas.sort((e1, e2) ->
            Integer.compare(
                e2.getValue().numeroDispositivos(),
                e1.getValue().numeroDispositivos()
            )
        );

        return todas.stream()
                .limit(3)
                .map(e ->
                    "Casa: " + e.getKey().getNome() +
                    " | Divisão: " + e.getValue().getNome() +
                    " | Nº dispositivos: " +
                    e.getValue().numeroDispositivos())
                .toList();
    }

    public List<String> top3Dispositivos() {

        List<Dispositivo> l = new ArrayList<>();

        for(Casa c : domus.getCasas().values()) {

            for(Divisao d : c.getDivisoes().values()) {

                for(Dispositivo dis : d.getDispositivos().values()) {
                    l.add(dis.clone());
                }
            }
        }

        l.sort((d1, d2) -> {

            int cmpTempo = Long.compare(
                    d2.getTempoAtivoTotal(),
                    d1.getTempoAtivoTotal());

            if(cmpTempo == 0) {

                return Integer.compare(
                        d2.getNumeroAtivacoes(),
                        d1.getNumeroAtivacoes());
            }

            return cmpTempo;
        });

        return l.stream()
                .limit(3)
                .map(Dispositivo::toString)
                .toList();
    }
}