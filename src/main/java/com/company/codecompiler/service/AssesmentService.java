package com.company.codecompiler.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.company.codecompiler.entity.Assesment;
import com.company.codecompiler.model.RequestModel;
import com.company.codecompiler.model.ResponseModel;
import com.company.codecompiler.model.UserInputModel;
import com.company.codecompiler.repository.AssesmentRepo;

@Service
public class AssesmentService {
    @Autowired
    private AssesmentRepo assesmentRepo;
    @Autowired
    private RestTemplate restTemplate;

    
    public List<Assesment> fetchAllAssesments() {
        return assesmentRepo.findAll();
    }
    
    public Assesment fetchAssesmentById(Long id) throws NoSuchElementException {
        return assesmentRepo.findById(id).get();
    }

    public List<Assesment> fetchAllAssesmentsByQuestionTitle(String questionTitle) {
        return assesmentRepo.findByQuestionTitle(questionTitle);
    }
    
    private static final String URL_STRING = "https://api.jdoodle.com/v1/execute";
    private static final String CLIENT_ID = "12cfa10c00d6e1ea65f2cc956a9e9ca7";
    private static final String CLIENT_SECRET = "52d28354db69fb1a4b6837454c2194278b7e616f129aa032526f8c09b042dd84";
    public void evaluateCode(UserInputModel userInputModel) throws Exception 
    {
        URI urlURI = new URI(URL_STRING);
        RequestModel requestModel = new RequestModel(
            CLIENT_ID, 
            CLIENT_SECRET, 
            userInputModel.getScript(), 
            userInputModel.getLanguage(), 
            userInputModel.getVersionIndex(), 
            userInputModel.getStdin(), 
            userInputModel.getAssesmentId()); 

        HttpEntity<RequestModel> requestHttpEntity = new HttpEntity<RequestModel>(requestModel);
        // System.out.println("ini-requestHttpentity:"+requestHttpEntity.toString());

        ResponseEntity<ResponseModel> response = restTemplate.exchange(urlURI, HttpMethod.POST, requestHttpEntity, ResponseModel.class);
        // System.out.println("ini-response:" + response.getBody().getOutput().toString());

        if (response.getBody().getCpuTime() != null) {
            checkTestCase(requestModel);
        } else {
            System.out.println("Syntax Error");
        }
    }

    public void checkTestCase(RequestModel requestModel) throws Exception
    {
        RequestModel testCaseRequestModel = new RequestModel();
        testCaseRequestModel = requestModel;

        Assesment assesment = fetchAssesmentById(testCaseRequestModel.getAssesmentId());
        HashMap<String, String> testCases = new HashMap<String, String>();
        testCases.put(assesment.getTestCaseFirstInput(), assesment.getTestCaseFirstOutput());
        testCases.put(assesment.getTestCaseSecondInput(), assesment.getTestCaseSecondOutput());

        List<String> testCaseResponseOutput = new LinkedList<String>();

        Iterator<HashMap.Entry<String, String>> iterator = testCases.entrySet().iterator();
        while(iterator.hasNext()){
            HashMap.Entry<String, String> entry = iterator.next();
            testCaseRequestModel.setStdin(entry.getKey());

            URI urlURI = new URI(URL_STRING);
            HttpEntity<RequestModel> requestHttpEntity = new HttpEntity<RequestModel>(testCaseRequestModel);
            ResponseEntity<ResponseModel> response = restTemplate.exchange(urlURI, HttpMethod.POST, requestHttpEntity, ResponseModel.class);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("response.getbody: "+response.getBody().getOutput().toString());

            testCaseResponseOutput.add(response.getBody().getOutput().toString().strip());
        }

        for(int i=0; i<testCases.size(); i++) {
            if (testCases.values().toArray()[i].equals(testCaseResponseOutput.get(i)))
                System.out.println("test case #" + i + ": " + "passed");
            else {
                System.out.println(testCases.values().toArray()[i]);
                System.out.println(testCaseResponseOutput.get(i));
                System.out.println("test case #" + i + ": " + "failed");
            }
        }

    }
}
