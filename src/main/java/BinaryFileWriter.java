import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BinaryFileWriter {
    public static boolean writeFile(byte[] buffer, final String fileAbsolutePath) throws IOException {
	File targetFile = new File(fileAbsolutePath);

	try (OutputStream outStream = new FileOutputStream(targetFile)) {
	    outStream.write(buffer);
	    return true;
	}
    }
}
