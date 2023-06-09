package com.company.codecompiler.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RequestModel {
    private String clientId;
    private String clientSecret;
    private String script;
    private String language;
    private String versionIndex;
    private String stdin;
    private Long assesmentId;
}
