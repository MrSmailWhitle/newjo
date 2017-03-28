package src.com.base.joms;

import java.io.IOException;

public class ConfigurationError extends Exception {
	public String message=null;
	public ConfigurationError(String message, IOException e) {
			this.message=message;
			e.printStackTrace();
	}

}
