package org.domuscontrol.exceptions;

public class CenarioInexistenteException extends Exception {

    public CenarioInexistenteException(int id) {
        super("Cenario com id " + id + " não existe.");
    }
}