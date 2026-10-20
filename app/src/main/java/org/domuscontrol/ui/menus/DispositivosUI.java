package org.domuscontrol.ui.menus;

import java.util.List;
import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.DispositivoDuplicadoException;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.ui.Menu;

public class DispositivosUI {

    private DomusControl model;
    private Scanner sc;

    private DivisoesUI divisoesUI;

    public DispositivosUI(DomusControl model, Scanner sc, DivisoesUI d) {
        this.model = model;
        this.sc = sc;
        this.divisoesUI = d;
    }

    public void setDivisoesUI(DivisoesUI d) {
        this.divisoesUI = d;
    }

    public void menuDispositivos(int idCasa) {
        try{
        divisoesUI.listDivisoes(idCasa);

        System.out.print("ID da divisão: ");
        int idD = Integer.parseInt(sc.nextLine());

        if (model.getCasa(idCasa).getDivisao(idD) == null) {
            System.out.println("Divisão não encontrada.");
            return;
        }

        Menu menu = new Menu(new String[] {
            "Adicionar Dispositivo",
            "Listar Dispositivos",
            "Remover Dispositivo",
            "Gerir Dispositivo"
        });
        menu.setHandler(1, () -> escolherDispositivo(idCasa, idD));
        menu.setHandler(2, () -> listDispositivos(idCasa, idD));
        menu.setHandler(3, () -> remDispositivo(idCasa, idD));
        menu.setHandler(4, () -> gerirDispositivoEscolher(idCasa, idD));

        menu.run();
        }
        catch(DivisaoInexistenteException | CasaInexistenteException e){
            System.out.println(e.getMessage());
        }
    }

    public void listDispositivos(int idCasa, int idD) {
        try{
        System.out.println("Dispositivos da " + model.getCasa(idCasa).getDivisao(idD).getNome() + ": ");
        
        List<String> l = model.getDispositivos().listarDispositivos(idCasa, idD);
        for(String s : l){
            System.out.println(s);
        }
        }
        catch (IllegalArgumentException | DivisaoInexistenteException | CasaInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void remDispositivo(int idCasa, int idD) {
        try{
        System.out.print("ID do dispositivo: ");
        int idDi = Integer.parseInt(sc.nextLine());
    
        model.getDispositivos().removerDispositivoAuxiliar(idCasa, idD, idDi);
        }
        catch (IllegalArgumentException | CasaInexistenteException | DivisaoInexistenteException | DispositivoInexistenteException e) {
                System.out.println(e.getMessage());
        }    
    }

    private void escolherDispositivo(int idCasa, int idD) {
        Menu menu = new Menu(new String[] {
            "Ar Condicionado",
            "Aspirador",
            "Câmara de Vigilância",
            "Coluna",
            "Forno",
            "Lâmpada",
            "Persiana",
            "Portão",
            "Televisão"
        });
        menu.setHandler(1, () -> { addDispositivo(idCasa, idD, 1); menu.stop(); });
        menu.setHandler(2, () -> { addDispositivo(idCasa, idD, 2); menu.stop(); });
        menu.setHandler(3, () -> { addDispositivo(idCasa, idD, 3); menu.stop(); });
        menu.setHandler(4, () -> { addDispositivo(idCasa, idD, 4); menu.stop(); });
        menu.setHandler(5, () -> { addDispositivo(idCasa, idD, 5); menu.stop(); });
        menu.setHandler(6, () -> { addDispositivo(idCasa, idD, 6); menu.stop(); });
        menu.setHandler(7, () -> { addDispositivo(idCasa, idD, 7); menu.stop(); });
        menu.setHandler(8, () -> { addDispositivo(idCasa, idD, 8); menu.stop(); });
        menu.setHandler(9, () -> { addDispositivo(idCasa, idD, 9); menu.stop(); });

        menu.run();
    }

    private void addDispositivo(int idCasa, int idD, int numeroMenu) {

        try{
            System.out.print("Marca: ");
            String marca = sc.nextLine();

            System.out.print("Modelo: ");
            String modelo = sc.nextLine();

            System.out.print("Consumo Ligado: ");
            double consumoLigado = Double.parseDouble(sc.nextLine());

            switch(numeroMenu) {

                case 1:
                    System.out.print("Temperatura mínima: ");
                    int tempMin = Integer.parseInt(sc.nextLine());

                    System.out.print("Temperatura máxima: ");
                    int tempMax = Integer.parseInt(sc.nextLine());

                    model.getDispositivos().adicionarArCondicionado(
                        idCasa,idD,marca,modelo,
                        consumoLigado,tempMin,tempMax
                    );
                    break;

                case 2:
                    System.out.print("Intensidade máxima: ");
                    int intensidadeMax = Integer.parseInt(sc.nextLine());

                    model.getDispositivos().adicionarAspirador(
                        idCasa,idD,marca,modelo,
                        consumoLigado,intensidadeMax
                    );
                    break;

                case 3:
                    model.getDispositivos().adicionarCamaraVigilancia(
                        idCasa,idD,marca,modelo,
                        consumoLigado
                    );
                    break;

                case 4:
                    model.getDispositivos().adicionarColuna(
                        idCasa,idD,marca,modelo,
                        consumoLigado
                    );
                    break;

                case 5:
                    System.out.print("Temperatura máxima: ");
                    int fornoTempMax = Integer.parseInt(sc.nextLine());

                    model.getDispositivos().adicionarForno(
                        idCasa,idD,marca,modelo,
                        consumoLigado,fornoTempMax
                    );
                    break;

                case 6:
                    System.out.print("Intensidade máxima: ");
                    int lampIntMax = Integer.parseInt(sc.nextLine());

                    model.getDispositivos().adicionarLampada(
                        idCasa,idD,marca,modelo,
                        consumoLigado,lampIntMax
                    );
                    break;

                case 7:
                    model.getDispositivos().adicionarPersiana(
                        idCasa,idD,marca,modelo,
                        consumoLigado
                    );
                    break;

                case 8:
                    model.getDispositivos().adicionarPortao(
                        idCasa,idD,marca,modelo,
                        consumoLigado
                    );
                    break;

                case 9:
                    model.getDispositivos().adicionarTelevisao(
                        idCasa,idD,marca,modelo,
                        consumoLigado
                    );
                    break;

                default:
                    System.out.println("Dispositivo inválido.");
                    return;
            }

            System.out.println("Dispositivo adicionado com sucesso!");
        }
        catch(IllegalArgumentException | DivisaoInexistenteException | DispositivoDuplicadoException e){
            System.out.println(e.getMessage());
        }
    }

    private void gerirDispositivoEscolher(int idCasa, int idDiv) {
        System.out.println("Dispositivos Disponiveis: ");
        try{
        List<String> l = model.getDispositivos().listarDispositivos(idCasa, idDiv);
        for(String s : l){
            System.out.println(s);
        }
        System.out.print("ID Dispositivo: ");
        int idDis = Integer.parseInt(sc.nextLine());

        if(model.getDispositivos().existeDispositivo(idCasa, idDiv, idDis)){
            gerirDispositivoMenu(idCasa, idDiv, idDis);
        }
        
        System.out.println("Dispositivo não encontrado.");
        }
        catch (IllegalArgumentException | CasaInexistenteException | DivisaoInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void gerirDispositivoMenu(int idCasa, int idDiv, int idDis) {

        try {
            List<String> opcoes =
                    model.getDispositivos().listarOpcoesDispositivo(idCasa, idDiv, idDis);

            Menu menu = new Menu(opcoes.toArray(new String[0]));

            menu.setHandler(1, () -> {
                try {
                    model.getDispositivos().ligarDispositivo(idCasa, idDiv, idDis);
                    System.out.println("Dispositivo Ligado");
                } catch (DispositivoInexistenteException |
                        CasaInexistenteException |
                        DivisaoInexistenteException e) {
                    System.out.println(e.getMessage());
                }
            });

            menu.setHandler(2, () -> {
                try {
                    model.getDispositivos().desligarDispositivo(idCasa, idDiv, idDis);
                    System.out.println("Dispositivo Desligado");
                } catch (DispositivoInexistenteException |
                        CasaInexistenteException |
                        DivisaoInexistenteException e) {
                    System.out.println(e.getMessage());
                }
            });

            menu.setHandler(3, () -> {
                try {
                    System.out.println(model.getDispositivos().infoDispositivo(idCasa, idDiv, idDis));
                } catch (DispositivoInexistenteException |
                        CasaInexistenteException |
                        DivisaoInexistenteException e) {
                    System.out.println(e.getMessage());
                }
            });

            int opcao = 4;

            for(String s : opcoes.subList(3, opcoes.size())) {

                switch(s) {

                    case "Alterar Intensidade":
                        menu.setHandler(opcao,
                                () -> alterarIntensidade(idCasa,idDiv,idDis));
                        break;

                    case "Alterar Temperatura":
                        menu.setHandler(opcao,
                                () -> alterarTemperatura(idCasa,idDiv,idDis));
                        break;

                    case "Ligar Visão Noturna":
                        menu.setHandler(opcao,
                                () -> alterarVisaoNoturna(idCasa,idDiv,idDis,true));
                        break;

                    case "Desligar Visão Noturna":
                        menu.setHandler(opcao,
                                () -> alterarVisaoNoturna(idCasa,idDiv,idDis,false));
                        break;

                    case "Ligar Visão InfraVermelho":
                        menu.setHandler(opcao,
                                () -> alterarVisaoInfraVermelho(idCasa,idDiv,idDis,true));
                        break;

                    case "Desligar Visão InfraVermelho":
                        menu.setHandler(opcao,
                                () -> alterarVisaoInfraVermelho(idCasa,idDiv,idDis,false));
                        break;

                    case "Alterar Volume":
                        menu.setHandler(opcao,
                                () -> alterarVolume(idCasa,idDiv,idDis));
                        break;

                    case "Alterar Brilho":
                        menu.setHandler(opcao,
                                () -> alterarBrilho(idCasa,idDiv,idDis));
                        break;

                    case "Alterar Percentagem de Abertura":
                        menu.setHandler(opcao,
                                () -> alterarPercentagemAbertura(idCasa,idDiv,idDis));
                        break;
                }

                opcao++;
            }

            menu.run();

        }
        catch(DispositivoInexistenteException | CasaInexistenteException | DivisaoInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void alterarTemperatura(int idCasa, int idDiv, int idDis) {

        try {

            System.out.print("Nova temperatura: ");
            int temp = Integer.parseInt(sc.nextLine());

            List<String> opcoes =
                    model.getDispositivos().listarOpcoesDispositivo(idCasa,idDiv,idDis);

            if(opcoes.contains("Alterar Temperatura")) {

                if(model.getDispositivos().getTipoDispositivo(idCasa,idDiv,idDis)
                        .equals("ArCondicionado")) {

                    model.getDispositivos().alterarTemperaturaAC(idCasa,idDiv,idDis,temp);
                }

                else if(model.getDispositivos().getTipoDispositivo(idCasa,idDiv,idDis)
                        .equals("Forno")) {

                    model.getDispositivos().alterarTemperaturaForno(idCasa,idDiv,idDis,temp);
                }
            }

            System.out.println("Temperatura alterada!");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void alterarIntensidade(int idCasa, int idDiv, int idDis) {

        try {

            System.out.print("Nova intensidade: ");
            int intensidade = Integer.parseInt(sc.nextLine());

            String tipo =
                    model.getDispositivos().getTipoDispositivo(idCasa,idDiv,idDis);

            if(tipo.equals("Aspirador")) {
                model.getDispositivos().alterarIntensidadeAspirador(
                        idCasa,idDiv,idDis,intensidade);
            }

            else if(tipo.equals("Lampada")) {
                model.getDispositivos().alterarIntensidadeLampada(
                        idCasa,idDiv,idDis,intensidade);
            }

            System.out.println("Intensidade alterada!");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void alterarVolume(int idCasa, int idDiv, int idDis) {

        try {

            System.out.print("Novo volume: ");
            int volume = Integer.parseInt(sc.nextLine());

            String tipo =
                    model.getDispositivos().getTipoDispositivo(idCasa,idDiv,idDis);

            if(tipo.equals("Televisao")) {
                model.getDispositivos().alterarVolumeTv(
                        idCasa,idDiv,idDis,volume);
            }

            else if(tipo.equals("Coluna")) {
                model.getDispositivos().alterarVolumeColuna(
                        idCasa,idDiv,idDis,volume);
            }

            System.out.println("Volume alterado!");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void alterarBrilho(int idCasa, int idDiv, int idDis) {

        try {

            System.out.print("Novo brilho: ");
            int brilho = Integer.parseInt(sc.nextLine());

            model.getDispositivos().alterarBrilhoTv(
                    idCasa,idDiv,idDis,brilho);

            System.out.println("Brilho alterado!");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void alterarVisaoNoturna(int idCasa,
                                    int idDiv,
                                    int idDis,
                                    boolean b) {

        try {

            model.getDispositivos().alterarVisaoNoturnaCamara(
                    idCasa,idDiv,idDis,b);

            System.out.println("Visão noturna alterada!");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void alterarVisaoInfraVermelho(int idCasa,
                                        int idDiv,
                                        int idDis,
                                        boolean b) {

        try {

            model.getDispositivos().alterarVisaoInfraVermelhoCamara(
                    idCasa,idDiv,idDis,b);

            System.out.println("Visão infravermelho alterada!");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void alterarPercentagemAbertura(int idCasa,
                                            int idDiv,
                                            int idDis) {

        try {

            System.out.print("Nova percentagem: ");
            int p = Integer.parseInt(sc.nextLine());

            String tipo =
                    model.getDispositivos().getTipoDispositivo(idCasa,idDiv,idDis);

            if(tipo.equals("Persiana")) {

                model.getDispositivos().alterarPercentagemAberturaPersiana(
                        idCasa,idDiv,idDis,p);
            }

            else if(tipo.equals("Portao")) {

                model.getDispositivos().alterarPercentagemAberturaPortao(
                        idCasa,idDiv,idDis,p);
            }

            System.out.println("Percentagem alterada!");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
