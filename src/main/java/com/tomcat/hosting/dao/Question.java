package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the QUESTION database table.
 * 
 */
@Entity
@Table(name = "QUESTION")
@NamedQueries({
		@NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
		@NamedQuery(name = "Question.findQuestionsByExamName", query = "SELECT q FROM Question q where id.examName= :examName"),
		@NamedQuery(name = "Question.findQuestionsByExamNameAndQuestionId", 
		query = "SELECT q FROM Question q where id.examName= :examName and id.id= :questionId") })
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private QuestionPK id;

	private byte choice;

	private String exp;

	@Column(name = "objective_id")
	private Integer objectiveId;

	private String s;

	private String t;

	private int topic;

	// bi-directional many-to-one association to Answer
	@OneToMany(mappedBy = "question")
	private List<Answer> answers;

	// bi-directional many-to-one association to Explanation
	@OneToMany(mappedBy = "question")
	private List<Explanation> explanations;

	// /bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name = "exam_name", insertable = false, updatable = false)
	private Exam exam;

	// bi-directional many-to-one association to Exhibit
	@OneToMany(mappedBy = "question")
	private List<Exhibit> exhibits;

	public Question() {
	}

	public QuestionPK getId() {
		return this.id;
	}

	public void setId(QuestionPK id) {
		this.id = id;
	}

	public byte getChoice() {
		return this.choice;
	}

	public void setChoice(byte choice) {
		this.choice = choice;
	}

	public String getExp() {
		return this.exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public Integer getObjectiveId() {
		return this.objectiveId;
	}

	public void setObjectiveId(Integer objectiveId) {
		this.objectiveId = objectiveId;
	}

	public String getS() {
		return this.s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getT() {
		return this.t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public int getTopic() {
		return this.topic;
	}

	public void setTopic(int topic) {
		this.topic = topic;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Answer addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setQuestion(this);

		return answer;
	}

	public Answer removeAnswer(Answer answer) {
		getAnswers().remove(answer);
		answer.setQuestion(null);

		return answer;
	}

	public List<Explanation> getExplanations() {
		return this.explanations;
	}

	public void setExplanations(List<Explanation> explanations) {
		this.explanations = explanations;
	}

	public Explanation addExplanation(Explanation explanation) {
		getExplanations().add(explanation);
		explanation.setQuestion(this);

		return explanation;
	}

	public Explanation removeExplanation(Explanation explanation) {
		getExplanations().remove(explanation);
		explanation.setQuestion(null);

		return explanation;
	}

	public List<Exhibit> getExhibits() {
		return this.exhibits;
	}

	public void setExhibits(List<Exhibit> exhibits) {
		this.exhibits = exhibits;
	}

	public Exhibit addExhibit(Exhibit exhibit) {
		getExhibits().add(exhibit);
		exhibit.setQuestion(this);

		return exhibit;
	}

	public Exhibit removeExhibit(Exhibit exhibit) {
		getExhibits().remove(exhibit);
		exhibit.setQuestion(null);

		return exhibit;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}