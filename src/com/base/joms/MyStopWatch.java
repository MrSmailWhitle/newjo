package src.com.base.joms;

import org.apache.commons.lang3.time.StopWatch;
//�̳���commoms.lang3��stopwatch����
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
