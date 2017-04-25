package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ANSWER database table.
 * 
 */
@Entity
@Table(name="ANSWER")

@NamedQueries({
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a"),
@NamedQuery(name = "Answer.findbyExamNameAndQuestion", query = "SELECT e FROM Answer e where id.examName = :examName and id.questionId= :questionId")
})
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnswerPK id;

	@Column(name="is_correct")
	private byte isCorrect;

	private String text;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="exam_name", referencedColumnName="exam_name", insertable = false, updatable = false),
		@JoinColumn(name="question_id", referencedColumnName="id", insertable = false, updatable = false)
		})
	private Question question;

	public Answer() {
	}

	public AnswerPK getId() {
		return this.id;
	}

	public void setId(AnswerPK id) {
		this.id = id;
	}

	public byte getIsCorrect() {
		return this.isCorrect;
	}

	public void setIsCorrect(byte isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}