package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


/**
 * �������������ӳ�����������,
 * ���ӳش�����DriverManager.
 * ����DBTool��������.
 */

public class DBUtil {
	
	private static BasicDataSource ds;
	static{
		//1.ֻ��ȡһ�����Ӳ���
		Properties p=new Properties();
		//��ȡһ����
		try {
			p.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("��ȡ�ļ�ʧ��",e);//�򵥴���,������,���ߵ�����,�õ����ߴ���,������������ʾ
		}
		String driver=p.getProperty("driver");
		String url=p.getProperty("url");
		String user=p.getProperty("user");
		String pwd=p.getProperty("pwd");
		String initSize=p.getProperty("initSize");//����Ҳ��,��Ĭ��ֵ
		String maxSize=p.getProperty("maxSize");
		//2.ֻ����һ�����ӳ�
		ds=new BasicDataSource();
		//3.�����Ӳ������ø����ӳ�
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pwd);
		ds.setInitialSize(Integer.parseInt(initSize));//��ʼ��
		ds.setMaxActive(new Integer(maxSize));//����Ծ
		
	}
	
	public static Connection getConnection() throws SQLException{//��������
		return ds.getConnection();//ֱ���׳��쳣�õ����ߴ���,�ر����ӳ�.
		
	}
	/**
	 * �����ӳش���������,��close()���������ӳظ�Ϊ�黹������,������
	 * �����ر�����,���ҹ黹ʱ,�������ڵ����ݱ����,״̬����Ϊ����̬.
	 * @param conn
	 */
	public static void close(Connection conn){//�黹����
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("�黹����ʧ��",e);
			}
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Connection conn=DBUtil.getConnection();
		System.out.println(conn);
		DBUtil.close(conn);
	}
	
}
