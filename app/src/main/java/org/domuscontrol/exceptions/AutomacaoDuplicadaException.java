package org.domuscontrol.exceptions;

public class AutomacaoDuplicadaException extends Exception{

    public AutomacaoDuplicadaException(int id) {
        super("Já existe uma automacao com id " + id + ".");
    }
}