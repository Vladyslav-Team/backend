package com.softserve.skillscope.exception.proofException;

public class ProofAlreadyPublishedException extends RuntimeException{
    public ProofAlreadyPublishedException(){
        super("Already published Proof cannot be edited");
    }
}
