import java.util.HashMap;
import java.util.Map;

/**
 * This is request class for http client
 */
public class Request {

	public String url;
	public String key;
	public String value;
	public boolean isVerbose;
	Map<String, String> requestParameter;

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
