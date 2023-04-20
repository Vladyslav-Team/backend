package com.softserve.skillscope.advice;

import com.softserve.skillscope.exception.ErrorDTO;
import com.softserve.skillscope.exception.proofException.ProofAlreadyPublishedException;
import com.softserve.skillscope.exception.proofException.ProofHasNullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProofExceptionsControllerAdvice {

    @ExceptionHandler({ProofAlreadyPublishedException.class, ProofHasNullValue.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDTO> ProofExceptionsControllerAdvice(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(exception.getMessage()));
    }
}
