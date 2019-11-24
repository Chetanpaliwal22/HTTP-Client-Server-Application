package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class httpfs {

	private boolean verbose = false;
	private int portNumber = 8080;
	private String directory = ".";
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	public static void main(String args[]) {
		
		httpfs httpfs = new httpfs();
		for(int i=0;i<args.length; i++) {
			if(args[i].equals("help")){
                System.out.println("httpfs is a simple file server.\r\n" + 
                		"usage: httpfs [-v] [-p PORT] [-d PATH-TO-DIR]\r\n" + 
                		"-v Prints debugging messages.\r\n" + 
                		"-p Specifies the port number that the server will listen and serve at.\r\n" + 
                		"Default is 8080.\r\n" + 
                		"-d Specifies the directory that the server will use to read/write requested files. Default is the current directory when launching the application.");
			}
			if(args[i].equals("-v")) {
				httpfs.setverbose(true);
			}
			if(args[i].equals("-p")) {
				try {
					httpfs.setPortNumber(++i > args.length ? Integer.parseInt(args[i]) : 8080);
				}
				catch (Exception e) {
					System.out.println("Port number has to be integer");
				}
				if(i <  args.length -1 ) {
					System.out.println("Port number missing");
				}
			}
			if(args[i].equals("-d")) {
				httpfs.setDirectory(++i > args.length ? args[i] : ".");
				if(i <  args.length -1 ) {
					System.out.println("Directory Path missing");
				}
			}
		}
		
		httpfs.run();
		
	}
	
	
	 void run() {
	        try {
	            ServerSocket serverSocket = new ServerSocket(portNumber);
	            while (true) {
	                Socket client = serverSocket.accept();
	                new ClientRequest(client, verbose, directory);
	            }
	        } catch (IOException e) {
	            System.out.println("Httphttpfs.run():  " + e.getMessage());
	        }
	    }
	/**
	 * @return the verbose
	 */
	public boolean getverbose() {
		return verbose;
	}
	/**
	 * @param verbose the verbose to set
	 */
	public void setverbose(boolean verbose) {
		this.verbose = verbose;
	}
}
