package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CERTIFICATION database table.
 * 
 */
@Entity
@Table(name = "CERTIFICATION")
@NamedQueries({
		@NamedQuery(name = "Certification.findAll", query = "SELECT c FROM Certification c"),
		@NamedQuery(name = "Certification.findCertificationByName", query = "SELECT t FROM Certification t where name= :name"),
		@NamedQuery(name = "Certification.findCertificationByPageName", query = "SELECT t FROM Certification t where name= :name") })
public class Certification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private byte available;

	private String descr;

	private String info;

	private String name;

	@Column(name = "page_name")
	private String pageName;

	private String prerequisite;

	private int rank;

	private String recertification;

	private String resources;

	@Column(name = "vendor_id", insertable = false, updatable = false)
	private int vendorId;

	// bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;

	// bi-directional many-to-many association to Course
	@ManyToMany
	@JoinTable(name = "CERTIFICATION_EXAM", joinColumns = { @JoinColumn(name = "cert_id") }, inverseJoinColumns = { @JoinColumn(name = "course_name") })
	private List<Exam> exams;

	public Certification() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAvailable() {
		return this.available;
	}

	public void setAvailable(byte available) {
		this.available = available;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPrerequisite() {
		return this.prerequisite;
	}

	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getRecertification() {
		return this.recertification;
	}

	public void setRecertification(String recertification) {
		this.recertification = recertification;
	}

	public String getResources() {
		return this.resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public int getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

}