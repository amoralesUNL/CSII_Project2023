package com.fmt;

/**
 * Subclass of items that models services
 * */

public class Service extends Item {
	private double hourlyRate;
	private Double hoursBilled;

	
	public Service(String code, String type, String name, double hourlyRate, Double hoursBilled) {
		super(code, type, name);
		this.hourlyRate = hourlyRate;
		this.hoursBilled = hoursBilled;
	}

	public Service(String code, String type, String name,double hourlyRate) {
		super(code,type,name);
		this.hourlyRate = hourlyRate;
		this.hoursBilled = null;
	}
	
	public Service(Service service, Double hoursBilled) {
		this(service.getCode(), service.getType(),service.getName(), service.getHourlyRate(),hoursBilled);
	}	
	
	public double getHourlyRate() {
		return hourlyRate;
	}

	public double getHoursBilled() {
		return hoursBilled;
	}
	
	public double getTaxes() {
		double taxes = this.getSubtotal() * .0345;
		taxes *= 100;
		taxes = Math.round(taxes);
		taxes = taxes/100;
		return taxes;
	}
	public double getSubtotal() {
		double subtotal = this.hourlyRate * this.hoursBilled;
		subtotal *= 100;
		subtotal = Math.round(subtotal);
		subtotal = subtotal / 100;
		return subtotal;
	}
	
	public double getTotal(){
		double total = this.getSubtotal() + this.getTaxes();
		return total;
	}
	
	public void setHoursBilled(Double hoursBilled) {
		this.hoursBilled = hoursBilled;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s ,%s",
				this.getCode(),
				this.getType(),
				this.getName(),
				this.hourlyRate);
	}
	
	public String toReport() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s (Service) %s\n",this.getCode(), this.getName()));
		sb.append(String.format("         %s @  %s/hr\n", this.hoursBilled, this.hourlyRate));
		return sb.toString();
	}
	
}
