package com.ec.util;
 import java.sql.*;
 
 public class DBConnection{
	 private static final String URL="jdbc:mysql://localhost:3306/economictracker?useSSL=false&serverTimezone=UTC";
	 private static final String USER="root";
	 private static final String PASS="";
	 
	 static {
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
		 }
		 catch(ClassNotFoundException e) {
			 throw new RuntimeException(e);
		 }
	 }
	 public static Connection getConnection() throws SQLException{
		 return DriverManager.getConnection(URL,USER,PASS);
	 }
 }
 
