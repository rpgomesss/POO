package org.domuscontrol.exceptions;

public class CasaDuplicadaException extends Exception {
    
    public CasaDuplicadaException(int id) {
        super("Já existe uma casa com id " + id + ".");
    }
}