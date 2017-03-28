package com.oms.datasets;

import java.io.InputStream;

import org.apache.log4j.Logger;

import src.com.base.joms.Oms;
import src.com.base.joms.OmsLogger;
import src.com.base.joms.OmsTestCase;

public class InputFileFinder {
	private static final Logger log=OmsLogger.getLogger(InputFileFinder.class.getName());
	public static InputStream getInputstreamAsStream(OmsTestCase testClass){
		return getInputstreamAsStream(testClass.getClass());
		
	}
	public static InputStream getInputstreamAsStream(Oms omsclass){
		return getInputstreamAsStream(omsclass.getClass());
	}
	private static InputStream getInputstreamAsStream(Class<?> clazz) {
		String packagename="";
		if(clazz.getPackage()!=null){
			packagename=clazz.getClass().getName()+".";
		}
		String resourcebase=(packagename+clazz.getSimpleName()).replace(".", "/");
		String resource=resourcebase+".xml";
		ClassLoader loader=clazz.getClassLoader();
		return loader.getResourceAsStream(resource);
	}
	
}
