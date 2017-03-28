package src.com.base.joms;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.*;
public class Xmlpaser {
	private static Logger logger=Logger.getLogger(Xmlpaser.class);
	private Document doc;
	//private static Xmlpaser instance;
	public Xmlpaser(){
		InputStream in=getInputAsStream(this.getClass());
		SAXReader reader=new SAXReader();
		try {
			doc=reader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	//获取输入的xml文件：follow:
	private InputStream getInputAsStream(Class<?> testclass) {
		String packagename=null;
		if(testclass.getPackage()!=null){
			packagename=testclass.getPackage().getName().toString()+".";
		}
		String baseresoure=(packagename+testclass.getSimpleName()).replace('.', '/');
		ClassLoader load=testclass.getClassLoader();
		String xmlpath=baseresoure+".xml";
		return load.getResourceAsStream(xmlpath);
	}
	 /*public static synchronized Xmlpaser getInstance(){  
	        if(instance == null)  
	            instance = new Xmlpaser();  
	        return instance;  
	    }  */
        
	public String locator(String name){
		String locator=null;
		Element datasets=(Element) this.doc.selectSingleNode("datasets");
		for(Iterator d=datasets.elementIterator("locators");d.hasNext();){
			Element data=(Element) d.next();
			for(Iterator e=data.elementIterator("locator");e.hasNext();){
				Element elem=(Element) e.next();
					//String value=elem.valueOf(".");
					String value=elem.getText();
					String nameString=elem.valueOf("@name");
					//System.out.println("\n'"+nameString+"'");
					if(name.equalsIgnoreCase(nameString.toUpperCase())){
						if(!elem.valueOf("@by").isEmpty()){
							
							locator=elem.valueOf("@by")+"="+value;
						}
						break;
					}
				}
			}
		return locator;
		
	}
	@Test
	public void testLoginlocator(){
		String lor="pwdlocator";
		String result=this.locator(lor);
		System.out.println(lor+"::"+result);
	}
}
