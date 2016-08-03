package cn.amazon.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuran
 * TODO  get all file which suffix is like '.txt' of one directory
 * Jul 31, 20167:38:56 PM
 *
 */
public class FileScannerUtil {
	/**
	 * 
	 * @param absolutepath like"/home/wuran/inputfile"
	 * @return
	 */
	public static Map<Integer,String> actionGettxtfile(String absolutepath){
		Map<Integer,String> filemap = new HashMap<Integer,String>();
		File dir = new File(absolutepath);
		File[] files = dir.listFiles();
		if(files == null){
			return null;
		}
		int count = 0;
		for(File file : files){
			filemap.put(count, file.getAbsolutePath());
			count++;
		}
		return filemap;
	}
}
