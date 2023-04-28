package com.company.codecompiler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.codecompiler.entity.Assesment;
import com.company.codecompiler.repository.AssesmentRepo;

@Service
public class AssesmentService {
    @Autowired
    private AssesmentRepo assesmentRepo;

    public List<Assesment> fetchAllAssesments() {
        return assesmentRepo.findAll();
    }

    public List<Assesment> fetchAllAssesmentsByQuestionTitle(String questionTitle) {
        return assesmentRepo.findByQuestionTitle(questionTitle);
    }
}
