package com.rest.recruit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    private int questionId;
    private String questionContent;
    private int questionLimit;//check!!!

    public Question(int questionId, String questionContent) {
        this.questionId = questionId;
        this.questionContent = questionContent;
    }
}
