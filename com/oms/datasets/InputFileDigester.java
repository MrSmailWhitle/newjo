package com.oms.datasets;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import src.com.base.joms.OmsException;
import src.com.base.joms.OmsLogger;
import src.com.base.joms.OmsTestCase;

public class InputFileDigester{
private static final Logger log=OmsLogger.getLogger(InputFileDigester.class.getName());
private Document doc;
private OmsTestDataset  workingDataSet;

public InputFileDigester(InputStream in){
SAXReader reader=new SAXReader();
try{
doc=reader.read(in);
}catch(DocumentException e){
log.error("Error when attemping to parse input file",e);}
}

public OmsTestDataset getWorkingDataSet(){
return workingDataSet;
}
public ArrayList<OmsTestDataset > parseDatasets(String suiteDataSetName) throws OmsException{
ArrayList<OmsTestDataset> datasetcollection=new ArrayList<OmsTestDataset>();
Element workingDS=(Element) this.doc.selectSingleNode("/workingDataSet");
String workingDSname=null;
if(suiteDataSetName!=null&&suiteDataSetName.trim().length()!=0){
workingDSname=suiteDataSetName.trim();
}else{
if(workingDS!=null){
	workingDSname=workingDS.attributeValue("name");
}
}
log.debug("=======Working Data Set:"+workingDSname);
if(workingDSname!=null){
OmsTestCase.setDataSetOverride(workingDSname);
Element datasets=(Element) this.doc.selectSingleNode("//datasets");
for(Iterator d=datasets.elementIterator("dataset");d.hasNext();){
Element data=(Element) d.next();
String dataSetName=data.attributeValue("name");
//
OmsTestDataSetBean dataSetBean=new OmsTestDataSetBean(dataSetName);

for(Iterator v=data.elementIterator("var");v.hasNext();){
Element elem=(Element) v.next();
dataSetBean.addVars(elem.valueOf("@name"),elem.valueOf("."));
}
for(Iterator f=data.elementIterator("file");f.hasNext();){
Element elem=(Element) f.next();
dataSetBean.addFiles(elem.valueOf("@name"),elem.valueOf("."));
}

for(Iterator l=data.elementIterator("list");l.hasNext();){
Element elem=(Element) l.next();
String listname=elem.attributeValue("name");
ArrayList<String > varlist=new ArrayList<String>();
for(Iterator v=data.elementIterator("var");v.hasNext();){
Element listElem=(Element) v.next();
varlist.add(listElem.getText());
}
if(varlist.size()>0){
dataSetBean.addVarlist(listname,varlist);
}else{
ArrayList<String> fileList=new ArrayList<String>();
for(Iterator f=data.elementIterator("file");f.hasNext();){
Element listElem=(Element) f.next();
fileList.add(listElem.getText());
}
dataSetBean.addFileslist(listname,fileList);
}
}
OmsTestDataset dataSet=new OmsTestDataset(dataSetBean);
datasetcollection.add(dataSet);
if(dataSetName.equals(workingDSname)){
this.workingDataSet=dataSet;
}
}
}
else{
throw new OmsException("No data set defined");
}
return datasetcollection;
}



}
