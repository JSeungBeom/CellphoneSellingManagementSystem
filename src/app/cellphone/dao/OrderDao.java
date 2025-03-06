package app.cellphone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.cellphone.common.DBManager;
import app.cellphone.dto.OrderDto;
import app.cellphone.dto.PhoneDto;

public class OrderDao {
	
	private PhoneDao phoneDao = new PhoneDao();
	
	// 주문 
	public int insertOrder(OrderDto orderDto) {
		int ret = -1;
		String insertSql = "INSERT INTO ORDERS (USER_ID, PHONE_ID, SALEPRICE, ORDERCOUNT, ORDERDATE) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		// 재고가 없을 시
		if(adjustStock(orderDto.getPhoneId(), orderDto.getOrdercount()) == -1)
			return ret;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(insertSql);
			
			pstmt.setInt(1, orderDto.getUserId());
			pstmt.setInt(2, orderDto.getPhoneId());
			pstmt.setInt(3, orderDto.getSaleprice());
			pstmt.setInt(4, orderDto.getOrdercount());
			pstmt.setTimestamp(5, orderDto.getOrderdate());
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	// 수량 조정
	private int adjustStock(int phoneId, int ordercount) {
		int ret = -1;
		String updateSql = "UPDATE PHONE SET COUNT = ? WHERE PHONE_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		PhoneDto phoneDto = phoneDao.detailPhone(phoneId);
		
		int count = phoneDto.getCount() - ordercount;
		
		if(count < 0) {
			return ret;
		}
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(updateSql);
			
			pstmt.setInt(1, count);
			pstmt.setInt(2, phoneId);
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
}
