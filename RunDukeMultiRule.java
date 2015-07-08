import no.priv.garshol.duke.*;
import no.priv.garshol.duke.matchers.PrintMatchListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class RunDukeMultiRule{

  private static long startTime;
  private static long lastEndTime;

  public static void main(String[] argv) throws Exception {
	startTime = System.currentTimeMillis();
	lastEndTime = startTime;
	
	// Read in matching configuration text - scan by line
	// Eventually we want to move the config into a transparent location like UI interface or sql
		String fileName = "X:\\brandon\\matching_configuration.txt";
		System.out.println("FileName: "+ fileName); 
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        int inputCount=0; 
		
	while(scanner.hasNext()){    
			
			//read in line by line
        	String FileTest = ""+scanner.next()+"";
        	inputCount++;
            String filePath = FileTest;
			String newLine = System.getProperty("line.separator");//This will retrieve line separator dependent on OS. 
            System.out.println(newLine+"Matching config number "+ inputCount + " - " + filePath+newLine); 
            Configuration config = ConfigLoader.load(filePath); 
    	    Processor proc = new Processor(config);
			
			//parameter: showmatches,showmaybe,progress,linkage, properties, pretty
			//first parameter showMatches impacts performance
    	    proc.addMatchListener(new PrintMatchListener(false,false, true, false,config.getProperties(),true));
    	    
			//settings
			proc.setPerformanceProfiling(true);
    	    proc.setThreads(5);
			
			//proc.link
			proc.link(config.getDataSources(1), config.getDataSources(2), 120000);
			
			//Log Timestamp 
    		long start = System.currentTimeMillis(); 
    		System.out.println("Processing time for this rule: " + (System.currentTimeMillis() - lastEndTime)/1000.0 + " seconds");
    		System.out.println("Total processing time so far: " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds");
    		lastEndTime = System.currentTimeMillis();
			proc.close(); 
			
        }
        scanner.close();
  } 

}