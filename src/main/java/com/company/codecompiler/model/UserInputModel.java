package com.company.codecompiler.model;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInputModel {
    @NotBlank(message = "script is mandatory")
    private String script;
    @NotBlank(message = "language is mandatory")
    private String language;
    @NotBlank(message = "versionIndex is mandatory")
    private String versionIndex;
    @NotBlank(message = "stdin is mandatory")
    private String stdin;
    @NotNull(message = "assesmentId is mandatory")
    private Long assesmentId;
}
