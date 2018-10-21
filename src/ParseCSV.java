import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;


public class ParseCSV {
	private String path;
	private static final String STUDENT_CRITERIA = "student.csv";
    public static HashMap<Integer,Double> student;
	
	public ParseCSV() {
		
	}

	public ParseCSV(String path) {
		
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	public void parse(XY xy) throws IOException{
		 Reader reader = Files.newBufferedReader(Paths.get(path));
         CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';'));
        
         ArrayList<Double> list_x=xy.getX();
     	 ArrayList<Double> list_y=xy.getY();

     	 list_x.clear();
     	 list_y.clear();
     	 
         for (CSVRecord csvRecord : csvParser) {
             // Accessing values by the names assigned to each column
             String x = csvRecord.get(0);
             String y = csvRecord.get(1);
             list_x.add(Double.valueOf(x));
             list_y.add(Double.valueOf(y));
             
        
          }
         
           xy.setX(list_x);
           xy.setY(list_y);
     }
	
	
	   public void writeToFile(XY xy, String path, String name) throws IOException{
		   BufferedWriter writer = Files.newBufferedWriter(Paths.get(path + "/" + name));
           
           CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.newFormat(';').withRecordSeparator("\r\n"));
           int size = xy.getX().size();
           for(int i=0;i<size;i++)
               csvPrinter.printRecord(xy.getX().get(i), xy.getY().get(i), xy.getY_solve().get(i));
               
           
           csvPrinter.flush();  
		   
		
	   }
	   
	   
	   public void parseStud() throws IOException {
			 Reader reader = Files.newBufferedReader(Paths.get(STUDENT_CRITERIA));
	         CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';'));
	         this.student=new HashMap<Integer,Double>();
	         

	     	 
	         for (CSVRecord csvRecord : csvParser) {
	             // Accessing values by the names assigned to each column
	             String x = csvRecord.get(0);
	             String y = csvRecord.get(1);
	            
	             student.put( Integer.valueOf(x), Double.valueOf(y));
	          
	             
	        
	          }
	         
	          
	     }
	


}

    
	

