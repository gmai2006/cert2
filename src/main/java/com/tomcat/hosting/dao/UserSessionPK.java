package com.tomcat.hosting.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the USERSESSION database table.
 * 
 */
@Embeddable
public class UserSessionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Column(name="session_id")
	private String sessionId;
	
	@Column(name="user_id")
	private String userId;

	public UserSessionPK() {
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserSessionPK)) {
			return false;
		}
		UserSessionPK castOther = (UserSessionPK)other;
		return 
			this.userId.equals(castOther.userId)			
			&& (this.sessionId == castOther.sessionId);
	}
}