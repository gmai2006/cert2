package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EXAMHISTORY database table.
 * 
 */
@Embeddable
public class ExamHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private String userId;

	@Column(name="session_id")
	private String sessionId;


	@Column(name="exam_name")
	private String examName;	
	
	public void ExamHistoryPK(){
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
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ExamHistoryPK)) {
			return false;
		}
		ExamHistoryPK castOther = (ExamHistoryPK)other;
		return 
			this.examName.equals(castOther.examName)
			&& (this.sessionId == castOther.sessionId)
			&& (this.userId == castOther.userId);
		
	}

	
}