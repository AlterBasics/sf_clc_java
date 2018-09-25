import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
    private int port;
    private String filePath;
    private ServerSocket serverSocket;

    public static void main(String args[]) {
	System.out.println("Starting Server...");

	HTTPServer server = new HTTPServer();

	try {
	    
	    server.parseArgs(args);
	    server.createServerSocket();
	    server.runConnectionAccepter();

	} catch (Exception e) {
	    System.out.println("Failed to start server ");
	    e.printStackTrace();
	}

    }

    private void runConnectionAccepter() throws IOException {
	System.out.println("Running connection accepter...");
	while (true) {
	    final Socket socket = serverSocket.accept();
	    System.out.println("Connection accpted " + socket);
	    handleConnectionResquests(socket);
	}
    }

    private void handleConnectionResquests(final Socket socket) {
	try {
	    new Thread(new ConnectionHandler(socket)).start();
	} catch (Exception e) {
	    System.out.println("Failed to handle Connection request for socket " + socket);
	    closeSocket(socket);
	}

    }

    private void closeSocket(final Socket socket) {
	System.out.println("Closing socket " + socket);

	try {

	    socket.close();

	} catch (IOException e) {
	    // swallow exception
	}
    }

    private void createServerSocket() throws IOException {
	this.serverSocket = new ServerSocket(port);
    }

    private void parseArgs(String[] args) {
	if (args.length < 2) {

	    System.out.println("Server arguments  port or file path not found, Please provide these");
	    System.exit(0);

	} else {
	    this.port = Integer.parseInt(args[0]);
	    this.filePath = args[1];
	}

    }

    class ConnectionHandler implements Runnable {
	private final Socket socket;
	private InputStream inputStream;

	public ConnectionHandler(final Socket socket) throws IOException {
	    this.socket = socket;
	    this.inputStream = socket.getInputStream();
	}

	@Override
	public void run() {
	    try {
		HTTPRequestReceiver requestReceiver = new HTTPRequestReceiver(filePath);
		requestReceiver.readInputData(this.inputStream);

	    } catch (Throwable e) {
		System.out.println("Failed to read data from socket " + this.socket);
		e.printStackTrace();

	    } finally {
		closeSocket();
	    }

	}

	private void closeSocket() {
	    System.out.println("Closing socket...");

	    try {
		this.inputStream.close();
		this.socket.close();
	    } catch (IOException e) {
		// swallow Exception
	    }

	}

    }
}
