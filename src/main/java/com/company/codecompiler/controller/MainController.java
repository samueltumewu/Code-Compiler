package com.company.codecompiler.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import com.company.codecompiler.service.AssesmentService;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private AssesmentService assesmentService;

    @Autowired
    private RequestModel requestModel;

    @Autowired
    private ResponseModel responseModel;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/assesment/{questionTitle}")
    public List<Assesment> getAllAssesmentByQuestionTitle(
        @PathVariable("questionTitle") String questionTitle) 
    {
        return assesmentService.fetchAllAssesmentsByQuestionTitle(questionTitle);
    }

    @PostMapping("/assesment/evaluate")
    public String evaluateCode(
        @RequestBody RequestModel requestModel
    ) throws Exception
    {
        String urlString = "https://api.jdoodle.com/v1/execute";
        URI urlUri = new URI(urlString);

        requestModel.setClientId("12cfa10c00d6e1ea65f2cc956a9e9ca7");
        requestModel.setClientSecret("52d28354db69fb1a4b6837454c2194278b7e616f129aa032526f8c09b042dd84");
        HttpEntity<RequestModel> requestHttpEntity = new HttpEntity<RequestModel>(requestModel);
        System.out.println("ini-requestHttpentity:"+requestHttpEntity.toString());

        ResponseEntity<ResponseModel> response = restTemplate.exchange(urlUri, HttpMethod.POST, requestHttpEntity, ResponseModel.class);
        System.out.println("ini-response:" + response.toString());
        return response.toString();
    }

}
