package cn.amazon.util;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author wuran
 * TODO create sample data for test
 * Aug 1, 201610:42:01 PM
 *
 */
public class CreateMysqlData {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		 FileWriter fw = new FileWriter("C://test/indextest.txt");
         BufferedWriter bw = new BufferedWriter(fw);
//         bw.write("a");
//         bw.write("\t");   //split use '/t'
//    	 bw.write("b");
//    	 bw.write("\t");
//    	 bw.write("c");
//         bw.newLine();
         for(int i=0;i<1000000;i++){
        	 String temp = String.valueOf(i-2);
        	 String temp1 = String.valueOf(i*3);
        	 String temp2 = String.valueOf(i+1);
        	 bw.write(temp);
        	 bw.write("\t");   
        	 bw.write(temp1);
        	 bw.write("\t");
        	 bw.write(temp2);
             bw.newLine();
         }
         System.out.println("okay");
         bw.flush();
         bw.close();
         fw.close();
	}
}

