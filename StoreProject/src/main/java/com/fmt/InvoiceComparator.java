package com.fmt;

import java.util.Comparator;

/**
 * Class that contains comparators for the Invoice Object
 */

public class InvoiceComparator {
	/**
	 * Comparator that compares an Invoice by the customer last name.
	 * If the last names are the same it will compare the first name of the customer.
	 * If the names are identical it will compare the value of the Invoice.
	 * @return Comparator<Invoice>
	 */
	public static Comparator<Invoice> compareByName(){
		return new Comparator<Invoice>(){
			
		    public int compare(Invoice p1, Invoice p2) {
		        if (p1.getCustomer().compareTo(p2.getCustomer()) < 0) {
		            return -1;
		        } else if (p1.getCustomer().compareTo(p2.getCustomer()) == 0) {
		        	return p1.compareTo(p2);
		        } else {
		            return 1;
		        	}
		    }
		};
	}
	/**
	 * Comparator that compares an Invoice by the total value of the Invoice.
	 * If the values are equal then it will compare the customer names
	 * @return Comparator<Invoice>
	 */
	public static Comparator<Invoice> compareByValue(){
		return new Comparator<Invoice>(){
			public int compare(Invoice p1, Invoice p2){
			   return p1.compareTo(p2);
			}
		};
	}
	/**
	 * Comparator that compares an Invoice by the store code.
	 * If the stores are the same then it will compare the customer names
	 * @return Comparator<Invoice>
	 */
	public static Comparator<Invoice> compareByStore(){
		return new Comparator<Invoice>(){	
			public int compare(Invoice p1, Invoice p2) {
		        if (p1.getStoreCode().compareTo(p2.getStoreCode()) < 0) {
		            return -1;
		        } else if (p1.getStoreCode().compareTo(p2.getStoreCode()) == 0) {
		            return p1.getCustomer().compareTo(p2.getCustomer());
		        } else {
		            return 1;
		        }
		    }
		};
	}
}	
	