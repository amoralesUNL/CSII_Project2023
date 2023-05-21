package com.fmt;


import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
/***
 * 
 * This class is used to gather the information required to produce an invoice report.
 * It gathers data provided from data loader class and provides it to list.
 * Then this data is used by the summary class to construct the full report.
 */
public class InvoiceReport {
	
	public static void main(String[] args) {
		
		Configurator.initialize(new DefaultConfiguration());
		Configurator.setRootLevel(Level.INFO);
		
		DataLoader dl = new DatabaseLoader();
		List<Invoice> invoiceData = dl.loadInvoices();
		
		
		Comparator<Invoice> customerComparator = InvoiceComparator.compareByName();
		Comparator<Invoice> totalComparator = InvoiceComparator.compareByValue();
		Comparator<Invoice> storeComparator = InvoiceComparator.compareByStore();
		
		
		MyLinkedList<Invoice> personSort = new MyLinkedList<Invoice>(invoiceData,customerComparator);
		MyLinkedList<Invoice> totalSort = new MyLinkedList<Invoice>(invoiceData,totalComparator);
		MyLinkedList<Invoice> storeSort = new MyLinkedList<Invoice>(invoiceData, storeComparator); 
		
		Summary a = new Summary();
		
		System.out.println(a.invoiceSummary(personSort, "Customer"));
		System.out.println(a.invoiceSummary(totalSort, "Total"));
		System.out.println(a.invoiceSummary(storeSort, "Store"));
		
	}

}
