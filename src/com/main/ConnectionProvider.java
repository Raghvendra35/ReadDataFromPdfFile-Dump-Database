package com.main;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider 
{

	public static Connection getConnection()
	{
	 try
	 {
	  Class.forName("com.mysql.cj.jdbc.Driver");
	  Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/electionallyear","root","root");
	//  System.out.println(con);
	  return con;
	  
	 }catch(Exception e)
	 {
		 e.printStackTrace();
	 }
		return null;
	}
	
	public static void main(String ar[])
	{
		System.out.println("Running");
	 Connection con=getConnection();
	 System.out.print("Con==========="+con);
	}
}
