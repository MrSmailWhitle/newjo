package com.setmet;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import src.com.base.joms.Oms;
import src.com.base.joms.OmsLogger;

public abstract class SetMet extends Oms{
	private static final Logger log=OmsLogger.getLogger(SetMet.class);
	private static String id;
	private static String name;
	private static String xpath=null;
	private String rootxpath=null;
	private WebElement element=null;
	private String elementTag="*";
	protected static RemoteWebDriver driver=null;
	public RemoteWebDriver getRemoteWebDriver(){
		return SetMetWebDriverSession.get();
	}
	public void setRemoteWebDriver(RemoteWebDriver remotewebdriver){
		SetMetWebDriverSession.set(remotewebdriver);
		driver=SetMetWebDriverSession.get();
	}
	
	protected SetMet(){
		this((RemoteWebDriver)null);
		log.debug("\n \n called::'SetMet()'");
	}
	protected SetMet(RemoteWebDriver driver){
		log.debug("\n \n called::'setmet(remotewebdriver driver)'");
		if(driver!=null){
			SetMetWebDriverSession.set(driver);
		}
		driver=SetMetWebDriverSession.get();
	}
	public SetMet(WebElement element){
		this();
		this.element=element;
		elementTag=element.getTagName();
	}
	public SetMet(String...params){
		this();
		prepare();
		log.debug("\n called prepare() in SetMet(String param)");
		parameters(params);
		log.debug("\n param::"+params);
		if(paramDefined("id")){setId(getParam("id"));}
		if(paramDefined("name")){this.name=getParam("name");}
		if(paramDefined("xpath")){this.xpath=getParam("xpath");}
		if(getId()!=null&&getId.length()>0){rootxpath="//"+elementTag+"[id='"+getId()+"']";
		return;;}
		if(this.name!=null&&this.name.length()>0){
			rootxpath="//"+elementTag+"[@name="+this.name+"']";
			return;}
		if(this.xpath!=null&&this.xpath.length()>0){
			rootxpath=this.xpath;
			return;
		}
		rootxpath=null;
		return;
	}
	//*********************************************************************
	public String getRootXpath(){
		return rootxpath;
	}
	protected WebElement getElementUsingXpath(String xpathlocator){
		return getElement(By.xpath(xpathlocator));
	}
	protected List<WebElement> getElementsUsingXpath(String xpathlocator){
		return getElements(By.xpath(xpathlocator));
	}
	
	
	
	private List<WebElement> getElements(By by) {
		List<WebElement> elements;
		try{elements=driver.findElements(by);}catch(NoSuchElementException e){
			log.error("Cound not find elements :"+by.toString());
			elements=null;
		}
		return elements;
	}
	private WebElement getElement(By by) {
		try{element=(WebElement)driver.findElement(by);
		}catch(NoSuchElementException e){
			log.error("could not find element :"+by.toString());
		}
		return element;
	}
	
	private List<WebElement> getElements(String param) {
		try{return getElements(getLocator(param));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	private WebElement getElement(String param) {
		try{return getElement(getLocator(param));
		}catch(Exception e){
			e.printStackTrace();
			log.debug("getElement returns a null");
			return null;
		}
	}

	
	private By getLocator(String param) throws Exception {
		String locator=param;
		String locatorType=locator.substring(0,locator.indexOf("=")).trim();
		String locatorvalue=locator.substring(locator.indexOf("=")+1).trim();
		if(locatorType.toLowerCase().equals("id"))
			return By.id(locatorvalue);
			else if(locatorType.toLowerCase().equals("name"))
				return By.name(locatorvalue);
				else if(locatorType.toLowerCase().equals("xpath"))
					return By.xpath(locatorvalue);
					else if(locatorType.toLowerCase().equals("classname"))
						return By.className(locatorvalue);
						
					
					else{
							throw new Exception("no defined.");
					}

	}
	
	

	private String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	private void setId(String param) {
		// TODO Auto-generated method stub
		this.id=param;
	}
//***************************************************************	
	public abstract void prepare();
	

	public void scrollTo(String locator){
		scrollTo(getElement(locator));
	}
	public static void scrollTo(WebElement element){
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	//******************************************************************************
	public boolean isElementPresent(String locator,int wait){
		boolean present=(getElementUsingXpath(locator)!=null)?true:false;
		while(!present&&wait>0){
			waitfor(500);
			wait-=500;
			present=(getElementUsingXpath(locator)!=null)?true:false;
		}
		if(!present){
			log.debug("element not presten,locator:"+locator);
		}
		return present;
		}

	
	public boolean isElementPresentAndVisible(String locator,int wait){
		boolean present=(getElementUsingXpath(locator)!=null)?true:false;
		while(!present&&wait>0){
			waitfor(500);
			wait-=500;
			present=(getElementUsingXpath(locator)!=null)?true:false;
			if(getElementUsingXpath(locator)!=null){
				present=(getElementUsingXpath(locator)).isDisplayed();
			}
		}
		if(!present){
			log.debug("element not presten,locator:"+locator);
		}
		return present;
		}
	
	public boolean isElementPresent(String locator){
		return isElementPresent(locator,5000);
	}
	//********************************************************
	public void moveto(WebElement element){
		Actions action=new Actions(driver);
		action.moveToElement(element).build().perform();
		pause(500);
	}
	private void pause(long time) {
		// TODO Auto-generated method stub
		SetMetWebDriverSession.pause(time);
	}
//**************************************************
	public void mouseOver(WebElement element){
		StringBuilder sb=new StringBuilder();
		sb.append("var element=arguments[0];");
		sb.append("if(document.createEvent){");
		sb.append("Var event=document.createEvent(\"MouseEvent\");");
		sb.append("event.initMouseEvent(\"mouseover\",true,true,window,0,0,0,0,0,false,false,false,false,0,null);");
		sb.append("element.dispatcheEvent(event)");
		sb.append("}");
		sb.append("else if (element.fireEvent){");
		sb.append("element.fireEvent(\"onmouseover\");");
		sb.append("}");
		driver.executeScript(sb.toString(), element);
		
	}
	public void mouseOver(String locator){
		mouseOver(getElementUsingXpath(locator));
		pause(500);
	}
	public static void waitForContent(){
		log.debug("wait for page content to be ready.Ensures all ajax calls are complied before test continues.");
		SetMetWebDriverSession.pause(3000);
	}
	public void setCheckbox(WebElement checkbox,boolean check){
		if(checkbox.isSelected()){
			checkbox.click();
			
		}
	else{
		if(check){checkbox.click();}
	}
	}
	protected void setRadioByValue(List<WebElement>options,String value){
		for(WebElement optin:options){
			String optionvalue=optin.getAttribute("value");
			if(optionvalue.hashCode()==value.hashCode()){
				log.debug("\n found radio box with value="+value+"'");
				optin.click();
				break;
			}
			
		}
	}
	public void importFile(WebElement element,String filepath){
		element.sendKeys(filepath);
	}
	
}
