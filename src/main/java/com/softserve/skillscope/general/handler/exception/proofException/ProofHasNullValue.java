package com.softserve.skillscope.general.handler.exception.proofException;

public class ProofHasNullValue  extends RuntimeException{
    public ProofHasNullValue(){
        super("Title or description is not completed");
    }
}
