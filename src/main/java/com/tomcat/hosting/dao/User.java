package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
@NamedQueries({
@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
@NamedQuery(name="User.findByUserId", query="SELECT u FROM User u where userId= :id"),
@NamedQuery(name="User.findByEmailPassword", query="SELECT u FROM User u where emailAddress= :eaddress and password= :password"),
@NamedQuery(name="User.findAllEmail", query="SELECT emailAddress FROM User u"),
@NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u where userId= :id and password= :password")})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id", unique = true, nullable = false)
	private String userId;

	@Column(name="password_2")
	private String password;
	
	@Column(name="activation_code")
	private int activationCode;

	private String address;

	private String city;

	private String country;

	@Column(name="email_address")
	private String emailAddress;

	@Column(name="invalid_login")
	private int invalidLogin;

	private String ipaddress;

	private String phone;

	@Column(name="receive_mail")
	private byte receiveMail;

	@Column(name="signup_time")
	private Timestamp signupTime;

	private String state;

	private int status;

	@Column(name="user_role")
	private int userRole;

	private String zipcode;
	
	// bi-directional many-to-one association to UserSession
	@OneToMany(mappedBy = "user")
	private List<UserSession> userSessions = new ArrayList<UserSession>();
	
	public User() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getActivationCode() {
		return this.activationCode;
	}

	public void setActivationCode(int activationCode) {
		this.activationCode = activationCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getInvalidLogin() {
		return this.invalidLogin;
	}

	public void setInvalidLogin(int invalidLogin) {
		this.invalidLogin = invalidLogin;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte getReceiveMail() {
		return this.receiveMail;
	}

	public void setReceiveMail(byte receiveMail) {
		this.receiveMail = receiveMail;
	}

	public Timestamp getSignupTime() {
		return this.signupTime;
	}

	public void setSignupTime(Timestamp signupTime) {
		this.signupTime = signupTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserRole() {
		return this.userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public List<UserSession> getUserSessions() {
		return new ArrayList<UserSession>(this.userSessions);
	}

	/**
	   * Add new account to the person. The method keeps 
	   * relationships consistency:
	   * * this person is set as the account owner
	   */
	  public void adduserSession(UserSession session) {
	    //prevent endless loop
	    if (userSessions.contains(session))
	      return ;
	    //add new account
	    userSessions.add(session);
	    //set myself into the twitter account
	    session.setUser(this);
	  }
	  
	  /**
	   * Removes the account from the person. The method keeps 
	   * relationships consistency:
	   * * the account will no longer reference this person as its owner
	   */
	  public void removeUserSession(UserSession account) {
	    //prevent endless loop
	    if (!userSessions.contains(account))
	      return ;
	    //remove the account
	    userSessions.remove(account);
	    //remove myself from the twitter account
	    account.setUser(null);
	  }
}