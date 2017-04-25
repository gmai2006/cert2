package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EXAM_CREATION database table.
 * 
 */
@Embeddable
public class ExamCreationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private String userId;

	@Column(name="exam_name")
	private String examName;

	public ExamCreationPK() {
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getExamName() {
		return this.examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ExamCreationPK)) {
			return false;
		}
		ExamCreationPK castOther = (ExamCreationPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.examName.equals(castOther.examName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.examName.hashCode();
		
		return hash;
	}
}