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
 * The persistent class for the EXPLANATION database table.
 * 
 */
@Entity
@Table(name="EXPLANATION")
@NamedQueries({
@NamedQuery(name="Explanation.findAll", query="SELECT e FROM Explanation e"),
@NamedQuery(name = "Explanation.findbyExamNameAndQuestion", query = "SELECT e FROM Explanation e where id.examName = :examName and id.questionId= :questionId")
})
public class Explanation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExplanationPK id;

	@Column(name="exp_type")
	private int expType;

	private String image;

	private String text;

	private String text2;

	private String text3;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="exam_name", referencedColumnName="exam_name", insertable = false, updatable = false),
		@JoinColumn(name="question_id", referencedColumnName="id", insertable = false, updatable = false)
		})
	private Question question;

	public Explanation() {
	}

	public ExplanationPK getId() {
		return this.id;
	}

	public void setId(ExplanationPK id) {
		this.id = id;
	}

	public int getExpType() {
		return this.expType;
	}

	public void setExpType(int expType) {
		this.expType = expType;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText2() {
		return this.text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return this.text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}