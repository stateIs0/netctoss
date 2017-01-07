package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Cost;
import util.DBUtil;

public class CostDao implements Serializable {
	
	/**
	 * 查询某一页的资费数据
	 * @param page 页码
	 * @param size 每页显示的行数
	 */
	public List<Cost> findByPage(
		int page, int size) {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			String sql = 
				"select * from ("
				+ "	select c.*,rownum r from ("
				+ "	  select * from cost_cxs "
				+ "	  order by cost_id"
				+ " ) c"
				+ ") where r between ? and ?";
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setInt(1, (page-1)*size+1);
			ps.setInt(2, page*size);
			ResultSet rs = ps.executeQuery();
			List<Cost> list = new ArrayList<Cost>();
			while(rs.next()) {
				Cost c = creatcost(rs);
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(
				"分页查询资费失败", e);
		} finally {
			DBUtil.close(con);
		}
	}
	
	/**
	 * 查询总行数
	 */
	public int findRows() {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			String sql = 
				"select count(*) from cost_cxs";
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(
				"查询总行数失败", e);
		} finally {
			DBUtil.close(con);
		}
		return 0;
	}
	
	public List<Cost> toPage(String pag){
		int page=new Integer(pag);
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from"
					+ "(select t.*,rownum r from"
					+ "(select * from cost_cxs order by cost_id) t)"
					+ " where r between ? and ?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, (page-1)*page*10+1);
			ps.setInt(2, page*10);
			ResultSet rs=ps.executeQuery();
			List<Cost>list=new ArrayList<Cost>();
			while(rs.next()){
				Cost c=creatcost(rs);
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询翻页失败",e);
		}finally{
			DBUtil.close(conn);
		}
		
		
		
	}
	
	
	
	
	public void delete(String id)	{
		Connection conn=null;
		try {
			
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="delete from cost_cxs where cost_id=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("删除失败");
		}finally{
			DBUtil.close(conn);
		}
		
	}
	
	
	public void update(Cost c){
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="update cost_cxs set name=?,cost_type=?,base_duration=?,base_cost=?,"
					+ "unit_cost=?,descr=? where cost_id=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, c.getName());
			ps.setString(2, c.getCostType());
			ps.setObject(3, c.getBaseDuration());
			ps.setObject(4, c.getBaseCost());
			ps.setObject(5, c.getUnitCost());
			ps.setString(6,c.getDescr());
			ps.setObject(7, c.getCostID());
			ps.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("连接数据库失败",e);
		}finally{
			if(conn!=null){
				DBUtil.close(conn);
			}
		}
	}
	
	
	
	
	
	
	public Cost findByid(String id){
		Connection  conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from cost_cxs where cost_id=? ";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			Cost c=new Cost();
			c.setCostID(new Integer(rs.getInt("cost_id")));
			c.setName(rs.getString("name"));
			c.setCostType(rs.getString("cost_type"));
			c.setBaseDuration(rs.getInt("base_duration"));
			c.setBaseCost(rs.getDouble("base_cost"));
			c.setUnitCost(rs.getDouble("unit_cost"));
			c.setDescr(rs.getString("descr"));
			return c;
			
					
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("连接数据库失败",e);
		}finally{
			DBUtil.close(conn);
		}
	}
	
	
	public List<Cost> findAll(){
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from cost_cxs"
					+ " order by cost_id";
			Statement smt=conn.createStatement();
			ResultSet rs=smt.executeQuery(sql);
			List<Cost> list=new ArrayList<Cost>();
			while(rs.next()){
				Cost c = creatCost(rs);
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询资费失败", e);
		}finally{
			if(conn!=null){
				DBUtil.close(conn);
			}
		}
		
	}






	private Cost creatCost(ResultSet rs) throws SQLException {
		Cost c = creatcost(rs);
		return c;
	}






	public Cost creatcost(ResultSet rs) throws SQLException {
		Cost c=new Cost();
		c.setCostID(rs.getInt("cost_id"));
		c.setName(rs.getString("name"));
		c.setBaseDuration(rs.getInt("base_duration"));
		c.setBaseCost(rs.getDouble("base_cost"));
		c.setUnitCost(rs.getDouble("unit_cost"));
		c.setStatus(rs.getString("status"));
		c.setDescr(rs.getString("descr"));
		c.setCreatime(rs.getTimestamp("creatime"));
		c.setStartime(rs.getTimestamp("startime"));
		c.setCostType(rs.getString("cost_type"));
		return c;
	}
	public void save(Cost c){
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
//			String sql="INSERT INTO cost_cxs (cost_id,name"
//					+ ")VALUES"
//					+ "(cost_seq_cxs.nextval,?)";
			String sql="INSERT INTO cost_cxs (cost_id,name,cost_type,base_duration,base_cost"
					+ ",unit_cost,descr,status)VALUES"
					+ "(cost_seq_cxs.nextval,?,?,?,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, c.getName());
			ps.setString(2,c.getCostType());
			ps.setObject(3, c.getBaseDuration());
			ps.setObject(4,c.getBaseCost());
			ps.setObject(5, c.getUnitCost());
			ps.setString(6,c.getDescr());
			ps.setString(7,c.getStatus());
			ps.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new RuntimeException("回滚失败!");
			}
			throw new RuntimeException("保存失败");
		}finally{
			if(conn!=null){
				DBUtil.close(conn);
			}
		}
	}
	
	
}
