package com.setmet;

import java.io.File;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import src.com.base.joms.Configurator;
import src.com.base.joms.OmsLogger;

public class WebDrivers {
	private static final Logger log=OmsLogger.getLogger(WebDrivers.class);
	private static WebDriver driver;
	static{
		log.debug("called webdrivers static.");
		setDriver();
	}
	
	
	
	
	public static WebDriver getDriver() {
		// TODO Auto-generated method stub
		return driver;
	}




	private static void setDriver() {
		String browserName=Configurator.getBrowserName();
		DesiredCapabilities capabilities=new DesiredCapabilities();
		capabilities.setBrowserName(browserName);
		driver=setDriver(capabilities);
	}



	private static WebDriver setDriver(DesiredCapabilities capabilities) {
		String browserType=capabilities.getBrowserName();
		if(browserType.equals(BrowserType.IE))
		return setDriverIE(capabilities);
		else{
			log.info("\n BrowserType Not Supported.using ie now");
			return setDriverIE(capabilities);
		}
	}




	private static WebDriver setDriverIE(DesiredCapabilities capabilities) {
		log.debug("\n set ie driver");
		File file=Configurator.geIEDriver();
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		driver=new InternetExplorerDriver(capabilities);
		return driver;
		
		
		
	}

}
