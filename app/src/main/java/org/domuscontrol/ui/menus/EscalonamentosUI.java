package org.domuscontrol.ui.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.mecanismos.Escalonamento;
import org.domuscontrol.mecanismos.AcaoDispositivo;
import org.domuscontrol.mecanismos.AcaoLigar;
import org.domuscontrol.mecanismos.AcaoDesligar;
import org.domuscontrol.mecanismos.AcaoMudarTemperatura;
import org.domuscontrol.mecanismos.AcaoMudarIntensidadeLampada;
import org.domuscontrol.ui.Menu;

public class EscalonamentosUI {

    private DomusControl model;
    private Scanner sc;

    public EscalonamentosUI(DomusControl model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void menuEscalonamentos(int idCasa) {
        Menu menu = new Menu(new String[] {
            "Adicionar Escalonamento",
            "Listar Escalonamentos",
            "Remover Escalonamento"
        });
        menu.setHandler(1, () -> addEscalonamento(idCasa));
        menu.setHandler(2, () -> listEscalonamentos(idCasa));
        menu.setHandler(3, () -> remEscalonamento(idCasa));
        menu.run();
    }

    private void addEscalonamento(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);

            System.out.print("Nome do escalonamento: ");
            String nome = sc.nextLine();

            System.out.print("Hora de disparo (HH:MM): ");
            String horaStr = sc.nextLine().trim();
            int horaMinutos = parseHora(horaStr);
            if (horaMinutos < 0) {
                System.out.println("Formato de hora inválido. Use HH:MM (ex: 08:30)");
                return;
            }

            System.out.print("Disparar todos os dias? (s/n): ");
            boolean todosDias = sc.nextLine().equalsIgnoreCase("s");

            boolean[] diasSemana = new boolean[7];
            if (!todosDias) {
                System.out.println("Selecione os dias (s/n para cada um):");
                String[] nomesDias = {"Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
                for (int i = 0; i < 7; i++) {
                    System.out.print("  " + nomesDias[i] + "? (s/n): ");
                    diasSemana[i] = sc.nextLine().equalsIgnoreCase("s");
                }
            }

            System.out.println("--- Ações do escalonamento ---");
            List<AcaoDispositivo> acoes = adicionarAcoes();
            if (acoes.isEmpty()) {
                System.out.println("Aviso: escalonamento sem ações definidas.");
            }

            Escalonamento esc;
            if (todosDias) {
                esc = new Escalonamento(nome, horaMinutos, acoes);
            } else {
                esc = new Escalonamento(nome, horaMinutos, diasSemana, acoes);
            }

            c.adicionarEscalonamento(esc);
            System.out.println("✓ Escalonamento '" + nome + "' criado — dispara às " + horaStr
                    + (todosDias ? " todos os dias" : " nos dias selecionados") + ".");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private int parseHora(String horaStr) {
        try {
            String[] partes = horaStr.split(":");
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);
            if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) return -1;
            return horas * 60 + minutos;
        } catch (Exception e) {
            return -1;
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

    public void listEscalonamentos(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Escalonamento> escalonamentos = c.getEscalonamentos();
            if (escalonamentos.isEmpty()) {
                System.out.println("Sem escalonamentos nesta casa.");
            } else {
                System.out.println("=== Escalonamentos da casa " + c.getNome() + " ===");
                for (Escalonamento e : escalonamentos) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void remEscalonamento(int idCasa) {
        try {
            Casa c = model.getCasa(idCasa);
            List<Escalonamento> escalonamentos = c.getEscalonamentos();
            if (escalonamentos.isEmpty()) {
                System.out.println("Sem escalonamentos para remover.");
                return;
            }
            System.out.println("Escalonamentos existentes:");
            for (Escalonamento e : escalonamentos) {
                System.out.println("  ID " + e.getIdentificador() + ": " + e.getNome());
            }
            System.out.print("ID do escalonamento a remover: ");
            int idE = Integer.parseInt(sc.nextLine());
            c.removerEscalonamento(idE);
            System.out.println("✓ Escalonamento removido!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
