package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the EXAM_CREATION database table.
 * 
 */
@Entity
@Table(name="EXAM_CREATION")
@NamedQuery(name="ExamCreation.findAll", query="SELECT e FROM ExamCreation e")
public class ExamCreation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExamCreationPK id;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="ip_address")
	private String ipAddress;

	public ExamCreation() {
	}

	public ExamCreationPK getId() {
		return this.id;
	}

	public void setId(ExamCreationPK id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}