
public class Httpc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			System.out.println("Start HTTPC Code.");
			Request request = new Request();
			GetPost getPost = new GetPost();
			
			System.out.println(args[0]);
			System.out.println(args[1]);
			System.out.println(args[2]);
			System.out.println(args[3]);
			System.out.println(args.length);

			
			if (args[1].equalsIgnoreCase("get")) {

				// ================
				int i = 2;
				while (i < args.length) {
					if (args[i].equalsIgnoreCase("-v")) {
						System.out.println("in verbose");
						request.setVerbose(true);
						i++;
					} else if (args[i].equalsIgnoreCase("-h")) {
						System.out.println("in -h");
						String[] argumentsArray = args[i + 1].split(":");
						request.setKey(argumentsArray[0]);
						request.setValue(argumentsArray[1]);
						i += 2;
					}else {
						System.out.println("do nothing");
						i++;
					}
				}
				request.setUrl(args[3]);
				getPost.getRequest(request);
			} else if (args[1].equalsIgnoreCase("post")) {
				getPost.postRequest();
			} else if (args[1].equalsIgnoreCase("help")) {
				getHelp();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void getHelp() {
		// Simple Help
		System.out.println("httpc is a curl-like application but supports HTTP protocol only. Usage:");
		System.out.println("\t httpc command [arguments]");
		System.out.println("The commands are:");
		System.out.println("\t get \texecutes a HTTP GET request and prints the response.");
		System.out.println("\t post \texecutes a HTTP POST request and prints the response.");
		System.out.println("\t help \tprints this screen.");
		System.out.println();
		System.out.println("Use \"httpc help [command]\" for more information about a command.");

		// Get Details
		System.out.println("httpc help get");
		System.out.println();
		System.out.println("usage: httpc get [-v] [-h key:value] URL");
		System.out.println();
		System.out.println("Get executes a HTTP GET request for a given URL.");
		System.out.println("\t-v \t\t Prints the detail of the response such as protocol, status, and headers.");
		System.out.println("\t-h \t\t key:value Associates headers to HTTP Request with the format 'key:value'");

		// Post Details
		System.out.println("httpc help post");
		System.out.println();
		System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL");
		System.out.println();
		System.out.println("Post executes a HTTP POST request for a given URL with inline data or from file.");
		System.out.println("\t-v \t\t Prints the detail of the response such as protocol, status, and headers.");
		System.out.println("\t-h \t\t key:value Associates headers to HTTP Request with the format 'key:value'");
		System.out.println("\t-d string \t\t Associates an inline data to the body HTTP POST request.");
		System.out.println("\t-v file \t\t Associates the content of a file to the body HTTP POST");
		System.out.println();
		System.out.println("Either [-d] or [-f] can be used but not both.");
	}

}
