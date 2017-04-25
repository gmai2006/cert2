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
 * The persistent class for the EXHIBIT database table.
 * 
 */
@Entity
@Table(name = "EXHIBIT")
@NamedQueries({ 
	@NamedQuery(name = "Exhibit.findAll", query = "SELECT e FROM Exhibit e"),
	@NamedQuery(name = "Exhibit.findbyExamName", query = "SELECT e FROM Exhibit e where id.examName = :examName"),
	@NamedQuery(name = "Exhibit.findbyExamNameAndQuestion", query = "SELECT e FROM Exhibit e where id.examName = :examName and id.questionId= :questionId")
})
public class Exhibit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExhibitPK id;

	private int a;

	private String dnd;

	@Column(name = "exhibit_type")
	private int exhibitType;

	private String ip;

	@Column(name = "order_number")
	private int orderNumber;

	private int ss;

	private String start;

	private String stop;

	private String text;

	private String text2;

	private String text3;

	private int x1;

	private int x2;

	private int y1;

	private int y2;

	// bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "exam_name", referencedColumnName = "exam_name", insertable = false, updatable = false),
			@JoinColumn(name = "question_id", referencedColumnName = "id", insertable = false, updatable = false) })
	private Question question;

	public Exhibit() {
	}

	public ExhibitPK getId() {
		return this.id;
	}

	public void setId(ExhibitPK id) {
		this.id = id;
	}

	public int getA() {
		return this.a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getDnd() {
		return this.dnd;
	}

	public void setDnd(String dnd) {
		this.dnd = dnd;
	}

	public int getExhibitType() {
		return this.exhibitType;
	}

	public void setExhibitType(int exhibitType) {
		this.exhibitType = exhibitType;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getSs() {
		return this.ss;
	}

	public void setSs(int ss) {
		this.ss = ss;
	}

	public String getStart() {
		return this.start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return this.stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
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

	public int getX1() {
		return this.x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return this.x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return this.y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return this.y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}