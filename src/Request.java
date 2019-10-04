import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is request class for http client
 */
public class Request {

	public String url;
	public String key;
	public int port = 80;
	public int localHostPort = 8080;
	public String host ="";
	public String value = "";
	public boolean isVerbose = false;
	Map<String, String> requestParameter;
	public String fileData = "";
	public String inlineData = "";
	public String userAgent = "Windows NT 6.3";
	public int[] redirectCodeArray = {301,302,303};
	public boolean dOptionStatus = false;
	public boolean fOptionStatus = false;
	String USER_AGENT = "CN Concordia - HTTP/1.0";
	public String fileName = "";
	ArrayList<String> headers = new ArrayList<String>();
	boolean isdfOptionDone = false;
	String crlf = "\r\n";
	public boolean isWriteFile;

	public boolean isIsdfOptionDone() {
		return isdfOptionDone;
	}

	public void setIsdfOptionDone(boolean isdfOptionDone) {
		this.isdfOptionDone = isdfOptionDone;
	}

	public ArrayList<String> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<String> headers) {
		this.headers = headers;
	}

	public boolean isdOptionStatus() {
		return dOptionStatus;
	}

	public void setdOptionStatus(boolean dOptionStatus) {
		this.dOptionStatus = dOptionStatus;
	}

	public boolean isfOptionStatus() {
		return fOptionStatus;
	}

	public void setfOptionStatus(boolean fOptionStatus) {
		this.fOptionStatus = fOptionStatus;
	}

	public Map<String, String> getRequestParameter() {
		return requestParameter;
	}

	public void setRequestParameter(Map<String, String> requestParameter) {
		this.requestParameter = requestParameter;
	}

	public String getInlineData() {
		return inlineData;
	}

	public void setInlineData(String inlineData) {
		this.inlineData = inlineData;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	Request() {
		this.url = "";
		this.isVerbose = false;
		this.requestParameter = new HashMap<String, String>();
	}

	public boolean isVerbose() {
		return isVerbose;
	}

	public void setVerbose(boolean isVerbose) {
		this.isVerbose = isVerbose;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParameter() {
		String response = "";
		for (Map.Entry<String, String> entry : this.requestParameter.entrySet()) {
			response += entry.getKey() + ":" + entry.getValue() + " \r\n";
		}
		return response;
	}

}
