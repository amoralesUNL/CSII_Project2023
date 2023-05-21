package com.fmt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Removes all records from all tables in the database.
	 */
	
	public static void clearDatabase() {
		Connection conn = null;
		PreparedStatement ps  = null;
		
		try {
			conn = MySQLConnection.createConnection();
			String query =  "DELETE FROM InvoiceItem WHERE invoiceItemId > 0";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			String query2 = "DELETE FROM  Invoice WHERE invoiceId > 0";
			ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			ps.close();
			String query3 = "DELETE FROM  Item WHERE itemId > 0";
			ps = conn.prepareStatement(query3);
			ps.executeUpdate();
			ps.close();
			String query4 = "DELETE FROM  Store WHERE storeId > 0";
			ps = conn.prepareStatement(query4);
			ps.executeUpdate();
			ps.close();
			String query5 = "DELETE FROM  Email WHERE emailId > 0";
			ps = conn.prepareStatement(query5);
			ps.executeUpdate();
			ps.close();
			String query6 = "DELETE FROM  Person WHERE personId > 0";
			ps = conn.prepareStatement(query6);
			ps.executeUpdate();
			ps.close();
			String query7 = "DELETE FROM  Address WHERE addressId > 0";
			ps = conn.prepareStatement(query7);
			ps.executeUpdate();
			ps.close();
			
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }
	}

	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
        Connection conn = null;
        PreparedStatement ps =null;

        try {
        	conn = MySQLConnection.createConnection();
        	int addressId = InvoiceData.addAddress(street, city, state, zip, country);
            String query = "INSERT INTO Person (personCode,lastName,firstname,addressId) values(?,?,?,?)";
        	ps = conn.prepareStatement(query);
        	ps.setString(1, personCode);
        	ps.setString(2, lastName);
        	ps.setString(3,firstName);
        	ps.setInt(4, addressId);
			ps.executeUpdate();
        	ps.close();

        }catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			int personId = DatabaseLoader.loadPersonIdbyCode(personCode);
			String emailQuery = "INSERT INTO Email (personId,address) values (?,?)";
			ps = conn.prepareStatement(emailQuery);
			ps.setInt(1, personId);
			ps.setString(2, email);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }

	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = MySQLConnection.createConnection();
        	int addressId = InvoiceData.addAddress(street, city, state, zip, country);
        	int managerId = DatabaseLoader.loadPersonIdbyCode(managerCode);
        	String query = "INSERT INTO Store (storeCode,managerCode,managerId,addressId) values (?,?,?,?)";
        	ps = conn.prepareStatement(query);
        	ps.setString(1, storeCode);
        	ps.setString(2, managerCode);
        	ps.setInt(3, managerId);
        	ps.setInt(4,addressId);
        	ps.executeUpdate();
        	ps.close();
        }catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }       
	}

	/**
	 * Adds a product record to the database with the given <code>code</code>, <code>name</code> and
	 * <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			String query = "INSERT INTO Item (code,type,name,unit,unitPrice) values (?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, "P");
			ps.setString(3, name);
			ps.setString(4, unit);
			ps.setDouble(5, pricePerUnit);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }       
		
	}

	/**
	 * Adds an equipment record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>modelNumber</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addEquipment(String code, String name, String modelNumber) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			String query = "INSERT INTO Item (code,type,name,model) values (?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, "E");
			ps.setString(3, name);
			ps.setString(4, modelNumber);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }       
	}

	/**
	 * Adds a service record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>costPerHour</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addService(String code, String name, double costPerHour) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			String query = "INSERT INTO Item (code,type,name,hourlyRate) values (?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, "S");
			ps.setString(3, name);
			ps.setDouble(4, costPerHour);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }       
			
	}

	/**
	 * Adds an invoice record to the database with the given data.
	 *
	 * @param invoiceCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
   * @param invoiceDate
	 */
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode, String invoiceDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			int customerId = DatabaseLoader.loadPersonIdbyCode(customerCode);
			int salesPersonId = DatabaseLoader.loadPersonIdbyCode(salesPersonCode);
			int storeId = DatabaseLoader.loadStoreIdbyCode(storeCode);
			String query = "INSERT INTO  Invoice(invoiceCode,storeCode,customerCode,salesPersonCode,Date,storeId,customerId,salesPersonId) values (?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1,invoiceCode );
			ps.setString(2,storeCode);
			ps.setString(3, customerCode);
			ps.setString(4, salesPersonCode);
			ps.setString(5, invoiceDate);
			ps.setInt(6, storeId);
			ps.setInt(7, customerId);
			ps.setInt(8, salesPersonId);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }       
	}

	/**
	 * Adds a particular product (identified by <code>itemCode</code>)
	 * to a particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			int invoiceId = DatabaseLoader.loadInvoiceIdbyCode(invoiceCode);
			int itemId = DatabaseLoader.loadItemIdbyCode(itemCode);
			String query = "INSERT INTO InvoiceItem(invoiceCode,itemCode,itemId,invoiceId,quantityPurchased) VALUES (?,?,?,?,?)";
			ps =conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, itemCode);
			ps.setInt(3, itemId);
			ps.setInt(4, invoiceId);
			ps.setInt(5,quantity);
			ps.executeUpdate();
			ps.close();
			
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }       
	}

	/**
	 * Adds a particular equipment <i>purchase</i> (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			int invoiceId = DatabaseLoader.loadInvoiceIdbyCode(invoiceCode);
			int itemId = DatabaseLoader.loadItemIdbyCode(itemCode);
			String query = "INSERT INTO InvoiceItem(invoiceCode,itemCode,itemId,invoiceId,price) VALUES (?,?,?,?,?)";
			ps =conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, itemCode);
			ps.setInt(3, itemId);
			ps.setInt(4, invoiceId);
			ps.setDouble(5,purchasePrice);
			ps.executeUpdate();
			ps.close();
			
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }       

	}

	/**
	 * Adds a particular equipment <i>lease</i> (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the given 30-day
	 * <code>periodFee</code> and <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate, String endDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			int invoiceId = DatabaseLoader.loadInvoiceIdbyCode(invoiceCode);
			int itemId = DatabaseLoader.loadItemIdbyCode(itemCode);
			String query = "INSERT INTO InvoiceItem(invoiceCode,itemCode,itemId,invoiceId,fee,startDate,endDate,leased) VALUES (?,?,?,?,?,?,?,?)";
			ps =conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, itemCode);
			ps.setInt(3, itemId);
			ps.setInt(4, invoiceId);
			ps.setDouble(5,periodFee);
			ps.setString(6, beginDate);
			ps.setString(7, endDate);
			ps.setString(8, "L");
			ps.executeUpdate();
			ps.close();
			
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        } 
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified number of hours.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param billedHours
	 */
	public static void addServiceToInvoice(String invoiceCode, String itemCode, double billedHours) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MySQLConnection.createConnection();
			int invoiceId = DatabaseLoader.loadInvoiceIdbyCode(invoiceCode);
			int itemId = DatabaseLoader.loadItemIdbyCode(itemCode);
			String query = "INSERT INTO InvoiceItem(invoiceCode,itemCode,itemId,invoiceId,hoursBilled) VALUES (?,?,?,?,?)";
			ps =conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, itemCode);
			ps.setInt(3, itemId);
			ps.setInt(4, invoiceId);
			ps.setDouble(5,billedHours);
			ps.executeUpdate();
			ps.close();
			
		}catch(SQLException e) {
        	throw new RuntimeException(e);
        }finally {
        	MySQLConnection.closeConnection(conn);
        }      
	}
	
	/**
	 * Adds an address to the database
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return addressId
	 */
	public static int addAddress(String street, String city, String state, String zip, String country) {
        Connection conn = null;
        PreparedStatement ps =null;
        Integer addressId = null;
        try {
        	conn = MySQLConnection.createConnection();
            String addressQuery = "INSERT INTO Address (street,city,state,zipCode,country) values (?,?,?,?,?)";
            ps = conn.prepareStatement(addressQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, street);
        	ps.setString(2, city);
        	ps.setString(3, state);
        	ps.setString(4, zip);
        	ps.setString(5, country);
        	ps.executeUpdate();
        	ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			addressId = keys.getInt(1);
        	ps.close();
        	keys.close();
        	}catch(SQLException e) {
            	throw new RuntimeException(e);
            }finally {
            	MySQLConnection.closeConnection(conn);
            }
		return addressId;
	}
}