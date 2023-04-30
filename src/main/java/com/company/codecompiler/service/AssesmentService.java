package com.company.codecompiler.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.company.codecompiler.entity.Assesment;
import com.company.codecompiler.model.RequestModel;
import com.company.codecompiler.model.ResponseModel;
import com.company.codecompiler.model.ResponseModelJdoodle;
import com.company.codecompiler.model.UserInputModel;
import com.company.codecompiler.repository.AssesmentRepo;

@Service
public class AssesmentService {
    @Autowired
    private AssesmentRepo assesmentRepo;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${jdoodle.compiler.url}")
    private String URL_STRING;
    @Value("${jdoodle.client.id}")
    private String CLIENT_ID;
    @Value("${jdoodle.client.secret}")
    private String CLIENT_SECRET;
    @Value("${return.code.1}")
    private String RETURN_CODE_1;
    @Value("${return.code.2}")
    private String RETURN_CODE_2;
    
    public List<Assesment> fetchAllAssesments() {
        return assesmentRepo.findAll();
    }
    
    public Assesment fetchAssesmentById(Long id) throws NoSuchElementException {
        return assesmentRepo.findById(id).get();
    }

    public List<Assesment> fetchAllAssesmentsByQuestionTitle(String questionTitle) {
        return assesmentRepo.findByQuestionTitle(questionTitle);
    }
    
    public ResponseModel evaluateCode(UserInputModel userInputModel) throws Exception 
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

        ResponseEntity<ResponseModelJdoodle> responseJdoodle = restTemplate.exchange(urlURI, HttpMethod.POST, requestHttpEntity, ResponseModelJdoodle.class);
        // System.out.println("ini-response:" + response.getBody().getOutput().toString());

        if (responseJdoodle.getBody().getCpuTime() != null) {
            ResponseModel responseModel = checkTestCase(requestModel);
            responseModel.setAssesmentId(userInputModel.getAssesmentId());
            responseModel.setAssesmentTitle(fetchAssesmentById(responseModel.getAssesmentId()).getQuestionTitle());
            responseModel.setReturnCode(RETURN_CODE_1);
            return responseModel;
        } else {
            String syntaxErrorDetails = responseJdoodle.getBody().getOutput();
            ResponseModel responseModel = new ResponseModel();
            responseModel.addMessages("Syntax Error: " + syntaxErrorDetails);
            responseModel.setReturnCode(RETURN_CODE_1);
            return responseModel;
        }

    }

    public ResponseModel checkTestCase(RequestModel requestModel) throws Exception
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
            ResponseEntity<ResponseModelJdoodle> response = restTemplate.exchange(urlURI, HttpMethod.POST, requestHttpEntity, ResponseModelJdoodle.class);
            
            testCaseResponseOutput.add(response.getBody().getOutput().toString().strip());
        }

        ResponseModel responseModel = new ResponseModel();
        for(int i=0; i<testCases.size(); i++) {
            if (testCases.values().toArray()[i].equals(testCaseResponseOutput.get(i))) {
                System.out.println("test case #" + i + ": " + "passed");
                String testCaseResultString = "test case #" + i + ": " + "passed";
                responseModel.addMessages(testCaseResultString);
            }
            else {
                System.out.println("test case #" + i + ": " + "failed");
                String testCaseResultString = "test case #" + i + ": " + "failed";
                responseModel.addMessages(testCaseResultString);
            }
        }

        return responseModel;

    }
}
