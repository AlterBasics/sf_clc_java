package abs.ixi.client;

import abs.ixi.client.net.ConnectionConfig;

/**
 * Builder pattern implementation to build {@link ClientConfig} objects.
 */
public class ClientConfigBuilder {
	protected ClientConfig config;

	public ClientConfigBuilder() {
		this.config = new ClientConfig();
	}

	public ClientConfigBuilder(ClientConfig config) {
		this.config = config;
	}

	public ClientConfigBuilder withUser(String username) {
		this.config.setUsername(username);
		return this;
	}

	public ClientConfigBuilder withPassword(String password) {
		this.config.setPassword(password);
		return this;
	}

	public ClientConfigBuilder withAuthMechanism(String authMechanism) {
		this.config.setAuthMechanism(authMechanism);
		return this;
	}

	public ClientConfigBuilder withDomain(String domain) {
		this.config.setDomain(domain);
		return this;
	}

	public ClientConfigBuilder withConnectionConfig(ConnectionConfig connConf) {
		this.config.setConnectionConfig(connConf);
		return this;
	}

	public ClientConfig build() {
		return this.config;
	}
}
