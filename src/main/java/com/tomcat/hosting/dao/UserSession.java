package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
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
 * The persistent class for the USER_SESSION database table.
 * 
 */
@Entity
@Table(name="USER_SESSION")
@NamedQueries({
@NamedQuery(name="UserSession.findAll", query="SELECT u FROM UserSession u"),
@NamedQuery(name="UserSession.findByUserId", query="SELECT u FROM UserSession u where id.userId= :userId"),
})
public class UserSession implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private UserSessionPK id;
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Column(name="login_time")
	private Timestamp loginTime;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName="user_id", insertable = false, updatable = false)
	private User user;

	// bi-directional many-to-one association to ExamHistory
	@OneToMany(mappedBy = "userSession")
	private List<ExamHistory> examHistories;
			
	public UserSession() {
	}
	public UserSessionPK getId() {
		return this.id;
	}

	public void setId(UserSessionPK id) {
		this.id = id;
	}
	
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Timestamp getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public List<ExamHistory> getExamHistories() {
		return new ArrayList<ExamHistory>(this.examHistories);
	}

	/**
	   * Add new account to the person. The method keeps 
	   * relationships consistency:
	   * * this person is set as the account owner
	   */
	  public void addExamHistories(ExamHistory session) {
	    //prevent endless loop
	    if (examHistories.contains(session))
	      return ;
	    //add new account
	    examHistories.add(session);
	    //set myself into the twitter account
	    session.setUserSession(this);
	  }
	  
	  /**
	   * Removes the account from the person. The method keeps 
	   * relationships consistency:
	   * * the account will no longer reference this person as its owner
	   */
	  public void removeExamHistories(ExamHistory account) {
	    //prevent endless loop
	    if (!examHistories.contains(account))
	      return ;
	    //remove the account
	    examHistories.remove(account);
	    //remove myself from the twitter account
	    account.setUserSession(null);
	  }
	  
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}