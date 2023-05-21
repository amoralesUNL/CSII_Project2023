package com.fmt;

import java.util.List;
import java.util.ArrayList;
/***
 * Class that models a person
 * 
 * */

public class Person implements Comparable<Person>{
	private String personCode;
	private String lastName;
	private String firstName;
	private Address address;
	private List<String> email;
	


	public Person(String personCode, String lastName, String firstName,Address address) {
	    this.personCode = personCode;
	    this.lastName = lastName;
	    this.firstName = firstName;
	    this.address = address;
		this.email = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s ,%s , %s, %s", 
				this.personCode, 
				this.lastName,
				this.firstName,
				this.address, 
				this.getStringEmail());
	}

	
	public String getPersonCode() {
		return this.personCode;
	}


	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}

	public Address getAddress() {
		return this.address;
	}
	
	public List<String> getEmail() {
		return this.email;
	}
	
	public void addEmail(String address) {
		this.email.add(address);
	}
	public String getStringEmail() {
		return email.toString().replace("[", "").replace("]", "");
	}
	
	public int compareTo(Person s) {
		  if (this.lastName.compareTo(s.lastName) < 0) {
	            return -1;
	        } else if (this.lastName.compareTo(s.lastName) == 0) {
	        	if(this.firstName.compareTo(s.firstName) < 0) {
	        		return -1;
	        	}else if (this.firstName.compareTo(s.firstName) == 0) {
	        		return 0;
	        	}else {
	        		return 1;
	        	}
	        } else {
	            return 1;
	        }
	    }
}
