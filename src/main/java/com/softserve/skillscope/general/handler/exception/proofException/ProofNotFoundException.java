package com.softserve.skillscope.general.handler.exception.proofException;

public class ProofNotFoundException extends RuntimeException{
    public  ProofNotFoundException() {
        super("Proof with such credentials is not found");
    }

    public ProofNotFoundException(String message) {
        super(message);
    }
}
