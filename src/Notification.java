import com.google.gson.Gson;


public class Notification {
	public int customerId;
	public Message data = new Message();
		
	public String getJSONString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public class Message {
		public String title = "";
		public String message = "";
		public int id = 0;
	}
}
