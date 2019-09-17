
public class Httpc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			System.out.println("Start HTTPC Code.");
			Request request = new Request();
			GetPost getPost = new GetPost();
			

			if (args[1].equalsIgnoreCase("get")) {

				int i = 2;
				while (i < args.length) {
					if (args[i].equalsIgnoreCase("-v")) {
						// System.out.println("in verbose");
						request.setVerbose(true);
						i++;
					} else if (args[i].contains("http")) {
						request.setUrl(args[i]);
						i++;
					} else {
						// System.out.println("do nothing");
						i++;
					}
				}
				getPost.getRequest(request);
			} else if (args[1].equalsIgnoreCase("post")) {
				int i = 2;
				while (i < args.length) {
					if (args[i].equalsIgnoreCase("-v")) {
						request.setVerbose(true);
						i++;
					} else if (args[i].equalsIgnoreCase("--d")) {
						request.setKey(args[i + 1]);
						i += 2;
					} else if (args[i].contains("http")) {
						request.setUrl(args[i]);
						i++;
					} else {
						// System.out.println("do nothing");
						i++;
					}
				}
				getPost.postRequest(request);
			} else if (args[1].equalsIgnoreCase("help")) {
				getHelp(args);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

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
		} else if(args.length == 2){
			if(args[1].equalsIgnoreCase("help")) {
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
		}else {
			System.out.println("No matching command found, Please check your command.");
		}
	}
}
