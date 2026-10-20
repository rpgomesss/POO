package org.domuscontrol.ui.menus;

import java.util.List;
import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.DivisaoDuplicadaException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.ui.Menu;

public class DivisoesUI {

    private DomusControl model;
    private Scanner sc;

    private DispositivosUI dispositivosUI;

    public DivisoesUI(DomusControl model, Scanner sc, DispositivosUI d) {
        this.model = model;
        this.sc = sc;

        this.dispositivosUI = d;
    }

    public void menuDivisoes(int idCasa) {
        Menu menu = new Menu(new String[] {
            "Adicionar Divisão",
            "Listar Divisões",
            "Remover Divisão",
            "Gerir Dispositivos"
        });
        menu.setHandler(1, () -> addDivisao(idCasa));
        menu.setHandler(2, () -> listDivisoes(idCasa));
        menu.setHandler(3, () -> remDivisao(idCasa));
        menu.setHandler(4, () -> dispositivosUI.menuDispositivos(idCasa));

        menu.run();
    }

    private void addDivisao(int idCasa) {
        try{
        System.out.print("Nome da divisão: ");
        String nome = sc.nextLine();

        model.getDivisoes().adicionarDivisaoAuxiliar(nome, idCasa);
        System.out.println("Divisão adicionada!");
        }
        catch(CasaInexistenteException | DivisaoDuplicadaException e){
            System.out.println(e.getMessage());
        }
    }

    public void listDivisoes(int idCasa) {
        try{
            List<String> l = model.getDivisoes().listarDivisoes(idCasa);
            System.out.println("Divisões da casa " + model.getCasa(idCasa).getNome() + ":");
            for(String s : l){
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException | CasaInexistenteException e){
            System.out.println(e.getMessage());
        }
    }

    private void remDivisao(int idCasa) {
        System.out.print("ID da divisão: ");
        int idD = Integer.parseInt(sc.nextLine());

        try{
            model.getDivisoes().removerDivisaoAuxiliar(idCasa, idD);
            System.out.println("Divisão removida!");
        }
        catch(IllegalArgumentException | CasaInexistenteException | DivisaoInexistenteException e){
            System.out.println(e.getMessage());
        }
    }

    public void listDispositivosCasa(int idCasa) {
        try {
            org.domuscontrol.casa.Casa c = model.getCasa(idCasa);
            System.out.println("=== Dispositivos da casa " + c.getNome() + " ===");
            if (c.getDivisoes().isEmpty()) {
                System.out.println("  (sem divisões)");
                return;
            }
            for (org.domuscontrol.casa.Divisao d : c.getDivisoes().values()) {
                System.out.println("  Divisão [" + d.getIdentificador() + "] " + d.getNome() + ":");
                if (d.getDispositivos().isEmpty()) {
                    System.out.println("    (sem dispositivos)");
                } else {
                    d.getDispositivos().values().forEach(disp ->
                        System.out.println("    [" + disp.getId() + "] "
                            + disp.getMarca() + " " + disp.getModelo()
                            + " — " + (disp.getEstado() ? "LIGADO" : "desligado"))
                    );
                }
            }
        } catch (CasaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
