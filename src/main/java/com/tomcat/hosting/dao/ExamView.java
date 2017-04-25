package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the EXAM_VIEW database table.
 * 
 */
@Entity
@Table(name = "EXAM_VIEW")
@NamedQueries({
		@NamedQuery(name = "ExamView.findAll", query = "SELECT c FROM ExamView c"),
		@NamedQuery(name = "ExamView.findAllByVendorId", query = "SELECT c FROM ExamView c where vendorId= :id"),
		@NamedQuery(name = "ExamView.findAllByVendorRankByRank", query = "SELECT c FROM ExamView c where vendorRank= :r and rank= :rank order by vendorName"),
		@NamedQuery(name = "ExamView.findExamByPageName", query = "SELECT c FROM ExamView c where pageName= :name"),		
		@NamedQuery(name = "ExamView.findExamByName", query = "SELECT c FROM ExamView c where examName= :name") })

public class ExamView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exam_name")
	private String examName;

	@Column(name = "exam_desc")
	private String examDesc;

	@Column(name = "exam_title")
	private String examTitle;

	@Column(name = "exam_time")
	private int examTime;

	private byte explanation;

	@Column(name = "file_location")
	private String fileLocation;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "page_name")
	private String pageName;

	@Column(name = "passing_score")
	private int passingScore;

	private String prerequisite;

	@Column(name = "question_format")
	private String questionFormat;

	private int questions;

	private String resources;
	
	private int rank;
	
	@Column(name = "vendor_rank")
	private int vendorRank;
	
	@Column(name = "exam_key")
	private String examKey;

	@Temporal(TemporalType.DATE)
	@Column(name = "retired_date")
	private Date retiredDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "update_date")
	private Date updateDate;

	private int valid;

	@Column(name = "vendor_id")
	private int vendorId;

	@Column(name = "vendor_name")
	private String vendorName;

	private String ver;

	
	public ExamView() {
	}

	public int getExamTime() {
		return this.examTime;
	}

	public void setExamTime(int examTime) {
		this.examTime = examTime;
	}

	public byte getExplanation() {
		return this.explanation;
	}

	public void setExplanation(byte explanation) {
		this.explanation = explanation;
	}

	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public int getPassingScore() {
		return this.passingScore;
	}

	public void setPassingScore(int passingScore) {
		this.passingScore = passingScore;
	}

	public String getPrerequisite() {
		return this.prerequisite;
	}

	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}

	public String getQuestionFormat() {
		return this.questionFormat;
	}

	public void setQuestionFormat(String questionFormat) {
		this.questionFormat = questionFormat;
	}

	public int getQuestions() {
		return this.questions;
	}

	public void setQuestions(int questions) {
		this.questions = questions;
	}

	public String getResources() {
		return this.resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public Date getRetiredDate() {
		return this.retiredDate;
	}

	public void setRetiredDate(Date retiredDate) {
		this.retiredDate = retiredDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getValid() {
		return this.valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public int getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVer() {
		return this.ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	
	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamDesc() {
		return examDesc;
	}

	public void setExamDesc(String examDesc) {
		this.examDesc = examDesc;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getVendorRank() {
		return vendorRank;
	}

	public void setVendorRank(int vendorRank) {
		this.vendorRank = vendorRank;
	}

	public String getExamKey() {
		return examKey;
	}

	public void setExamKey(String examKey) {
		this.examKey = examKey;
	}

}