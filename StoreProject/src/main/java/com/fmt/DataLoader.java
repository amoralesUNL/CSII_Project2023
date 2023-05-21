package com.fmt;

import java.util.List;

/**
 * Interface that defines methods needed for data loader to parse
 * into a report.
 */

public interface DataLoader {
	
	/**
	 * Method that returns a list of stores from a Data loader class
	 */
	
	public List<Store> loadStores();
	
	/**
	 * Method that returns a list of invoices from a Data loader class
	 */
	
	public List<Invoice> loadInvoices();
}
