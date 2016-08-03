package cn.amazon.dao.imp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

/**
* 处理批量插入的回调类
* */  
public  class MyBatchPreparedStatementSetter implements BatchPreparedStatementSetter{  
    final List<String> temList;  
    /**通过构造函数把要插入的数据传递进来处理*/  
    public MyBatchPreparedStatementSetter(List<String> list){  
        temList = list;  
    }  
    public int getBatchSize() {  
        return temList.size();  
    }  
  
    public void setValues(PreparedStatement ps, int i) throws SQLException  {  
    		int len =temList.size();
    		String[] fields = temList.get(i).split("\t");
    		for(int j=0,size=fields.length; j<size; j++){
				ps.setString(j+1, fields[j]);
			}
    }  
} 