package org.domuscontrol.exceptions;

public class AutomacaoInexistenteException extends Exception{

    public AutomacaoInexistenteException(int id) {
        super("Automacao com id " + id + " não existe.");
    }
}