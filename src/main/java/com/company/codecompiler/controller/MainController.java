package com.company.codecompiler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.company.codecompiler.entity.Assesment;
import com.company.codecompiler.model.RequestModel;
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
    public String evaluateCode(
        @RequestBody UserInputModel userInputModel
    ) throws Exception
    {
        assesmentService.evaluateCode(userInputModel);
        return "null";
    }

}
