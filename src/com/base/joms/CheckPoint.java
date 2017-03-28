package src.com.base.joms;

import org.apache.log4j.Logger;

public class CheckPoint {
		private final String id;
		private String description="";
		private Throwable error=null;
		private String message="";
		private boolean result=false;
		private Logger log;
		private CheckPoint(String id) throws OmsException{
			if(id==null||id.trim().isEmpty()){
				throw new OmsException("CheckPoint ID cannot be <null> or an empty String.");
			}
			this.id=id;
			log.info("Checkpoint:"+id+"created.");
		}
		public CheckPoint(String id,String description) throws OmsException{
			this(id);
			if(description==null){
				throw new OmsException("CheckPoint Description cannot be <null>.");
			}
			this.description=description;
		}
		
		
		public void validate(boolean condition,String failureMessage){
			if(condition){
				succeeded();
			}else{
				failed(failureMessage);
			}
		}
		public void validate(boolean condition){
			validate(condition,null);
		}
		
		
		private void failed(String failureMessage) {
			result=false;
			message=prepareMessage(failureMessage,result);
			log.error(message);
		}
		public void failed(String message,Throwable t){
			result=false;
			this.message=message;
			this.error=t;
			log.error("CheckPoint:"+getId()+":"+message+"failed.");
		}
		
		
		
		public String getId() {
			// TODO Auto-generated method stub
			return this.id;
		}
		
		
		private void succeeded() {
			succeeded(null);
		}
		private void succeeded(String successMessage) {
			// TODO Auto-generated method stub
			result=true;
			this.message=prepareMessage(successMessage,result);
			log.info(this.message);
		}
		
		private String prepareMessage(String message,boolean success){
			StringBuilder text=new StringBuilder("CheckPoint"+id);
			if(!description.trim().isEmpty()){
				text.append("("+message+")");
			}
			text.append(success?"succeeded":"failed");
			if(!(message==null||message.trim().isEmpty())){
				text.append(success?"Message:":"Reason:");
				text.append(message);
			}
			return text.toString();
		}
		
		public boolean status(){
			return result;
		}
		
		public Throwable getError(){
			return this.error;
		}
		public String getStatusMessage(){
			return message;
		}
		public String getDescription(){
			return description;
		}
		
		
		
}
