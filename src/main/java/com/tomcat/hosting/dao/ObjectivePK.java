package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the OBJECTIVE database table.
 * 
 */
@Embeddable
public class ObjectivePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="exam_name")
	private String examName;

	private int id;

	public ObjectivePK() {
	}
	public String getExamName() {
		return this.examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
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
		if (!(other instanceof ObjectivePK)) {
			return false;
		}
		ObjectivePK castOther = (ObjectivePK)other;
		return 
			this.examName.equals(castOther.examName)
			&& (this.id == castOther.id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.examName.hashCode();
		hash = hash * prime + this.id;
		
		return hash;
	}
}