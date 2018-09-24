package abs.ixi.client;

public class ClientStartupException extends Exception {
    private static final long serialVersionUID = 1L;

    public ClientStartupException(Exception e) {
	super(e);
    }

    public ClientStartupException(String msg) {
	super(msg);
    }

}
