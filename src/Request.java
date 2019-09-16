/**
 * This is request class for http client
 */
public class Request {

	public String url;
	public String key;
	public String value;
	public boolean isVerbose;

	Request() {
		this.url = "";
		this.isVerbose = false;
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

	
}
