package com.tomcat.hosting.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the VENDOR database table.
 * 
 */
@Entity
@Table(name = "VENDOR")
@NamedQueries({
		@NamedQuery(name = "Vendor.findAll", query = "SELECT v FROM Vendor v order by rank, vendorName"),
		@NamedQuery(name = "Vendor.findVendorByRank", query = "SELECT v FROM Vendor v where rank= :rank order by vendorName"),
		@NamedQuery(name = "Vendor.findVendorByName", query = "SELECT v FROM Vendor v where vendorName= :name"),
		@NamedQuery(name = "Vendor.findVendorByPageName", query = "SELECT v FROM Vendor v where pageName= :name")
})

public class Vendor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id")
	private int vendorId;

	private String info;

	@Column(name = "page_name")
	private String pageName;

	private String resources;

	@Column(name = "vendor_desc")
	private String vendorDesc;

	@Column(name = "vendor_name")
	private String vendorName;

	// bi-directional many-to-one association to Certification
	@OneToMany(mappedBy = "vendor")
	private List<Certification> certifications;

	private int rank;
	public Vendor() {
	}

	public int getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getResources() {
		return this.resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getVendorDesc() {
		return this.vendorDesc;
	}

	public void setVendorDesc(String vendorDesc) {
		this.vendorDesc = vendorDesc;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public List<Certification> getCertifications() {
		return this.certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	public Certification addCertification(Certification certification) {
		getCertifications().add(certification);
		certification.setVendor(this);

		return certification;
	}

	public Certification removeCertification(Certification certification) {
		getCertifications().remove(certification);
		certification.setVendor(null);

		return certification;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}