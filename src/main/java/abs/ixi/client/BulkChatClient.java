package abs.ixi.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import abs.ixi.client.core.Packet;
import abs.ixi.client.io.StreamNegotiator.NegotiationResult;
import abs.ixi.client.io.XMPPStreamManager;
import abs.ixi.client.net.ConnectionConfig;
import abs.ixi.client.net.ConnectionConfigBuilder;
import abs.ixi.client.util.ObjectUtils;
import abs.ixi.client.util.UUIDGenerator;
import abs.ixi.client.xmpp.InvalidJabberId;
import abs.ixi.client.xmpp.JID;
import abs.ixi.client.xmpp.packet.Message;
import abs.ixi.client.xmpp.packet.MessageBody;

public class BulkChatClient extends Client {
	private static final Logger LOGGER = Logger.getLogger(BulkChatClient.class.getName());

	private List<ChatManager> managerList;
	private ExecutorService threadPool;

	private boolean fireBulkMsg;

	private AtomicInteger receivedMessageCount;

	BulkChatClient() {
		this.receivedMessageCount = new AtomicInteger();
		this.managerList = new ArrayList<>();
		this.threadPool = Executors.newCachedThreadPool();
	}

	public static void main(String args[]) {
		BulkChatClient client = new BulkChatClient();

		try {
			client.init();
			client.start();

		} catch (ClientStartupException e) {
			LOGGER.log(Level.FINE, "Failed to start Bulk chat client", e);

			if (client != null) {
				client.stop();
			}

			System.exit(1);
		}
	}

	@Override
	protected ClientConfig prepareClientConfig(ConnectionConfig connConf, ClientConfigBuilder builder)
			throws ClientStartupException {

		super.prepareClientConfig(connConf, builder);

		int connectionCount = ObjectUtils.parseToInt(this.clientProps.getProperty(PropNames.CONNECTION_COUNT));
		int bulkMsgCount = ObjectUtils.parseToInt(this.clientProps.getProperty(PropNames.BULK_MSG_COUNT));
		int connectionInterval = ObjectUtils.parseToInt(this.clientProps.getProperty(PropNames.CONNECTION_INTERVAL));

		String message = this.clientProps.getProperty(PropNames.MESSAGE);
		String recepient = this.clientProps.getProperty(PropNames.RECEPIENT);

		BulkClientConfigBuilder bulkConfBuilder = (BulkClientConfigBuilder) builder;

		return bulkConfBuilder.withConnectionCount(connectionCount).withBulkMsgCount(bulkMsgCount)
				.withConnectionInterval(connectionInterval).withMessage(message).withRecepient(recepient).build();
	}

	@Override
	public void start() throws ClientStartupException {
		LOGGER.log(Level.FINE, "Starting BulkChatClient");

		try {
			if (isInitilized()) {
				setupConnections();

				LOGGER.log(Level.FINE, "Created connection count : " + managerList.size());

				LOGGER.log(Level.FINE, "Waiting for end of stream negotiation");
				TimeUnit.MILLISECONDS.sleep(5000);

				writeBulkMessages();

			} else {
				LOGGER.log(Level.FINE, "Client is not yet initilized. Failed to start. Exiting...");
				throw new ClientStartupException("IXI client is not yet initilized");
			}

		} catch (Exception e) {
			LOGGER.log(Level.FINE, "Failed to Start Bulk chat client");
			throw new ClientStartupException(e);
		}

	}

	private void writeBulkMessages() throws Exception {
		String bulkMsg = buildBulkMessage(getBulkClientConf().getBulkMsgCount(), getBulkClientConf().getMessage(),
				getBulkClientConf().getUsername(), getBulkClientConf().getRecepient());

		for (ChatManager chatManager : managerList) {
			this.threadPool.submit(new BulkMsgSender(chatManager, bulkMsg, false));
		}

		this.fireBulkMsg = true;
	}

	private void setupConnections() throws Exception {
		if (getBulkClientConf().getConnectionCount() > 0) {
			LOGGER.log(Level.FINE, "Creating " + getBulkClientConf().getConnectionCount() + " server connections");

			for (int i = 0; i < getBulkClientConf().getConnectionCount(); i++) {
				XMPPStreamManager streamManager = prepareXMPPStreamManager();
				UserManager userManager = new UserManager(streamManager);
				// Initiate Login
				NegotiationResult output = userManager.login(this.clientConfig.getUsername(),
						this.clientConfig.getPassword(), this.clientConfig.getDomain());

				if (output.isError()) {
					throw new RuntimeException("login failed");
				}

				ChatManager chatMgr = new ChatManager(streamManager);
				managerList.add(chatMgr);
			}

		} else {
			LOGGER.log(Level.FINE, "No connection created");
		}
	}

	private String buildBulkMessage(long count, String msg, String sender, String recepient) throws InvalidJabberId {
		StringBuilder messages = new StringBuilder();

		MessageBody body = new MessageBody(msg);
		Message message = new Message(UUIDGenerator.secureId());
		message.addContent(body);

		message.setTo(new JID(recepient, this.clientConfig.getDomain()));
		message.setFrom(new JID(sender, this.clientConfig.getDomain()));

		for (long i = 0; i < count; i++) {
			messages.append(message.xml());
		}

		return messages.toString();
	}

	@Override
	protected ConnectionConfigBuilder getConnectionConfigBuilder() {
		return new ConnectionConfigBuilder();
	}

	@Override
	protected ClientConfigBuilder getClientConfigBuilder() {
		return new BulkClientConfigBuilder();
	}

	public BulkClientConfig getBulkClientConf() {
		return (BulkClientConfig) this.clientConfig;
	}

	class BulkClientConfigBuilder extends ClientConfigBuilder {

		BulkClientConfigBuilder() {
			super(new BulkClientConfig());
		}

		public BulkClientConfigBuilder withConnectionCount(long count) {
			((BulkClientConfig) this.config).setConnectionCount(count);
			return this;
		}

		public BulkClientConfigBuilder withBulkMsgCount(long bulkMsgCount) {
			((BulkClientConfig) this.config).setBulkMsgCount(bulkMsgCount);
			return this;
		}

		public BulkClientConfigBuilder withMessage(String message) {
			((BulkClientConfig) this.config).setMessage(message);
			return this;
		}

		public BulkClientConfigBuilder withRecepient(String recepient) {
			((BulkClientConfig) this.config).setRecepient(recepient);
			return this;
		}

		public BulkClientConfigBuilder withConnectionInterval(int connectionInterval) {
			((BulkClientConfig) this.config).setConnectionInterval(connectionInterval);
			return this;
		}

	}

	class BulkClientConfig extends ClientConfig {
		private long connectionCount;
		private long bulkMsgCount;
		private String message;
		private String recepient;
		private int connectionInterval;

		public long getConnectionCount() {
			return connectionCount;
		}

		public long getBulkMsgCount() {
			return bulkMsgCount;
		}

		public void setBulkMsgCount(long bulkMsgCount) {
			this.bulkMsgCount = bulkMsgCount;
		}

		public void setConnectionCount(long connCount) {
			this.connectionCount = connCount;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getRecepient() {
			return recepient;
		}

		public void setRecepient(String recepient) {
			this.recepient = recepient;
		}

		public int getConnectionInterval() {
			return connectionInterval;
		}

		public void setConnectionInterval(int connectionInterval) {
			this.connectionInterval = connectionInterval;
		}

	}

	private class BulkMsgSender implements Runnable {
		private ChatManager chatManager;
		private String msg;

		BulkMsgSender(ChatManager chatManager, String msg, boolean escapeSpecialCharacters) {
			this.chatManager = chatManager;
			this.msg = msg;
		}

		@Override
		public void run() {
			LOGGER.log(Level.FINE, "Waiting for fire event");

			while (!fireBulkMsg) {
				// waiting for fire Event
			}

			LOGGER.log(Level.FINE, "Revceived fire event");

			try {
				this.chatManager.sendRawMsg(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void collect(Packet packet) {
		if (packet instanceof Message) {
			Message message = (Message) packet;
			receivedMessageCount.incrementAndGet();
			LOGGER.log(Level.FINE, "Received message (count" + receivedMessageCount + "): " + message.xml());
		}
	}

}
