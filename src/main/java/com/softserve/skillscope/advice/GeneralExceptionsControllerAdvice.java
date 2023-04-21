package com.softserve.skillscope.advice;

import com.softserve.skillscope.exception.ErrorDTO;
import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.exception.generalException.ValidationException;
import com.softserve.skillscope.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GeneralExceptionsControllerAdvice {

    @ExceptionHandler(ForbiddenRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDTO> forbiddenRequestExceptionHandler(ForbiddenRequestException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorDTO> unauthorizedUserExceptionHandler(UnauthorizedUserException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> badRequestExceptionHandler(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler({TalentNotFoundException.class, ProofNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> notFoundExceptionExceptionHandler(TalentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDTO> validationException(ValidationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(exception.getMessage()));
    }
}
