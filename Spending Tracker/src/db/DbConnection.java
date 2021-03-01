package db;
import java.sql.*;

import javax.swing.JOptionPane;

public class DbConnection {
	
  public static Connection con;
  public static Statement stmt;
  static {
	  try {
		 
		  con= DriverManager.getConnection("jdbc:mysql://localhost:3307/spendingdb" + "?useSSL=false","root","root");
		  stmt =con.createStatement();
          
	  }catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex);

		}
  }
  


}
