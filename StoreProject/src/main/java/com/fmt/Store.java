package com.fmt;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that models a store
 * */


public class Store {
	private String storeCode;
	private Person manager;
	private Address address;

	private List<Invoice> invoices;
	

	public Store(String storeCode, Person manager, Address address) {
		this.storeCode = storeCode;
		this.manager = manager;
		this.address = address;
		this.invoices = new ArrayList<>();
	}
	
		
	public String getStoreCode() {
		return this.storeCode;
	}

	public Person getManager() {
		return this.manager;
	}

	public Address getAddress() {
		return this.address;
	}
	
	public void addInvoice(Invoice e) {
		this.invoices.add(e);
	}

	public int getSalesNum() {
		return this.invoices.size();
	}
	public double getSalesTotal() {
		double salesTotal = 0.0;
		for(Invoice a : this.invoices) {
			salesTotal += a.getInvoiceTotal();
		}
		return salesTotal;
	}
	
	public List<Invoice> getInvoice() {
		return this.invoices;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s", 
				this.storeCode, 
				this.manager, 
				this.address);

	}

}
