package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


/**
 * 该类引入了连接池来管理连接,
 * 连接池代替了DriverManager.
 * 它是DBTool的升级版.
 */

public class DBUtil {
	
	private static BasicDataSource ds;
	static{
		//1.只读取一次连接参数
		Properties p=new Properties();
		//获取一个流
		try {
			p.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("读取文件失败",e);//简单处理,往上抛,告诉调用者,让调用者处理,并给调用者提示
		}
		String driver=p.getProperty("driver");
		String url=p.getProperty("url");
		String user=p.getProperty("user");
		String pwd=p.getProperty("pwd");
		String initSize=p.getProperty("initSize");//不给也行,有默认值
		String maxSize=p.getProperty("maxSize");
		//2.只创建一个连接池
		ds=new BasicDataSource();
		//3.将连接参数设置给连接池
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pwd);
		ds.setInitialSize(Integer.parseInt(initSize));//初始化
		ds.setMaxActive(new Integer(maxSize));//最大活跃
		
	}
	
	public static Connection getConnection() throws SQLException{//创建连接
		return ds.getConnection();//直接抛出异常让调用者处理,关闭连接池.
		
	}
	/**
	 * 由连接池创建的连接,其close()方法被连接池改为归还的作用,而不是
	 * 真正关闭连接,并且归还时,该连接内的数据被清空,状态重置为空闲态.
	 * @param conn
	 */
	public static void close(Connection conn){//归还连接
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("归还连接失败",e);
			}
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Connection conn=DBUtil.getConnection();
		System.out.println(conn);
		DBUtil.close(conn);
	}
	
}
