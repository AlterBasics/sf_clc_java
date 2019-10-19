package abs.ixi.client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import abs.ixi.client.io.StreamNegotiator.NegotiationResult;
import abs.ixi.client.xmpp.JID;

/**
 * Stringflow Command Line Client
 * 
 * @author Yogi
 *
 */
public class CommandLineClient {
	private static final Logger LOGGER = Logger.getLogger(CommandLineClient.class.getSimpleName());

	private static final String SERVER = "142.93.208.62";
	private static final int PORT = 5222;

	public static void main(String[] args) {
		try {
			Platform.initialize(new JavaSDKInitializer(SERVER, PORT));
		} catch (Exception e1) {
			System.out.println("Failed to load SDK");
			e1.printStackTrace();
			System.exit(-1);
		}

		try (Scanner sc = new Scanner(System.in)) {
			NegotiationResult result = Platform.getInstance().login("yogi", "alterbasics.com", "default");
			LOGGER.log(Level.INFO, "Negotiation result:" + result);

			if (result.isSuccess()) {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Another JID:");

				String jid = sc.nextLine();
				LOGGER.log(Level.INFO, "Connecting with {0}", jid);

				Platform.getInstance().getChatManager().say("clc-msg-100", "Hello Mr Green", new JID(jid));

				sendMessage(new JID(jid), 100, 1, TimeUnit.SECONDS);
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Caught Exception; shutting down client", e);
			Platform.getInstance().shutdown();
		}
	}

	private static void sendMessage(JID jid, int count, int unit, TimeUnit seconds) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			Platform.getInstance().getChatManager().say("clc-msg-1010", "Hi Test", jid);
			seconds.sleep(unit);
		}
	}

}
