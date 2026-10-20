package org.domuscontrol.exceptions;

public class EscalonamentoInexistenteException extends Exception{

    public EscalonamentoInexistenteException(int id) {
        super("Escalonamento com id " + id + " não existe.");
    }
}