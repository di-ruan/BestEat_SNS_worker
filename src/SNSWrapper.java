import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;


public class SNSWrapper {
	private static AmazonSNSClient amazonSNSClient = null;
	private static String TopicArn = "arn:aws:sns:us-west-2:146409140165:Coupon";

	private static void createSNS() {
		AWSCredentials credentials = null;
		try {
			credentials = new PropertiesCredentials(
					SNSWrapper.class
							.getResourceAsStream("AwsCredentials.properties"));
			amazonSNSClient = new AmazonSNSClient(credentials);		
			amazonSNSClient.setRegion(Region.getRegion(Regions.US_WEST_2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMessage(String msg) {
		if(amazonSNSClient == null) {
			createSNS();
		}
		try {
			PublishRequest publishReq = new PublishRequest().withTopicArn(TopicArn).withMessage(msg);
			amazonSNSClient.publish(publishReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
