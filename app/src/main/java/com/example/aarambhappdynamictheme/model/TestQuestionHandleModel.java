package com.example.aarambhappdynamictheme.model;

public class TestQuestionHandleModel {
   private String question;
   private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private  String answer2;
    private  int id;
    private String type;
    public TestQuestionHandleModel() {
        id = 0;
        question = "";
        option1 = "";
        option2 = "";
        option3 = "";
        option4 = "";
        answer = "";
        answer2="";
    }

    public TestQuestionHandleModel(String question, String option1, String option2, String option3, String option4, String answer,String type) {
    this.question=question;
    this.option1=option1;
    this.option2=option2;
    this.option3=option3;
    this.option4=option4;
    this.answer=answer;
    this.type=type;
    }

    public TestQuestionHandleModel(String question, String option1, String option2, String answer,String type) {
        this.question=question;
        this.option1=option1;
        this.option2=option2;
        this.answer=answer;
        this.type=type;
    }

    public TestQuestionHandleModel(String question, String option1, String option2, String option3, String option4, String answer1, String answer2,String type) {
   this.question=question;
   this.option1=option1;
   this.option2=option2;
   this.option3=option3;
   this.option4=option4;
   this.answer=answer1;
   this.answer2=answer2;
   this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}