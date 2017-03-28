package src.com.base.joms;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class OmsLogger {
	public static final String CONSOLE_APPENDER_NAME=UUID.randomUUID().toString();
	public static final String FILE_APPENDER_NAME=UUID.randomUUID().toString();
	
	private static Logger root=LogManager.getRootLogger();
	
	private static boolean configuredExternally=root.getAllAppenders().hasMoreElements();
	private static String defaultPattern=Configurator.getOmsOutpuPattern();
	private static String defaultVerbosity=Configurator.getOmsOutputVerbosity();
	
	static {
		initializeLogging(defaultPattern,defaultVerbosity,null);
	}

	static void initializeLogging(String pattern, String verbosity, File logFile) {
		if(!configuredExternally){
			root.removeAppender(CONSOLE_APPENDER_NAME);
			Appender conApp=new ConsoleAppender(new PatternLayout(pattern));
			conApp.setName(CONSOLE_APPENDER_NAME);
			root.addAppender(conApp);
			root.setLevel(Level.toLevel(verbosity, Level.ALL));
		}
		if(logFile!=null){
			try {
				Appender fileApp=new FileAppender(new PatternLayout(pattern),logFile.getPath());
				fileApp.setName(FILE_APPENDER_NAME);
				root.addAppender(fileApp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void disableFIleLogging(){
		root.removeAppender(FILE_APPENDER_NAME);
	}
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getClass());
	}
	public static Logger getLogger(String name){
		return LogManager.getLogger("oms."+name);
	}

}
