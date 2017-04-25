package com.tomcat.hosting.dao;

import java.io.Serializable;

public class ExamInfo implements Serializable 
{
    public String examName;
    public int questionNumber;
    public int incorrectNumber;
    public int correctNumber;
    public String sessionId;
    
    public String getExamName() { return examName; }
    public int getQuestionNumber() { return questionNumber; }
    public int getIncorrectNumber() { return incorrectNumber; }
    public int getCorrectNumber() {return correctNumber; }
    public String getSessionId() {return sessionId; }
    public void setExamName(String name) {
    	this.examName = name;
    }
    public void setQuestionNumber(int number) {
    	this.questionNumber = number;
    }
    public void setIncorrectNumber(int number) {
    	this.incorrectNumber = number;
    }
    public void setCorrectNumber(int number) {
    	this.correctNumber = number;
    }
    
    public void setSessionId(String sId) {
    	this.sessionId = sId;
    }
    
    
}