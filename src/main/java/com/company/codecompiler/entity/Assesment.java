package com.company.codecompiler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assesment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assesment {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "question_title", unique = true)
	@NotBlank(message = "questionTitle is mandatory")
	private String questionTitle;

    @Column(name = "question_detail")
	@NotBlank(message = "questiondetail is mandatory")
	private String questiondetail;

	@Column(name = "test_case_example_input")
	@NotBlank(message = "testCaseExampleInput is mandatory")
	private String testCaseExampleInput;

	@Column(name = "test_case_example_output")
	@NotBlank(message = "testCaseExampleOutput is mandatory")
	private String testCaseExampleOutput;

	@Column(name = "test_case_first_input")
	@NotBlank(message = "testCaseFirstInput is mandatory")
	private String testCaseFirstInput;

	@Column(name = "test_case_first_output")
	@NotBlank(message = "testCaseFirstOutput is mandatory")
	private String testCaseFirstOutput;

	@Column(name = "test_case_second_input")
	@NotBlank(message = "testCaseSecondInput is mandatory")
	private String testCaseSecondInput;

	@Column(name = "test_case_second_output")
	@NotBlank(message = "testCaseSecondOutput is mandatory")
	private String testCaseSecondOutput;
	
}
