package org.domuscontrol.ui.menus;

import java.util.List;
import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.CasaDuplicadaException;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.UtilizadorInexistenteException;
import org.domuscontrol.ui.Menu;

public class CasasUI {

    private DomusControl model;
    private Scanner sc;

    private DivisoesUI divisoesUI;
    private AutomacoesUI automacoesUI;
    private EscalonamentosUI escalonamentosUI;
    private CenariosUI cenariosUI;

    public CasasUI(DomusControl model, Scanner sc, DivisoesUI div, AutomacoesUI aut,
                   EscalonamentosUI esc, CenariosUI cen) {
        this.model = model;
        this.sc = sc;
        this.divisoesUI = div;
        this.automacoesUI = aut;
        this.escalonamentosUI = esc;
        this.cenariosUI = cen;
    }


    public void menuCasas() {
        Menu menu = new Menu(new String[] {
            "Adicionar Casa",
            "Listar Casas",
            "Remover Casa",
            "Gerir Casa do Utilizador"
        });

        menu.setHeader(() -> System.out.println("\n=== GESTÃO DE CASAS ==="));

        menu.setHandler(1, this::addCasa);
        menu.setHandler(2, this::listCasas);
        menu.setHandler(3, this::remCasa);
        menu.setHandler(4, this::gerirCasasUtilizador);

        menu.run();
    }

    private void addCasa() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Localização: ");
            String loc = sc.nextLine();

            model.adicionarCasa(nome, loc);

            System.out.println("✓ Casa criada!");
        } catch (CasaDuplicadaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listCasas() {
        if (model.getCasas().isEmpty()) {
            System.out.println("Sem casas registadas.");
            return;
        }
        for (Casa c : model.getCasas().values()) {
            System.out.println("  [" + c.getIdentificador() + "] " + c.getNome()
                    + " — " + c.getLocalizacao());
        }
    }

    private void remCasa() {
        try {
            System.out.print("ID da casa: ");
            int id = Integer.parseInt(sc.nextLine());
            model.removerCasa(id);
            System.out.println("✓ Casa removida!");
        } catch (CasaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void gerirCasasUtilizador() {
        try {
            System.out.print("ID do Utilizador: ");
            int id = Integer.parseInt(sc.nextLine());

            List<String> casas = model.getUtilizadoresECasas().listarCasasDoUtilizador(id);

            if (casas.isEmpty()) {
                System.out.println("Este utilizador não tem casas associadas.");
                return;
            }

            System.out.println("Casas do utilizador:");
            for (String s : casas) {
                System.out.println("  " + s);
            }

            System.out.print("ID da Casa para gerir: ");
            int idCasa = Integer.parseInt(sc.nextLine());

            // Determine if admin or normal user
            boolean isAdmin = model.getUtilizadores().get(id) != null
                    && model.getUtilizadores().get(id).getCasasAdmin().containsKey(idCasa);

            if (isAdmin) {
                menuGerirCasaAdmin(idCasa);
            } else {
                menuGerirCasaNormal(idCasa);
            }

        } catch (UtilizadorInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void menuGerirCasaNormal(int idCasa) {
        String nomeCasa;
        try {
            nomeCasa = model.getCasa(idCasa).getNome();
        } catch (CasaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Menu menu = new Menu(new String[] {
            "Listar Divisões",
            "Listar Dispositivos da Casa",
            "Listar Automações",
            "Listar Escalonamentos",
            "Listar Cenários"
        });

        menu.setHeader(() -> System.out.println("\n=== Gerir Casa: " + nomeCasa + " ==="));

        menu.setHandler(1, () -> divisoesUI.listDivisoes(idCasa));
        menu.setHandler(2, () -> divisoesUI.listDispositivosCasa(idCasa));
        menu.setHandler(3, () -> automacoesUI.listAutomacoes(idCasa));
        menu.setHandler(4, () -> escalonamentosUI.listEscalonamentos(idCasa));
        menu.setHandler(5, () -> cenariosUI.listCenarios(idCasa));

        menu.run();
    }

    private void menuGerirCasaAdmin(int idCasa) {
        String nomeCasa;
        try {
            nomeCasa = model.getCasa(idCasa).getNome();
        } catch (CasaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Menu menu = new Menu(new String[] {
            "Gerir Divisões",
            "Gerir Automações",
            "Gerir Escalonamentos",
            "Gerir Cenários"
        });

        menu.setHeader(() -> System.out.println("\n=== Gerir Casa (Admin): " + nomeCasa + " ==="));

        menu.setHandler(1, () -> divisoesUI.menuDivisoes(idCasa));
        menu.setHandler(2, () -> automacoesUI.menuAutomacoes(idCasa));
        menu.setHandler(3, () -> escalonamentosUI.menuEscalonamentos(idCasa));
        menu.setHandler(4, () -> cenariosUI.menuCenarios(idCasa));

        menu.run();
    }
}
