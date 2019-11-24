package server;

public class HandleLock {
    private String filename;
    private int ReadFiles;
    private int filesWriting;

    public HandleLock(String filename, String action) {
        this.filename = filename;
        if(action.equals("READ")){
            	ReadFiles = 1;
                filesWriting = 0;
        }
        else if(action.equals("WRITE")){
            	ReadFiles = 0;
            	filesWriting = 1;
        }
        else {
                System.out.println("Lock not valid " + action);
        }
    }

    public String getFilename() { 
    	return filename;
    	}

    public int currentlyreading() {
    	return ReadFiles; 
    	}

    public int currentlywriting() { 
    	return filesWriting; 
    }

    public void increaseRead() { 
    	ReadFiles = ReadFiles + 1; 
    	}

    public void decreaseRead() { 
    	ReadFiles = ReadFiles - 1; 
    	}
}
