package application;

import java.sql.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dynamic.Expenselist;

class DBUtils{
	static String username;
	static String password;
	static String email;
	static String f_name;
	static String l_name;
	static int u_id;
	static double food;
	static double ent;
	static double shop;
	static double tran;
	static double misc;
	static double total_expense;
	static double total_balance;
	static ResultSet daily_details = null;
	public static int Checkinfo(String uname, String pass){ 
		Connection con = null;
		PreparedStatement pscheck = null;
		ResultSet rs = null;
		String fname; 
		String lname;
		String e_mail;
		int uid;
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");    
			pscheck = con.prepareStatement("select * from user_details where user_name = ?");
			pscheck.setString(1, uname);
			rs = pscheck.executeQuery();
			if(!rs.next()) {
				return 1;
			}
			pscheck = con.prepareStatement("select * from user_details where password = ? and user_name = ?");
			pscheck.setString(1, pass);
			pscheck.setString(2, uname);
			rs = pscheck.executeQuery();
			if(!rs.next()) {
				return 2;
			}
			username = uname;
			password = pass;
			fname = rs.getString(3);
			lname = rs.getString(4);
			e_mail = rs.getString(6);
			uid = rs.getInt(1);
			email = e_mail;
			f_name = fname;
			l_name = lname;
			u_id = uid;
			Main m = new Main();
			m.changeScene("Homepage.fxml");
			con.close();
		}catch(Exception e){ e.printStackTrace();} 
		return 0;
	}
	
	public static int Enterinfo(String fname, String lname, String uname, String pass, String e_mail) {
		Connection con = null;
		PreparedStatement psinsert = null;
		PreparedStatement pscheck = null;
		ResultSet rs = null;
		int uid;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");    
			pscheck = con.prepareStatement("select * from user_details where user_name = ?");
			pscheck.setString(1, uname);
			rs = pscheck.executeQuery();
			if(rs.next()) {
				return 1;
			}
			psinsert = con.prepareStatement("insert into user_details(user_name, f_name, l_name, password, email) values(?,?,?,?,?)");
			psinsert.setString(1, uname);
			psinsert.setString(2, fname);
			psinsert.setString(3, lname);
			psinsert.setString(4, pass);
			psinsert.setString(5, e_mail);
			psinsert.executeUpdate();
			pscheck = con.prepareStatement("select * from user_details where user_name = ?");
			pscheck.setString(1,uname);
			rs = pscheck.executeQuery();
			rs.next();
			uid = rs.getInt(1);
			username = uname;
			password = pass;
			email = e_mail;
			f_name = fname;
			l_name = lname;
			u_id = uid;
			Main m = new Main();
			m.changeScene("Initialdeposit.fxml");
			con.close();
		}catch(Exception e){ e.printStackTrace();} 
		return 0;
	}
	
	public static void expenses() {
		Connection con = null;
		PreparedStatement pscheck = null;
		ResultSet rs = null;
		double fe=0.0;
		double te=0.0;
		double e=0.0;
		double se=0.0;
		double me=0.0;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");
			//food expense
			pscheck = con.prepareStatement("select sum(Amount) from food_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			rs = pscheck.executeQuery();
			if(rs.next())
				fe = rs.getDouble(1);
			//transportation expense
			pscheck = con.prepareStatement("select sum(Amount) from transportation_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			rs = pscheck.executeQuery();
			if(rs.next())
				te = rs.getDouble(1);
			//entertainment expense
			pscheck = con.prepareStatement("select sum(Amount) from entertainment_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			rs = pscheck.executeQuery();
			if(rs.next())
				e = rs.getDouble(1);
			//shopping expense
			pscheck = con.prepareStatement("select sum(Amount) from shopping_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			rs = pscheck.executeQuery();
			if(rs.next())
				se = rs.getDouble(1);
			//miscellaneous expense
			pscheck = con.prepareStatement("select sum(Amount) from miscellaneous_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			rs = pscheck.executeQuery();
			if(rs.next())
				me = rs.getDouble(1);
		}catch(Exception x) {x.printStackTrace();};
		food = fe;
		ent = e;
		shop = se;
		misc = me;
		tran = te;
	}
	
	public static void balandexp() {
		Connection con = null;
		PreparedStatement pscheck = null;
		ResultSet rs = null;
		double exp=0.0;
		double bal=0.0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");
			//total expenses
			pscheck = con.prepareStatement("select Amount_spent from expenditure_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			rs = pscheck.executeQuery();
			if(rs.next())
				exp = rs.getDouble(1);
			//current balance
			pscheck = con.prepareStatement("select Balance_Amount from balance_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			rs = pscheck.executeQuery();
			if(rs.next())
				bal = rs.getDouble(1);
		}catch(Exception e) {e.printStackTrace();};
		total_expense=exp;
		total_balance=bal;
	}
	
	public static List<Expenselist> expenselist(){
		List<Expenselist> ls = new ArrayList<>();
		Expenselist el; 
		Connection con = null;
		PreparedStatement pscheck = null;
		ResultSet rs = null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");  
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");
		pscheck = con.prepareStatement("select Amount, time, date from debit_details where U_ID = ?");
		pscheck.setInt(1, u_id);
		rs = pscheck.executeQuery();
		while(rs.next()) {
			el = new Expenselist();
			el.setamount(rs.getDouble(1));
			el.settime(rs.getString(2));
			el.setdate(rs.getString(3));
			ls.add(el);
		}
		}catch(Exception e) {e.printStackTrace();}
		return ls;
	}
	
	public static void daily_graph() {
		Connection con = null;
		PreparedStatement pscheck = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");
			pscheck = con.prepareStatement("select time, Amount from debit_details where U_ID = ?");
			pscheck.setInt(1, u_id);
			daily_details = pscheck.executeQuery();
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public static void set_expense(String type, Double amt, String time, String date) {
		Connection con = null;
		PreparedStatement psinsert = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");
			if(type=="food") {
				psinsert = con.prepareStatement("insert into food_details values(?,?,?,?)");
				psinsert.setInt(1, u_id);
				psinsert.setDouble(2, amt);
				psinsert.setString(3, time);
				psinsert.setString(4, date);
				psinsert.executeQuery();
			}
			
			else if(type=="transportation") {
				psinsert = con.prepareStatement("insert into transportation_details values(?,?,?,?)");
				psinsert.setInt(1, u_id);
				psinsert.setDouble(2, amt);
				psinsert.setString(3, time);
				psinsert.setString(4, date);
				psinsert.executeQuery();
			}
			
			else if(type=="shopping") {
				psinsert = con.prepareStatement("insert into shopping_details values(?,?,?,?)");
				psinsert.setInt(1, u_id);
				psinsert.setDouble(2, amt);
				psinsert.setString(3, time);
				psinsert.setString(4, date);
				psinsert.executeQuery();
			}
			
			else if(type=="entertainment") {
				psinsert = con.prepareStatement("insert into entertaiment_details values(?,?,?,?)");
				psinsert.setInt(1, u_id);
				psinsert.setDouble(2, amt);
				psinsert.setString(3, time);
				psinsert.setString(4, date);
				psinsert.executeQuery();
			}
			else {
				psinsert = con.prepareStatement("insert into miscellaneous_details values(?,?,?,?)");
				psinsert.setInt(1, u_id);
				psinsert.setDouble(2, amt);
				psinsert.setString(3, time);
				psinsert.setString(4, date);
				psinsert.executeQuery();
			}
		}catch(Exception e) {e.printStackTrace();}
	}
	public static void cleartable() {
		Connection con = null;
		PreparedStatement psinsert = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");
			psinsert = con.prepareStatement("delete from debit_details where U_ID = ?");
			psinsert.setInt(1, u_id);
			psinsert.executeUpdate();
		}catch(Exception e) {e.printStackTrace();}
	}
} 

class regexOper {
    String transaction_type = "";
    int transaction_clause;
    String amount_paid = "";
    String time = "";
    String new_date = "";
    double amount;
    void regexWork(String data) {
	    // finding transaction type
	    String pattern_deb = "Debit";
	    String pattern_cred = "Credit";
	    Pattern pt = Pattern.compile(pattern_deb);
	    Pattern pt2 = Pattern.compile(pattern_cred);
	        
	    Matcher m1 = pt.matcher(data);
	    Matcher m2 = pt2.matcher(data);
	        
	    if(m1.find())
	    {
	        transaction_type = m1.group();
	    }
	    else if(m2.find())
	    {
	        transaction_type = m2.group();
	    }
	    else {
	        transaction_type = "Neither";
	    }
	    // end of transaction type    
	    // start of amount
	    Pattern pt3 = Pattern.compile("[0-9]+[^a-zA-Z0-9-:][0-9]+");
	    Matcher m3 = pt3.matcher(data);
	    if(m3.find())
	    {
	        amount_paid = m3.group();
	    }
	    else {
	        amount_paid = "0";
	    }
	    //end of amount
	    // start date
        String date = new String("");
	    Pattern pt4 = Pattern.compile("[0-9]{2}-[0-9]{2}-[0-9]{2}");
	    Matcher m4 = pt4.matcher(data);
	    if(m4.find())
	    {
	        date = m4.group();
	    }
	    else
	    {
	        date = "Not Found";
	    }
	    // end date
	    //start of time
	    Pattern pt5 = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}");
	    Matcher m5 = pt5.matcher(data);
	    if(m5.find())
	    {
	        time = m5.group();
	    }
	    else
	    {
	        time= "not found";
	    }
	    // date string reverse;
	    String day = "";
	    String month = "";
	    String year = "";
	    Pattern days = Pattern.compile("^[0-9]{2}");
	    Matcher days_match = days.matcher(date);
	    if(days_match.find())
	    {
	        day = days_match.group();
	    }
	    else
	    {
	        day = "Not Found";
	    }    
	    Pattern months = Pattern.compile(".[0-9]{2}.");
	    Matcher months_match = months.matcher(date);
	    if(months_match.find())
	    {
	        month = months_match.group();
	    }
	    else
	    {
	        month = "Not Found";
	    }
	    Pattern years = Pattern.compile("[0-9]{2}$");
	    Matcher years_match = years.matcher(date);
	    if(years_match.find())
	    {
	        year = years_match.group();
	    }
	    else
	    {
	        year = "Not Found";
	    }
	    new_date = "20" + year + month + day;
	    // end of string reverse
	    // database management 
	    amount= Double.parseDouble(amount_paid);
		Connection con = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","Achupapa-1");
			if(transaction_clause==0) {
				psinsert = con.prepareStatement("insert into debit_details(U_ID, Amount, Time, Date) values(?,?,?,?)");
				psinsert.setInt(1, DBUtils.u_id);
				psinsert.setDouble(2, amount);
				psinsert.setString(3, time);
				psinsert.setString(4, new_date);
				psinsert.executeUpdate();
				DBUtils.total_expense=DBUtils.total_expense + amount;
				DBUtils.total_balance=DBUtils.total_balance - amount;
				psupdate = con.prepareStatement("update expenditure_details set Amount_spent = ? where U_ID = ?");
				psupdate.setDouble(1, DBUtils.total_expense);
				psupdate.setInt(2, DBUtils.u_id);
				psupdate.executeUpdate();
				psupdate = con.prepareStatement("update balance_details set Balance_Amount = ? where U_ID = ?");
				psupdate.setDouble(1, DBUtils.total_balance);
				psupdate.setInt(2, DBUtils.u_id);
				psupdate.executeUpdate();
			}
			else {
				psinsert = con.prepareStatement("insert into credit_details(U_ID, Amount, Time, Date) values(?,?,?,?)");
				psinsert.setInt(1, DBUtils.u_id);
				psinsert.setDouble(2, amount);
				psinsert.setString(3, time);
				psinsert.setString(4, new_date);
				psinsert.executeUpdate();
				DBUtils.total_balance=DBUtils.total_balance + amount;
				psupdate = con.prepareStatement("update balance_details set Balance_Amount = ? where U_ID = ?");
				psupdate.setDouble(1, DBUtils.total_balance);
				psupdate.setInt(2, DBUtils.u_id);
				psupdate.executeUpdate();
			}
		}catch(Exception e) {e.printStackTrace();}
		
    }
    void messagePartition(String data) {
    	int i;
    	String message = new String("");
    	String dummy = "";
    	for(i=0;data.charAt(i)!='&';i++)
    	{
    		dummy += data.charAt(i);
    		if(data.charAt(i) == '$')
    		{
    			message = dummy;
    	    	this.regexWork(message);
    			dummy = "";
    		}
    	}
    }
}

class regex_applications{
	  public static String readFileAsString(String fileName)throws Exception {
		  String data = "";
		  data = new String(Files.readAllBytes(Paths.get(fileName)));
		  return data;
	  }
	  public static void readmessage() {
		  regexOper oper = new regexOper();
		  String data = new String("");
		  try {
			  data = readFileAsString("C:/Users/Asus/Documents/JavaFX/MyJavaFXProject/src/messages.txt");
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  oper.messagePartition(data);
	  }
}
