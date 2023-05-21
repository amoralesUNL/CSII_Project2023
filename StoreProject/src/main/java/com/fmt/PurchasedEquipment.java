package com.fmt;

/***
 * 
 * Class that models Purchased Equipment.
 * This class is a subclass of purchased Equipment.
 * 
 *
 */

public class PurchasedEquipment extends Equipment {
	private String model;
	private Double price;
	
	public PurchasedEquipment(String code, String type, String name, String model, Double price) {
		super(code, type, name,model);
		this.model = model;
		this.price = price;

	} 
	
	public double getTaxes() {
		return 0.0;
	}
	
	public double  getSubtotal() {
		return this.price;
	}
	
	public double getTotal() {
		return this.price;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s ,%s",
				this.getCode(),
				this.getType(),
				this.getName(),
				this.model);
	}
	
	public String toReport() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s (Purchase) %s %s\n",this.getCode(), this.getName(),this.model));
		return sb.toString();
	}

}
