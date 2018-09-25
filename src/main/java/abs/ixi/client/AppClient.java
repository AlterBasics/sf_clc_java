package abs.ixi.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import abs.ixi.client.net.ConnectionConfigBuilder;

public class AppClient extends Client {
	private static final Logger LOGGER = Logger.getLogger(O2OChatClient.class.getName());

	public static void main(String[] args) {
		AppClient client = new AppClient();

		try {
			client.init();
			client.start();

		} catch (ClientStartupException e) {
			LOGGER.log(Level.WARNING, "Failed to start client", e);

			if (client != null) {
				client.stop();
			}

			System.exit(1);
		}
	}

	@Override
	protected ConnectionConfigBuilder getConnectionConfigBuilder() {
		return new ConnectionConfigBuilder();
	}

	@Override
	protected ClientConfigBuilder getClientConfigBuilder() {
		return new ClientConfigBuilder();
	}
}
