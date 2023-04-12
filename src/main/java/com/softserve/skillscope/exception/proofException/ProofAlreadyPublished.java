package com.softserve.skillscope.exception.proofException;

public class ProofAlreadyPublished extends RuntimeException{
    public ProofAlreadyPublished(){
        super("Proof that has already been published cannot be changed");
    }
}
