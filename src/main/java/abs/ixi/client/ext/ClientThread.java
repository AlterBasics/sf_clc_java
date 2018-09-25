package abs.ixi.client.ext;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

public class ClientThread extends Thread {
    private int id;
    SmackClient client;
    String to;

    public ClientThread(int i, SmackClient client, String to) {
	this.id = i;
	this.client = client;
	this.to = to;
    }

    @Override
    public void run() {
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	System.out.println("Connecting :" + id + " Timestamp:" + dateFormat.format(date));
	try {
	    client.init(to);
	    date = new Date();
	    System.out.println("Connected :" + id + " Timestamp:" + dateFormat.format(date));

	} catch (XMPPException e) {
	    e.printStackTrace();
	} catch (SmackException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
