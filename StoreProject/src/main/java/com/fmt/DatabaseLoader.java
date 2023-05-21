package com.fmt;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Class that loads instances of objects from a Database. This class using details from
 * MySQlConnection class to establish a connection
 */

public class DatabaseLoader implements DataLoader {

	private static final Logger LOGGER = LogManager.getLogger(DatabaseLoader.class);
	/**
	 * @param database personId
	 * @return person object
	 */
	
	public static Person loadPersonById(int id) {
		Person p = null;
		Address a = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = ("select  p.personCode, p.lastName, p.firstName, a.street, a.city, a.state, a.country, a.zipCode from Person p join Address a on p.addressId = a.addressId where p.personId = ?");
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String country = rs.getString("country");
				String zipCode = rs.getString("zipCode");
				a = new Address(street,city,state,country,zipCode);
				p = new Person(personCode,lastName,firstName,a);	
			}
			
			if(rs.next()) {
				throw new IllegalStateException("Invalid Data in Database");
			}
			rs.close();
			ps.close();
			
			if(p != null) {
				query = "select Email.address from Email where personId = ?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				while(rs.next()) {
					String address = rs.getString("address");
					p.addEmail(address);
				}
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			MySQLConnection.closeConnection(conn);
		}
		
		return p;
	}
	
	public  List<Person> loadPeople(){
		Connection conn = null;
		Person p = null;
		
		String query = "select personId from Person";
		List<Person> people = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int key = rs.getInt("personId");
				p = DatabaseLoader.loadPersonById(key);
				people.add(p);
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			MySQLConnection.closeConnection(conn);
		}

		return people;
	}
	
	public List<Store> loadStores(){
		Connection conn = null;
		Store s = null;
		
		LOGGER.info("loading Stores");
		
		String query = "select s.storeCode, s.managerId, a.street, a.city, a.state, a.country, a.zipCode from Store s left join Address a on s.addressid = a.addressid";
		List<Store> stores = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String storeCode = rs.getString("storeCode");
				int managerId = rs.getInt("managerId");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String country = rs.getString("country");
				String zipCode = rs.getString("zipCode");
				Address a = new Address(street,city,state,country,zipCode);
				Person p = DatabaseLoader.loadPersonById(managerId);
				s = new Store(storeCode,p,a);
				stores.add(s);
			}
			if(rs.next()) {
				throw new IllegalStateException("Invalid Data in Database");
			}

			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			MySQLConnection.closeConnection(conn);
		}
		return stores;
	}
	/**
	 * @param database Item id
	 * @return Item object
	 */
	public static Item loadItemById(int id){
		Connection conn = null;
		Item a = null;
		
		String query = "select * from Item where Item.itemId = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				String code = rs.getString("code");
				String type = rs.getString("type");
				String name = rs.getString("name");
				if(type.equals("P")) {
					String unit = rs.getString("unit");
					double unitPrice = rs.getDouble("unitPrice");
					a = new Product(code,type,name,unit,unitPrice);
				}else if (type.equals("S")) {
					double hourlyRate = rs.getDouble("hourlyRate");
					a = new Service(code,type,name,hourlyRate);
				}else if (type.equals("E")){
					String model = rs.getString("model");
					a = new Equipment(code,type,name,model);
				}else {
					throw new IllegalStateException("Invalid Item Type");
				}	
			}
			
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			MySQLConnection.closeConnection(conn);
		}

		return a;
		
	}
/**
 * 
 * @param databse invoice item id
 * @return item object that is on an invoice
 */
	public static List<Item> loadInvoiceItems(int id){
		Connection conn = null;
		Item a = null;

		LOGGER.info("loading InvoiceItems with invoiceId = " + id);
		
		List<Item> items = new ArrayList<>();
		String query = "SELECT i.itemId, i.quantityPurchased, i.leased, i.fee, i.price, i.startDate,i.endDate,i.hoursBilled FROM InvoiceItem i WHERE i.invoiceId = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1,id);
			rs = ps.executeQuery();
			while(rs.next()) {
				int itemId = rs.getInt("itemId");
				a = DatabaseLoader.loadItemById(itemId);
				if(a.getType().equals("P")) {
					Product b = (Product) a;
					double quantityPurchased = rs.getDouble("quantityPurchased");
					Product c = new Product(b,quantityPurchased);
					items.add(c);
				}else if (a.getType().equals("S")) {
					Service b = (Service) a;
					double hoursBilled = rs.getDouble("hoursBilled");
					Service c = new Service(b,hoursBilled);
					items.add(c);
				}else if (a.getType().equals("E")){
					if (rs.getString("leased") != null) {
						LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
						LocalDate endDate = LocalDate.parse(rs.getString("endDate"));
						double fee = rs.getDouble("fee");
						Equipment b = (Equipment) a;
						LeasedEquipment c = new LeasedEquipment(b.getCode(),b.getType(),b.getName(),b.getModel(), fee,startDate,endDate);
						items.add(c);
						
					}else {
						double price = rs.getDouble("price");
						Equipment b = (Equipment) a;
						PurchasedEquipment c = new PurchasedEquipment(b.getCode(),b.getType(),b.getName(),b.getModel(),price);
						items.add(c);
					}
				}
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			MySQLConnection.closeConnection(conn);
		}
		return items;
	}
	
	public List<Invoice> loadInvoices(){
		Connection conn = null;
		Invoice invoice = null;
		
		LOGGER.info("loading Invoices");
		
		List<Invoice> invoices = new ArrayList<>();
		String query = "SELECT a.invoiceId, a.invoiceCode, a.storeId, a.customerId, a.salesPersonId, a.date, a.storeCode FROM Invoice a";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				String invoiceCode = rs.getString("invoiceCode");
				String storeCode = rs.getString("storeCode");
				int customerId = rs.getInt("customerId");
				int salesPersonId = rs.getInt("salesPersonId");
				Person customer = DatabaseLoader.loadPersonById(customerId);
				Person salesPerson = DatabaseLoader.loadPersonById(salesPersonId);
				String date = rs.getString("date");
				invoice = new Invoice(invoiceCode,storeCode,customer,salesPerson,date);
				List<Item> invoiceItems = DatabaseLoader.loadInvoiceItems(invoiceId);
				for (Item i : invoiceItems) {
					invoice.addItems(i);
				}
				invoices.add(invoice);
				
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			MySQLConnection.closeConnection(conn);
		}
		return invoices;
	}
	
	/**
	 * 
	 * @param personCode
	 * @return personId
	 */
	
	public static int loadPersonIdbyCode(String personCode) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Integer personId = null;
		String query = "SELECT personId FROM Person WHERE personCode = ?";
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1,personCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				personId = rs.getInt("personId");
			}else {
				throw new IllegalArgumentException("Invalid personCode");
			}
			if(rs.next()) {
				throw new IllegalStateException("Invalid Data in Database");
			}	
		}catch(SQLException e) {
	        	throw new RuntimeException(e);
	        }finally {
	        	MySQLConnection.closeConnection(conn);
	        }
			
		return personId;
	}
	
	/**
	 * 
	 * @param storeCode
	 * @return storeId
	 */
	
	public static int loadStoreIdbyCode(String storeCode) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Integer personId = null;
		String query = "SELECT storeId FROM Store WHERE storeCode = ?";
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1,storeCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				personId = rs.getInt("storeId");
			}else {
				throw new IllegalArgumentException("Invalid storeCode");
			}
			if(rs.next()) {
				throw new IllegalStateException("Invalid Data in Database");
			}	
		}catch(SQLException e) {
	        	throw new RuntimeException(e);
	        }finally {
	        	MySQLConnection.closeConnection(conn);
	        }
			
		return personId;
	}
	
	/**
	 * 
	 * @param itemCode
	 * @return itemId
	 */
	
	public static int loadItemIdbyCode(String itemCode) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Integer itemId = null;
		String query = "SELECT itemId FROM Item WHERE code = ?";
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1,itemCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				itemId = rs.getInt("itemId");
			}else {
				throw new IllegalArgumentException("Invalid itemCode");
			}
			if(rs.next()) {
				throw new IllegalStateException("Invalid Data in Database");
			}	
		}catch(SQLException e) {
	        	throw new RuntimeException(e);
	        }finally {
	        	MySQLConnection.closeConnection(conn);
	        }
			
		return itemId;
	}	

	/**
	 * 
	 * @param invoiceCode
	 * @return invoiceId
	 */
	
	public static int loadInvoiceIdbyCode(String invoiceCode) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Integer invoiceId = null;
		String query = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
		
		try {
			conn = MySQLConnection.createConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1,invoiceCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				invoiceId = rs.getInt("invoiceId");
			}else {
				throw new IllegalArgumentException("Invalid invoiceCode");
			}
			if(rs.next()) {
				throw new IllegalStateException("Invalid Data in Database");
			}	
		}catch(SQLException e) {
	        	throw new RuntimeException(e);
	        }finally {
	        	MySQLConnection.closeConnection(conn);
	        }
			
		return invoiceId;
	}	
}
