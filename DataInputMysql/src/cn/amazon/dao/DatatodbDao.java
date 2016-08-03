package cn.amazon.dao;

import java.util.List;

import javax.sql.DataSource;

public interface DatatodbDao {
	public void setDataSource(DataSource ds);
	public int insertContractAch(List<String> list,String tablename);
}
