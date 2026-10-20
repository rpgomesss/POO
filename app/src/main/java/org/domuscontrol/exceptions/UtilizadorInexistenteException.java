package org.domuscontrol.exceptions;

public class UtilizadorInexistenteException extends Exception{

    public UtilizadorInexistenteException(int id) {
        super("Utilizador com id " + id + " não existe.");
    }
}