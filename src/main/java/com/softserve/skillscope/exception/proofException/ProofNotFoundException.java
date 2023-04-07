package com.softserve.skillscope.exception.proofException;

public class ProofNotFoundException extends RuntimeException{
    public  ProofNotFoundException() {
        super("Proof with such credentials is not found");
    }
}
