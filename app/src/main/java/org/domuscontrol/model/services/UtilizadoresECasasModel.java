package org.domuscontrol.model.services;

import java.util.ArrayList;
import java.util.List;

import org.domuscontrol.Utilizador;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.UtilizadorInexistenteException;
import org.domuscontrol.model.DomusControl;

public class UtilizadoresECasasModel{

    private DomusControl domus;

    public UtilizadoresECasasModel(DomusControl d){
        this.domus = d;
    }

    public void associarUtilizadorCasa(int idu, int idc, boolean admin) throws IllegalArgumentException, CasaInexistenteException, UtilizadorInexistenteException
    {
        Utilizador u = domus.getUtilizadores().get(idu);
        Casa c = domus.getCasas().get(idc);

        if (admin) {
            if(u.getCasasAdmin().containsKey(idc)) {
                throw new IllegalArgumentException("Utilizador já tem acesso admin a esta casa");
            }
            else {
                u.adicionarCasaAdmin(c);
            }
        } else {
            if(u.getCasas().containsKey(idc)) {
                throw new IllegalArgumentException("Utilizador já tem acesso a esta casa");
            }
             else {
                u.adicionarCasa(c);
            }
        }
    }

    public void removerAcessoCasa(int idu, int idc) throws IllegalArgumentException, UtilizadorInexistenteException, CasaInexistenteException{
        Utilizador u = domus.getUtilizador(idu);

        if(!u.getCasas().containsKey(idc) && !u.getCasasAdmin().containsKey(idc)) {
            throw new IllegalArgumentException("Utilizador não tem acesso a esta casa");
        }
        if (u.getCasas().containsKey(idc)) u.removerCasa(idc);
        if (u.getCasasAdmin().containsKey(idc)) u.removerCasaAdmin(idc);
    }

    public void alterarPermissao(int idu, int idc, boolean admin) throws IllegalArgumentException, CasaInexistenteException, UtilizadorInexistenteException{
        Utilizador u = domus.getUtilizador(idu);
        Casa c = domus.getCasa(idc);
        
        if (admin) {
            if(u.getCasasAdmin().containsKey(idc)) {
                throw new IllegalArgumentException("Utilizador já tem acesso admin a esta casa");
            }
            else if(!u.getCasas().containsKey(idc)) {
                throw new IllegalArgumentException("Utilizador não tem acesso a esta casa");
            }
            else {
                u.removerCasa(idc);
                u.adicionarCasaAdmin(c);
            }
        } else {
            if(u.getCasas().containsKey(idc)) {
                throw new IllegalArgumentException("Utilizador já não é admin da casa");
            }
            else if(!u.getCasasAdmin().containsKey(idc)) {
                throw new IllegalArgumentException("Utilizador não tem acesso a esta casa");
            }
            else {
                u.removerCasaAdmin(idc);
                u.adicionarCasa(c);
            }
        }
    }

    public List<String> listarCasasDoUtilizador(int idU) throws  UtilizadorInexistenteException{
        List<String> res = new ArrayList<>();

        Utilizador u = domus.getUtilizador(idU);

        if(u == null) return res;

        for (Casa c : u.getCasas().values()) {
            res.add(c.getIdentificador() + " - " + c.getNome() + " (Acesso normal)");
        }
        for (Casa c : u.getCasasAdmin().values()) {
            res.add(c.getIdentificador() + " - " + c.getNome() + " (Acesso admin)");
        }
        return res;
    }
}
