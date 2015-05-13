
public class SNSWorker {
	
	public static void main(String[] args) {
		while(true){
			RDSWrapper.getInstance().getRestaurantAndCoupon();		
			try{
				System.out.println("No more messages. Going to sleep...");
				Thread.sleep(10000);
			}catch(Exception e){ 
				e.printStackTrace();
			}
			System.out.println("Woke up");
		}
	}
}
