package org.domuscontrol.ui.menus;

import java.util.Scanner;

import org.domuscontrol.model.*;

public class FicheirosUI {

    private DomusControl model;
    private Scanner sc;

    public FicheirosUI(DomusControl model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void guardar() {
        System.out.println("Qual o nome do ficheiro? (deixe vazio para 'estado.dat')");
        String nomeFicheiro = sc.nextLine();
        if (nomeFicheiro.isEmpty()) {
            nomeFicheiro = "estado.dat";
        }
        try {
            model.getFicheiros().guardarEstado(nomeFicheiro);
            System.out.println("Estado guardado!");
        } catch (Exception e) {
            System.out.println("Erro ao guardar: " + e.getMessage());
        }
    }

    public void carregar() {
        System.out.println("Qual o nome do ficheiro?");
        String nomeFicheiro = sc.nextLine();
        try {
            model.getFicheiros().carregarEstado(nomeFicheiro);
            System.out.println("Estado carregado!");
        } catch (Exception e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }
}
