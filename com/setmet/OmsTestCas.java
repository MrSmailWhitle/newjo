package com.setmet;

import org.apache.log4j.Logger;
import org.junit.After;
import org.openqa.selenium.remote.RemoteWebDriver;

import src.com.base.joms.Configurator;
import src.com.base.joms.OmsLogger;
import src.com.base.joms.OmsTestCase;

public class OmsTestCas extends OmsTestCase {
	protected static Logger log;
	private static RemoteWebDriver rwd=null;
	public OmsTestCas(){
		log=OmsLogger.getLogger(this.getClass());
	}
	public void start(){
		if(SetMetWebDriverSession.get()==null){
			rwd=(RemoteWebDriver) WebDrivers.getDriver();
			start(rwd);
			waitfor(3000);
		}
	}
	public void start(RemoteWebDriver wd){
		String appurl=Configurator.getAppURL();
		wd.get(appurl);
		log.info("\n \n setting selenium with url::"+appurl);
		SetMetWebDriverSession.set(wd);
	}
	@After
	public void cleanUP(){
		SetMetWebDriverSession.get().quit();
	}
}
