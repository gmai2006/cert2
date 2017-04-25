package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EXHIBIT database table.
 * 
 */
@Embeddable
public class ExhibitPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="exam_name")
	private String examName;

	@Column(name="question_id")
	private int questionId;

	private int id;

	public ExhibitPK() {
	}
	public String getExamName() {
		return this.examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getQuestionId() {
		return this.questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ExhibitPK)) {
			return false;
		}
		ExhibitPK castOther = (ExhibitPK)other;
		return 
			this.examName.equals(castOther.examName)
			&& this.questionId == castOther.questionId
			&& (this.id == castOther.id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.examName.hashCode();
		hash = hash * prime + this.questionId;
		hash = hash * prime + this.id;
		
		return hash;
	}
}