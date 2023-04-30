package com.company.codecompiler.handler;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Value;
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
    @Value("${return.code.3}")
    private String RETURN_CODE_3;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseModel> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ResponseModel responseModel = new ResponseModel();
        responseModel.addMessages(e.getClass().toString() + ": " + e.getMessage());
        responseModel.setReturnCode(RETURN_CODE_3);
        return ResponseEntity.badRequest().body(responseModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ResponseModel responseModel = new ResponseModel();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
          responseModel.addMessages(error.getDefaultMessage());
        }
        responseModel.setReturnCode(RETURN_CODE_3);
        return ResponseEntity.badRequest().body(responseModel);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ResponseModel> handleNoSuchElementException(NoSuchElementException e){
      ResponseModel responseModel = new ResponseModel();
      responseModel.addMessages(e.getClass().toString() + ": " + e.getMessage());
      responseModel.setReturnCode(RETURN_CODE_3);
      return ResponseEntity.badRequest().body(responseModel);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<ResponseModel> handleSQLException(SQLException e){
      ResponseModel responseModel = new ResponseModel();
      responseModel.addMessages(e.getClass().toString() + ": " + e.getMessage());
      responseModel.setReturnCode(RETURN_CODE_3);
      return ResponseEntity.badRequest().body(responseModel);
    }
}
