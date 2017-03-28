package src.com.base.joms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Configurator {
	public static final String DEFAULT_PROPERTIES_RESOURCE="/src/com/oms/conf/oms-default.properties";
	public static final String OMS_PROPERTIES="oms.conf";
	private static Properties defaults=new Properties();
	private HashMap<String,String> conMap=new HashMap<String ,String>();
	private static Configurator INSTANCE=null;
	private static Logger log=OmsLogger.getLogger(Configurator.class);
	private static String propFileName=System.getProperty(OMS_PROPERTIES);
	static{
		System.out.println("Loading defualt.....");
		try{
			defaults.load(Configurator.class.getResourceAsStream(DEFAULT_PROPERTIES_RESOURCE));
		}catch(IOException e){
			try {
				throw new ConfigurationError(e.getMessage(),e);
				} catch (ConfigurationError e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
		if(propFileName!=null){
			File propFile=new File(propFileName);
			log.info("\n PropFile: "+propFile.getName());
			try{
				Properties props=new Properties();
				props.load(new FileInputStream(propFile));
				initialize(props);
			}catch(IOException e){
				try {
					//configurationerror是怎么来的呢；
					throw new ConfigurationError("While load propfile erro:"+propFile.getAbsolutePath(),e);
				} catch (ConfigurationError e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		}
	//****************************************************************************
	public static final String OMS_OUTPUT_VERBOSITY="oms.output.verbosity";
	public static final String OMS_OUTPUT_PATTERN="oms.output.pattern";
	public static final String OMS_OUTPUT_FILENAME="oms.output.file";
	//****************************************************************************
	public static String getOmsOutpuPattern() {
		// TODO Auto-gen.erated method stub
		return getString(OMS_OUTPUT_PATTERN);
	}

	

	public static String getOmsOutputVerbosity() {
		return getString(OMS_OUTPUT_VERBOSITY);
	}
	public static File getOmsOutputFile(){
		return getFile(OMS_OUTPUT_FILENAME);
	}
	
	private Configurator(Properties prop){
		this();
		String pattern=prop.getProperty(OMS_OUTPUT_PATTERN);
		String verbosity=prop.getProperty(OMS_OUTPUT_VERBOSITY);
		File logFile=getLogFile(prop.getProperty(OMS_OUTPUT_FILENAME));
		if(logFile!=null){
			prop.setProperty(OMS_OUTPUT_FILENAME,logFile.getAbsolutePath());
		}
		OmsLogger.initializeLogging(pattern,verbosity,logFile);
		for(Entry<Object, Object> entry:prop.entrySet()){
			String key=(String) entry.getKey();
			String value=(String) entry.getValue();
			if(value!=null&&!value.isEmpty()){
				log.info("Loading configuration property:"+key+"="+value);
				conMap.put(key, value);
			}
		}
	}
//****************************************************************
	
	private static void initialize(Properties prop) {
		INSTANCE=new Configurator(prop);		
	}
	private Configurator() {
		setDefaults();
	}
	private static Configurator getInstance(){
		return INSTANCE;
		
	}
	private void setDefaults() {
		// L10N
		setDefault(L10N_lANGUAGE);
		setDefault(APP_URL);
		setDefault(OMS_OUTPUT_PATTERN);
		setDefault(OMS_OUTPUT_VERBOSITY);
		setDefault(OMS_OUTPUT_FILENAME);
		
		
	}
	private void setDefault(String option){
		String value=defaults.getProperty(option);
		if(value!=null&&value.trim().isEmpty()){
			value=null;
		}
		conMap.put(option, value);
	}
	
	private File getLogFile(String LogFilename) {
		File logFile=null;
		if(LogFilename!=null&&!LogFilename.trim().isEmpty()){
			logFile=new File(LogFilename);
			if(!logFile.isAbsolute()){
				logFile=new File(System.getProperty("user.home"),LogFilename);
			}
		}
		System.out.println("\n logfile path:"+logFile.getAbsolutePath());
		return logFile;
	}

	private static File getFile(String omsOutputFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getString(String optionName) {
		// TODO Auto-generated method stub
		return getInstance().conMap.get(optionName);
	}
	private static int getIntoOption(String optionname){
		String value=getInstance().conMap.get(optionname);
		return Integer.parseInt(value);
	}
	private static File getFile(String optionname,String defaultvalue){
		String value=getInstance().conMap.get(optionname);
		if(value==null){
			return (defaultvalue==null)? null:new File(new File(System.getProperty("user.home"),".setMet"),defaultvalue);
		}
		System.out.println("Filename:"+value);
		return new File(value);
	}
	//*****************************************
	public static final String APP_URL="app.url";
	public static final String SELENIUM_PORT="app.url";
	public static final String BROWSER_COMMAND="app.url";
	public static final String SELENIUM_HOST="app.url";
	public static final String WEBDRIVER_NAME="app.url";
	public static final String TESTCASE_TIMEOUT="app.url";
	public static final String L10N_lANGUAGE="app.url";
	//***************************
	public static String getAppURL(){
		return getString(APP_URL);
	}
	public static int getTestCaseTimeout(){
		int time=getIntoOption(TESTCASE_TIMEOUT)*60+1000;
		return time;
	}



	public static String getBrowserName() {
		// TODO Auto-generated method stub
		return null;
	}



	public static File geIEDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}
