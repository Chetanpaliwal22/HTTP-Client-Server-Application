import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements basic get and post. Reference:
 * https://www.tutorialspoint.com/java/io/bufferedreader_read_char.htm
 * https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
 */

public class GetPost {

	InetAddress address;
	Socket s;
	boolean isVerbose;

	public static void main(String[] args) {
		GetPost getPOst = new GetPost();
		// getPOst.getRequest();
		// getPOst.postRequest();
	}

	public void getRequest(Request request) {
		// TODO Auto-generated method stub

		try {

			System.out.println("Start Get: ");
			URL url = new URL(request.getUrl());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			// Setting parameter
//			Map<String, String> parameters = new HashMap<>();
//			parameters.put(request.getKey(), request.getValue());
//			
//			con.setDoOutput(true);
//			DataOutputStream out = new DataOutputStream(con.getOutputStream());
//			out.writeBytes(request.getParameter());

			if (request.getParameter() != null) {
				System.out.println(request.getParameter());
			}

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer sb = new StringBuffer();
				int value = 0;
				while ((value = br.read()) != -1) {

					// converts int to character
					char c = (char) value;
					sb.append(c);

					// prints character
					// System.out.println(c);
				}
				System.out.println("*** Output ***");
				isVerbose = false;
				if (request.isVerbose()) {

					System.out.println("HTTP/1.1 " + con.getResponseCode() + " " + con.getResponseMessage());
					System.out.println("Server: " + con.getHeaderField(6));
					System.out.println(con.getHeaderField(5));
					// System.out.println(con.getDate());
					System.out.println("Content type: " + con.getContentType());
					System.out.println("Content length: " + con.getContentLength());

					System.out.println("Access Control Allow Origin: " + con.getContentEncoding());

					System.out.println("Access-Control-Allow-Credentials: " + con.getHeaderField(11));

				} else {

				}
				System.out.println(sb.toString());
			} else {
				System.out.println("Issue with get request");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void postRequest(Request request) {
		// TODO Auto-generated method stub
		try {
			String params = "";
			if (request != null && request.getKey() != null) {
				params = request.getKey();
			}
			URL url = new URL(request.getUrl());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(params.getBytes());
			// os.flush();
			os.close();

			int responseCode = con.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer sb = new StringBuffer();
				int value = 0;
				while ((value = br.read()) != -1) {

					// converts int to character
					char c = (char) value;
					sb.append(c);

					// prints character
					// System.out.println(c);
				}
				System.out.println(sb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}