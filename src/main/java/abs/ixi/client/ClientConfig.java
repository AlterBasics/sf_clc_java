package abs.ixi.client;

import abs.ixi.client.net.ConnectionConfig;

/**
 * Holds configuration required for Client to work; Also encapsulate connection
 * configurations
 */
public class ClientConfig {
	private String username;
	private String password;
	private String authMechanism;
	private String domain;

	private ConnectionConfig connectionConfig;

	public ClientConfig() {
		// do nothing public constructor
	}

	public ClientConfig(ConnectionConfig config) {
		this.connectionConfig = config;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthMechanism() {
		return authMechanism;
	}

	public void setAuthMechanism(String authMechanism) {
		this.authMechanism = authMechanism;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public ConnectionConfig getConnectionConfig() {
		return connectionConfig;
	}

	public void setConnectionConfig(ConnectionConfig connectionConfig) {
		this.connectionConfig = connectionConfig;
	}

}
