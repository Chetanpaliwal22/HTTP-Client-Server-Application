import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Httpc {

	public static Logger logger = Logger.getLogger(Httpc.class.getName());

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			logger.info("Driver class (HTTPC.java) started.");
			Request request = new Request();
			GetPost getPost = new GetPost();

			if(args == null || args.length == 0)
				throw new Exception("No Argument found!");
				
			if (args[1].equalsIgnoreCase("get")) {
				getGetRequest(args, request, getPost);
			} else if (args[1].equalsIgnoreCase("post")) {
				getpostRequest(args, request, getPost);
			} else if (args[1].equalsIgnoreCase("help")) {
				getHelp(args);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void getpostRequest(String[] args, Request request, GetPost getPost)
			throws FileNotFoundException, IOException {
		int i = 2;
		while (i < args.length) {
			if (args[i].equalsIgnoreCase("-v")) {
				request.setVerbose(true);
				i++;
			} else if (args[i].contains("-h")) {
				String[] keyValueArray = args[i + 1].split(":");
				if (keyValueArray.length == 2) {
					request.setKey(keyValueArray[0]);
					request.setValue(keyValueArray[1]);
					i += 2;
				} else {
					System.out.println("Data format incorrect");
					logger.info("Data format for key value incorrect.");
				}
			} else if (args[i].equalsIgnoreCase("-d") && !request.isfOptionStatus()) {
				request.setInlineData(args[i + 1]);
				i += 2;
			} else if (args[i].contains("http")) {
				request.setUrl(args[i].substring(1, args[i].length()-1));
				i++;
			} else if (args[i].contains("-f") && !request.isdOptionStatus()) {
				String inputFileData = "";
				FileReader fr = new FileReader(args[i + 1]);

				int charFile = 0;
				while ((i = fr.read()) != -1) {
					inputFileData += (char) charFile;
				}
				System.out.println("Input Data: " + inputFileData);
				i += 2;
				request.setFileData(inputFileData);
			} else if (args[i].contains("-o")) {
				// Pending
				i++;
			} else {

				 System.out.println("Invalid Parameter.");
				i++;
			}
		}
		getPost.postRequest(request);

	}

	private static void getGetRequest(String[] args, Request request, GetPost getPost) {
		int i = 2;
		while (i < args.length) {
			if (args[i].equalsIgnoreCase("-v")) {
				// System.out.println("in verbose");
				request.setVerbose(true);
				i++;
			} else if (args[i].contains("http")) {
				request.setUrl(args[i].substring(1, args[i].length()-1));
				i++;
			} else if (args[i].contains("-h")) {
				String[] keyValueArray = args[i + 1].split(":");
				if (keyValueArray.length == 2) {
					request.setKey(keyValueArray[0]);
					request.setValue(keyValueArray[1]);
					i += 2;
				} else {
					System.out.println("Data format incorrect");
					logger.info("Data format for key value incorrect.");
				}
			} else {
				// System.out.println("do nothing");
				i++;
			}
		}
		getPost.getRequest(request);
	}

	private static void getHelp(String[] args) {
		if (args.length > 2) {
			if (args[2].equalsIgnoreCase("get")) {
				// Get Details
				System.out.println("httpc help get");
				System.out.println();
				System.out.println("usage: httpc get [-v] [-h key:value] URL");
				System.out.println();
				System.out.println("Get executes a HTTP GET request for a given URL.");
				System.out
						.println("\t-v \t\t Prints the detail of the response such as protocol, status, and headers.");
				System.out
						.println("\t-h \t\t key:value Associates headers to HTTP Request with the format 'key:value'");
			} else if (args[2].equalsIgnoreCase("post")) {
				// Post Details
				System.out.println("httpc help post \n");
				System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\n");
				System.out.println("Post executes a HTTP POST request for a given URL with inline data or from file.");
				System.out
						.println("\t-v \t\t Prints the detail of the response such as protocol, status, and headers.");
				System.out
						.println("\t-h \t\t key:value Associates headers to HTTP Request with the format 'key:value'");
				System.out.println("\t-d string \t\t Associates an inline data to the body HTTP POST request.");
				System.out.println("\t-v file \t\t Associates the content of a file to the body HTTP POST");
				System.out.println();
				System.out.println("Either [-d] or [-f] can be used but not both.");
			} else {
				System.out.println("No matching command found, Please check your command.");
			}
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				// Simple Help
				System.out.println("httpc is a curl-like applicatio	n but supports HTTP protocol only. Usage:");
				System.out.println("\t httpc command [arguments]");
				System.out.println("The commands are:");
				System.out.println("\t get \texecutes a HTTP GET request and prints the response.");
				System.out.println("\t post \texecutes a HTTP POST request and prints the response.");
				System.out.println("\t help \tprints this screen.");
				System.out.println();
				System.out.println("Use \"httpc help [command]\" for more information about a command.");
			}
		} else {
			System.out.println("No matching command found, Please check your command.");
		}
	}
}
