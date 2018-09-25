package abs.ixi.client;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import abs.ixi.client.core.AbstractPacketCollector;
import abs.ixi.client.core.Packet;
import abs.ixi.client.core.Platform;
import abs.ixi.client.io.StreamNegotiator.NegotiationResult;
import abs.ixi.client.io.XMPPStreamManager;
import abs.ixi.client.lang.Duration;
import abs.ixi.client.net.Connection;
import abs.ixi.client.net.ConnectionConfig;
import abs.ixi.client.net.ConnectionConfigBuilder;
import abs.ixi.client.net.ConnectionManager;
import abs.ixi.client.net.NetworkException;
import abs.ixi.client.net.XMPPConnection;
import abs.ixi.client.util.ConfigLoader;
import abs.ixi.client.util.ObjectUtils;
import abs.ixi.client.xmpp.packet.IQ;
import abs.ixi.client.xmpp.packet.Message;

/**
 * Base class for all the test clients for IXI server. This class includes
 * implementation for common task such as configuration reading, connection
 * setup, stream negotiation, user login etc.
 */
public abstract class Client extends AbstractPacketCollector {
	private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

	protected static final String FILE_CLIENT_CONFIG = "client.properties";
	private static final String FILE_CONNECTION_CONFIG = "connection.properties";

	protected Properties connectionProps;
	protected Properties clientProps;

	protected ClientConfig clientConfig;

	protected boolean initilized;

	public boolean isInitilized() {
		return initilized;
	}

	/**
	 * Returns the build which will be used to build {@link ConnectionConfig}
	 * object
	 */
	protected abstract ConnectionConfigBuilder getConnectionConfigBuilder();

	/**
	 * Returns {@link ClientConfigBuilder} instance which will be used to build
	 * {@link ClientConfig} object
	 */
	protected abstract ClientConfigBuilder getClientConfigBuilder();

	/**
	 * Build {@link ConnectionConfig} as well as {@link ClientConfig} objects
	 * 
	 * @throws ClientStartupException
	 */
	public void init() throws ClientStartupException {
		LOGGER.log(Level.FINE, "Initializing IXI client");

		ConnectionConfig connConfig = prepareConectionConfig(getConnectionConfigBuilder());
		this.clientConfig = prepareClientConfig(connConfig, getClientConfigBuilder());

		this.initilized = true;
		LOGGER.log(Level.FINE, "IXI client has bean initilized successfully");
	}

	public void start() throws ClientStartupException {
		LOGGER.log(Level.FINE, "Starting IXI client");

		try {
			if (initilized) {
				XMPPStreamManager streamManager = prepareXMPPStreamManager();

				new ChatManager(streamManager);
				new PresenceManager(streamManager);
				new UserManager(streamManager);

				ConnectionManager.getInstance().setNetworkConnectivity(true);

				NegotiationResult output = Platform.getInstance().getUserManager().login(
						this.clientConfig.getUsername(), this.clientConfig.getPassword(),
						this.clientConfig.getDomain());

				if (output.isError()) {
					throw new RuntimeException("login failed");
				}

			} else {
				System.out.println("Client is not yet initilized. Failed to start. Exiting...");
				throw new ClientStartupException("IXI client is not yet initilized");
			}

		} catch (Exception e) {
			LOGGER.log(Level.FINE, "Failed to connect to server due to IOException", e);
			throw new ClientStartupException(e);
		}
	}

	public XMPPStreamManager prepareXMPPStreamManager() throws NetworkException {
		ConnectionManager.instantiate(this.clientConfig.getConnectionConfig());
		ConnectionManager connManager = ConnectionManager.getInstance();
		Connection connection = connManager.getXmppConnection();
		XMPPStreamManager streamManager = new XMPPStreamManager((XMPPConnection) connection);

		return streamManager;
	}

	protected ConnectionConfig prepareConectionConfig(ConnectionConfigBuilder builder) throws ClientStartupException {
		LOGGER.log(Level.FINE, "preparing connection configrations");

		this.connectionProps = loadConfigs(FILE_CONNECTION_CONFIG);

		String xmppServerIP = this.connectionProps.getProperty(PropNames.XMPP_SERVER_IP);
		int xmppServerPort = ObjectUtils.parseToInt(this.connectionProps.getProperty(PropNames.XMPP_SERVER_PORT));
		String mediaServerIP = this.connectionProps.getProperty(PropNames.MEDIA_SERVER_IP);
		int mediaServerPort = ObjectUtils.parseToInt(this.connectionProps.getProperty(PropNames.MEDIA_SERVER_PORT));
		int hbf = ObjectUtils.parseToInt(this.connectionProps.getProperty(PropNames.HEART_BEAT_FREQUENCY));
		int replyTimeOut = ObjectUtils.parseToInt(this.connectionProps.getProperty(PropNames.SERVER_REPLY_TIMEOUT));

		return builder.withServerIP(xmppServerIP).withServerPort(xmppServerPort).withHeartBeat()
				.withHeartBeatFrequency(Duration.ofMinutes(hbf)).withReplyTimeout(Duration.ofMinutes(replyTimeOut))
				.build();
	}

	protected ClientConfig prepareClientConfig(ConnectionConfig connConf, ClientConfigBuilder builder)
			throws ClientStartupException {
		LOGGER.log(Level.FINE, "Getting client configrations");

		this.clientProps = loadConfigs(FILE_CLIENT_CONFIG);

		String user = this.clientProps.getProperty(PropNames.USERNAME);
		String pwd = this.clientProps.getProperty(PropNames.PASSWORD);
		String authMechanism = this.clientProps.getProperty(PropNames.AUTH_MECHANISM);
		String domain = this.clientProps.getProperty(PropNames.DOMAIN_NAME);

		return builder.withUser(user).withPassword(pwd).withAuthMechanism(authMechanism).withDomain(domain)
				.withConnectionConfig(connConf).build();
	}

	protected Properties loadConfigs(String path) throws ClientStartupException {
		Properties props = new Properties();

		try {
			LOGGER.log(Level.FINE, "Reading configuration files " + path);
			ConfigLoader.loadConfig(path, props);

		} catch (Exception e) {
			LOGGER.log(Level.FINE, "Failed to load config files " + path);
			throw new ClientStartupException(e);
		}

		return props;
	}

	public void stop() {
		LOGGER.log(Level.FINE, "Stopping client...");
	}

	@Override
	public void collect(Packet packet) {
		if (packet instanceof Message) {
			Message message = (Message) packet;
			LOGGER.log(Level.FINE, "Received message : " + message.xml());

		} else if (packet instanceof IQ) {
			IQ iq = (IQ) packet;
			LOGGER.log(Level.FINE, "Received IQ : " + iq.xml());
		}
	}

}
