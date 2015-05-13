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
	                + "user=other&password=root");
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
	
	public void getRestaurantAndCoupon() {
		try {
			String query = "select x.couponId, CustomerConfig.customerId from  (select Coupon.*, R.chinese chinese , "
					+ "R.colombian colombian, R.indian indian, R.italian italian, R.japanese japanese,"
					+ "R.mexican mexican, R.latitude latitude, R.longitude longitude, R.restaurantName restaurantName "
					+ "from Coupon, RestaurantConfig R where timeDeliver < now() and timeEnd > now() "
					+ " and R.restaurantId = Coupon.restaurantId) x, CustomerConfig, CustomerInfo"
					+ " where ( (x.chinese = 'yes' and  CustomerConfig.chinese = 'yes') or "
					+	"(x.colombian = 'yes' and  CustomerConfig.colombian = 'yes') or "
					+	"(x.indian = 'yes' and  CustomerConfig.indian = 'yes') or "
					+	"(x.italian = 'yes' and  CustomerConfig.italian = 'yes') or "
					+	"(x.japanese = 'yes' and  CustomerConfig.japanese = 'yes') or "
					+	"(x.mexican = 'yes' and  CustomerConfig.mexican = 'yes') "
					+	") and CustomerConfig.customerId = CustomerInfo.customerId and x.couponId not in ( "
					+	"select couponId from CustomerCoupon)";
			System.out.println(query);
			ResultSet resultSet2 = statement.executeQuery(query);	
			while(resultSet2.next()) {
				int couponId = resultSet2.getInt("x.couponId");
				int customerId = resultSet2.getInt("CustomerConfig.customerId");
				String query2 = "Insert into CustomerCoupon "
						+ "(couponId, customerId) values ('" 
						+ couponId + "','" + customerId + "')";
				Statement statement2 = connect.createStatement();
				statement2.executeUpdate(query2);
				SNSWrapper.sendMessage(Integer.toString(customerId));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
