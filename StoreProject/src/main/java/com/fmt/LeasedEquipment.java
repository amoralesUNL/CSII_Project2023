package com.fmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 
 * Class that models LeasedEquipment
 * This is a subclass of Equipment.
 *
 */

public class LeasedEquipment extends Equipment{

	private double fee;
	private String model;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public LeasedEquipment(String code, String type, String name, String model, double fee, LocalDate startDate,
			LocalDate endDate) {
		super(code, type, name,model);
		this.model = model;
		this.fee = fee;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public double getFee() {
		return fee;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	public double getTimeLeased() {
	    long days = ChronoUnit.DAYS.between(startDate, endDate); 
	    days += 1;
	    double daysBetween = (double)days;
		return daysBetween;
	}
	
	public double getTaxes() {
		double taxes = 0.0;
		if ((this.getSubtotal() >= 10000) && (this.getSubtotal() < 100000)) {
			taxes = 500;
		}else if(this.getSubtotal() >= 100000 ) 
			taxes = 1500;
		return taxes;
	}
	
	public double  getSubtotal() {
		double subTotal = 0.0;
		subTotal = (this.getTimeLeased() /30) * this.getFee();
		subTotal *= 100;
		subTotal = Math.round(subTotal);
		subTotal /= 100;
		return subTotal;
	}
	
	public double getTotal() {
		return this.getSubtotal() + this.getTaxes();
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
		sb.append(String.format("%s (Lease) %s %s\n",this.getCode(), this.getName(),this.model));
		sb.append(String.format("%s Days (%s ->%s) @ %s/30 \n", this.getTimeLeased(),this.startDate,this.endDate,this.fee));
		return sb.toString();
	}
}
