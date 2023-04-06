package com.softserve.skillscope.advice;

import com.softserve.skillscope.exception.ErrorDTO;
import com.softserve.skillscope.exception.talentException.TalentAlreadyExistsException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TalentExceptionsControllerAdvice {

    @ExceptionHandler(TalentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDTO> talentAlreadyExistsExceptionHandler(TalentAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(TalentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> talentNotFoundExceptionExceptionHandler(TalentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(exception.getMessage()));
    }
}
