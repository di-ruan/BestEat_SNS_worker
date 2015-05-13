import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class RDSWrapper {
	private Connection connect = null;	
	private Statement statement = null;
	private ResultSet resultSet = null;
	private static RDSWrapper db = null;
	
	private RDSWrapper(){ 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//connect = DriverManager.getConnection("jdbc:mysql://besteat.csfqneogzevi.us-west-2.rds.amazonaws.com:3306/BestEat?"
	         //       + "user=besteat&password=cloudcomputing");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:8889/BestEat?"
	                + "user=root&password=root");
			statement = connect.createStatement();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static RDSWrapper getInstance() {
		try {
			if(db == null) {
				db = new RDSWrapper();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return db;
	}
	
	/*
	public ArrayList<Coupon> getRestaurantCoupons(int restaurantId) {
		ArrayList<Coupon> list = new ArrayList<Coupon>();
		try {
			ResultSet resultSet = statement.executeQuery("Select * from UserInfo Where facebookId = ");			
			while(resultSet.next()) {
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return list;
	}
	
	public ResultSet readUser(String facebookId) {
		try {
			resultSet = statement.executeQuery("Select * from UserInfo Where facebookId = " + facebookId);			
			while(resultSet.next()) {
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	
		return resultSet;
	}
	
	public void updateCustomerRegisterId(int userId, String registerId) {
		try {
			String query = "Update UserInfo Set registerId='" + registerId + "' Where userId=" + userId;
			System.out.println(query);
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateCustomerPosition(int userId, double lng, double lat) {
		try {
			String query = "Update UserInfo Set lng=" + lng + ",lat=" + lat + " Where userId=" + userId;
			System.out.println(query);
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	public ResultSet addData(String dbName, ArrayList<Object> list) {
		try {
			String query = "Insert into " + dbName;
			if(dbName.equals("ManagerConfig")) {
				query += " (userId,chinese,colombian,indian,italian,japanese,mexican,latitude,longitude,restaurantAddress,"
						+ "restaurantName,restaurantPhoneNumber,restaurantZipCode) values ('"
			+ list.get(0) + "','" + list.get(1) + "','" + list.get(2) + "','" + list.get(3) +  "','"
			+ list.get(4) + "','" + list.get(5) + "','" + list.get(6) + "','" + list.get(7) +  "','"
			+ list.get(8) + "','" + list.get(9) + "','" + list.get(10) + "','" + list.get(11) + "','" + list.get(12) + "')";
				
			} else if(dbName.equals("CustomerConfig")) {
				query += " (userId) values ('" + list.get(0) +"')";		
				
			}
			System.out.println(query);
			statement.executeUpdate(query);
		} catch(Exception e) {
			e.printStackTrace();
		}	
		return resultSet;
	}
	*/
}
