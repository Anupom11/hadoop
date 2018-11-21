package org.apache.hadoop.hdfs.server.namenode;

/**
 * A class contains all the function that takes different transaction informations from the FSEditLog class and then write
 * that data to a log file
 * By Anupom Chakrabarty, created on date 08/05/2018, date of last modification 03/11/2018 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileOpTracer {
	
	File traceFileName;
	
	FileOpTracer() {
		
		// if trace file is not created then create it
		traceFileName = new File("/home/hduser/traceLog.xml");
		
		if(!traceFileName.exists()) {
			try {
				traceFileName.createNewFile();
			}catch(IOException e) 
			{
				System.out.println("Trace file creation error! "+e.toString());
			}
		}
	}
	
	// return the trace file name
	public File getTraceFileName() {
		return this.traceFileName;
	}
	
	/**
	 * function record open/ newly created file records
	 */
	/*
	public void recordOpenFile(String path, INodeFile newNode) {
		String openRecordFilePath 	= "/home/hduser/anupomFSEditLogInfo.txt"; 
    	File file 					= new File(openRecordFilePath);
    	if(file.exists()) {
    		try {
    			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
    			bw.newLine();
    			bw.write(newNode.getLocalName()+"::"+path+"::"+newNode.getAccessTime()+"::"+newNode.getLogFileName());
    			bw.flush();
    			bw.close();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else {
    		try {
        		FileOutputStream fout = new FileOutputStream(file);
        		OutputStreamWriter ow = new OutputStreamWriter(fout);
        		ow.write(newNode.getLocalName()+"::"+path+"::"+newNode.getAccessTime()+"::"+newNode.getLogFileName());
        		ow.flush();
        		ow.close();
        		fout.close();
        	}catch(IOException e) {
        		e.printStackTrace();
        	}
    	}
	}
	*/
	
	/**
	 * function to record the file movement information. All the file movement operation will be traced in 
	 * a file according to the configuration of the user.
	 * date of last modification 04/11/2018
	 */
	 public void recordRenameFile(String src, String dst, long timestamp) { 
		
		//String logFile		= new String(logFileName, StandardCharsets.UTF_8);
		//String openRecordFilePath 	= "/home/hduser/tracelog"+logFile+".xml";	// saved as xml file  
		//File file 			= new File(openRecordFilePath);
    	
		 File file = getTraceFileName();
		 
		 StringBuffer logXML = new StringBuffer();
		 logXML.append("<RENAME>"+"\n");
		 logXML.append("<SRC>"+src+"</SRC>"+"\n");
		 logXML.append("<DST>"+dst+"</DST>"+"\n");
		 logXML.append("<TIMESTAMP>"+timestamp+"</TIMESTAMP>"+"\n");
		 logXML.append("</RENAME>"+"\n");
    	
		 if(file.exists()) {
			 try { 
				 BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
				 bw.newLine();
				 //bw.write(src+"::"+dst+"::"+timestamp);
				 bw.write(logXML.toString());
				 bw.flush();
				 bw.close();
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
		 }else {
			 try {
				 FileOutputStream fout = new FileOutputStream(file);
				 OutputStreamWriter ow = new OutputStreamWriter(fout);
				 //ow.write(src+"::"+dst+"::"+timestamp);
				 ow.write(logXML.toString());
				 ow.flush();
				 ow.close();
				 fout.close();
			 }catch(IOException e) {
				 e.printStackTrace();
			 }
		 }
	 }	
}

