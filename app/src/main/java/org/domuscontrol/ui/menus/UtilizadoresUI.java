package org.domuscontrol.ui.menus;

import java.util.List;
import java.util.Scanner;

import org.domuscontrol.Utilizador;
import org.domuscontrol.model.*;
import org.domuscontrol.mecanismos.Escalonamento;
import org.domuscontrol.mecanismos.Mecanismo;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.UtilizadorDuplicadoException;
import org.domuscontrol.exceptions.UtilizadorInexistenteException;
import org.domuscontrol.ui.Menu;

public class UtilizadoresUI {

    private DomusControl model;
    private Scanner sc;

    public UtilizadoresUI(DomusControl model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void menuUtilizadores() {
        Menu menu = new Menu(new String[] {
            "Adicionar Utilizador",
            "Listar Utilizadores",
            "Remover Utilizador",
            "Listar Casas de um Utilizador",
            "Associar Casa a Utilizador",
            "Remover Associação de Casa",
            "Gerir Permissões",
            "Ver Sugestões de Escalonamentos"
        });

        menu.setHeader(() -> System.out.println("\n=== GESTÃO DE UTILIZADORES ==="));

        menu.setHandler(1, this::addUtilizador);
        menu.setHandler(2, this::listUtilizadores);
        menu.setHandler(3, this::remUtilizador);
        menu.setHandler(4, this::listarCasasUtilizador);
        menu.setHandler(5, this::associarCasa);
        menu.setHandler(6, this::removerAssociacao);
        menu.setHandler(7, this::menuGerirPermissoes);
        menu.setHandler(8, this::verSugestoes);

        menu.run();
    }

    private void addUtilizador() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            model.adicionarUtilizador(nome);
            System.out.println("✓ Utilizador criado!");
        } catch (UtilizadorDuplicadoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listUtilizadores() {
        if (model.getUtilizadores().isEmpty()) {
            System.out.println("Sem utilizadores registados.");
            return;
        }
        model.getUtilizadores().values().forEach(u ->
            System.out.println("  [" + u.getIdentificador() + "] " + u.getNome())
        );
    }

    private void remUtilizador() {
        try {
            System.out.print("ID do utilizador: ");
            int id = Integer.parseInt(sc.nextLine());
            model.removerUtilizador(id);
            System.out.println("✓ Utilizador removido!");
        } catch (UtilizadorInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarCasasUtilizador() {
        try {
            System.out.print("ID do utilizador: ");
            int id = Integer.parseInt(sc.nextLine());
            List<String> casas = model.getUtilizadoresECasas().listarCasasDoUtilizador(id);
            if (casas.isEmpty()) {
                System.out.println("Este utilizador não tem casas associadas.");
            } else {
                casas.forEach(s -> System.out.println("  " + s));
            }
        } catch (UtilizadorInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void associarCasa() {
        System.out.print("ID do utilizador: ");
        int idU = Integer.parseInt(sc.nextLine());

        System.out.print("ID da casa: ");
        int idC = Integer.parseInt(sc.nextLine());

        System.out.print("É admin? (s/n): ");
        boolean admin = sc.nextLine().equalsIgnoreCase("s");

        if (model.existeUtilizador(idU) && model.existeCasa(idC)) {
            try {
                model.getUtilizadoresECasas().associarUtilizadorCasa(idU, idC, admin);
                System.out.println("✓ Associação feita!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Utilizador ou Casa não encontrados.");
        }
    }

    private void removerAssociacao() {
        System.out.print("ID do utilizador: ");
        int idU = Integer.parseInt(sc.nextLine());

        System.out.print("ID da casa: ");
        int idC = Integer.parseInt(sc.nextLine());

        if (model.existeUtilizador(idU) && model.existeCasa(idC)) {
            try {
                model.getUtilizadoresECasas().removerAcessoCasa(idU, idC);
                System.out.println("✓ Associação removida!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Utilizador ou Casa não encontrados.");
        }
    }

    private void menuGerirPermissoes() {
        System.out.print("ID do utilizador: ");
        int idU = Integer.parseInt(sc.nextLine());

        System.out.print("ID da casa: ");
        int idC = Integer.parseInt(sc.nextLine());

        Menu menu = new Menu(new String[] {
            "Tornar Admin",
            "Remover Admin (volta a utilizador normal)"
        });
        menu.setHandler(1, () -> this.tornarAdmin(idU, idC));
        menu.setHandler(2, () -> this.removerAdmin(idU, idC));
        menu.run();
    }

    private void tornarAdmin(int idU, int idC) {
        if (model.existeUtilizador(idU) && model.existeCasa(idC)) {
            try {
                model.getUtilizadoresECasas().alterarPermissao(idU, idC, true);
                System.out.println("✓ " + model.getUtilizador(idU).getNome()
                        + " agora é admin da casa " + model.getCasa(idC).getNome() + ".");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Utilizador ou Casa não encontrados.");
        }
    }

    private void removerAdmin(int idU, int idC) {
        if (model.existeUtilizador(idU) && model.existeCasa(idC)) {
            try {
                model.getUtilizadoresECasas().alterarPermissao(idU, idC, false);
                System.out.println("✓ " + model.getUtilizador(idU).getNome()
                        + " já não é admin da casa " + model.getCasa(idC).getNome() + ".");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Utilizador ou Casa não encontrados.");
        }
    }

    // ================= SUGESTÕES DE ESCALONAMENTOS =================

    private void verSugestoes() {
        try {
            System.out.print("ID do utilizador: ");
            int id = Integer.parseInt(sc.nextLine());

            // Obtém o utilizador diretamente do mapa interno (não clone) para ter o histórico
            Utilizador u = model.getUtilizadores().get(id);
            if (u == null) {
                System.out.println("Utilizador não encontrado.");
                return;
            }

            List<Mecanismo> sugestoes = u.gerarSugestoes();

            if (sugestoes.isEmpty()) {
                System.out.println("Não existem sugestões de escalonamento para este utilizador.");
                System.out.println("(São necessárias pelo menos 3 interações iguais à mesma hora)");
                return;
            }

            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║   Sugestões Automáticas de Escalonamento ║");
            System.out.println("╚══════════════════════════════════════╝");

            for (Mecanismo m : sugestoes) {
                System.out.println("\n" + m);
                System.out.print("Aceitar esta sugestão? (s/n): ");
                String resp = sc.nextLine();
                if (resp.equalsIgnoreCase("s")) {
                    System.out.println("Casas disponíveis:");
                    model.getCasas().forEach((k, v) ->
                        System.out.println("  [" + k + "] " + v.getNome()));
                    System.out.print("ID da casa onde guardar: ");
                    int idCasa = Integer.parseInt(sc.nextLine());
                    try {
                        org.domuscontrol.casa.Casa c = model.getCasa(idCasa);
                        c.adicionarEscalonamento((Escalonamento) m);
                        System.out.println("✓ Escalonamento adicionado à casa '" + c.getNome() + "'!");
                    } catch (CasaInexistenteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Erro ao adicionar: " + e.getMessage());
                    }
                } else {
                    System.out.println("  Sugestão ignorada.");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
