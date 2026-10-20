package org.domuscontrol.mecanismos;

import org.domuscontrol.casa.Casa;
import java.io.Serializable;

public interface CondicaoAutomacao extends Serializable {

    boolean avaliar(Casa casa);

    String descricao();

    CondicaoAutomacao clone();
}