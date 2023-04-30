package com.company.codecompiler.model;

import java.util.LinkedList;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
    private String returnCode;
    private Long assesmentId;
    private String assesmentTitle;
    private LinkedList<String> message = new LinkedList<String>();

    public void addMessages(String msg){
        message.add(msg);
    }
}
