package com.serendio.textanalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class CommonUtils {
	
	public static String convetToString(InputStream stream)throws IOException{
		return convetToString(stream,"UTF-8");
	}
	public static String convetToString(InputStream inputStream,String encoding)throws IOException{
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, encoding);
		return writer.toString();		
	}
}
