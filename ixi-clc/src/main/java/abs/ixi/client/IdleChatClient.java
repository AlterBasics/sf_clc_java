package abs.ixi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import abs.ixi.client.core.Platform;
import abs.ixi.client.net.ConnectionConfigBuilder;
import abs.ixi.client.util.StringUtils;
import abs.ixi.client.util.UUIDGenerator;
import abs.ixi.client.xmpp.JID;

public class IdleChatClient extends Client {
    private static final Logger LOGGER = Logger.getLogger(O2OChatClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
	IdleChatClient client = new IdleChatClient();

	try {

	    for (int i = 0; i < 60000; i++) {

		client.init();
		client.start();
		LOGGER.log(Level.FINE, "@@@@@@@@@@@@@@@@@@@Connection Count :" + (i + 1));
		TimeUnit.MILLISECONDS.sleep(10);
	    }
	    TimeUnit.MINUTES.sleep(50);

	} catch (ClientStartupException e) {
	    LOGGER.log(Level.WARNING, "Failed to start IdleChatClient client", e);

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
	    // TimeUnit.SECONDS.sleep(5);
	    //
	    // O2OChatter chatter = new O2OChatter();
	    // new Thread(chatter).start();
	    //
	    // LOGGER.log(Level.FINE, "Client is ready to start chatting");

	} catch (Exception e) {
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

    class O2OChatter implements Runnable {
	@Override
	public void run() {
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
		LOGGER.log(Level.FINE, "Whom do you want to chat :");
		String user = reader.readLine();

		JID receiver = new JID(user, clientConfig.getDomain());

		ChatManager chatManager = Platform.getInstance().getChatManager();

		while (!Thread.currentThread().isInterrupted()) {
		    LOGGER.fine("Write your msg : ");
		    String msg = reader.readLine();

		    if (StringUtils.safeEquals(msg, "BYE", false)) {
			LOGGER.log(Level.FINE, "Chatting is over");
			break;

		    } else {
			chatManager.say(UUIDGenerator.secureId(), msg, receiver);
		    }
		}

	    } catch (Exception e) {
		LOGGER.log(Level.WARNING, "Chatting is intrupted due to some error", e);
	    }
	}
    }

}
