package org.domuscontrol.ui;

import java.util.Scanner;

import org.domuscontrol.model.*;
import org.domuscontrol.ui.menus.AutomacoesUI;
import org.domuscontrol.ui.menus.CasasUI;
import org.domuscontrol.ui.menus.CenariosUI;
import org.domuscontrol.ui.menus.DispositivosUI;
import org.domuscontrol.ui.menus.DivisoesUI;
import org.domuscontrol.ui.menus.EscalonamentosUI;
import org.domuscontrol.ui.menus.EstatisticasUI;
import org.domuscontrol.ui.menus.FicheirosUI;
import org.domuscontrol.ui.menus.TempoUI;
import org.domuscontrol.ui.menus.UtilizadoresUI;

public class TextUI {

    private DomusControl model;
    private Scanner sc;

    private UtilizadoresUI utilizadoresUI;
    private CasasUI casasUI;
    private EstatisticasUI estatisticasUI;
    private FicheirosUI ficheirosUI;
    private DispositivosUI dispositivosUI;
    private DivisoesUI divisoesUI;
    private AutomacoesUI automacoesUI;
    private EscalonamentosUI escalonamentosUI;
    private CenariosUI cenariosUI;
    private TempoUI tempoUI;

    public TextUI() {
        this.model = new DomusControl();
        this.sc = new Scanner(System.in);

        // Instanciar por ordem (alguns dependem de outros)
        this.automacoesUI   = new AutomacoesUI(model, sc);
        this.escalonamentosUI = new EscalonamentosUI(model, sc);
        this.cenariosUI     = new CenariosUI(model, sc);
        this.dispositivosUI = new DispositivosUI(model, sc, null); // divisoesUI injectado depois
        this.divisoesUI     = new DivisoesUI(model, sc, dispositivosUI);
        this.dispositivosUI.setDivisoesUI(divisoesUI);

        this.utilizadoresUI  = new UtilizadoresUI(model, sc);
        this.casasUI         = new CasasUI(model, sc, divisoesUI, automacoesUI,
                                           escalonamentosUI, cenariosUI);
        this.estatisticasUI  = new EstatisticasUI(model, sc);
        this.ficheirosUI     = new FicheirosUI(model, sc);
        this.tempoUI         = new TempoUI(model, sc);
    }

    public void run() {
        Menu menu = new Menu(new String[] {
            "Gestão de Utilizadores",
            "Gestão de Casas",
            "Estatísticas",
            "Avançar Tempo",
            "Guardar Estado",
            "Carregar Estado"
        });

        menu.setHeader(() -> {
            System.out.println("╔══════════════════════╗");
            System.out.println("║     DomusControl     ║");
            System.out.println("╚══════════════════════╝");
            if (model.getTempoAtual() != null) {
                System.out.println(" Horário Atual: " + model.getTempoAtual());
            }
        });

        menu.setHandler(1, utilizadoresUI::menuUtilizadores);
        menu.setHandler(2, casasUI::menuCasas);
        menu.setHandler(3, estatisticasUI::menuEstatisticas);
        menu.setHandler(4, tempoUI::avancarTempo);
        menu.setHandler(5, ficheirosUI::guardar);
        menu.setHandler(6, ficheirosUI::carregar);

        menu.run();
    }
}