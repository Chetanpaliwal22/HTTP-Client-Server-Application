import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class implements basic get and post. Reference:
 * https://www.tutorialspoint.com/java/io/bufferedreader_read_char.htm
 * https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
 */

public class GetPost {

	InetAddress address;
	Socket s;
	boolean isVerbose = false;
	public static Logger logger = Logger.getLogger(GetPost.class.getName());
	DataInputStream input = null;
	DataOutputStream out;
	String crlf = "\r\n";
	BufferedWriter bw = null;
	BufferedReader br = null;
	String path;
	String finalOutput = "";
	boolean isRedirect = false;
	private String newurl;
	// private boolean isContent = false;
	private String headerMessage = "";
	private String bodyMessage = "";

	public static void main(String[] args) {
		// GetPost getPOst = new GetPost();
		// getPOst.getRequest();
		// getPOst.postRequest();
	}

	/**
	 * method to trigger get response
	 * 
	 * @param request
	 */
	public void getRequest(Request request) {

		try {
			if (request != null) {

				int lengthURL = 0;
				if (request.getUrl() != null) {
					lengthURL = request.getUrl().length();
				}

				System.out.println("URL: " + request.getUrl());

				if (request.getUrl() == null || request.getUrl().isEmpty() || request.getUrl().equalsIgnoreCase("")) {
					throw new Exception("Get: Missing URL");
				} else if (lengthURL >= 9 && request.getUrl().substring(0, 9).equals("localhost")) {
					int indexLocation = request.getUrl().indexOf("/", 9);
					request.host = request.getUrl().substring(0, 9);
					request.localHostPort = 8080;
					request.port = request.localHostPort;
					// request.host = request.getUrl().substring(0, 9);
					if (indexLocation != -1) {
						path = request.getUrl().substring(indexLocation);
					}
				} else {
					int indexLocation = request.getUrl().indexOf("/", 9);
					if (indexLocation != -1) {
						request.host = request.getUrl().substring(7, indexLocation);
						path = request.getUrl().substring(indexLocation);
					} else if (indexLocation == -1) {
						request.host = request.getUrl().substring(7, request.getUrl().length());
					}
				}

				// Socket socket = new Socket("localhost", 80);
				Socket socket = new Socket(InetAddress.getByName(request.host), 80);
				System.out.println("Socket Connected...");

				// Socket socket = new
				// Socket(InetAddress.getByName(request.host),(request.localHostPort));

				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
				bw.write("GET " + path + " HTTP/1.0 " + crlf);
				bw.write("Host: " + request.host + crlf);
				bw.write("User-Agent: " + request.USER_AGENT + crlf);

				for (String headerValue : request.getHeaders()) {
					bw.write(headerValue + crlf);
				}

				bw.write(crlf);
				bw.flush();

				// Response
				boolean hasContent = false;
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String outputLine;

				while ((outputLine = br.readLine()) != null) {

					// System.out.println("ABC "+ outputLine);
					if (outputLine.contains("302")) {
						isRedirect = true;
						// redirectGetRequest(request, con);
					} else if (!hasContent && outputLine.equals("")) {
						hasContent = true;
					}

					// handle redirect location
					if (isRedirect && outputLine.substring(0, 10).equals("Location: ") && outputLine.length() > 10) {
						String newCompleteURL = outputLine.substring(10, outputLine.length());
						// determine relative/absolute redirect
						if (newCompleteURL.length() >= 7 && newCompleteURL.substring(0, 7).equals("http://")) {
							newurl = newCompleteURL;
						} else {
							newurl = "http://" + request.host + newCompleteURL;
						}
						if (isRedirect) {
							System.out.println("302: redirect to \"" + newurl + "\"");
						}
						break;
					}

					if (!hasContent) {
						headerMessage += outputLine + "\n";
					} else {
						bodyMessage += outputLine + "\n";
					}

					// System.out.println(isVerbose);
					// finalOutput += outputLine + "\n";

				}

			}

			if (request.isVerbose) {
				finalOutput += headerMessage;
			}
			finalOutput = finalOutput + bodyMessage;
			System.out.println(finalOutput);
			System.out.println("header vakye: " + request.getHeaders());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to handle redirect request
	 * 
	 * @param request
	 * @param con
	 */
	private void redirectGetRequest(String newURL) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * method to check the redirect
	 * 
	 * @param responseCode
	 * @return
	 */
	private boolean isRedirect(int responseCode) {
		if (responseCode == 301 || responseCode == 302 || responseCode == 304) {
			return true;
		}
		return false;
	}

	/**
	 * method to post the request
	 * 
	 * @param request
	 */
	public void postRequest(Request request) {
		// TODO Auto-generated method stub
		try {
			int lengthURL = 0;
			if (request.getUrl() != null) {
				lengthURL = request.getUrl().length();
			}

			System.out.println("URL: " + request.getUrl());

			if (request.getUrl() == null || request.getUrl().isEmpty() || request.getUrl().equalsIgnoreCase("")) {
				throw new Exception("Get: Missing URL");
			} else if (lengthURL >= 9 && request.getUrl().substring(0, 9).equals("localhost")) {
				int indexLocation = request.getUrl().indexOf("/", 9);
				request.host = request.getUrl().substring(0, 9);
				request.localHostPort = 8080;
				request.port = request.localHostPort;
				// request.host = request.getUrl().substring(0, 9);
				if (indexLocation != -1) {
					path = request.getUrl().substring(indexLocation);
				}
			} else {
				int indexLocation = request.getUrl().indexOf("/", 9);
				if (indexLocation != -1) {
					request.host = request.getUrl().substring(7, indexLocation);
					path = request.getUrl().substring(indexLocation);
				} else if (indexLocation == -1) {
					request.host = request.getUrl().substring(7, request.getUrl().length());
				}
			}

			// Socket socket = new Socket("localhost", 80);
			Socket socket = new Socket(InetAddress.getByName(request.host), 80);
			System.out.println("Socket Connected...");

			// Socket socket = new
			// Socket(InetAddress.getByName(request.host),(request.localHostPort));

			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			bw.write("POST " + path + " HTTP/1.0 " + crlf);
			bw.write("Host: " + request.host + crlf);
			bw.write("User-Agent: " + request.USER_AGENT + crlf);

			for (String headerValue : request.getHeaders()) {
				bw.write(headerValue + crlf);
			}

			if (request.getInlineData() != null && !request.getInlineData().isEmpty()) {
				bw.write("Content-Length: " + request.getInlineData().length() + crlf);
			}

			bw.write(crlf);
			bw.write(request.getInlineData());
			bw.flush();

			// Response
			boolean hasContent = false;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String outputLine;

			while ((outputLine = br.readLine()) != null) {

				// System.out.println("ABC "+ outputLine);
				if (outputLine.contains("302")) {
					isRedirect = true;
					// redirectGetRequest(request, con);
				} else if (!hasContent && outputLine.equals("")) {
					hasContent = true;
				}

				// handle redirect location
				if (isRedirect && outputLine.substring(0, 10).equals("Location: ") && outputLine.length() > 10) {
					String newCompleteURL = outputLine.substring(10, outputLine.length());
					// determine relative/absolute redirect
					if (newCompleteURL.length() >= 7 && newCompleteURL.substring(0, 7).equals("http://")) {
						newurl = newCompleteURL;
					} else {
						newurl = "http://" + request.host + newCompleteURL;
					}
					if (isRedirect) {
						System.out.println("302: redirect to \"" + newurl + "\"");
					}
					break;
				}

				if (!hasContent) {
					headerMessage += outputLine + "\n";
				} else {
					bodyMessage += outputLine + "\n";
				}

				// System.out.println(isVerbose);
				// finalOutput += outputLine + "\n";

			}

			if (request.isVerbose) {
				finalOutput += headerMessage;
			}
			finalOutput = finalOutput + bodyMessage;
			System.out.println(finalOutput);
			//System.out.println("header vakye: " + request.getHeaders());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}