package third.MovieSearcher;


import java.io.*;
import java.net.URL;


public class FileDownloader {

	/**
	 * Downloads file using the url and saves it
	 *
	 * @param url      url of the file that should be downloaded
	 * @param filePath file path where  downloaded file should be stored
	 * @throws IOException
	 */
	public void downloadFile(String url, String filePath) throws IOException {

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new URL(url).openStream());
			out = new BufferedOutputStream(new FileOutputStream(filePath));
			for (int b; (b = in.read()) > -1; ) {
				out.write(b);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}


}




