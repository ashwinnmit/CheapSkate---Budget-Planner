
package application;

import java.sql.*;  
class sqlconnect{  
	public static void main(String args[]){  
	try{  
	Class.forName("com.mysql.cj.jdbc.Driver");  
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");   
	Statement stmt=con.createStatement();
	stmt.executeUpdate("insert into User_Details values(11,'varun','123456','ashwin@gmail.com')");
	ResultSet rs=stmt.executeQuery("select * from User_Details"); 
	while(rs.next())  
	System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));  
	con.close();  
	}catch(Exception e){ System.out.println(e);}  
	}  
}