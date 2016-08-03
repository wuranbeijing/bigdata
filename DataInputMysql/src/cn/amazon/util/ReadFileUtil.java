package cn.amazon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadFileUtil {
	/**
	 * ���ļ�������bufferreader
	 * @param filePath
	 */
	public static BufferedReader readTxtFile(String filePath){
		BufferedReader br = null;
		try{
			File file = new File(filePath);
			if(file.isFile()&&file.exists()){
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), Constant.ENCODING);
				br = new BufferedReader(isr);
			}
			return br;
		}catch(Exception e){
			return br;
		}
	}

}
