package com.fmt;
/**
 * Class that models equipment
 * 
 * */

public  class Equipment  extends Item {
	private String model;
	public Equipment(String code, String type, String name, String model) {
		super(code, type, name);
		this.model = model;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s ,%s",
				this.getCode(),
				this.getType(),
				this.getName(),
				this.model);
	}

	public double getTaxes() {
		return 0.0;
	}
	
	public double  getSubtotal() {
		return 0.0;
	}
	
	public double getTotal() {
		return 0.0;
	}

	public String getModel() {
		return this.model;
	}
	
	public String getInvoiceCode(){
		return null;
	}
	
	public String toReport() {
		return null;
	}
}
