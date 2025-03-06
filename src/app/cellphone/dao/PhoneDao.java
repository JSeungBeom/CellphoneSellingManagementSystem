package app.cellphone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.cellphone.common.DBManager;
import app.cellphone.dto.PhoneDto;

// Phone table에 대한 CRUD
public class PhoneDao {
	
	public int insertPhone(PhoneDto phoneDto) {
		int ret = -1;
		String insertSql = "INSERT INTO PHONE VALUES (?, ?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(insertSql);
			
			pstmt.setInt(1, phoneDto.getPhoneId());
			pstmt.setString(2, phoneDto.getBrand());
			pstmt.setString(3, phoneDto.getName());
			pstmt.setInt(4, phoneDto.getPrice());
			pstmt.setInt(5, phoneDto.getCount());
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public int updatePhone(PhoneDto phoneDto) {
		int ret = -1;
		String updateSql = "UPDATE PHONE SET BRAND = ?, NAME = ?, PRICE = ?, COUNT = ? WHERE PHONE_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(updateSql);
			
			pstmt.setString(1, phoneDto.getBrand());
			pstmt.setString(2, phoneDto.getName());
			pstmt.setInt(3, phoneDto.getPrice());
			pstmt.setInt(4, phoneDto.getCount());
			pstmt.setInt(5, phoneDto.getPhoneId());
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public int deletePhone(int phoneId) {
		int ret = -1;
		String updateSql = "DELETE FROM PHONE WHERE PHONE_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(updateSql);
			
			pstmt.setInt(1, phoneId);
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public List<PhoneDto> listPhone() {
		
		String selectSql = "SELECT * FROM PHONE";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PhoneDto> list = new ArrayList<>();
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PhoneDto phoneDto = new PhoneDto();
				
				phoneDto.setPhoneId(rs.getInt("phone_id"));
				phoneDto.setBrand(rs.getString("brand"));
				phoneDto.setName(rs.getString("name"));
				phoneDto.setPrice(rs.getInt("price"));
				phoneDto.setCount(rs.getInt("count"));
				
				list.add(phoneDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	
	public PhoneDto detailPhone(String name) {
		String selectSql = "SELECT * FROM PHONE WHERE NAME = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PhoneDto phoneDto = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				phoneDto = new PhoneDto();
				
				phoneDto.setPhoneId(rs.getInt("phone_id"));
				phoneDto.setBrand(rs.getString("brand"));
				phoneDto.setName(rs.getString("name"));
				phoneDto.setPrice(rs.getInt("price"));
				phoneDto.setCount(rs.getInt("count"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return phoneDto;
	}
	
	public PhoneDto detailPhone(int phoneId) {
		String selectSql = "SELECT * FROM PHONE WHERE PHONE_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PhoneDto phoneDto = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setInt(1, phoneId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				phoneDto = new PhoneDto();
				
				phoneDto.setPhoneId(rs.getInt("phone_id"));
				phoneDto.setBrand(rs.getString("brand"));
				phoneDto.setName(rs.getString("name"));
				phoneDto.setPrice(rs.getInt("price"));
				phoneDto.setCount(rs.getInt("count"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return phoneDto;
	}
}
