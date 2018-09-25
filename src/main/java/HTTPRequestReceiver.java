import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPRequestReceiver {
    private static final String EMPTY_LINE = "";

    private final String filePath;
    private HttpPostRequest request;

    public HTTPRequestReceiver(String filePath) {
	this.filePath = filePath;
	this.request = new HttpPostRequest();
    }

    public void readInputData(final InputStream inputStream) throws IOException {
	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	String url = reader.readLine();
	request.setRequestURL(url);

	String line = reader.readLine();

	while (!line.equals(EMPTY_LINE)) {
	    request.parseHeader(line);
	    line = reader.readLine();
	}

	byte[] binaryDataBuffer = getBinaryDataBuffer(reader);

	if (binaryDataBuffer != null) {
	    final String binaryFilePath = createFilePath();
	    boolean written = BinaryFileWriter.writeFile(binaryDataBuffer, binaryFilePath);

	    if (written) {
		request.setBinaryFilePath(binaryFilePath);
		System.out.println("File is saved to path " + binaryFilePath);
	    }

	} else {
	    System.out.print("No binary data found");
	}

    }

    private byte[] getBinaryDataBuffer(BufferedReader reader) throws IOException {
	int contentLength = request.getContentLength();

	if (contentLength > 0) {
	    byte[] buffer = new byte[contentLength];

	    int buffPosition = 0;

	    int readedByte = 0;

	    while (buffPosition < contentLength && (readedByte = reader.read()) != -1) {
		buffer[buffPosition++] = (byte) readedByte;
	    }

	    return buffer;
	}

	return null;
    }

    private String createFilePath() {
	String fileName = request.getBinaryFileName();
	return filePath + File.separator + fileName;
    }

}
