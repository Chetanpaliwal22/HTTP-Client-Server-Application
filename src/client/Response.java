package client;
/**
 * This is response class for http client
 */
public class Response {

	public String responseUrl;
	public String status;

	Response() {
		this.status = "";
		this.responseUrl = "";
	}

	public String getResponseUrl() {
		return responseUrl;
	}

	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
