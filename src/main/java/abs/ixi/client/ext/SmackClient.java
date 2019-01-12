package abs.ixi.client.ext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class SmackClient {
	private String server;
	private String user;
	private String pwd;
	private int port;

	public SmackClient(String server, String user, String pwd, int port) {
		this.server = server;
		this.user = user;
		this.pwd = pwd;
		this.port = port;
	}

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("XMPP Server you want to connect to: ");
			String fqdn = reader.readLine();

			System.out.println("What is the server port:");
			int port = Integer.parseInt(reader.readLine());

			System.out.println("User Name:");
			String userName = reader.readLine();

			System.out.println("Whom do you want to chat with : ");
			String to = reader.readLine();

			String passwd = "default";

			SmackClient client = new SmackClient(fqdn, userName, passwd, port);

			for (int i = 0; i < 1; i++) {
				(new ClientThread(i, client, to)).start();
				Thread.sleep(1);
			}

		} catch (NumberFormatException e) {
			System.out.println("Failed to parse port string");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(String to) throws XMPPException, SmackException, IOException {
		System.out.println("Initializing XMPP Command Line client...");

		XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

		config.setUsernameAndPassword(user, pwd);
		config.setServiceName("alterbasics.com");
		config.setHost(server);
		config.setDebuggerEnabled(true);
		// config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		config.setConnectTimeout(Integer.MAX_VALUE);
		AbstractXMPPConnection connection = new XMPPTCPConnection(config.build());
		connection.setPacketReplyTimeout(1000 * 60 * 500);

		// SASLAuthentication.registerSASLMechanism(new
		// SASLDigestMD5Mechanism());
		connection.connect();
		System.out.println("Connected successfully");

		connection.login();

		Chat chat = ChatManager.getInstanceFor(connection).createChat(to + "@alterbasics.com",
				new ChatMessageListener() {

					@Override
					public void processMessage(Chat arg0, Message msg) {
						System.out.println("Received message: " + msg);

					}
				});

		System.out.println("Sending chat messages...");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// reader.readLine();

		/*
		 * connection.sendStanza(new Stanza() {
		 * 
		 * @Override public CharSequence toXML() {
		 * 
		 * return
		 * "<iq from='juliet@example.com/balcony' id='ph1xaz53' type='set'>" +
		 * "<query xmlns='jabber:iq:roster'>" +
		 * "<item jid='jim@alterbasics.com' name='Jim' subscription='both'>" +
		 * "</item>" + "</query>" + "</iq>"; } });
		 * 
		 */
		String msg = "Hi";
		// int i = 0;
		// msg = reader.readLine();
		// while(i <1) {
		// System.out.println("Count : "+i);
		// chat.sendMessage(msg);
		// i++;
		// }

		while (true) {
			System.out.println("Type your msg");
			msg = reader.readLine();
			chat.sendMessage(msg);

			if (msg.equals("bye")) {
				break;
			}

			/*
			 * if(msg.equals("bye")){ break;
			 * 
			 * }
			 */
		}

	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void addRosterItem(AbstractXMPPConnection connection, String selfFullJid, String itemJid, String name)
			throws NotConnectedException {

		connection.sendStanza(new Stanza() {

			@Override
			public CharSequence toXML() {

				return String.format(
						"<iq from='%s' id='ph1xaz53' type='set'>" + "<query xmlns='jabber:iq:roster'>"
								+ "<item jid='%s' name='%s'>" + "</item>" + "</query>" + "</iq>",
						selfFullJid, itemJid, name);

			}
		});

	}

	public void delRosterItem(AbstractXMPPConnection connection, String selfFullJid, String itemJid, String name)
			throws NotConnectedException {
		connection.sendStanza(new Stanza() {

			@Override
			public CharSequence toXML() {
				return String.format(
						"<iq from='juliet@example.com/balcony' type='set' id='roster_4'>"
								+ "<query xmlns='jabber:iq:roster'>"
								+ "<item jid='abc@alterbasics.com' subscription='remove'/>" + "</query>" + "</iq>",
						selfFullJid, itemJid, name);

			}
		});
	}

	public void sendUserRegistration(AbstractXMPPConnection connection) throws NotConnectedException, IOException {
		connection.sendStanza(new Stanza() {

			@Override
			public CharSequence toXML() {
				return "<iq type='get' id='reg1' to='admin@alterbasics.com'>" + "<query xmlns='jabber:iq:register'/>"
						+ "</iq>";

			}
		});

		System.out.println("Sending Registration");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		reader.readLine();

		connection.sendStanza(new Stanza() {

			@Override
			public CharSequence toXML() {
				return "<iq type='set' id='reg1'>" + "<query xmlns='jabber:iq:register'>"
						+ "<username>juliet</username>" + "<password>R0m30</password>"
						+ "<email>juliet@capulet.com</email>" + "</query>" + "</iq>";

			}
		});
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
