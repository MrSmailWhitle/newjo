package src.com.base.joms;

import org.apache.commons.lang3.time.StopWatch;
//继承自commoms.lang3的stopwatch工具
public class MyStopWatch extends StopWatch{
	private final String id;
	public MyStopWatch(){
		super();
		this.id="";
	}
	public MyStopWatch(String id){
		this.id=id;
	}
	public String getid(){
		return this.id;
	}
}
