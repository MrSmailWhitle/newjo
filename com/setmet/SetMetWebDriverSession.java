package com.setmet;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import src.com.base.joms.OmsLogger;

public class SetMetWebDriverSession {
	private static ThreadLocal<RemoteWebDriver> rwd=new ThreadLocal<RemoteWebDriver>();
	private static Logger log=OmsLogger.getLogger(SetMetWebDriverSession.class);
	public static  RemoteWebDriver get() {
		// TODO Auto-generated method stub
		return rwd.get();
	}

	public static void set(RemoteWebDriver remotewebdriver) {
		// TODO Auto-generated method stub
		rwd.set(remotewebdriver);
	}

	public static void pause(long time) {
		// TODO Auto-generated method stub
		
	}

}
