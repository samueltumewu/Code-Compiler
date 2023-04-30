package com.company.codecompiler.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.company.codecompiler.model.ResponseModel;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseModel> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ResponseModel responseModel = new ResponseModel();
        responseModel.addMessages(e.getClass().toString() + ": " + e.getMessage());
        responseModel.setReturnCode("A-003");
        return ResponseEntity.badRequest().body(responseModel);
    }
}
