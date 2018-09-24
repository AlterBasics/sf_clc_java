package abs.ixi.client;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import abs.ixi.client.core.Platform;
import abs.ixi.client.net.ConnectionConfigBuilder;
import abs.ixi.client.util.UUIDGenerator;
import abs.ixi.client.xmpp.JID;

public class ActiveChatClient extends Client {
	private static final Logger LOGGER = Logger.getLogger(O2OChatClient.class.getName());

	public static void main(String[] args) {
		ActiveChatClient client = new ActiveChatClient();

		try {
			for (int i = 0; i < 5000; i++) {
				LOGGER.log(Level.FINE, "@@@@@@@@@@@@@@@@@@@@ Connection count:" + (i + 1));
				client.init();
				client.start();
			}

		} catch (ClientStartupException e) {
			LOGGER.log(Level.WARNING, "Failed to start O2OChat client", e);

			if (client != null) {
				client.stop();
			}

			System.exit(1);
		}
	}

	@Override
	public void init() throws ClientStartupException {
		super.init();
	}

	@Override
	public void start() throws ClientStartupException {
		super.start();
		readyToChat();
	}

	private void readyToChat() {
		try {
			LOGGER.log(Level.FINE, "Waiting for Stream ready");
			TimeUnit.SECONDS.sleep(1);

			O2OChatter chatter = new O2OChatter();
			new Thread(chatter).start();

			LOGGER.log(Level.FINE, "Client is ready to start chatting");

		} catch (InterruptedException e) {
			LOGGER.log(Level.FINE, "Client is not working due to " + e.getMessage());
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

	private class O2OChatter implements Runnable {
		@Override
		public void run() {
			try {
				// LOGGER.log(Level.FINE, "Whom do you want to chat :");
				String user = "dharmu";

				JID receiver = new JID(user, clientConfig.getDomain());

				// LOGGER.fine("Write your msg : ");
				String msg = "Hello";

				ChatManager chatManager = Platform.getInstance().getChatManager();

				while (!Thread.currentThread().isInterrupted()) {
					chatManager.say(UUIDGenerator.secureId(), msg, receiver);
					TimeUnit.SECONDS.sleep(10);
				}

			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Chatting is intrupted due to some error", e);
			}
		}
	}

}
