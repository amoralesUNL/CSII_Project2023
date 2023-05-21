package com.fmt;
/**
 * Subclass of items that models various products
 * 
 * */
public class Product extends Item {
	private String unit;
	private double unitPrice;
	private Double quantityPurchased;
	
	public Product(String code, String type, String name, String unit, double unitPrice, Double quantityPurchased) {
		super(code, type, name);
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.quantityPurchased = quantityPurchased;
	}
	
	public Product(String code, String type, String name, String unit, double unitPrice) {
		super(code, type, name);
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.quantityPurchased = null;
	}
	
	public Product(Product product, Double quantityPurchased) {
		this(product.getCode(),product.getType(),product.getName(),product.getUnit(),product.getUnitPrice(), quantityPurchased);
	}
	
	public String getUnit() {
		return unit;
	}

	public double getUnitPrice() {
		return unitPrice;
	}
	
	public double getTaxes() {
		double taxes = .0715 *this.getSubtotal();
		taxes = Math.round(taxes * 100.0) / 100.0;
		return taxes;
	}
	public double getSubtotal() {
		double subtotal = this.unitPrice * this.quantityPurchased;
		subtotal *= 100;
		subtotal = Math.round(subtotal);
		subtotal = subtotal / 100;
		return subtotal;
	}
	
	public double getTotal(){
		double total = this.getSubtotal() + this.getTaxes();
		return total;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s ,%s, %s",
				this.getCode(),
				this.getType(),
				this.getName(),
				this.unit,
				this.unitPrice);
	}
	
	public String toReport() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s (Product) %s\n",this.getCode(), this.getName()));
		sb.append(String.format("         %s @  %s/%s\n", this.quantityPurchased, this.unitPrice,this.unit));
		return sb.toString();
	}
	
	
}
