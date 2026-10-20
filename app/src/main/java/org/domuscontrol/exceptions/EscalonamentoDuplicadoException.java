package org.domuscontrol.exceptions;

public class EscalonamentoDuplicadoException extends Exception {

    public EscalonamentoDuplicadoException(int id) {
        super("Já existe um escalonamento com id " + id + ".");
    }
}