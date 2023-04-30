package com.company.codecompiler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.codecompiler.entity.Assesment;

public interface AssesmentRepo extends JpaRepository<Assesment, Long>{
    @Query(value = "select * from assesment where question_title = :question_title", nativeQuery = true)
    Assesment findByQuestionTitle(@Param("question_title") String questionTitle);
}
