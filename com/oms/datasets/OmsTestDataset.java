package com.oms.datasets;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class OmsTestDataset {
	private OmsTestDataSetBean mybean;
	public OmsTestDataset(OmsTestDataSetBean bean){
		this.mybean=bean;
	}
	public String getName(){
		return mybean.getName();
	}
	public String getVariable(String varName){
		String varValue=mybean.getVar(varName);
		if(varValue==null){
			throw new InvalidParameterException("Input varialbe \""+varName+"\"not found.");
		}
		return varValue;
		
	}
	public ArrayList<String> getVariables(String listname){
		ArrayList<String> vars=mybean.getVars(listname);
		if(vars==null){
			throw new InvalidParameterException("Input varialbes \""+listname+"\"not found.");
		}
		return vars;
	}
	
	public File getFile(String fileName){
		String filepath=mybean.getFile(fileName);
		if(filepath==null){
			throw new InvalidParameterException("Input file \""+fileName+"\"not found.");
		}
		File file=new File(filepath);
		if(!file.exists()){
			throw new InvalidParameterException("Input file's filepath \""+filepath+"\"not found.");			
		}
		return file;
	}
	
	public ArrayList<File> getFiles(String listName){
		ArrayList<String> filespath=mybean.getFiles(listName);
		if(filespath==null){
			throw new InvalidParameterException("Input files's  \""+listName+"\"not found.");
		}
		ArrayList<File> files=new ArrayList<File>();
		for(String filepath:filespath){
			File file=new File(filepath);
			if(!file.exists()){
				throw new InvalidParameterException("Input file's filepath \""+filepath+"\"not found.");
			}
			files.add(file);
		}
		return files;
		
	}

}
