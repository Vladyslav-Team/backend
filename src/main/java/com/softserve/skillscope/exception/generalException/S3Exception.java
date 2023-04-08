package com.softserve.skillscope.exception.generalException;

import java.io.IOException;

public class S3Exception extends RuntimeException {
    public S3Exception() {
        super("Error occured while working with Amazon S3 bucket");
    }
}
