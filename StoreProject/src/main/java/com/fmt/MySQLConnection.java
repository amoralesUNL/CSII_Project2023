package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that contains the connection info for the database. It is able to create
 * and close a connection to an SQL database. 
 * */
public class MySQLConnection {

	public static final String PARAMETERS = "your-parameters";

	public static final String USERNAME = "your-username";
	public static final String PASSWORD = "your-password";
	public static final String URL = "your-url" + USERNAME + PARAMETERS;

    public static Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }
	
    public static void closeConnection(Connection conn) {
    	if(conn != null) {
    		try {
    			conn.close();
    		}catch(SQLException e ){
    			e.printStackTrace();
    		}
    	}
    }
}
