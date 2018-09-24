import java.util.HashMap;
import java.util.Map;

public class HttpPostRequest {
    private static final String COLAN = ":";
    private static final String SPACE = " ";
    private static final String SLASH = "/";
    private static final String CONTENT_LENGTH = "Content-Length";

    private String requestURL;
    private Map<String, String> headers;
    private String binaryFilePath;

    public HttpPostRequest() {
	this.headers = new HashMap<>();
    }

    public String getRequestURL() {
	return requestURL;
    }

    public void setRequestURL(String requestURL) {
	this.requestURL = requestURL;
    }

    public Map<String, String> getHeaders() {
	return headers;
    }

    public void setHeaders(Map<String, String> headers) {
	this.headers = headers;
    }

    public String getBinaryFilePath() {
	return binaryFilePath;
    }

    public void setBinaryFilePath(String binaryFilePath) {
	this.binaryFilePath = binaryFilePath;
    }

    public String getBinaryFileName() {
	String requestPath = requestURL.substring(requestURL.indexOf(SLASH));
	return requestPath.substring(1, requestPath.indexOf(SPACE));
    }

    public void addHeader(String key, String value) {
	this.headers.put(key, value);
    }

    public void parseHeader(String headerLine) {
	String[] header = headerLine.split(COLAN);

	if (header.length == 2) {
	    this.addHeader(header[0].trim(), header[1].trim());
	}
    }

    public int getContentLength() {
	String length = this.headers.get(CONTENT_LENGTH);

	if (length != null) {
	    return Integer.parseInt(length);
	}

	return 0;
    }
}
