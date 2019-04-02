package com.neu.rule.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtil {
	/**
	 *  文件写入本地工具
	 * @throws IOException  抛出IO异常
	 */
	public static void createFile(byte[] file, String filePath, String fileName) throws IOException{
		File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
	}
	
	
	
	
	

}
