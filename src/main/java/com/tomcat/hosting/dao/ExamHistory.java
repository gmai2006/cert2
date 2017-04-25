package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the EXAM_HISTORY database table.
 * 
 */
@Entity
@Table(name="EXAM_HISTORY")
@NamedQueries({
@NamedQuery(name="ExamHistory.findAll", query="SELECT e FROM ExamHistory e"),
@NamedQuery(name="ExamHistory.findExamHistoryByUserId", query="SELECT e FROM ExamHistory e where id.userId= :userId"),
@NamedQuery(name="ExamHistory.findExamHistoryId", query="SELECT e FROM ExamHistory e "
		+ "where id.userId= :userId and id.examName= :examName and id.sessionId= :sessionId")
})
public class ExamHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExamHistoryPK id;
	

	@Column(name="create_time")
	private Timestamp createTime;

	// /bi-directional many-to-one association to UserSession
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="user_id", referencedColumnName="user_id", insertable = false, updatable = false),
		@JoinColumn(name="session_id", referencedColumnName="session_id", insertable = false, updatable = false)
	})
	private UserSession userSession;

	// bi-directional many-to-one association to QuestionHistory
		@OneToMany(mappedBy = "examHistory")
		private List<QuestionHistory> questionHistories;
		
		public ExamHistory() {
		}
		
		public ExamHistoryPK getId() {
			return this.id;
		}

		public void setId(ExamHistoryPK id) {
			this.id = id;
		}
		
		public Timestamp getCreateTime() {
			return this.createTime;
		}			
		
		public void setCreateTime(Timestamp createTime) {
			this.createTime = createTime;
		}
		public List<QuestionHistory> getQuestionHistories() {
			return this.questionHistories;
		}

		public void setQuestionHistories(List<QuestionHistory> questionHistories) {
			this.questionHistories = questionHistories;
		}	
		
		public UserSession getUSerSession() {
			return this.userSession;
		}

		public void setUserSession(UserSession userSession) {
			this.userSession = userSession;
		}
}