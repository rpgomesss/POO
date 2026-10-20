package org.domuscontrol.ui.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.mecanismos.Automacao;
import org.domuscontrol.mecanismos.AcaoDispositivo;
import org.domuscontrol.mecanismos.AcaoLigar;
import org.domuscontrol.mecanismos.AcaoDesligar;
import org.domuscontrol.mecanismos.AcaoMudarTemperatura;
import org.domuscontrol.mecanismos.AcaoMudarIntensidadeLampada;
import org.domuscontrol.mecanismos.CondicaoAutomacao;
import org.domuscontrol.mecanismos.CondicaoDispositivoLigado;
import org.domuscontrol.mecanismos.CondicaoDispositivoDesligado;
import org.domuscontrol.mecanismos.CondicaoTemperaturaMaiorQue;
import org.domuscontrol.mecanismos.CondicaoConsumoMaiorQue;
import org.domuscontrol.ui.Menu;

public class AutomacoesUI {

    private DomusControl model;
    private Scanner sc;

    public AutomacoesUI(DomusControl model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void menuAutomacoes(int idCasa) {
        Menu menu = new Menu(new String[] {
            "Adicionar Automação",
            "Listar Automações",
            "Remover Automação",
            "Avaliar Automações (verificar condições)"
        });
        menu.setHandler(1, () -> addAutomacao(idCasa));
        menu.setHandler(2, () -> listAutomacoes(idCasa));
        menu.setHandler(3, () -> remAutomacao(idCasa));
        menu.setHandler(4, () -> avaliarAutomacoes(idCasa));
        menu.run();
    }

    private void addAutomacao(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);

            System.out.print("Nome da automação: ");
            String nome = sc.nextLine();

            CondicaoAutomacao condicao = escolherCondicao();
            if (condicao == null) {
                System.out.println("Nenhuma condição escolhida. Automação cancelada.");
                return;
            }

            List<AcaoDispositivo> acoes = adicionarAcoes(c);
            if (acoes.isEmpty()) {
                System.out.println("Nenhuma ação definida. Automação cancelada.");
                return;
            }

            Automacao a = new Automacao(nome, condicao, acoes);
            c.adicionarAutomacao(a);
            System.out.println("✓ Automação '" + nome + "' criada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private CondicaoAutomacao escolherCondicao() {
        Menu menuCond = new Menu(new String[] {
            "Dispositivo Ligado",
            "Dispositivo Desligado",
            "Temperatura do A/C maior que valor",
            "Consumo do dispositivo maior que valor"
        });

        final CondicaoAutomacao[] resultado = {null};

        menuCond.setHandler(1, () -> {
            System.out.print("ID da divisão: ");
            int idDiv = Integer.parseInt(sc.nextLine());
            System.out.print("ID do dispositivo: ");
            int idDisp = Integer.parseInt(sc.nextLine());
            resultado[0] = new CondicaoDispositivoLigado(idDiv, idDisp);
            menuCond.stop();
        });
        menuCond.setHandler(2, () -> {
            System.out.print("ID da divisão: ");
            int idDiv = Integer.parseInt(sc.nextLine());
            System.out.print("ID do dispositivo: ");
            int idDisp = Integer.parseInt(sc.nextLine());
            resultado[0] = new CondicaoDispositivoDesligado(idDiv, idDisp);
            menuCond.stop();
        });
        menuCond.setHandler(3, () -> {
            System.out.print("ID da divisão: ");
            int idDiv = Integer.parseInt(sc.nextLine());
            System.out.print("ID do A/C: ");
            int idDisp = Integer.parseInt(sc.nextLine());
            System.out.print("Temperatura limite (°C): ");
            int temp = Integer.parseInt(sc.nextLine());
            resultado[0] = new CondicaoTemperaturaMaiorQue(idDiv, idDisp, temp);
            menuCond.stop();
        });
        menuCond.setHandler(4, () -> {
            System.out.print("ID da divisão: ");
            int idDiv = Integer.parseInt(sc.nextLine());
            System.out.print("ID do dispositivo: ");
            int idDisp = Integer.parseInt(sc.nextLine());
            System.out.print("Limite de consumo (Wh): ");
            double consumo = Double.parseDouble(sc.nextLine());
            resultado[0] = new CondicaoConsumoMaiorQue(idDiv, idDisp, consumo);
            menuCond.stop();
        });

        menuCond.run();
        return resultado[0];
    }

    private List<AcaoDispositivo> adicionarAcoes(Casa c) {
        List<AcaoDispositivo> acoes = new ArrayList<>();
        System.out.println("Adicionar ações (0 para terminar):");

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
            });
            menuAcao.setHandler(2, () -> {
                System.out.print("ID da divisão: ");
                int idDiv = Integer.parseInt(sc.nextLine());
                System.out.print("ID do dispositivo: ");
                int idDisp = Integer.parseInt(sc.nextLine());
                acoes.add(new AcaoDesligar(idDiv, idDisp));
            });
            menuAcao.setHandler(3, () -> {
                System.out.print("ID da divisão: ");
                int idDiv = Integer.parseInt(sc.nextLine());
                System.out.print("ID do A/C: ");
                int idDisp = Integer.parseInt(sc.nextLine());
                System.out.print("Nova temperatura (°C): ");
                int temp = Integer.parseInt(sc.nextLine());
                acoes.add(new AcaoMudarTemperatura(idDiv, idDisp, temp));
            });
            menuAcao.setHandler(4, () -> {
                System.out.print("ID da divisão: ");
                int idDiv = Integer.parseInt(sc.nextLine());
                System.out.print("ID da lâmpada: ");
                int idDisp = Integer.parseInt(sc.nextLine());
                System.out.print("Nova intensidade (0-100): ");
                int intens = Integer.parseInt(sc.nextLine());
                acoes.add(new AcaoMudarIntensidadeLampada(idDiv, idDisp, intens));
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

    public void listAutomacoes(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Automacao> automacoes = c.getAutomacoes();
            if (automacoes.isEmpty()) {
                System.out.println("Sem automações nesta casa.");
            } else {
                System.out.println("=== Automações da casa " + c.getNome() + " ===");
                for (Automacao a : automacoes) {
                    System.out.println(a);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void remAutomacao(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Automacao> automacoes = c.getAutomacoes();
            if (automacoes.isEmpty()) {
                System.out.println("Sem automações para remover.");
                return;
            }
            System.out.println("Automações existentes:");
            for (Automacao a : automacoes) {
                System.out.println("  ID " + a.getIdentificador() + ": " + a.getNome());
            }
            System.out.print("ID da automação a remover: ");
            int idA = Integer.parseInt(sc.nextLine());
            c.removerAutomacao(idA);
            System.out.println("✓ Automação removida!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void avaliarAutomacoes(int idCasa) {
        try {
            model.avaliarAutomacoes(idCasa);
            System.out.println("✓ Automações avaliadas. As condições satisfeitas foram executadas.");
        } catch (Exception e) {
            System.out.println("Erro ao avaliar automações: " + e.getMessage());
        }
    }
}
