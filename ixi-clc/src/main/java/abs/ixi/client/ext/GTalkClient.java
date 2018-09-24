package abs.ixi.client.ext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class GTalkClient {
    private String user;
    private String pwd;
    private int port;

    public GTalkClient(String user, String pwd, int port) {
	this.user = user;
	this.pwd = pwd;
	this.port = port;
    }

    public static void main(String[] args) {
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	try {
	    System.out.println("What is the server port:");
	    int port = Integer.parseInt(reader.readLine());

	    System.out.println("User Name:");
	    String userName = reader.readLine();

	    String passwd = "07@mmini90";

	    GTalkClient client = new GTalkClient(userName, passwd, port);

	    client.init();

	} catch (NumberFormatException e) {
	    System.out.println("Failed to parse port string");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void init() throws XMPPException, SmackException, IOException {
	System.out.println("Initializing XMPP Command Line client...");

	XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
	config.setSecurityMode(ConnectionConfiguration.SecurityMode.required);

	config.setUsernameAndPassword(this.user, this.pwd);
	config.setServiceName("gmail.com");
	config.setHost("talk.google.com");
	config.setPort(port);
	config.setDebuggerEnabled(true);
	// config.setSecurityMode(SecurityMode.disabled);
	config.setConnectTimeout(1000 * 60 * 100);

	AbstractXMPPConnection connection = new XMPPTCPConnection(config.build());
	connection.connect();
	System.out.println("Connected successfully");

	connection.login();

	Chat chat = ChatManager.getInstanceFor(connection).createChat("akhil7490@gmail.com", new ChatMessageListener() {

	    @Override
	    public void processMessage(Chat arg0, Message msg) {
		System.out.println("Received message: " + msg);

	    }
	});

	System.out.println("Sending chat messages...");

	chat.sendMessage("<body><hello></body>");

    }

    public void setUser(String user) {
	this.user = user;
    }

    public void setPwd(String pwd) {
	this.pwd = pwd;
    }

}
