package src.com.base.joms;

//截屏工具，继承自junit的testwatcher;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.runner.Description;



public class NewTestWatcher extends  org.junit.rules.TestWatcher{
	private final File directory=null;
	protected File filenameFor(Description description){
		String classname=description.getClassName();
		String methodname=description.getMethodName();
		
		
		return new File(directory,classname+'_'+methodname+".png");
		}
	protected void silentlySaveScreenshotTo(File file,String format){
		try{
			saveScreenshotTo(file,format);
		}
		catch(Exception e){
			System.err.println("Failed to Screenshot to"+file+","+e);
		}
	}
	private static BufferedImage takeScreenshot() throws AWTException{
		Robot robot=new Robot();
		Rectangle capturesize=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		return robot.createScreenCapture(capturesize);
	}
	private void saveScreenshotTo(File file, String format) throws IOException, AWTException {
		ImageIO.write(takeScreenshot(), format, file);
	}
}
