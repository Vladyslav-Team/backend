package com.softserve.skillscope.general.handler.advice;

import com.softserve.skillscope.general.handler.exception.ErrorDTO;
import com.softserve.skillscope.general.handler.exception.proofException.ProofAlreadyPublishedException;
import com.softserve.skillscope.general.handler.exception.proofException.ProofHasNullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProofExceptionsControllerAdvice {

    @ExceptionHandler({ProofAlreadyPublishedException.class, ProofHasNullValue.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDTO> proofExceptionsControllerAdvice(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(exception.getMessage()));
    }
}
