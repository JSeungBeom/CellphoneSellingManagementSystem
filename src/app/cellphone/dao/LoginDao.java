package app.cellphone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.cellphone.common.DBManager;
import app.cellphone.dto.AdminDto;
import app.cellphone.dto.UserDto;

public class LoginDao {
	
	public int insertUser(UserDto userDto) {
		int ret = -1;
		String insertSql = "INSERT INTO USER(USERNAME, PASSWORD) VALUES (?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		if(checkDuplicateUser(userDto.getUsername())) {
			return ret;
		}
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(insertSql);
			
			pstmt.setString(1, userDto.getUsername());
			pstmt.setString(2, userDto.getPassword());
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public boolean checkDuplicateUser(String username) {
		String selectSql = "SELECT * FROM USER WHERE USERNAME = ?";
				
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setString(1, username);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return false;
	}
	
	public UserDto detailUser(String username, String password) {
		String selectSql = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDto userDto = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userDto = new UserDto();
				
				userDto.setUserId(rs.getInt("user_id"));
				userDto.setUsername(rs.getString("username"));
				userDto.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return userDto;
	}
	
	public AdminDto detailAdmin(String username, String password) {
		String selectSql = "SELECT * FROM ADMIN WHERE USERNAME = ? AND PASSWORD = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminDto adminDto = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				adminDto = new AdminDto();
				
				adminDto.setUserId(rs.getInt("admin_id"));
				adminDto.setUsername(rs.getString("username"));
				adminDto.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			return null;
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return adminDto;
	}
}
