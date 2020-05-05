package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class payment {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paymentdb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String name, String appointmentNumber, String time,String date,String amount) {
		
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = "insert into payment(`Pname`,`aptId`,`Ttime`,`Pdate`,`Pamount`)"+ " values (?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, appointmentNumber);
			preparedStmt.setString(3, time);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, amount);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//embedded into a json object
			 String newPayments = viewPayment();
			 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
			 
			 }catch (Exception e){
				 output = "{\"status\":\"error\", \"data\":\"Error while inserting the Payment.\"}"; 
				 System.err.println(e.getMessage());
			 }
		return output;
	}

	public String viewPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"
					+ "<th>patientName</th>"
					+ "<th>appointmentNumber</th><th>Time</th><th>Date</th><th>Amount</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String payId = Integer.toString(rs.getInt("PId"));
				String pName = rs.getString("Pname");
				String aptId = rs.getString("aptId");
				String time = rs.getString("Ttime");
				String date = rs.getString("Pdate");		
				String amount = rs.getString("Pamount");
			
				
				// Add into the html table
				output +=  "<tr><td><input id='hidPaymentIDUpdate'"
						+ "name='hidPaymentIDUpdate' type='hidden'"
						+ "value='" + payId + "'>" + pName + "</td>";
				output += "<td>" + aptId + "</td>";
				output += "<td>" + time + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + amount + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button'"
						+ "value='Update'"
						+ "class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button'"
						+ "value='Remove'"
						+ "class='btnRemove btn btn-danger' data-payid='"
						 + payId + "'>" + "</td></tr>"; 
	
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String id, String name, String appointmentNumber, String time,String date,String amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET Pname=?,aptId=?,Ttime=?,Pdate=?,Pamount=? WHERE PId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(6, id);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, appointmentNumber);
			preparedStmt.setString(3, time);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5,amount);
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//embedded into a json object
			 String newPayments = viewPayment();
			 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
			 
			 }catch (Exception e){
				 output = "{\"status\":\"error\", \"data\":\"Error while Updating the Payment.\"}"; 
				 System.err.println(e.getMessage());
			 }
		return output;
	}

	public String deletePayment(String pId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where PId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//embedded into a json object
			 String newPayments = viewPayment();
			 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
			 
			 }catch (Exception e){
				 output = "{\"status\":\"error\", \"data\":\"Error while Deleting the Payment.\"}"; 
				 System.err.println(e.getMessage());
			 }
		return output;
	}
	
}
