package app.cellphone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import app.cellphone.common.DBManager;
import app.cellphone.dto.OrderDto;
import app.cellphone.dto.PhoneDto;
import app.cellphone.dto.UserOrderDto;

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
	
	public List<UserOrderDto> listOrder(int userId) {
		String selectSql = "SELECT ORDER_ID, BRAND, NAME, SALEPRICE, ORDERCOUNT, ORDERDATE "
				+ "FROM ORDERS JOIN PHONE USING (PHONE_ID) WHERE USER_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserOrderDto> list = new ArrayList<>();
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setInt(1, userId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int orderId = rs.getInt("order_id");
				String brand = rs.getString("brand");
				String name = rs.getString("name");
				int saleprice = rs.getInt("saleprice");
				int ordercount = rs.getInt("ordercount");
				Timestamp orderdate = rs.getTimestamp("orderdate");
				
				UserOrderDto userOrderDto = new UserOrderDto(orderId, brand, name,
						saleprice, ordercount, orderdate);
				
				list.add(userOrderDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	
	public List<OrderDto> listOrder() {
		String selectSql = "SELECT * FROM ORDERS";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDto> list = new ArrayList<>();
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int orderId = rs.getInt("order_id");
				int userId = rs.getInt("user_id");
				int phoneId = rs.getInt("phone_id");
				int saleprice = rs.getInt("saleprice");
				int ordercount = rs.getInt("ordercount");
				Timestamp orderdate = rs.getTimestamp("orderdate");
				
				OrderDto orderDto = new OrderDto(orderId, userId, phoneId,
						saleprice, ordercount, orderdate);
				
				list.add(orderDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	
	// 주문 취소 & 수량 조정
	public void cancelOrder(OrderDto orderDto) {
		String deleteSql = "DELETE FROM ORDERS WHERE ORDER_ID = ?";
		String updateSql = "UPDATE PHONE SET COUNT = COUNT + ? WHERE PHONE_ID = ?";
		Connection con = null;
		PreparedStatement deletePstmt = null;
		PreparedStatement updatePstmt = null;
		
		try {
			con = DBManager.getConnection();
			con.setAutoCommit(false); // 트랜잭션 시작
			
			deletePstmt = con.prepareStatement(deleteSql);
			deletePstmt.setInt(1, orderDto.getOrderId());
			deletePstmt.executeUpdate();
			
			updatePstmt = con.prepareStatement(updateSql);
			updatePstmt.setInt(1, orderDto.getOrdercount());
			updatePstmt.setInt(2, orderDto.getPhoneId());
			updatePstmt.executeUpdate();
			
			con.commit(); // 트랜잭션 종료
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(deletePstmt, updatePstmt, con);
		}
		
	}
	 
	public OrderDto detailOrder(int orderId) {
		String selectSql = "SELECT * FROM ORDERS WHERE ORDER_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderDto orderDto = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setInt(1, orderId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userId = rs.getInt("user_id");
				int phoneId = rs.getInt("phone_id");
				int saleprice = rs.getInt("saleprice");
				int ordercount = rs.getInt("ordercount");
				Timestamp orderdate = rs.getTimestamp("orderdate");
				
				
				orderDto = new OrderDto(orderId, userId, phoneId,
						saleprice, ordercount, orderdate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return orderDto;
	}
}
