package org.domuscontrol.exceptions;

public class CenarioDuplicadoException extends Exception{

    public CenarioDuplicadoException(int id) {
        super("Já existe um cenario com id " + id + ".");
    }
}