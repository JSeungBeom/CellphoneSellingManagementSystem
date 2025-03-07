package app.cellphone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.cellphone.common.DBManager;
import app.cellphone.common.HashManager;
import app.cellphone.dto.AdminDto;
import app.cellphone.dto.UserDto;

// 로그인, 회원가입 관련 로직을 담는 DAO
public class LoginDao {
	
	// 회원 가입
	public int insertUser(String username, String password) {
		int ret = -1;
		String selectSql = "SELECT * FROM USER WHERE USERNAME = ?";
		String insertSql = "INSERT INTO USER(USERNAME, PASSWORD, SALT) VALUES (?, ?, ?)";

		Connection con = null;
		PreparedStatement selectPstmt = null;
		PreparedStatement insertPstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			con.setAutoCommit(false);
				
			// 중복 회원 확인 로직
			selectPstmt = con.prepareStatement(selectSql);
			selectPstmt.setString(1, username);
			
			rs = selectPstmt.executeQuery();
			
			if(rs.next())
				return ret;
			
			// 회원가입
			insertPstmt = con.prepareStatement(insertSql);
			
			insertPstmt.setString(1, username);
			
			// 해싱 + 솔트
			String salt = HashManager.getSalt();
			String saltedPassword = HashManager.hashPassword(password, salt);
			insertPstmt.setString(2, saltedPassword);
			insertPstmt.setString(3, salt);
			
			ret = insertPstmt.executeUpdate();
			
			con.commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, selectPstmt, insertPstmt, con);
		}
		
		return ret;
	}
	
	// 로그인 확인 용
	public UserDto detailUser(String username, String password, String salt) {
		String selectSql = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDto userDto = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			String saltedPassword = HashManager.hashPassword(password, salt);
			
			pstmt.setString(1, username);
			pstmt.setString(2, saltedPassword);
			
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
	
	// 사용자명으로 솔트 찾기
	public String detailSalt(String username) {
		String selectSql = "SELECT SALT FROM USER WHERE USERNAME = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String salt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setString(1, username);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				salt = rs.getString("salt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return salt;
	}
	
	// 어드민 찾기
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
