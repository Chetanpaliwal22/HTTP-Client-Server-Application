/**
 * This is request class for http client
 */
public class Request {

	public String url;
	public String inputFile;
	public String outputFile;

	Request() {

		this.url = "";
		this.inputFile = "";
		this.outputFile = "";
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

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

}
