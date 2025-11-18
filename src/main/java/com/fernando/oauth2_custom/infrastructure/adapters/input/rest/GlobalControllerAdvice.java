package com.fernando.oauth2_custom.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.responses.ErrorResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.fernando.oauth2_custom.domain.enums.ErrorType.FUNCTIONAL;
import static com.fernando.oauth2_custom.domain.enums.ErrorType.SYSTEM;
import static com.fernando.oauth2_custom.infrastructure.utils.ErrorCatalog.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleEmailAlreadyExistsException(MethodArgumentNotValidException e){
        logException(FUNCTIONAL.name(), OAUTH2_BAD_PARAMETER.getCode(), e.getMessage());
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        return ErrorResponse.builder()
                .code(OAUTH2_BAD_PARAMETER.getCode())
                .type(FUNCTIONAL)
                .message(OAUTH2_BAD_PARAMETER.getMessage())
                .details(errors)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public ErrorResponse handleFeignException(FeignException e) throws Exception{
        logException(FUNCTIONAL.name(), FEIGN_CLIENT_ERROR.getCode(), e.getMessage());
        String code="";
        String body=e.contentUTF8();
        if(!body.isEmpty()){
            log.info(body);
            ObjectMapper mapper= new ObjectMapper();
            ErrorResponse error = mapper.readValue(body, ErrorResponse.class);
            code=error.code();
        }
        return ErrorResponse.builder()
                .code(FEIGN_CLIENT_ERROR.getCode())
                .type(FUNCTIONAL)
                .message(FEIGN_CLIENT_ERROR.getMessage())
                .details(Collections.singletonList(code))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        logException(SYSTEM.name(), OATH2_INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
        return ErrorResponse.builder()
                .code(OATH2_INTERNAL_SERVER_ERROR.getCode())
                .type(SYSTEM)
                .message(OATH2_INTERNAL_SERVER_ERROR.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    private void logException(String type, String code, String message){
        if (type.equals("FUNCTIONAL")) {
            log.warn("⚠️ Warning ({}): {}", code, message);
        } else {
            log.error("❌ Error ({}): {}", code, message);
        }
    }
}
