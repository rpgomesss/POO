package org.domuscontrol.ui.menus;

import java.util.List;
import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.exceptions.CasaInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;
import org.domuscontrol.ui.Menu;

public class EstatisticasUI {

    private DomusControl model;
    private Scanner sc;

    public EstatisticasUI(DomusControl model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void menuEstatisticas() {
        Menu menu = new Menu(new String[] {
            "Casa que mais consome",
            "Top 3 dispositivos de uma casa",
            "Top 3 divisões com mais dispositivos"
        });

        menu.setHeader(() -> System.out.println("\n=== ESTATÍSTICAS ==="));

        menu.setHandler(1, this::casaMaisConsome);
        menu.setHandler(2, this::topDispositivos);
        menu.setHandler(3, this::topDivisoes);

        menu.run();
    }

    private void casaMaisConsome() {
        try{
        String c = model.getTops().casaMaisConsome();
        if (c != null) {
            System.out.println("Casa que mais consome:");
            System.out.println(c);
        } else {
            System.out.println("Sem dados.");
        }
        }
        catch (CasaInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void topDispositivos() {

        List<String> res = model.getTops().top3Dispositivos();

        System.out.println("Top 3 dispositivos:");

        for(String s : res) {
            System.out.println(s);
        }
    }

    private void topDivisoes() {

        try {

            System.out.println(
                    "Top 3 divisões com mais dispositivos:");

            model.getTops().top3DivisoesMaisDispositivos()
                    .forEach(System.out::println);
        }

        catch(CasaInexistenteException |
                DivisaoInexistenteException e) {

            System.out.println(e.getMessage());
        }
    }
}
