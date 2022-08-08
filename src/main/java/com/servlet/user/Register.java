package com.servlet.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
  
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		// get Data from input
		String user_name = request.getParameter("user_name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String tableName = "user101";
		String createTable = "create table user101(id int NOT NULL AUTO_INCREMENT, user_name varchar(20), password varchar(20), email varchar(30), PRIMARY KEY (id))";
		// JDBC Connection
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "system", "root");
			DatabaseMetaData dmd = con.getMetaData();
			ResultSet rSet = dmd.getTables(null, null, null, new String[] { "TABLE" });
			// ResultSet rSet = dmd.getTables(null, null, null, new String[] {"TABLE"});
			Thread.sleep(2500);
			try {
				while (rSet.next()) { 
					String tName = rSet.getString("TABLE_NAME");
					if (tName != null && tName.equals(tableName)) {
						System.out.println("Table already exist...!");
						break;
					} else { 
						Statement stmt = con.createStatement();
						stmt.executeUpdate(createTable);
						stmt.close();
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("Inside loop Exception:" + e);
			}
			PreparedStatement pstm = con.prepareStatement("insert into user101 (user_name, password, email)  values(?,?,?)");
			pstm.setString(1, user_name);
			pstm.setString(2, password);
			pstm.setString(3, email);
			pstm.executeUpdate();
			System.out.println("done");
		} catch (SQLException e) {
			System.out.println("error");
		} catch (Exception e) {
			System.out.println("error");
		}

	}

}
