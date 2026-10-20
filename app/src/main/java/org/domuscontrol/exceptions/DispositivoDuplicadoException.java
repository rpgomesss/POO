package org.domuscontrol.exceptions;

public class DispositivoDuplicadoException extends Exception {

    public DispositivoDuplicadoException(int id) {
        super("Já existe um dispositivo com id " + id + ".");
    }
}