package com.company.codecompiler.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseModel {
    private String output;
    private String statusCode;
    private String memory;
    private String cpuTime;
    private String compilationStatus;
}
