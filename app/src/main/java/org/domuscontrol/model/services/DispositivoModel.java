package org.domuscontrol.model.services;

import java.util.ArrayList;
import java.util.List;

import org.domuscontrol.casa.Casa;
import org.domuscontrol.casa.Divisao;
import org.domuscontrol.dispositivos.ArCondicionado;
import org.domuscontrol.dispositivos.Aspirador;
import org.domuscontrol.dispositivos.CamaraVigilancia;
import org.domuscontrol.dispositivos.Coluna;
import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.dispositivos.Forno;
import org.domuscontrol.dispositivos.Lampada;
import org.domuscontrol.dispositivos.Persiana;
import org.domuscontrol.dispositivos.Portao;
import org.domuscontrol.dispositivos.Televisao;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.DispositivoDuplicadoException;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.model.DomusControl;

public class DispositivoModel {

    private DomusControl domus;

    public DispositivoModel(DomusControl d){
        this.domus = d;
    }
    
        // ================= DISPOSITIVOS =================

    public boolean existeDispositivo(int idCasa, int idDiv, int idDis) throws CasaInexistenteException, DivisaoInexistenteException{
        return domus.getCasa(idCasa).getDivisao(idDiv).getDispositivos().containsKey(idDis);
    }

    public void adicionarArCondicionado(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado, int tempMin, int tempMax) throws DivisaoInexistenteException, DispositivoDuplicadoException{

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo ac = new ArCondicionado(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0, tempMin, tempMax
        );

        d.adicionarDispositivo(ac);
    }

    public void adicionarAspirador(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado, int intensidadeMax) throws DivisaoInexistenteException, DispositivoDuplicadoException{

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo a = new Aspirador(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0, 0, intensidadeMax, 100
        );

        d.adicionarDispositivo(a);
    }

    public void adicionarCamaraVigilancia(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado) throws DivisaoInexistenteException, DispositivoDuplicadoException {

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo cv = new CamaraVigilancia(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            false, false
        );

        d.adicionarDispositivo(cv);
    }

    public void adicionarColuna(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado)throws DivisaoInexistenteException, DispositivoDuplicadoException {

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo col = new Coluna(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0, 100
        );

        d.adicionarDispositivo(col);
    }

    public void adicionarForno(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado, int tempMax) throws DivisaoInexistenteException, DispositivoDuplicadoException{

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo f = new Forno(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0, 0, tempMax
        );

        d.adicionarDispositivo(f);
    }

    public void adicionarLampada(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado, int intensidadeMax)throws DivisaoInexistenteException, DispositivoDuplicadoException {

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo l = new Lampada(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0, 0, intensidadeMax
        );

        d.adicionarDispositivo(l);
    }

    public void adicionarPersiana(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado)throws DivisaoInexistenteException, DispositivoDuplicadoException {

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo p = new Persiana(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0
        );

        d.adicionarDispositivo(p);
    }

    public void adicionarPortao(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado)throws DivisaoInexistenteException, DispositivoDuplicadoException {

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo p = new Portao(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0
        );

        d.adicionarDispositivo(p);
    }

    public void adicionarTelevisao(int idCasa, int idDivisao, String marca, String modelo, double consumoLigado) throws DivisaoInexistenteException, DispositivoDuplicadoException{

        Casa c = domus.getCasas().get(idCasa);

        if(c == null)
            throw new IllegalArgumentException("Casa não existe.");

        Divisao d = c.getDivisao(idDivisao);

        if(d == null)
            throw new IllegalArgumentException("Divisão não existe.");

        Dispositivo t = new Televisao(
            marca, modelo, false,
            consumoLigado, 0, 0,
            0, 0, 0,
            0, 0
        );

        d.adicionarDispositivo(t);
    }

    public List<String> listarDispositivos(int idC, int idD) throws IllegalArgumentException, CasaInexistenteException, DivisaoInexistenteException{
        List<String> res = new ArrayList<>();

        Casa c = domus.getCasa(idC);
        Divisao d = c.getDivisao(idD);

        for (Dispositivo dis : d.getDispositivos().values()) {
            res.add(dis.toString());
        }
        if(res.isEmpty()) throw new IllegalArgumentException (d.getNome() + " não tem dispositivos!");

        return res;
    }

    public void removerDispositivoAuxiliar(int idC, int idD, int idDis) throws IllegalArgumentException, CasaInexistenteException, DivisaoInexistenteException, DispositivoInexistenteException{
        Casa c = domus.getCasa(idC);
        Divisao d = c.getDivisao(idD);

        if(d.getDispositivo(idDis) == null) throw new IllegalArgumentException("Dispositivo não existe!");

        else d.removerDispositivo(idDis);
    }

    public List<String> listarOpcoesDispositivo(int idCasa, int idDiv, int idDis)
            throws CasaInexistenteException,
                DivisaoInexistenteException,
                DispositivoInexistenteException {

        List<String> opcoes = new ArrayList<>();

        opcoes.add("Ligar");
        opcoes.add("Desligar");
        opcoes.add("Informações");

        Dispositivo d = domus.getCasa(idCasa)
                    .getDivisao(idDiv)
                    .getDispositivo(idDis);

        if(d instanceof ArCondicionado){
            opcoes.add("Alterar Temperatura");
        }
        if(d instanceof Aspirador){
            opcoes.add("Alterar Intensidade");
        }
        if(d instanceof CamaraVigilancia){
            opcoes.add("Ligar Visão Noturna");
            opcoes.add("Ligar Visão InfraVermelho");
            opcoes.add("Desligar Visão Noturna");
            opcoes.add("Desligar Visão InfraVermelho");
        }
        if(d instanceof Coluna) {
            opcoes.add("Alterar Volume");
        }
        if(d instanceof Forno) {
            opcoes.add("Alterar Temperatura");
        }
        if(d instanceof Lampada){
            opcoes.add("Alterar Intensidade");
        }
        if(d instanceof Persiana){
            opcoes.add("Alterar Percentagem de Abertura");
        }
        if(d instanceof Portao){
            opcoes.add("Alterar Percentagem de Abertura");
        }
        if(d instanceof Televisao){
            opcoes.add("Alterar Volume");
            opcoes.add("Alterar Brilho");
        }

        return opcoes;
    }

    public void alterarTemperaturaAC(int idCasa, int idDiv, int idDis, int temp) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        ArCondicionado ac =
            (ArCondicionado) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        ac.setTemperatura(temp);
    }

    public void alterarTemperaturaForno(int idCasa, int idDiv, int idDis, int temp) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Forno f =
            (Forno) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        f.setTemperatura(temp);
    }

    public void alterarIntensidadeAspirador(int idCasa, int idDiv, int idDis, int intensidade) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Aspirador a =
            (Aspirador) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        a.setIntensidade(intensidade);
    }

    public void alterarIntensidadeLampada(int idCasa, int idDiv, int idDis, int intensidade) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Lampada l =
            (Lampada) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        l.setIntensidade(intensidade);
    }

    public void alterarVolumeTv(int idCasa, int idDiv, int idDis, int volume) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Televisao t =
            (Televisao) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        t.setVolume(volume);
    }

    public void alterarVolumeColuna(int idCasa, int idDiv, int idDis, int volume) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Coluna c =
            (Coluna) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        c.setVolume(volume);
    }

    public void alterarVisaoNoturnaCamara(int idCasa, int idDiv, int idDis, boolean b) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        CamaraVigilancia c =
            (CamaraVigilancia) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        c.setVisaoNoturna(b);
    }

    public void alterarVisaoInfraVermelhoCamara(int idCasa, int idDiv, int idDis, boolean b) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        CamaraVigilancia c =
            (CamaraVigilancia) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        c.setVisaoInfravermelho(b);
    }

    public void alterarBrilhoTv(int idCasa, int idDiv, int idDis, int b) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Televisao t =
            (Televisao) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        t.setBrilho(b);
    }

    public void alterarPercentagemAberturaPersiana(int idCasa, int idDiv, int idDis, int p) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Persiana per =
            (Persiana) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        per.setPercentagemAbertura(p);
    }

    public void alterarPercentagemAberturaPortao(int idCasa, int idDiv, int idDis, int p) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        Portao por =
            (Portao) domus.getCasa(idCasa)
                                .getDivisao(idDiv)
                                .getDispositivo(idDis);

        por.setPercentagemAbertura(p);
    }

    public String getTipoDispositivo(int idCasa,
                                 int idDiv,
                                 int idDis)
        throws CasaInexistenteException,
               DivisaoInexistenteException,
               DispositivoInexistenteException {

        return domus.getCasa(idCasa)
                .getDivisao(idDiv)
                .getDispositivo(idDis)
                .getClass()
                .getSimpleName();
    }





    public void ligarDispositivo(int idc, int iddiv, int iddis) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        domus.getCasa(idc).getDivisao(iddiv).getDispositivo(iddis).ligar();
    }

    public void desligarDispositivo(int idc, int iddiv, int iddis) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        domus.getCasa(idc).getDivisao(iddiv).getDispositivo(iddis).desligar();
    }

    public String infoDispositivo(int idc, int iddiv, int iddis) throws DispositivoInexistenteException, CasaInexistenteException, DivisaoInexistenteException{
        return domus.getCasa(idc).getDivisao(iddiv).getDispositivo(iddis).toString();
    }
}
