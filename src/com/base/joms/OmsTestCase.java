package src.com.base.joms;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.Description;

import com.oms.datasets.InputFileDigester;
import com.oms.datasets.InputFileFinder;
import com.oms.datasets.OmsTestDataset;

import src.com.base.i10helper.HsqlHelper;

public abstract class OmsTestCase {
	private static ArrayList<MyStopWatch> timers=null;
	private static ArrayList<CheckPoint> checkPoints=null;
	private static Logger log;
	private final File directory=new File("./pic");
	private static String dateSetoverride;
	OmsTestDataset data;
	public OmsTestCase(){
		log=OmsLogger.getLogger(this.getClass());
		I18Nutil.processI18NKeys(this);
		directory.mkdir();
	}
	@Rule
	public NewTestWatcher watcher=new NewTestWatcher(){
	@Override
	public void failed(Throwable e,Description description){
		silentlySaveScreenshotTo(filenameFor(description),"png");
	}
	};
	
	@BeforeClass
	private static void setUpClass(){
		HsqlHelper.startHsql();
	}
	@Before
	private void setUp(){
		timers=new ArrayList<MyStopWatch>();
		checkPoints=new ArrayList<>();
		initializeDataSet();
		start();
	}
	////initializeDataSet***************************************************************
	private void initializeDataSet() {
		try{InputStream in=InputFileFinder.getInputstreamAsStream(this);
		if(in!=null){
			log.info("found test input ...digesting file...." );
			InputFileDigester digester=new InputFileDigester(in);
			
			digester.parseDatasets(dateSetoverride);//原书中有get。应该去掉的；
			data=digester.getWorkingDataSet();
		}else{
			log.info("No test input file found.");
		}
		}catch(Exception e){
			log.error("Error while parsing input file...");
		}	
	}
	public static void setDataSetOverride(String dataname){
		dateSetoverride=dataname;
	}
	public static String getDataSetOverride(){
		return dateSetoverride;
	}
	public OmsTestDataset getData(){
		if(data==null){
			try {
				throw new OmsException("cannot retrie dataset.no dataset found");
			} catch (OmsException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	public void useDataSet(String datasetname){
		setDataSetOverride(datasetname);
		initializeDataSet();
	}
	//**************************************************************************************
	
	private void start() {	}
	public String uniquify(String str){
		return str+"_"+System.currentTimeMillis();
	}
	public static void waitfor(long millites){
		try{Thread.sleep(millites);}
		catch(Exception e){
			log.info("\n Time out with excepiton:"+e.getMessage());
			}
	}
	@After
	public static void tearDown(){
		checkForFailures();
		checkTimers();
	}
	@AfterClass
	public static void tearDownClass(){
		HsqlHelper.stopHsql();
	}
	

	public MyStopWatch newMystopWatch(String id){
		MyStopWatch timer=new MyStopWatch(id);
		timers.add(timer);
		return timer;
		
	}
	private static void checkTimers() {
		StringBuilder sb=new StringBuilder("ID,TIME");
		for(MyStopWatch timer:timers){
			sb.append("\n");
			sb.append("\n"+timer.getid()+"\"");
			sb.append(".");
			sb.append("\""+timer.getTime()+"\"");
		}
		log.info(sb.toString());
		
	}

	private static void checkForFailures() {
		String exceptionMessage="";
		boolean checkFailuredFound=false;
		for(CheckPoint check:checkPoints){
			if(!check.status()){checkFailuredFound=true;
			log.error(check.getStatusMessage());
			}
		}
		if(checkFailuredFound){
			exceptionMessage=exceptionMessage+"Found CheckPoint failures! see log for details.";
		}
		if(!exceptionMessage.isEmpty()){
			try {
				throw new OmsException(exceptionMessage);
			} catch (OmsException e) {
				e.printStackTrace();
			}
			
		}
	}

	public static ArrayList<CheckPoint> getCheckPoint(){
		return checkPoints;
	}//提供外部访问的方法
	
	public CheckPoint newCheckPoint(String id,String description) throws OmsException{
		if(!checkExists(id)){
			CheckPoint check=new CheckPoint(id,description);
			checkPoints.add(check);
			return check;
		}
		throw new OmsException("checkwithid:"+id+"alreadyexists.specify unique id for your check.");
		
	}
	private boolean checkExists(String id){
		for(CheckPoint check:checkPoints){
			if(check.getId().equals(id)){
				return true;
				
			}
		}
		return false;
	}
	
}
