package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Admin;
import util.DBUtil;

public class AdminDaao implements Serializable {
	
	public Admin findBuCode(String code){
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from admin_info_cxs where admin_code=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, code);
			ResultSet rs=ps.executeQuery();
			
			Admin admin=new Admin();
			while(rs.next()){
				admin.setAdminCode(rs.getString("admin_code"));
				admin.setAdminId(rs.getInt("admin_id"));
				admin.setEmail(rs.getString("email"));
				admin.setEnrolldate(rs.getTimestamp("enrolldate"));
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
				admin.setTelephone(rs.getString("telephone"));
			}
			return admin;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("≤È‘Éπ‹¿ÌÜT ßî°",e);
		}finally{
			DBUtil.close(conn);
		}
		
	}
	public static void main(String[] args) throws Throwable {
		System.out.println(new AdminDaao().findBuCode("co").getAdminId());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
