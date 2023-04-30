package com.company.codecompiler.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ResponseModel responseModel = new ResponseModel();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
          responseModel.addMessages(error.getDefaultMessage());
        }
        responseModel.setReturnCode("A-004");
        return ResponseEntity.badRequest().body(responseModel);
      }
}
