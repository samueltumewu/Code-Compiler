package com.company.codecompiler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "question_title")
	private String questionTitle;

    @Column(name = "question_detail")
	private String questiondetail;

	@Column(name = "test_case_example_input")
	private String testCaseExampleInput;

	@Column(name = "test_case_example_output")
	private String testCaseExampleOutput;

	@Column(name = "test_case_first_input")
	private String testCaseFirstInput;

	@Column(name = "test_case_first_output")
	private String testCaseFirstOutput;

	@Column(name = "test_case_second_input")
	private String testCaseSecondInput;

	@Column(name = "test_case_second_output")
	private String testCaseSecondOutput;
	
}
