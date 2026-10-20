package org.domuscontrol.ui.menus;

import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public class TempoUI {

    private DomusControl model;
    private Scanner sc;

    public TempoUI(DomusControl model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void avancarTempo() {
        try{
        System.out.print("Minutos: ");
        int min = Integer.parseInt(sc.nextLine());

        model.avancarTempo(min);
        System.out.println("Tempo avançado! Novo horário: " + model.getTempoAtual());
        }
        catch(DivisaoInexistenteException | DispositivoInexistenteException e){
            System.out.println(e.getMessage());
        }
    }
}
