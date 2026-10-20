package org.domuscontrol.mecanismos;

import java.io.Serializable;
import org.domuscontrol.casa.Casa;
import org.domuscontrol.exceptions.DispositivoInexistenteException;
import org.domuscontrol.exceptions.DivisaoInexistenteException;

public interface AcaoDispositivo extends Serializable {
 
    void executar(Casa casa) throws DivisaoInexistenteException, DispositivoInexistenteException;
 
    String descricao();
 
    AcaoDispositivo clone();
}
 
