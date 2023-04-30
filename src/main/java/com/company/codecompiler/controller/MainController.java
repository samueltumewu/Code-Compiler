package com.company.codecompiler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private AssesmentService assesmentService;

    @GetMapping("/assesment/{questionTitle}")
    public List<Assesment> getAllAssesmentByQuestionTitle(
        @PathVariable("questionTitle") String questionTitle) 
    {
        return assesmentService.fetchAllAssesmentsByQuestionTitle(questionTitle);
    }

    @PostMapping("/assesment/evaluate")
    public ResponseEntity<ResponseModel> evaluateCode(
        @RequestBody UserInputModel userInputModel
    ) throws Exception
    {
        ResponseModel responseModel = assesmentService.evaluateCode(userInputModel);
        if (responseModel != null && responseModel.getReturnCode().equals("A-001"))
            return ResponseEntity.ok().body(responseModel);
        return ResponseEntity.badRequest().body(responseModel);
    }

}
