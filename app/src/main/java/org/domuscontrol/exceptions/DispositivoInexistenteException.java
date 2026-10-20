package org.domuscontrol.exceptions;

public class DispositivoInexistenteException extends Exception {

    public DispositivoInexistenteException(int id) {
        super("Dispositivo com id " + id + " não existe.");
    }
}