package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class PracticeData implements Serializable {

    String TestId,ClassId1,CourseId,TopicId,
            ChapterId,PracticeQuestionType,PracticeQuestion,AnswerOption1,
            AnswerOption2,AnswerOption3,AnswerOption4,AnswerOption5,
            AnswerOption6,CorrectAnswer,StatusId,CreatedById,
            ModifiedById,CreationDate, ModificationDate;

    public PracticeData(String testId, String classId1, String courseId, String topicId, String chapterId, String practiceQuestionType, String practiceQuestion, String answerOption1, String answerOption2, String answerOption3, String answerOption4, String answerOption5, String answerOption6, String correctAnswer, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
        TestId = testId;
        ClassId1 = classId1;
        CourseId = courseId;
        TopicId = topicId;
        ChapterId = chapterId;
        PracticeQuestionType = practiceQuestionType;
        PracticeQuestion = practiceQuestion;
        AnswerOption1 = answerOption1;
        AnswerOption2 = answerOption2;
        AnswerOption3 = answerOption3;
        AnswerOption4 = answerOption4;
        AnswerOption5 = answerOption5;
        AnswerOption6 = answerOption6;
        CorrectAnswer = correctAnswer;
        StatusId = statusId;
        CreatedById = createdById;
        ModifiedById = modifiedById;
        CreationDate = creationDate;
        ModificationDate = modificationDate;
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getClassId1() {
        return ClassId1;
    }

    public void setClassId1(String classId1) {
        ClassId1 = classId1;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String chapterId) {
        ChapterId = chapterId;
    }

    public String getPracticeQuestionType() {
        return PracticeQuestionType;
    }

    public void setPracticeQuestionType(String practiceQuestionType) {
        PracticeQuestionType = practiceQuestionType;
    }

    public String getPracticeQuestion() {
        return PracticeQuestion;
    }

    public void setPracticeQuestion(String practiceQuestion) {
        PracticeQuestion = practiceQuestion;
    }

    public String getAnswerOption1() {
        return AnswerOption1;
    }

    public void setAnswerOption1(String answerOption1) {
        AnswerOption1 = answerOption1;
    }

    public String getAnswerOption2() {
        return AnswerOption2;
    }

    public void setAnswerOption2(String answerOption2) {
        AnswerOption2 = answerOption2;
    }

    public String getAnswerOption3() {
        return AnswerOption3;
    }

    public void setAnswerOption3(String answerOption3) {
        AnswerOption3 = answerOption3;
    }

    public String getAnswerOption4() {
        return AnswerOption4;
    }

    public void setAnswerOption4(String answerOption4) {
        AnswerOption4 = answerOption4;
    }

    public String getAnswerOption5() {
        return AnswerOption5;
    }

    public void setAnswerOption5(String answerOption5) {
        AnswerOption5 = answerOption5;
    }

    public String getAnswerOption6() {
        return AnswerOption6;
    }

    public void setAnswerOption6(String answerOption6) {
        AnswerOption6 = answerOption6;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String statusId) {
        StatusId = statusId;
    }

    public String getCreatedById() {
        return CreatedById;
    }

    public void setCreatedById(String createdById) {
        CreatedById = createdById;
    }

    public String getModifiedById() {
        return ModifiedById;
    }

    public void setModifiedById(String modifiedById) {
        ModifiedById = modifiedById;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getModificationDate() {
        return ModificationDate;
    }

    public void setModificationDate(String modificationDate) {
        ModificationDate = modificationDate;
    }
}
