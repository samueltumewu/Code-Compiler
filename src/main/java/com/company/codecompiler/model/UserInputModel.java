package com.company.codecompiler.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInputModel {
    private String script;
    private String language;
    private String versionIndex;
    private String stdin;
    private Long assesmentId;
}
