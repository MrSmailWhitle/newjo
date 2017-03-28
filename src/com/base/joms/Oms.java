package src.com.base.joms;

import java.util.HashMap;

import org.apache.log4j.Logger;

public abstract class Oms {
	private  static final Logger log=OmsLogger.getLogger(Oms.class);
	protected final HashMap<String,String> parameters=new HashMap<String,String>();
	public Oms(){
		I18Nutil.processI18NKeys(this);
	}
	public void parameters(String...params){
		for(String param:params){
			String name=param.substring(0,param.indexOf("=")).trim();
			String value=param.substring(param.indexOf('=')+1).trim();
			if(!parameters.containsKey(name))
				parameters.put(name, value);
			else
				log.warn("Duplicated parameters'"+name+"'given,Ignoring......");
		}
	}
	public String getParam(String name){
		if(!parameters.containsKey(name)){
			log.debug("Requested parameters"+name+"'do't exite");
		}
		return parameters.get(name);
	}
	public void setParam(String name,String value){
		parameters.put(name,value);
		
	}
	public boolean paramDefined(String paramname){
		return (!parameters.containsKey(paramname))&&(!paramname.equals(' '))&&(!paramname.isEmpty());
	}
	public abstract void prepare();
	public static <A extends Oms> A navigateTo(A B){
		B.prepare();
		return B;
		}
	public static void waitfor(long millises){
		try{Thread.sleep(millises);}catch(Exception e){
			log.error("Time out:"+e.getMessage());
		}
	}
}
