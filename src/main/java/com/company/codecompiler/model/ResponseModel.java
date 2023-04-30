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
    private LinkedList<String> message = new LinkedList<String>();
    private Long assesmentId;
    private String assesmentTitle;

    public void addMessages(String msg){
        message.add(msg);
    }
}
