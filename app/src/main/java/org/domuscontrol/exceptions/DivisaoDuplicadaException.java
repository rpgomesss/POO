package org.domuscontrol.exceptions;

public class DivisaoDuplicadaException extends Exception {

    public DivisaoDuplicadaException(int id) {
        super("Já existe uma divisao com id " + id + ".");
    }
}