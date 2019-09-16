
public class Httpc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			GetPost getPost = new GetPost();

			if (args[0].equalsIgnoreCase("get")) {
				getPost.getRequest();
			} else if (args[0].equalsIgnoreCase("post")) {
				getPost.postRequest();
			} else if (args[0].equalsIgnoreCase("help")) {
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
