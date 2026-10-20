package org.domuscontrol.exceptions;

public class DivisaoInexistenteException extends Exception{

    public DivisaoInexistenteException(int id) {
        super("Divisao com id " + id + " não existe.");
    }

    public DivisaoInexistenteException(String msg) {
        super(msg);
    }
}