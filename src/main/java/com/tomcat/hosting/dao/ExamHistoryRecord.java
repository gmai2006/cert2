package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the EXAM_HISTORY_RECORD database table.
 * 
 */
@Entity
@Table(name="EXAM_HISTORY_RECORD")
@NamedQuery(name="ExamHistoryRecord.findAll", query="SELECT e FROM ExamHistoryRecord e")
public class ExamHistoryRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExamHistoryRecordPK id;

	private String answers;

	private String correct;

	@Column(name="exam_name")
	private String examName;

	private byte status;

	@Column(name="user_id")
	private String userId;

	public ExamHistoryRecord() {
	}

	public ExamHistoryRecordPK getId() {
		return this.id;
	}

	public void setId(ExamHistoryRecordPK id) {
		this.id = id;
	}

	public String getAnswers() {
		return this.answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getCorrect() {
		return this.correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}