package com.fmt;

import java.util.ArrayList;
import java.util.List;
/***
 * 
 * Class that models a summary of sales across many stores.
 * It takes list of invoices a produces a summary of all stores and displays the invoices provided.
 *
 */
public class Summary {

	
	List<Invoice>listInvoice  = new ArrayList<>();
	List<Store>storeData = new ArrayList<>();
	
	public void addInvoices(Invoice a){
		this.listInvoice.add(a);
	}
	public void addStores(Store s) {
		this.storeData.add(s);
	}
	
	public double getGrandTax() {
		double grandTax = 0.0;
		for (Invoice e : this.listInvoice) {
			 grandTax += e.getInvoiceTaxTotal();
		}
		return grandTax;
	}
	
	public double getGrandTotal() {
		double grandTotal = 0.0;
		for (Invoice e : this.listInvoice) {
			 grandTotal += e.getInvoiceTotal();
		}
		return grandTotal;
	}
	public int getItemSales() {
		int itemSales = 0;
		for(Invoice e : this.listInvoice) {
			itemSales += e.getItemsSold();
		}
		return itemSales;
	}
	public int getTotalSalesNum() {
		int totalSalesNum = 0;
		for(Store s : storeData) {
			totalSalesNum += s.getSalesNum();
		}
		return totalSalesNum;
	}
	
	public String invoiceSummary(MyLinkedList<Invoice> invoices, String type ) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("+----------------------------------------------------------------------------+\n"));
		sb.append(String.format("|Sales by %8s                                                           |\n", type));
		sb.append(String.format("+----------------------------------------------------------------------------+\n"));
		sb.append(String.format("Invoice     Store       Customer              SalesPerson          Total\n"));
		for(Invoice a : invoices) {
			sb.append(String.format("%s      %s      %-8s %-8s     %-8s %-8s    $%.2f \n", a.getInvoiceCode(),a.getStoreCode(),a.getCustomer().getLastName(),a.getCustomer().getFirstName(),
					a.getSalesPerson().getLastName(),a.getSalesPerson().getFirstName(),a.getInvoiceTotal()));
		}

		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
		sb.append(String.format("|Summary Report- By Total                                                                    |\n"));
		sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
		sb.append(String.format("Invoice       Store        Customer       Num Items        Taxes             Total\n"));
		for(Invoice o : listInvoice) {
			sb.append(String.format("%s        %s    %8s %8s     %-3s          $%.2f           $%.2f\n", o.getInvoiceCode(), o.getStoreCode(), o.getCustomer().getLastName(), o.getCustomer().getFirstName(), o.getItemsSold(), o.getInvoiceTaxTotal(),o.getInvoiceTotal()));
		}
		sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
		sb.append(String.format("                                              %s           $%.2f          $%.2f\n\n",this.getItemSales(), this.getGrandTax(), this.getGrandTotal()));
		sb.append(String.format("+-----------------------------------------------+\n"));
		sb.append(String.format("+Store Sales Summary Report                     +\n"));
		sb.append(String.format("+-----------------------------------------------+\n"));
		sb.append(String.format("Store          Manager        #Sales  Grand Total\n"));
		for(Store s : storeData) {
			sb.append(String.format("%s      %8s %8S   %-3s   $%.2f\n",s.getStoreCode(), s.getManager().getLastName(),s.getManager().getFirstName(),s.getSalesNum(),s.getSalesTotal()));
		}
		sb.append(String.format("+-----------------------------------------------+\n"));
		sb.append(String.format("                                %s     $%.2f",this.getTotalSalesNum(),this.getGrandTotal()));
		for(Invoice o :listInvoice) {
			sb.append(String.format("\n%s",o.toString()));
		}
		return sb.toString();
	}
	
}
