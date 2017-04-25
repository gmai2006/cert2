package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the QUESTION_HISTORY database table.
 * 
 */
@Entity
@Table(name="QUESTION_HISTORY")
@NamedQuery(name="QuestionHistory.findAll", query="SELECT q FROM QuestionHistory q")
public class QuestionHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private QuestionHistoryPK id;
		
	private int correct;

	

	/*// bi-directional many-to-one association to Explanation
		@OneToMany(mappedBy = "question")
		private List<Explanation> explanations;*/

		// /bi-directional many-to-one association to ExamHistory
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="user_id", referencedColumnName="user_id", insertable = false, updatable = false),
		@JoinColumn(name="session_id", referencedColumnName="session_id", insertable = false, updatable = false),
		@JoinColumn(name="exam_name", referencedColumnName="exam_name", insertable = false, updatable = false)
	})
	private ExamHistory examHistory;
	
		
		public QuestionHistory() {
		}

		
		public QuestionHistoryPK getId() {
			return this.id;
		}

		public void setId(QuestionHistoryPK id) {
			this.id = id;
		}
		
		public int getCorrect() {
			return this.correct;
		}			
		
		public void setCorrect(int correct) {
			this.correct = correct;
		}
		public ExamHistory getExamHistory() {
			return this.examHistory;
		}

		public void setExamHistory(ExamHistory examHistory) {
			this.examHistory = examHistory;
		}
}