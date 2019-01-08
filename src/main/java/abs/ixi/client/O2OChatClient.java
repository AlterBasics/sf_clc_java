package abs.ixi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import abs.ixi.client.core.Platform;
import abs.ixi.client.net.ConnectionConfigBuilder;
import abs.ixi.client.util.StringUtils;
import abs.ixi.client.util.UUIDGenerator;
import abs.ixi.client.xmpp.JID;

public class O2OChatClient extends Client {
	private static final Logger LOGGER = Logger.getLogger(O2OChatClient.class.getName());

	public static void main(String[] args) {
		O2OChatClient client = new O2OChatClient();

		try {
			client.init();
			client.start();

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

		try {
			System.out.println("Requesting for full roster");

			// Platform.getInstance().getUserManager().sendGetFullRosterRequest();

			// Platform.getInstance().getUserManager().sendGetChatRoomInfoRequest(new
			// JID("ixi@chat.alterbasics.com"));

			// Platform.getInstance().getUserManager().updateDeviceToken("12345",
			// PushNotificationService.FCM);

			// Platform.getInstance().getUserManager().leaveChatRoom(new
			// JID("stringflow@chat.alterbasics.com"));

			// Platform.getInstance().getPresenceManager().sendInitialPresence();

			// Platform.getInstance().getChatManager().sendRawMsg(streamClose(),
			// false);

			// Platform.getInstance().getChatManager().sendRawMsg(testRequest());
			// Platform.getInstance().getChatManager().sendRawMsg(mimeSample(),
			// false);
			// Platform.getInstance().getChatManager().sendRawMsg(getRequestSample(),
			// false);

			// Platform.getInstance().getApplicationManager().sendAppRequest(buildGetRequest());

			// Platform.getInstance().getApplicationManager().sendAppRequest(buildPostRequest());

			// Platform.getInstance().getUserManager().getFullRoster();

			// Platform.getInstance().getUserManager().updateRosterMember(new
			// JID("vishal@alterbasics.com"), "vishal p");

			// Platform.getInstance().getUserManager().sendAddRosterMemberRequest(new
			// JID("shabareesh@alterbasics.com"), "shabareesh",
			// UUIDGenerator.secureId());
			// Platform.getInstance().getUserManager().sendRemoveRosterMemberRequest(new
			// JID("yogi@alterbasics.com"), "yogi", UUIDGenerator.secureId());

			// Platform.getInstance().getUserManager().removeRosterMember(new
			// JID("vishal@alterbasics.com"), "vishal p");

			// Platform.getInstance().getChatManager().say(UUIDGenerator.secureId(),
			// "hiii", new
			// JID("stringflow@chat.alterbasics.com"), true);

			// Platform.getInstance().getChatManager().sendMedia("C:\\Users\\Gudia\\Downloads\\abc.jpg",
			// new JID("dharmu@alterbasics.com"), false, null);

			// Platform.getInstance().getChatManager().retrieveMedia("62453f98-5bc7-410a-942b-7d5285df72e1",
			// null);

			// Platform.getInstance().getUserManager().createPoll(PollType.MCQ,
			// "How r u", "good", "bad", "very good",
			// "very bad", "good", 0L, 0L, ResponseType.ANONYMOUS);

			// Platform.getInstance().getChatManager().sendPollMessage(1,
			// PollType.MCQ, "hii how ru", "good", "very good",
			// "bad", "very bad", "good", 0L, 0L, new
			// JID("dharmu@alterbasics.com"), false);

			// String msg =
			// "<message id='7343a467e' from='1033@alterbasics.com/7cc5d8de7'
			// to='ixi@chat.alterbasics.com' type='groupchat'
			// xml:lang='en'><body>vino</body></message><message id='7343a467e'
			// from='1033@alterbasics.com/7cc5d8de7'
			// to='ixi@chat.alterbasics.com' type='groupchat'
			// xml:lang='en'><body>vino</body></message><message id='c9ffccec7'
			// from='1033@alterbasics.com/7cc5d8de7' to='1007@alterbasics.com'
			// type='chat' xml:lang='en'><body>hiya</body></message><message
			// id='7343a467e' from='1033@alterbasics.com/7cc5d8de7'
			// to='ixi@chat.alterbasics.com' type='groupchat'
			// xml:lang='en'><body>vino</body></message><message id='c9ffccec7'
			// from='1033@alterbasics.com/7cc5d8de7' to='1007@alterbasics.com'
			// type='chat' xml:lang='en'><body>hiya</body></message><message
			// id='c9ffccec7' from='1033@alterbasics.com/7cc5d8de7'
			// to='1007@alterbasics.com' type='chat'
			// xml:lang='en'><body>hiya</body></message><message id='c9ffccec7'
			// from='1033@alterbasics.com/7cc5d8de7' to='1007@alterbasics.com'
			// type='chat' xml:lang='en'><body>hiya</body></message><message
			// id='c9ffccec7' from='1033@alterbasics.com/7cc5d8de7'
			// to='1007@alterbasics.com' type='chat'
			// xml:lang='en'><body>hiya</body></message>";
			// Platform.getInstance().getChatManager().sendRawMsg(msg);

			// Platform.getInstance().getUserManager().sendGetChatRoomsRequest(UUIDGenerator.secureId());
			// Platform.getInstance().getUserManager().sendGetChatRoomMembersRequest("stringflow");
			// Platform.getInstance().getUserManager().sendGetChatRoomInfoRequest("ixi");

			// Platform.getInstance().getUserManager().getChatRoomsAsync();
			// Platform.getInstance().getUserManager().getChatRoomMembers("stringflow");

			// Platform.getInstance().getUserManager().leaveChatRoom("ixi");

			// Platform.getInstance().getUserManager().sendGetChatRoomMembersRequest("ixi");

			// Platform.getInstance().getUserManager().sendGetChatRoomConfigParam(new
			// JID("ixi@chat.alterbasics.com"));

			// Platform.getInstance().getUserManager().updateRoomAccessMode(new
			// JID("ixi@chat.alterbasics.com"), AccessMode.PRIVATE);

			// Platform.getInstance().getUserManager().sendRemoveChatRoomMemberRequest(UUIDGenerator.secureId(),
			// new JID("ixi@chat.alterbasics.com"), new
			// JID("vishal@alterbasics.com"));

			// Platform.getInstance().getUserManager().leaveChatRoom("ixi");

			// Platform.getInstance().getUserManager().sendGetChatRoomsRequest(UUIDGenerator.secureId());

			// Platform.getInstance().getUserManager().joinChatRoom(new
			// JID("ixi@chat.alterbasics.com"), "dharmu");
			// Platform.getInstance().getUserManager().sendAddChatRoomMemberRequest(UUIDGenerator.secureId(),
			// new JID("ixi@chat.alterbasics.com"),
			// new JID("akhil@alterbasics.com"));
			// Platform.getInstance().getUserManager().changeRoomSubject("stringflow","stringflow-rock");
			//
			// Platform.getInstance().getChatManager().say("1234", "hahhaha",
			// new JID("Stratawiz Tech@chat.alterbasics.com"), true);
			// Platform.getInstance().getChatManager().say("1235",
			// "hahhahuiiiiiiha", new JID("Stratawiz
			// Tech@chat.alterbasics.com"), true);
			// Platform.getInstance().getChatManager().say("1234567890876543",
			// "haiiiiyaa", new JID("Stratawiz Tech@chat.alterbasics.com"),
			// true);

			// Platform.getInstance().getChatManager().sendMedia("1234567",
			// "C:\\Users\\Gudia\\Downloads\\abc.png",
			// new JID("dharmu@alterbasics.com"), false);
			//
			// Platform.getInstance().getChatManager().sendMedia("1234567",
			// "C:\\Users\\Gudia\\Downloads\\abc.png",
			// ContentType.IMAGE_PNG, "its thumb hah huu hiihey ...", new
			// JID("dharmu@alterbasics.com"), false);
			// //
			// Platform.getInstance().getChatManager().reterieveMedia("a52fe9a7-029e-45ec-a976-a07ceede829b");
			// Platform.getInstance().getChatManager().reterieveMedia("a52fe9a7-029e-45ec-a976-a07ceede829b");

			// Platform.getInstance().getChatManager().sendMedia("1234567",
			// "C:\\Users\\Gudia\\Downloads\\abc.png",
			// new JID("dharmu@alterbasics.com"), false);

			// Platform.getInstance().getUserManager()
			// .sendRemoveChatRoomMemberRequest(UUIDGenerator.secureId(), new
			// JID("KrishnaGroup@chat.alterbasics.com"), new
			// JID("yogi@alterbasics.com"));
			//

			// Platform.getInstance().getUserManager().sendAddChatRoomMemberRequest(UUIDGenerator.secureId(),
			// new JID("KrishnaGroup@chat.alterbasics.com"), new
			// JID("yogi@alterbasics.com"));

			// Platform.getInstance().getUserManager().createRoom("timbaktuuu");

			// Platform.getInstance().getChatManager().say("1234", "hahhaha",
			// new JID("Stratawiz Tech@chat.alterbasics.com"), true);
			List<JID> members = new ArrayList<>();
			members.add(new JID("yogi", "alterbasics.com"));
			members.add(new JID("ashutosh", "alterbasics.com"));
			// Platform.getInstance().getUserManager().createPrivateGroup("dharmuDemotest",
			// members);

			// Platform.getInstance().getUserManager().sendGetChatRoomInfoRequest(new
			// JID("dharmutest-2afe5d9e5@chat.alterbasics.com"));

			// Platform.getInstance().getUserManager().sendAddChatRoomMemberRequest(new
			// JID("dharmutest-b78ff7fbd@chat.alterbasics.com"), new
			// JID("ashutosh", "alterbasics.com"));
			// Platform.getInstance().shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void readyToChat() {
		try {
			LOGGER.log(Level.FINE, "Waiting for Stream ready");
			O2OChatter chatter = new O2OChatter();
			new Thread(chatter).start();

			LOGGER.log(Level.FINE, "Client is ready to start chatting");

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

	private class O2OChatter implements Runnable {
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
						// chatManager
						// .say(UUIDGenerator.secureId(), msg, receiver);

						chatManager.sendMarkableMessage(UUIDGenerator.secureId(), msg, receiver, false);
					}
				}

			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Chatting is intrupted due to some error", e);
			}
		}
	}

	public String getRequestSample() {
		StringBuilder sb = new StringBuilder("<iq id='1234' type='get'><request xmlns='stringflow:request'>");
		sb.append("uri = helloxi/appfront/helloxiReceiver/say/6/HelloWorld?userId=10000").append("\n");
		sb.append("content-length = 100");
		sb.append("\n");

		sb.append("</request></iq>");

		return sb.toString();
	}

	public String mimeSample() {
		StringBuilder sb = new StringBuilder();

		sb.append("--XXXXboundary text").append("\n");
		sb.append("Content-Type: text/plain").append("\n");
		sb.append("\n");
		sb.append("this is the body text").append("\n");

		sb.append("\n");

		sb.append("--XXXXboundary text").append("\n");
		sb.append("Content-Type: text/plain").append("\n");
		sb.append("Content-Disposition: attachment;").append("\n");
		sb.append("filename:test.txt").append("\n");
		sb.append("\n");
		sb.append("this is the attachment text").append("\n");

		sb.append("\n");

		sb.append("--XXXXboundary text--").append("\n");

		return sb.toString();
	}

	public String postRequestSample() {
		StringBuilder sb = new StringBuilder("<iq id='1234' type='set'><request xmlns='stringflow:request'>")
				.append("\n");
		sb.append("<![CDATA[").append("\n");
		sb.append("uri = helloxi/appfront/6/hello dharmu?userId=1000").append("\n");
		sb.append("boundary=XXXXboundary text").append("\n");
		sb.append("]]>").append("\n");
		sb.append("</request></iq>");

		return sb.toString();
	}

	public String streamClose() {
		return "</stream:stream>";
	}

}
