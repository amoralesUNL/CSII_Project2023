package com.fmt;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 *Class loads data into various objects 
 *
 */

public class CsvLoader implements DataLoader {
	
	private static final String Person_File = "data/Persons.csv";
	private static final String Stores_File = "data/Stores.csv";
	private static final String Items_File = "data/Items.csv";
	private static final String Invoice_Items_File = "data/InvoiceItems.csv";
	private static final String Invoice_File = "data/Invoices.csv";
	

	
	public static HashMap<String,Person> loadPeople() {
		HashMap<String,Person> report = new HashMap<>();
		
		try(Scanner s = new Scanner (new File (Person_File))) {
			int n = Integer.parseInt(s.nextLine());
			Person a = null;
			for(int i = 0; i < n; i++) {
				String line  = s.nextLine();
				String tokens[] = line.split(",");
				String id = tokens[0];
				String lastName = tokens[1];
				String firstName = tokens[2];
				Address b = new Address(tokens[3],tokens[4],tokens[5],tokens[7],tokens[6]);
				a = new Person(id,lastName,firstName, b);
				if (tokens.length > 8) {
					for(int k = 8; k < tokens.length;k++) {
						a.addEmail(tokens[k]);
					}
				}	
	
				report.put(id,a);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return report;
				
	}
	
	public List<Store> loadStores(){
		HashMap<String,Person> personData = CsvLoader.loadPeople();
		List<Store> report = new ArrayList<>();
		try(Scanner s = new Scanner(new File (Stores_File))) {
			int n = Integer.parseInt(s.nextLine());
			Store a = null;
			Person b = null;
			for(int i = 0; i < n; i++) {
				String line = s.nextLine();
				String tokens[] = line.split(",");
				b = personData.get(tokens[1]);
				Address d = new Address(tokens[2],tokens[3],tokens[4],tokens[5],tokens[6]);
				a = new Store(tokens[0],b,d);
				report.add(a);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}

		return report;
	}
	
	public static HashMap<String,Item> loadItems(){
		HashMap<String,Item> report = new HashMap<>();
		try(Scanner s = new Scanner(new File (Items_File))) {
			int n = Integer.parseInt(s.nextLine());
			Item a = null;
			for(int i = 0; i < n; i++) {
				String line = s.nextLine();
				String tokens[] = line.split(",");
				String code  = tokens[0];
				String type = tokens[1];
				String name = tokens[2];
				if (tokens[1].equals("E")) {
					a = new Equipment(code,type,name,tokens[3]);
				}else if(tokens[1].equals("P")){
					double unitPrice = Double.parseDouble(tokens[4]);
					a = new Product(code,type,name,tokens[3],unitPrice);
				}else if (tokens[1].equals("S")){
					double hourlyRate = Double.parseDouble(tokens[3]);
					a = new Service(code,type,name,hourlyRate);
				}
				report.put(tokens[0],a);	
 			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		return report;
	}
	
	public static HashMap<String,Invoice> loadInvoiceData() {
		HashMap<String,Person> personData = CsvLoader.loadPeople();
		HashMap<String,Invoice> report = new HashMap<>();
		Invoice a = null;
		Person c = null;
		Person d = null;
		try(Scanner s = new Scanner(new File(Invoice_File))){
			int n = Integer.parseInt(s.nextLine());
			for(int i = 0; i < n; i++) {
				String line = s.nextLine();
				String tokens[] = line.split(",");
				String invoiceCode = tokens[0];
				String storeCode = tokens[1];
				String customerCode = tokens[2];
				String salesPersonCode = tokens[3];
				String invoiceDate = tokens[4];
				c = personData.get(customerCode);
				d = personData.get(salesPersonCode);
				a = new Invoice(invoiceCode,storeCode,c,d,invoiceDate);
				report.put(invoiceCode,a);
				
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return report;
	}
	
	/**
	 * Loads Invoice Items into an invoices. This method then returns a list of invoices.
	 */
	
	public List<Invoice> loadInvoices(){
		HashMap<String,Invoice> invoiceData = CsvLoader.loadInvoiceData();
		HashMap<String,Item> itemData = CsvLoader.loadItems();
		List<Invoice> invoices  = new ArrayList<>();
		try(Scanner s = new Scanner(new File (Invoice_Items_File))){
			int n = Integer.parseInt(s.nextLine());
			for(int i = 0; i < n; i++) {
				String line = s.nextLine();
				String tokens[] = line.split(",");
				Item a = itemData.get(tokens[1]);
				if (tokens[2].equals("P")) {
					Double price = Double.parseDouble(tokens[3]);
					Equipment b = (Equipment)itemData.get(tokens[1]);
					PurchasedEquipment c = new PurchasedEquipment(b.getCode(),b.getType(),b.getName(),b.getModel(),price);
					invoiceData.get(tokens[0]).addItems(c);

				}else if (tokens[2].equals("L")) {
					double fee = Double.parseDouble(tokens[3]);
					LocalDate start = LocalDate.parse(tokens[4]);
					LocalDate end = LocalDate.parse(tokens[5]);
					Equipment b = (Equipment)itemData.get(tokens[1]);
					LeasedEquipment c = new LeasedEquipment(b.getCode(),b.getType(),b.getName(),b.getModel(),fee,start,end);
					invoiceData.get(tokens[0]).addItems(c);
					
				}else {
					if (a.getType().equals("S")) {
						Service b =  (Service) itemData.get(tokens[1]) ;
						Double ammountBought = Double.parseDouble(tokens[2]);
						Service c = new Service(b, ammountBought);
						invoiceData.get(tokens[0]).addItems(c);
						
					}else if (a.getType().equals("P")){
						Product b = (Product) itemData.get(tokens[1]);
						Double quantityPurchased = Double.parseDouble(tokens[2]);
						Product c = new Product(b, quantityPurchased);
						invoiceData.get(tokens[0]).addItems(c);
					}
				}
			}
			
			for(Map.Entry<String,Invoice> entry: invoiceData.entrySet()) {
				invoices.add(entry.getValue());
			}
		 
		}catch(Exception e) {
			throw new RuntimeException(e);
		}	
		return invoices;
	}
}
