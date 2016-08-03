package cn.amazon.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.amazon.dao.imp.DatatodbDaoImp;
import cn.amazon.util.Constant;
import cn.amazon.util.FileScannerUtil;
import cn.amazon.util.ReadFileUtil;

public class MyThreadPoolExecutor {
	int corePoolSize = 100;
	int maxPoolSize = 100;
	long keepAliveTime = 120;
	ExecutorService  threadPool = null;
	final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(120);

	public MyThreadPoolExecutor() {
		threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue,new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public void runTask(Runnable task) {
		threadPool.execute(task);
		System.out.println("Task count.." + queue.size());
	}

	public void shutDown() {
		threadPool.shutdown();
	}
	
	public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException{
		threadPool.awaitTermination(timeout, unit);
	}

	public static void main(String args[]) throws IOException {
		long startTime = System.currentTimeMillis();
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		DatatodbDaoImp datadbDaoImp = (DatatodbDaoImp) context.getBean("DatatodbDaoImp");
		MyThreadPoolExecutor mtpe = new MyThreadPoolExecutor();
		Map<Integer, String> filemap = FileScannerUtil.actionGettxtfile(Constant.TXT_FILE_PATH);
		int len=filemap.size();
		int task=0;//任务的个数
		List<String> eachtaskdata = new ArrayList<String>();
		for(int m=0;m<len;m++){
			String txtfilepath = filemap.get(m);
			String tablename = txtfilepath.substring(8,txtfilepath.length()-4);
			//返回每个文件的所有数据
			List<String> recordes = read(txtfilepath);
			System.out.println(recordes.size());
			if(recordes.size()%200==0){
				task = recordes.size()/200;
			}else{
				task = recordes.size()%200+1;
			}
			System.out.println("第"+m+"个文件可以做"+task+"个任务");
			//根据task的个数循环取数做数据入库任务
			for(int j=0;j<task;j++){
				//1.取数
				for(int l=j*200;l<200*(j+1);l++){
					eachtaskdata.add(recordes.get(j));
				}
				//2.做任务
				mtpe.runTask(new Runnable() {
					
					@Override
					public void run() {
						System.out.println("本次有"+eachtaskdata.size()+"条记录");
						//任务内容：数据库表插入200条数据，可能有更新或者插入情况
						int size = datadbDaoImp.insertContractAch(eachtaskdata, tablename);
						System.out.println(size+"条记录入库或更新");
						System.out.println("===========================");
						
					}
				});
				//任务做完，清空数据，以备后面重复利用
				eachtaskdata.clear();
			}
		}
		mtpe.shutDown();
		//任务停止后等待60s，以防有的任务不会被执行，漏掉数据
		try {
			mtpe.awaitTermination(120, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("total use :"+(endTime - startTime)/1000+" s");
		
	}
	
	
	/**
	 * 读取文件获取欲入库数据
	 * @param filePath:文件路径
	 * @return
	 */
	private static List<String> read(String filePath){
		//read这里循环到把文件读完  每次读200行
		BufferedReader br = ReadFileUtil.readTxtFile(filePath);
		String line = null;
		List<String> records = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null){
				records.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}	
} 