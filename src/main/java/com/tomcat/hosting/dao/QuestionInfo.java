package com.tomcat.hosting.dao;

import java.io.Serializable;

public class QuestionInfo implements Serializable 
{
    public String examName;
    public int questionId;
    public String text;
    
    
    public String getExamName() { return examName; }
    public int getQuestionId() { return questionId; }
    public String getText() { return text; }    
    
    public void setExamName(String name) {
    	this.examName = name;
    }
    public void setQuestionId(int id) {
    	this.questionId = id;
    }
    public void setText(String text) {
    	this.text = text;
    }

    
    
    
    
}