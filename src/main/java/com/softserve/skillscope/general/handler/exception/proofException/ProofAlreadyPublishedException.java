package com.softserve.skillscope.general.handler.exception.proofException;

public class ProofAlreadyPublishedException extends RuntimeException{
    public ProofAlreadyPublishedException(){
        super("Already published Proof cannot be edited");
    }
}
