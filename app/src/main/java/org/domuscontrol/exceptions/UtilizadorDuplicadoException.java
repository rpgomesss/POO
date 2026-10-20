package org.domuscontrol.exceptions;

public class UtilizadorDuplicadoException extends Exception{

    public UtilizadorDuplicadoException(int id) {
        super("Já existe um utilizador com id " + id + ".");
    }
}