package com.fmt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/***
 * This is a class that models an individual invoice.
 * 
 */

public class Invoice implements Comparable<Invoice>  {
	private String invoiceCode;
	private String storeCode;
	private Person customer;
	private Person salesPerson;
	private String date;
	private List<Item> invoiceItems;


	
	public Invoice(String invoiceCode, String storeCode, Person customer, Person salesPerson, String date) {
		this.invoiceCode = invoiceCode;
		this.storeCode = storeCode;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.date = date;
		this.invoiceItems = new ArrayList<Item>();
	}
	
	
	public Double getInvoiceTotal() {
		double invoiceTotal = 0.0;
		for(Item o : this.invoiceItems) {
			 invoiceTotal += o.getTotal();
		}
		return invoiceTotal;
	}
	public double getInvoiceSubtotal() {
		double invoiceSubtotal = 0.0;
		for(Item o : this.invoiceItems) {
			 invoiceSubtotal += o.getSubtotal();
		}
		return invoiceSubtotal;
	}
	public double getInvoiceTaxTotal() {
		double invoiceTaxTotal = 0.0;
		for(Item o : this.invoiceItems) {
			invoiceTaxTotal += o.getTaxes();
		}
		return invoiceTaxTotal;
	}
	
	public int getItemsSold(){
		return this.invoiceItems.size();
	}
	public void addItems(Item a){
		this.invoiceItems.add(a);
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public String getStoreCode() {
		return this.storeCode;
	}
	
	public Person getCustomer() {
		return customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public LocalDate getDate() {
		return LocalDate.parse(this.date);
	}
	
	public int compareTo(Invoice p2){
	    if (this.getInvoiceTotal().compareTo(p2.getInvoiceTotal()) > 0) {
	        return -1;
	    } else if (this.getInvoiceTotal().compareTo(p2.getInvoiceTotal()) == 0) {
	    	return this.getCustomer().compareTo(p2.getCustomer());
	    } else {
	        return 1;
	    }
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Invoice: #%s\nStore:   #%s\nDate:    %s\nCustomer:",invoiceCode,this.storeCode, date));
		sb.append(String.format("\n%s %s (%s : %s )", customer.getLastName(),customer.getFirstName(), customer.getPersonCode(), customer.getEmail()));
		sb.append(String.format("\n        %s\n        %s %s %s %s\n\nSales Person:",customer.getAddress().getStreet(), customer.getAddress().getCity(), customer.getAddress().getState(), customer.getAddress().getZipCode(),customer.getAddress().getCountry()));
		sb.append(String.format("\n%s %s (%s : %s )", salesPerson.getLastName(),salesPerson.getFirstName(), salesPerson.getPersonCode(), salesPerson.getEmail()));
		sb.append(String.format("\n        %s\n        %s %s %s %s\n",salesPerson.getAddress().getStreet(), salesPerson.getAddress().getCity(), salesPerson.getAddress().getState(), salesPerson.getAddress().getZipCode(),salesPerson.getAddress().getCountry()));
		sb.append(String.format("Items                                         Total\n====================                        -=-=-=-=-\n"));
		for (Item o : this.invoiceItems) {
			sb.append(String.format("%s                                             $%.2f\n", o.toReport(),o.getSubtotal()));
		}
		sb.append(String.format("                                            ==========\n"));
		sb.append(String.format("                                 Subtotal: $%.2f\n       ",this.getInvoiceSubtotal()));
		sb.append(String.format("                          Tax     :  $%.2f\n       ",this.getInvoiceTaxTotal()));
		sb.append(String.format("                      Grand Total :  $%.2f\n      ",this.getInvoiceTotal()));

		return sb.toString();

	}
	
}
