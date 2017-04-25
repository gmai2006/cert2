package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the OBJECTIVE database table.
 * 
 */
@Entity
@Table(name="OBJECTIVE")
@NamedQueries({ 
	@NamedQuery(name="Objective.findAll", query="SELECT o FROM Objective o"),
	@NamedQuery(name = "Objective.findbyExamName", query = "SELECT o FROM Objective o where id.examName = :examName")
})
public class Objective implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ObjectivePK id;

	@Column(name="order_number")
	private int orderNumber;

	private String text;

	private String title;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="exam_name", insertable = false, updatable = false)
	private Exam exam;

	public Objective() {
	}

	public ObjectivePK getId() {
		return this.id;
	}

	public void setId(ObjectivePK id) {
		this.id = id;
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}