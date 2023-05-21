package com.fmt;

/**
 * Superclass that models various items
 * 
 * */

public abstract class Item  {
	private String code;
	private String type;
	private String name;
	
	
	public Item(String code, String type, String name) {
		this.code = code;
		this.type = type;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * Calculates and returns the taxes of an Item
	 * @return double taxes 
	 */
	
	public abstract double getTaxes();
	
	/**
	 * Calculates and returns the Subtotal of an Item
	 * @return int double
	 */
	
	
	public abstract double getSubtotal();
	
	/**
	 * Calculates and returns the Total of an Item
	 * @return
	 */
	
	public abstract double getTotal();
	
	/**
	 * Returns attributes of an Item in the form of a string
	 * @return string Item
	 */
	
	public abstract String toString();
	
	/**
	 * Using the values of the item returns formatted report
	 * of the item for use in an invoice
	 * @return string report
	 */
	
	public abstract String toReport();
	
	
	
	
}
