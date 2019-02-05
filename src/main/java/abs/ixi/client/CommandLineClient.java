package abs.ixi.client;

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
		try {
			Platform.initialize(new JavaSDKInitializer(SERVER, PORT));
			Platform.getInstance().login("yogi", "alterbasics.com", "default123");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
