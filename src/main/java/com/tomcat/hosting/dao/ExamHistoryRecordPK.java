package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EXAM_HISTORY_RECORD database table.
 * 
 */
@Embeddable
public class ExamHistoryRecordPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="session_id")
	private int sessionId;

	@Column(name="question_id")
	private int questionId;

	public ExamHistoryRecordPK() {
	}
	public int getSessionId() {
		return this.sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public int getQuestionId() {
		return this.questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ExamHistoryRecordPK)) {
			return false;
		}
		ExamHistoryRecordPK castOther = (ExamHistoryRecordPK)other;
		return 
			(this.sessionId == castOther.sessionId)
			&& (this.questionId == castOther.questionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sessionId;
		hash = hash * prime + this.questionId;
		
		return hash;
	}
}