package org.domuscontrol.ui.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.mecanismos.Cenario;
import org.domuscontrol.mecanismos.AcaoDispositivo;
import org.domuscontrol.mecanismos.AcaoLigar;
import org.domuscontrol.mecanismos.AcaoDesligar;
import org.domuscontrol.mecanismos.AcaoMudarTemperatura;
import org.domuscontrol.mecanismos.AcaoMudarIntensidadeLampada;
import org.domuscontrol.ui.Menu;

public class CenariosUI {

    private DomusControl model;
    private Scanner sc;

    public CenariosUI(DomusControl model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void menuCenarios(int idCasa) {
        Menu menu = new Menu(new String[] {
            "Adicionar Cenário",
            "Listar Cenários",
            "Remover Cenário",
            "Ativar Cenário",
            "Desativar Cenário"
        });
        menu.setHandler(1, () -> addCenario(idCasa));
        menu.setHandler(2, () -> listCenarios(idCasa));
        menu.setHandler(3, () -> remCenario(idCasa));
        menu.setHandler(4, () -> ativarCenario(idCasa));
        menu.setHandler(5, () -> desativarCenario(idCasa));
        menu.run();
    }

    private void addCenario(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);

            System.out.print("Nome do cenário: ");
            String nome = sc.nextLine();
            System.out.print("Descrição do cenário: ");
            String descricao = sc.nextLine();

            System.out.println("--- Ações de ATIVAÇÃO ---");
            List<AcaoDispositivo> acoesAtivacao = adicionarAcoes();
            if (acoesAtivacao.isEmpty()) {
                System.out.println("Aviso: cenário sem ações de ativação.");
            }

            System.out.print("Deseja adicionar ações de DESATIVAÇÃO? (s/n): ");
            List<AcaoDispositivo> acoesDesativacao = new ArrayList<>();
            if (sc.nextLine().equalsIgnoreCase("s")) {
                System.out.println("--- Ações de DESATIVAÇÃO ---");
                acoesDesativacao = adicionarAcoes();
            }

            Cenario cenario = new Cenario(nome, descricao, acoesAtivacao, acoesDesativacao);
            c.adicionarCenario(cenario);
            System.out.println("✓ Cenário '" + nome + "' criado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private List<AcaoDispositivo> adicionarAcoes() {
        List<AcaoDispositivo> acoes = new ArrayList<>();
        System.out.println("Adicionar ações (escolha 'Terminar' quando acabar):");

        while (true) {
            Menu menuAcao = new Menu(new String[] {
                "Ligar dispositivo",
                "Desligar dispositivo",
                "Mudar temperatura de A/C",
                "Mudar intensidade de lâmpada",
                "Terminar"
            });

            final boolean[] terminar = {false};
            menuAcao.setHandler(1, () -> {
                System.out.print("ID da divisão: ");
                int idDiv = Integer.parseInt(sc.nextLine());
                System.out.print("ID do dispositivo: ");
                int idDisp = Integer.parseInt(sc.nextLine());
                acoes.add(new AcaoLigar(idDiv, idDisp));
                System.out.println("  ✓ Ação adicionada.");
            });
            menuAcao.setHandler(2, () -> {
                System.out.print("ID da divisão: ");
                int idDiv = Integer.parseInt(sc.nextLine());
                System.out.print("ID do dispositivo: ");
                int idDisp = Integer.parseInt(sc.nextLine());
                acoes.add(new AcaoDesligar(idDiv, idDisp));
                System.out.println("  ✓ Ação adicionada.");
            });
            menuAcao.setHandler(3, () -> {
                System.out.print("ID da divisão: ");
                int idDiv = Integer.parseInt(sc.nextLine());
                System.out.print("ID do A/C: ");
                int idDisp = Integer.parseInt(sc.nextLine());
                System.out.print("Nova temperatura (°C): ");
                int temp = Integer.parseInt(sc.nextLine());
                acoes.add(new AcaoMudarTemperatura(idDiv, idDisp, temp));
                System.out.println("  ✓ Ação adicionada.");
            });
            menuAcao.setHandler(4, () -> {
                System.out.print("ID da divisão: ");
                int idDiv = Integer.parseInt(sc.nextLine());
                System.out.print("ID da lâmpada: ");
                int idDisp = Integer.parseInt(sc.nextLine());
                System.out.print("Nova intensidade (0-100): ");
                int intens = Integer.parseInt(sc.nextLine());
                acoes.add(new AcaoMudarIntensidadeLampada(idDiv, idDisp, intens));
                System.out.println("  ✓ Ação adicionada.");
            });
            menuAcao.setHandler(5, () -> {
                terminar[0] = true;
                menuAcao.stop();
            });

            menuAcao.run();
            if (terminar[0]) break;
        }
        return acoes;
    }

    public void listCenarios(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Cenario> cenarios = c.getCenarios();
            if (cenarios.isEmpty()) {
                System.out.println("Sem cenários nesta casa.");
            } else {
                System.out.println("=== Cenários da casa " + c.getNome() + " ===");
                for (Cenario cenario : cenarios) {
                    System.out.println(cenario);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void remCenario(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Cenario> cenarios = c.getCenarios();
            if (cenarios.isEmpty()) {
                System.out.println("Sem cenários para remover.");
                return;
            }
            System.out.println("Cenários existentes:");
            for (Cenario cenario : cenarios) {
                System.out.println("  ID " + cenario.getIdentificador() + ": " + cenario.getNome()
                        + (cenario.isAtivo() ? " [ATIVO]" : ""));
            }
            System.out.print("ID do cenário a remover: ");
            int idCe = Integer.parseInt(sc.nextLine());
            c.removerCenario(idCe);
            System.out.println("✓ Cenário removido!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void ativarCenario(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Cenario> cenarios = c.getCenarios();
            if (cenarios.isEmpty()) {
                System.out.println("Sem cenários nesta casa.");
                return;
            }
            System.out.println("Cenários disponíveis:");
            for (Cenario cenario : cenarios) {
                System.out.println("  ID " + cenario.getIdentificador() + ": " + cenario.getNome()
                        + (cenario.isAtivo() ? " [JÁ ATIVO]" : ""));
            }
            System.out.print("ID do cenário a ativar: ");
            int idCe = Integer.parseInt(sc.nextLine());
            model.ativarCenario(idCasa, idCe);
            System.out.println("✓ Cenário ativado! As ações foram executadas.");
        } catch (Exception e) {
            System.out.println("Erro ao ativar cenário: " + e.getMessage());
        }
    }

    private void desativarCenario(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Cenario> cenarios = c.getCenarios();
            if (cenarios.isEmpty()) {
                System.out.println("Sem cenários nesta casa.");
                return;
            }
            System.out.println("Cenários disponíveis:");
            for (Cenario cenario : cenarios) {
                System.out.println("  ID " + cenario.getIdentificador() + ": " + cenario.getNome()
                        + (cenario.isAtivo() ? " [ATIVO]" : " [inativo]"));
            }
            System.out.print("ID do cenário a desativar: ");
            int idCe = Integer.parseInt(sc.nextLine());
            // Find and deactivate
            boolean found = false;
            for (Cenario cenario : cenarios) {
                if (cenario.getIdentificador() == idCe) {
                    cenario.desativar(c);
                    found = true;
                    System.out.println("✓ Cenário desativado! As ações de desativação foram executadas.");
                    break;
                }
            }
            if (!found) System.out.println("Cenário não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao desativar cenário: " + e.getMessage());
        }
    }
}
