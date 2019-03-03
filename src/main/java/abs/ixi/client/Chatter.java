package abs.ixi.client;

/**
 * {@link Chatter} represents an entity which holds a conversation; for example
 * in an application with UI, it will be screen on which user will send/receive
 * chat messages to another user/group.
 * 
 * @author Yogi
 *
 */
public class Chatter {
	private ChatManager chatManager;

	public Chatter(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

}
