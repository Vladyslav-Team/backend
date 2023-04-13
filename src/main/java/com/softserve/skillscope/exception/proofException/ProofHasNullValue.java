package com.softserve.skillscope.exception.proofException;

public class ProofHasNullValue  extends RuntimeException{
    public ProofHasNullValue(){
        super("Title or description is not completed");
    }
}
