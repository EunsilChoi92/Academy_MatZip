package com.koreait.matzip.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcTemplate {
	public static int executeUpdate(String sql, JdbcUpdateInterface jdbc) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DbManager.getCon();
			ps = con.prepareStatement(sql);
			
			// 바뀌는 부분
			jdbc.update(ps);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbManager.close(con, ps);
		}
		
		return result;
	}
	
	// select용
	public static int executeQuery(String sql, JdbcSelectInterface jdbc) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getCon();
			ps = con.prepareStatement(sql);
			
			// rs = jdbc.prepared(ps);
			
			// 수정
			jdbc.prepared(ps);
			rs = ps.executeQuery();
			
			jdbc.executeQuery(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbManager.close(con, ps, rs);
		}
		return result;
	}
}
