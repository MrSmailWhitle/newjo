package com.oms.datasets;

import java.util.ArrayList;
import java.util.Hashtable;

public class OmsTestDataSetBean{
private Hashtable<String,String> myVars=new Hashtable<String,String>();
private Hashtable<String ,ArrayList<String>> myVarlists=new Hashtable<String,ArrayList<String>>();

private Hashtable<String,String> myFiles=new Hashtable<String,String>();
private Hashtable<String ,ArrayList<String>> myFilelists=new Hashtable<String,ArrayList<String>>();

private String name;
public OmsTestDataSetBean(String name){
this.name=name;
}
public void addVars(String varname,String varval){
myVars.put(varname,varval);
}
public void addVarlist(String varname,ArrayList<String> valueList){
myVarlists.put(varname,valueList);
}
public void addFiles(String filename,String filepath){
myVars.put(filename,filepath);
}
public void addFileslist(String filename,ArrayList<String> FileList){
myVarlists.put(filename,FileList);
}
public String getName(){
return this.name;
}

public String getVar(String varname){
if(myVars.containsKey(varname)){
return myVars.get(varname);
}
return null;
}
public String getFile(String filename){
if(myFiles.containsKey(filename)){
return myFiles.get(filename);
}
return null;
}
public ArrayList<String> getVars(String listname){
if(myVarlists.containsKey(listname)){
return myVarlists.get(listname);
}
return null;
}

public ArrayList<String> getFiles(String listname){
if(myFilelists.containsKey(listname)){
return myFilelists.get(listname);
}
return null;
}

}