package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the QUESTIONHISTORY database table.
 * 
 */
@Embeddable
public class QuestionHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private String userId;

	@Column(name="session_id")
	private String sessionId;


	@Column(name="exam_name")
	private String examName;	

	@Column(name="question_id")
	private int questionId;	
	
	//public QuestionPK() {
	public void QuestionHistoryPK(){
	}
	
	public String getExamName() {
		return this.examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return this.sessionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getQuestionId() {
		return this.questionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof QuestionHistoryPK)) {
			return false;
		}
		QuestionHistoryPK castOther = (QuestionHistoryPK)other;
		return 
			this.examName.equals(castOther.examName)
			&& (this.sessionId == castOther.sessionId)
			&& (this.questionId == castOther.questionId)
			&& (this.userId == castOther.userId);		
	}

	
}