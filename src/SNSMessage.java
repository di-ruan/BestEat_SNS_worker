import java.io.InputStream;
import java.net.URL;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;


public class SNSMessage {
	public String Type;
	public String MessageId;
	public String Token;
	public String TopicArn;
	public String Subject;
	public String Message;
	public String SubscribeURL;
	public String Timestamp;
	public String SignatureVersion;
	public String Signature;
	public String SigningCertUrl;
	public String UnsubscribeURL;

	public SNSMessage(String JSON) {
		Gson gson = new Gson();
		SNSMessage msg = gson.fromJson(JSON, this.getClass());
		this.Type = msg.Type;
		this.MessageId = msg.MessageId;
		this.Token = msg.Token;
		this.TopicArn = msg.TopicArn;
		this.Subject = msg.Subject;
		this.Message = msg.Message;
		this.SubscribeURL = msg.SubscribeURL;
		this.Timestamp = msg.Timestamp;
		this.SignatureVersion = msg.SignatureVersion;
		this.Signature = msg.Signature;
		this.SigningCertUrl = msg.SigningCertUrl;
		this.UnsubscribeURL = msg.UnsubscribeURL;
	}

	public boolean isMessageSignatureValid() {
		try {
			URL url = new URL(this.SigningCertUrl);
			InputStream inStream = url.openStream();
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf
					.generateCertificate(inStream);
			inStream.close();

			Signature sig = java.security.Signature.getInstance("SHA1withRSA");
			sig.initVerify(cert.getPublicKey());
			sig.update(getMessageBytesToSign());
			return sig.verify(Base64.decodeBase64(this.Signature));
		} catch (Exception e) {
			throw new SecurityException("Verify method failed.", e);
		}
	}

	private byte[] getMessageBytesToSign() {
		byte[] bytesToSign = null;
		if (this.Type.equals("Notification"))
			bytesToSign = buildNotificationStringToSign().getBytes();
		else if (this.Type.equals("SubscriptionConfirmation")
				|| this.Type.equals("UnsubscribeConfirmation"))
			bytesToSign = buildSubscriptionStringToSign().getBytes();
		return bytesToSign;
	}

	// Build the string to sign for Notification messages.
	public String buildNotificationStringToSign() {
		String stringToSign = null;
		// Build the string to sign from the values in the message.
		// Name and values separated by newline characters
		// The name value pairs are sorted by name
		// in byte sort order.
		stringToSign = "Message\n";
		stringToSign += this.Message + "\n";
		stringToSign += "MessageId\n";
		stringToSign += this.MessageId + "\n";
		if (this.Subject != null) {
			stringToSign += "Subject\n";
			stringToSign += this.Subject + "\n";
		}
		stringToSign += "Timestamp\n";
		stringToSign += this.Timestamp + "\n";
		stringToSign += "TopicArn\n";
		stringToSign += this.TopicArn + "\n";
		stringToSign += "Type\n";
		stringToSign += this.Type + "\n";
		return stringToSign;
	}

	// Build the string to sign for SubscriptionConfirmation
	// and UnsubscribeConfirmation messages.
	public String buildSubscriptionStringToSign() {
		String stringToSign = null;
		stringToSign = "Message\n";
		stringToSign += this.Message + "\n";
		stringToSign += "MessageId\n";
		stringToSign += this.MessageId + "\n";
		stringToSign += "SubscribeURL\n";
		stringToSign += this.SubscribeURL + "\n";
		stringToSign += "Timestamp\n";
		stringToSign += this.Timestamp + "\n";
		stringToSign += "Token\n";
		stringToSign += this.Token + "\n";
		stringToSign += "TopicArn\n";
		stringToSign += this.TopicArn + "\n";
		stringToSign += "Type\n";
		stringToSign += this.Type + "\n";
		return stringToSign;
	}
}
