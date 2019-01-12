package abs.ixi.client;

import abs.ixi.client.net.NetworkException;

/**
 * Stringflow Command Line Client
 * 
 * @author Yogi
 *
 */
public class CommandLineClient {
	private static final String SERVER = "alterbasics.com";
	private static final int PORT = 5222;

	public static void main(String[] args) {
		Platform.initialize(new JavaSDKInitializer(SERVER, PORT));

		try {
			Platform.getInstance().login("yogi", "alterbasics.com", "default123");
		} catch (NetworkException e) {
			System.out.println(e);
		}
	}

}
