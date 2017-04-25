package com.tomcat.hosting.service;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
	   public static void main(String[] args) {
		   System.out.println("Begin.....................");
	               EntityManagerFactory emf = Persistence.createEntityManagerFactory("examJPA");
	               System.out.println("aaaa");
	    }
	}
