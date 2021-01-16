package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class TestSubjectDataModel implements Serializable {
    String testId;
    String classId;
    String courseId;
    String topicId;
    String chapterId;
    String testQuestionType;
    String testQuestions;
    String answerOption1;
    String answerOption2;
    String answerOption3;
    String answerOption4;
    String answerOption5;
    String answerOption6;
    String correctAnswer;
    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;
    String selectedAnswer;
    int correct;
    int worng;
    int not_attempt;
    String user_ans;
    String testDetailId;
    String testMasterId;

    public TestSubjectDataModel(String testId, String classId, String courseId, String topicId, String chapterId, String testQuestionType, String testQuestions, String answerOption1, String answerOption2, String answerOption3, String answerOption4, String answerOption5, String answerOption6, String correctAnswer, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate, int correct, int wrong, int not_attempt, String user_ans) {
        this.testId=testId;
        this.classId=classId;
        this.courseId=courseId;
        this.topicId=topicId;
        this.chapterId=chapterId;
        this.testQuestionType=testQuestionType;
        this.testQuestions=testQuestions;
        this.answerOption1=answerOption1;
        this.answerOption2=answerOption2;
        this.answerOption3=answerOption3;
        this.answerOption4=answerOption4;
        this.answerOption5=answerOption5;
        this.answerOption6=answerOption6;
        this.correctAnswer=correctAnswer;
        this.statusId=statusId;
        this.createdById=createdById;
        this.modifiedById=modifiedById;
        this.creationDate=creationDate;
        this.modificationDate=modificationDate;
         this.correct=correct;
         this.worng=wrong;
         this.not_attempt=not_attempt;
         this.user_ans=user_ans;
    }

    public TestSubjectDataModel(String testDetailId, String testMasterId, String testQuestionType, String testQuestions, String answerOption1, String answerOption2, String answerOption3, String answerOption4, String answerOption5, String answerOption6, String correctAnswer, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate, int correct, int wrong, int not_attempt, String user_ans) {
        this.testDetailId=testDetailId;
        this.testMasterId=testMasterId;
//        this.courseId=courseId;
//        this.topicId=topicId;
//        this.chapterId=chapterId;
        this.testQuestionType=testQuestionType;
        this.testQuestions=testQuestions;
        this.answerOption1=answerOption1;
        this.answerOption2=answerOption2;
        this.answerOption3=answerOption3;
        this.answerOption4=answerOption4;
        this.answerOption5=answerOption5;
        this.answerOption6=answerOption6;
        this.correctAnswer=correctAnswer;
        this.statusId=statusId;
        this.createdById=createdById;
        this.modifiedById=modifiedById;
        this.creationDate=creationDate;
        this.modificationDate=modificationDate;
        this.correct=correct;
        this.worng=wrong;
        this.not_attempt=not_attempt;
        this.user_ans=user_ans;
    }

    public String getTestDetailId() {
        return testDetailId;
    }

    public void setTestDetailId(String testDetailId) {
        this.testDetailId = testDetailId;
    }

    public String getTestMasterId() {
        return testMasterId;
    }

    public void setTestMasterId(String testMasterId) {
        this.testMasterId = testMasterId;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWorng() {
        return worng;
    }

    public void setWorng(int worng) {
        this.worng = worng;
    }

    public int getNot_attempt() {
        return not_attempt;
    }

    public void setNot_attempt(int not_attempt) {
        this.not_attempt = not_attempt;
    }

    public String getUser_ans() {
        return user_ans;
    }

    public void setUser_ans(String user_ans) {
        this.user_ans = user_ans;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }


    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTestQuestionType() {
        return testQuestionType;
    }

    public void setTestQuestionType(String testQuestionType) {
        this.testQuestionType = testQuestionType;
    }

    public String getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(String testQuestions) {
        this.testQuestions = testQuestions;
    }

    public String getAnswerOption1() {
        return answerOption1;
    }

    public void setAnswerOption1(String answerOption1) {
        this.answerOption1 = answerOption1;
    }

    public String getAnswerOption2() {
        return answerOption2;
    }

    public void setAnswerOption2(String answerOption2) {
        this.answerOption2 = answerOption2;
    }

    public String getAnswerOption3() {
        return answerOption3;
    }

    public void setAnswerOption3(String answerOption3) {
        this.answerOption3 = answerOption3;
    }

    public String getAnswerOption4() {
        return answerOption4;
    }

    public void setAnswerOption4(String answerOption4) {
        this.answerOption4 = answerOption4;
    }

    public String getAnswerOption5() {
        return answerOption5;
    }

    public void setAnswerOption5(String answerOption5) {
        this.answerOption5 = answerOption5;
    }

    public String getAnswerOption6() {
        return answerOption6;
    }

    public void setAnswerOption6(String answerOption6) {
        this.answerOption6 = answerOption6;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}
