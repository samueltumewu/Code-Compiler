package com.company.codecompiler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.company.codecompiler.entity.Assesment;
import com.company.codecompiler.model.ResponseModel;
import com.company.codecompiler.model.UserInputModel;
import com.company.codecompiler.service.AssesmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private AssesmentService assesmentService;

    @Value("${return.code.1}")
    private String RETURN_CODE_1;

    @GetMapping("/assesment/title/{questionTitle}")
    public ResponseEntity<ResponseModel> getAllAssesmentByQuestionTitle(
        @PathVariable("questionTitle") String questionTitle) 
    {
        Assesment assesment = assesmentService.fetchAllAssesmentsByQuestionTitle(questionTitle);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setReturnCode(RETURN_CODE_1);
        if (assesment != null)
            responseModel.addMessages(assesment.toString());
        else
            responseModel.addMessages("Data not found!");
        return ResponseEntity.ok().body(responseModel);
    }

    @GetMapping("/assesment/id/{questionId}")
    public ResponseEntity<ResponseModel> getAssesmentById(
        @PathVariable("questionId") Long questionId) 
    {
        Assesment assesment = assesmentService.fetchAssesmentById(questionId);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setReturnCode(RETURN_CODE_1);
        if (!assesment.toString().isEmpty())
            responseModel.addMessages(assesment.toString());
        else
            responseModel.addMessages("Data not found!");
        return ResponseEntity.ok().body(responseModel);
    }

    @PostMapping("/assesment/evaluate")
    public ResponseEntity<ResponseModel> evaluateCode(
        @Valid @RequestBody UserInputModel userInputModel
    ) throws Exception
    {
        ResponseModel responseModel = assesmentService.evaluateCode(userInputModel);
        if (responseModel != null && responseModel.getReturnCode().equals(RETURN_CODE_1))
            return ResponseEntity.ok().body(responseModel);
        return ResponseEntity.badRequest().body(responseModel);
    }

    @PostMapping("/assesment/")
    public ResponseEntity<ResponseModel> addNewAssesment(
        @Valid @RequestBody Assesment assesment
    ) throws Exception
    {
        String resultString = assesmentService.addNewAssesment(assesment);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setReturnCode(RETURN_CODE_1);
        responseModel.addMessages(resultString);
        return ResponseEntity.ok().body(responseModel);
    }
}
