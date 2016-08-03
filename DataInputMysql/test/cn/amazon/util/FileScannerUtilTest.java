package cn.amazon.util;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class FileScannerUtilTest {

	@Test
	public void testActionGettxtfile() {
		Map<Integer, String> filemap = FileScannerUtil
				.actionGettxtfile(Constant.TXT_FILE_PATH);
		for (int i = 0, len = filemap.size(); i < len; i++) {
			// one file=>one db table=>one task
			String txtfilepath = filemap.get(i);
			String tablename = txtfilepath.substring(8,
					txtfilepath.length()-4 );
			System.out.println(txtfilepath+" : "+tablename);
		}
	}

}
