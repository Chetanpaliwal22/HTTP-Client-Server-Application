package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ClientRequest implements Runnable {

	private static HashMap<String, HandleLock> allFilesList = new HashMap<>();
	private Socket socket;
	private boolean verbose;
	private String directory;
	private BufferedReader bufferReader;
	private String httpMethod = "";
    private int l = 0;
	private String path = "";
	private boolean showEntireFiles = false;
	private int codeStatus = 200;
    private StringBuilder response = new StringBuilder();
    private StringBuilder body = new StringBuilder();
    private String whiteSpace = "\r\n";
    private String t = "";

    private String p = "";
    
    private ArrayList<String> bodyForPost = new ArrayList<>();
    
    private boolean readBody = false;
    
	public ClientRequest(Socket socket, boolean verbose, String directory) {
		this.socket = socket;
		this.verbose = verbose;
		this.directory = directory;
		new Thread(this).start();
	}
	
	
	public void run() {
		try {
			if(verbose) {
				System.out.println("Debugging Message....");
			}
			
			req();
			
			
			 Thread.sleep(1000);
			 
			 
			 
			 if (verbose) {
	                System.out.println("request is being processed\n");
	                System.out.println("Method that has been requested:        " + httpMethod);
	            }
			 
			 
			 req2();
			 
			 if (verbose) {
		        	System.out.println("Status Code:         " + codeStatus);
		        }

		        if (verbose) {
		        	System.out.println("\nResponse generation in process....\n");
		        }
		        createResponse();
		        
		        if (verbose) {
		        	System.out.println("\nResponse is being sent...\n");
		        }
		        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		        writer.write(response.toString());
		        writer.flush();
		        writer.close();
		        writer.close();
		        if (verbose) {
		        	System.out.println("\nFinished....\n");
		        }
		        Thread.sleep(1000);

	            if (!showEntireFiles) {
	                if (httpMethod.equals("GET")) {
	                    HandleLock lock = allFilesList.get(path);
	                    if (1 == lock.currentlyreading()) {
	                        allFilesList.remove(path);
	                        if (verbose) {
	                        	System.out.println("Lock which was held is going to be released:        " + path);
	                        }
	                    } else {
	                        lock.decreaseRead();
	                    }
	                } else if (httpMethod.equals("POST") && 200 == codeStatus) {
	                    allFilesList.remove(path);
	                    if (verbose) {
	                    	System.out.println("Lock which was held is going to be released:         " + path);
	                    }
	                }
	            }
		        
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	
    private void req2() throws MalformedURLException, IOException, InterruptedException {

        if (!showEntireFiles && verbose) {
        	System.out.println("File is going to be locked:           " + path);
        }
        if (200 != codeStatus) {
        	return;
        }
        if (path.length() > 3 && path.substring(0, 4).equals("/../")) {
            codeStatus = 403;
            body.append("Working directory cannot be changed.\r\n");
            return;
        }
        if (httpMethod.equals("GET")) {
        	System.out.println("Method is get");
            if (showEntireFiles) {
                showEntireList(directory, body);
            } else {
                System.out.println(directory + path);
                File file = new File(directory + path);
                String type = file.toURL().openConnection().getContentType();
                t = type;
                if (t.equals("text/plain")) {
                    p = "inline";
                } else {
                    p = "attachment; filename=" + directory + path + ";";
                }
                if (verbose) {
                    System.out.println("Content Type:        " + type);
                }
                if (!t.equals("text/plain")) {
                    body.append("Type of the file is ").append(t).append(", cannot read byte file.");
                } else if (file.exists() && file.isFile()) {
                    BufferedReader getFileContents = new BufferedReader(new FileReader(file));
                    String getLine;
                    while (null != (getLine = getFileContents.readLine())) {
                        body.append(getLine).append(whiteSpace);
                    }
                } else {
                    codeStatus = 404;
                }
            }
        } else if (httpMethod.equals("POST")) {
        	System.out.println("Method is post");
            if (showEntireFiles) {
            	return;
            }
            File file = new File(directory + path);
            String NameFile = "";
            String PathFile = directory + path;
            boolean presentDir = false;
            boolean et = false;

            if (file.isDirectory()) {
                codeStatus = 400;
                body.append("Directory name and file name cannot be same : ");
                body.append(directory).append(path).append(whiteSpace);
            }
            if (file.exists()) {
            	et = true;
            }
            if (-1 == PathFile.indexOf("/", 2)) {
            	presentDir = true;
            }
            if (!presentDir) {
                int len = 2; 
                while(true) {
                	len = PathFile.indexOf("/", len);
                    if (-1 == len) {
                        break;
                    } else {
                        NameFile = PathFile.substring(len);
                        len++;
                    }
                }
            }
            if (!NameFile.isEmpty()) {
                String hosa = PathFile.substring(0, PathFile.length()-NameFile.length());
                File newDirectory = new File(hosa);
                if (newDirectory.mkdirs()) {
                    if (verbose) {
                        System.out.println("Create directory:    " + hosa);
                        System.out.println("Create new file:     " + PathFile);
                    }
                }
                file = new File(PathFile);
            } else {
                if (verbose && et) {
                    System.out.println("Overwrite file:      " + PathFile.substring(1));
                } else {
                    System.out.println("Create new file:     " + PathFile.substring(1));
                }
            }
            if (verbose && !presentDir && et) {
            	System.out.println("file being overwritten:      " + PathFile);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(getwrittenContent());
            writer.close();


       
    
        }
        Thread.sleep(1000);
        
		
	}


	private void req() throws IOException {
		String request;
		bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while ((request = bufferReader.readLine()) != null) {
			if (verbose) {
				System.out.println(request);
			}
			if(httpMethod.isEmpty()) {
				if (request.substring(0, 3).equals("GET")) {
					httpMethod = "GET";
                } else if (request.substring(0, 4).equals("POST")) {
                	httpMethod = "POST";
                } else {
                	httpMethod = "GET";
                }
				int t = request.indexOf("/");
				path = request.substring(t, request.indexOf(" ", t+1));
				if (path.equals("/")) {
                    showEntireFiles = true;
                    return;
                }
				
                if (httpMethod.equals("GET")) {
                    if (allFilesList.containsKey(path)) {
                        HandleLock fileHandle = allFilesList.get(path);
                        if (0 == fileHandle.currentlywriting()) {
                        	fileHandle.increaseRead();
                        } else {
                        	codeStatus = 403;
                            body.append("File ").append(path).append(" is currently being writing.").append(whiteSpace);
                            return;
                        }
                    } else {
                    	HandleLock fileHandle = new HandleLock(path, "READ");
                    	allFilesList.put(path, fileHandle);
                    }
                } else if (httpMethod.equals("POST")) {
                    if (allFilesList.containsKey(path)) {
                        codeStatus = 403;
                        String readOrWrite = 1 == allFilesList.get(path).currentlywriting() ? "writing." : "reading.";
                        body.append("File ").append(path).append(" is currently being ").append(readOrWrite).append(whiteSpace);
                        return;
                    } else {
                        HandleLock lock = new HandleLock(path, "WRITE");
                        allFilesList.put(path, lock);
                    }
                }
            } else if (0 == l &&  request.length() > 13 && request.substring(0, 14).equals("Content-Length")) {
                l = Integer.parseInt(request.substring(16));
            }
			

            // deal with POST -d or -f
            if (readBody) {
            	bodyForPost.add(request + whiteSpace);
                l -= request.length() + 2;
                if (l > 0) {
                    continue;
                } else if (l < 0) {
                    // TODO: set status code 400
                    System.out.println("ERROR: contentLength = " + l);
                    return;
                } else {
                    break;
                }
            }

            // deal with GET, POST without -d or -f
            if (request.isEmpty()) {
                if (0 == l) {
                    break;
                } else {
                    readBody = true;
                }
            }
        }
		
	}


	private void createResponse() throws InterruptedException {
            if (404 == codeStatus) {
                response.append("HTTP/1.1 404 NOT FOUND\r\n");
                body.append("The requested URL could not be found on server.\r\n");
            } else if (403 == codeStatus) {
                response.append("HTTP/1.1 403 Forbidden\r\n");
            } else if (400 == codeStatus) {
                response.append("HTTP/1.1 40 Bad Request\r\n");
            } else {
                Thread.sleep(1000);
                response.append("HTTP/1.1 200 OK\r\n");
                // TODO: show create/overwrite info
                if (httpMethod.equals("POST"))
                body.append("Post file successfully.");
            }
            response.append("Connection: close\r\n");
            response.append("Server: httpfs\n");
            response.append("Date: ").append(Calendar.getInstance().getTime().toString()).append(whiteSpace);
            response.append("Content-Type: ").append(t).append(whiteSpace);
            response.append("Content-Length: ").append(body.length()).append(whiteSpace);
            response.append(whiteSpace);
            response.append(body.toString());
        }
		


	private String getwrittenContent() {
        StringBuilder data = new StringBuilder();
        boolean r = false;
        for (String line : bodyForPost) {
            if (line.equals(whiteSpace)) {
                if (!r) {
                    r = true;
                } else {
                    break;
                }
            } else if (r) {
            	data.append(line);
            }
        }
        return data.toString();
    }

	private void showEntireList(String directory2, StringBuilder body2) {

        File presentDirectory = new File(directory2);
        File[] allList = presentDirectory.listFiles();
        if (null != allList) {
            for (File file : allList) {
                if(file.isFile()) {
                	body2.append(".").append(file.getPath().substring(path.length())).append(whiteSpace);
                } else if (file.isDirectory()) {
                	showEntireList(directory2 + "/" + file.getName(), body2);
                }
            }
        }
    
		
	}

}
