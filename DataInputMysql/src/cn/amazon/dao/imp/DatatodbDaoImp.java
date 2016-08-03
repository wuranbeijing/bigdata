package cn.amazon.dao.imp;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.amazon.dao.DatatodbDao;

public class DatatodbDaoImp implements DatatodbDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int insertContractAch(List<String> list, String tablename) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT into ").append(tablename).append("(a,b,c) VALUES(?,?,?)  on DUPLICATE key UPDATE b=VALUES(b),c=VALUES(c)");
		System.out.println(list.size());
		  try{  
		        int[] ii = jdbcTemplateObject.batchUpdate(sb.toString(), new MyBatchPreparedStatementSetter(list));  
		        return ii.length;  
		    }catch (DataAccessException e) {  
		        e.printStackTrace();  
		    } 
		return 0;
	}

}
