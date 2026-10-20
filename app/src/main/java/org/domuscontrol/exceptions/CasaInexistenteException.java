package org.domuscontrol.exceptions;

public class CasaInexistenteException extends Exception {

    public CasaInexistenteException(int id) {
        super("Casa com id " + id + " não existe.");
    }

    public CasaInexistenteException(String msg) {
        super(msg);
    }
}