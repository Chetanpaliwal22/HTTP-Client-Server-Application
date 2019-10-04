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
	boolean isVerbose;
	public static Logger logger = Logger.getLogger(GetPost.class.getName());
	DataInputStream input = null;
	DataOutputStream out;
	String crlf = "\r\n";
	BufferedWriter bw = null;
	BufferedReader br = null;
	String path;
	String finalOutput;
boolean isRedirect = false;
private String newurl;
	
	public static void main(String[] args) {
		GetPost getPOst = new GetPost();
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
					//request.host = request.getUrl().substring(0, 9);
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

				//Socket socket = new Socket("localhost", 80);
				Socket socket = new Socket(InetAddress.getByName(request.host),80);
				System.out.println("Socket Connected...");

				

				// Socket socket = new
				// Socket(InetAddress.getByName(request.host),(request.localHostPort));

				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
				bw.write("GET " + path + " HTTP/1.0 " + crlf);
				bw.write("Host: " + request.host + crlf);
				bw.write("User-Agent: " + request.USER_AGENT + crlf);

				bw.write(crlf);
				bw.flush();

				// Response

				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String outputLine;

				while ((outputLine = br.readLine()) != null) {
					
					if(outputLine.contains("302")) {
						isRedirect = true;
						redirectGetRequest(request, con);
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
                        if (seeRedirectDetail) {
                            System.out.println("302: redirect to \"" + url + "\"");
                        }
                        break;
                    }
					
					
					
                    finalOutput += outputLine + "\n";
					
				}

				System.out.println(finalOutput);

			}
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
			if (request != null) {

				logger.info("Response Redirect: ");
				System.out.println(con.getURL());
				// String redirectURL = con.getURL();
				// URL url = new URL(con.getURL());
				// HttpURLConnection conRedirect = (HttpURLConnection) url.openConnection();
				// con.setRequestMethod("GET");
				//
				// if (request.getParameter() != null) {
				// System.out.println(request.getParameter());
				// }
				//
				// if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//
				// if(!isRedirect(con.getResponseCode())) {
				// BufferedReader br = new BufferedReader(new
				// InputStreamReader(con.getInputStream()));
				// StringBuffer sb = new StringBuffer();
				// int value = 0;
				// while ((value = br.read()) != -1) {
				//
				// // converts int to character
				// char c = (char) value;
				// sb.append(c);
				//
				// // prints character
				// // System.out.println(c);
				// }
				// System.out.println("*** Output ***");
				// isVerbose = false;
				// if (request.isVerbose()) {
				//
				// System.out.println("HTTP/1.1 " + con.getResponseCode() + " " +
				// con.getResponseMessage());
				// System.out.println("Server: " + con.getHeaderField(6));
				// System.out.println(con.getHeaderField(5));
				// // System.out.println(con.getDate());
				// System.out.println("Content type: " + con.getContentType());
				// System.out.println("Content length: " + con.getContentLength());
				//
				// System.out.println("Access Control Allow Origin: " +
				// con.getContentEncoding());
				//
				// System.out.println("Access-Control-Allow-Credentials: " +
				// con.getHeaderField(11));
				//
				// } else {
				//
				// }
				// System.out.println(sb.toString());
				// }else {
				// redirectGetRequest(request,con);
				// }
				// }else {
				// System.out.println("Issue with get request");
				// }
			}
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