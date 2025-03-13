package app.cellphone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.jpa.HibernatePersistenceProvider;

import app.cellphone.common.DBManager;
import app.cellphone.dto.OrderDto;
import app.cellphone.dto.UserOrderDto;
import app.cellphone.entity.Orders;
import app.cellphone.entity.Phone;
import config.MyPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class OrderDao {
	
	private PhoneDao phoneDao = new PhoneDao();
	
	// 주문 
	public int insertOrder(OrderDto orderDto) {
		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), new HashMap<>()
		);
		EntityManager em = emf.createEntityManager();
		
		int stock = phoneDao.detailPhone(orderDto.getPhoneId()).getCount();
		
		int restStock = stock - orderDto.getOrdercount();
		
		if(restStock < 0) {
			return -1;
		}
		
		em.getTransaction().begin();
		
		Orders o = new Orders();
		o.setUserId(orderDto.getUserId());
		o.setPhoneId(orderDto.getPhoneId());
		o.setSaleprice(orderDto.getSaleprice());
		o.setOrdercount(orderDto.getOrdercount());
		o.setOrderdate(orderDto.getOrderdate());
		
		em.persist(o);
		
		Phone p = em.find(Phone.class, o.getPhoneId());
		
		p.setCount(restStock);
		
		em.getTransaction().commit();
			
		return 1;
	}
	
	public List<UserOrderDto> listOrder(int userId) {
		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), new HashMap<>()
		);
		EntityManager em = emf.createEntityManager();
		
		String jpql = "SELECT new app.cellphone.dto.UserOrderDto(o.order_id, p.brand, p.name, o.saleprice, o.ordercount, o.orderdate) " +
	              "FROM Orders o JOIN Phone p ON p.phone_id = o.phone_id " +
	              "WHERE o.user_id = :userId";


		TypedQuery<UserOrderDto> query = em.createQuery(jpql, UserOrderDto.class);
		query.setParameter("userId", userId);
		
		return query.getResultList();
	}
	
	public List<OrderDto> listOrder() {
		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), new HashMap<>()
		);
		EntityManager em = emf.createEntityManager();
		
		String jpql = "SELECT o FROM Orders o";
		
		TypedQuery<Orders> query = em.createQuery(jpql, Orders.class);
		List<OrderDto> list = new ArrayList<>();
		
		for(Orders o : query.getResultList()) {
			list.add(new OrderDto(o.getOrderId(), o.getUserId(), o.getPhoneId(),
					o.getSaleprice(), o.getOrdercount(), o.getOrderdate()));
		}
				
		return list;
	}
	
	// 주문 취소 & 수량 조정
	public void cancelOrder(OrderDto orderDto) {
		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), new HashMap<>()
		);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Orders orders = em.find(Orders.class, orderDto.getOrderId());
		em.remove(orders);
		
		Phone phone = em.find(Phone.class, orderDto.getPhoneId());
		phone.setCount(phone.getCount() + orderDto.getOrdercount());
		
		em.getTransaction().commit();
	}
	 
	public OrderDto detailOrder(int orderId) {
		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), new HashMap<>()
		);
		EntityManager em = emf.createEntityManager();
				
		Orders orders = em.find(Orders.class, orderId);
		
		return new OrderDto(orders.getOrderId(), orders.getUserId(), orders.getPhoneId(),
				orders.getSaleprice(), orders.getOrdercount(), orders.getOrderdate());
		
		
//		String selectSql = "SELECT * FROM ORDERS WHERE ORDER_ID = ?";
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		OrderDto orderDto = null;
//		
//		try {
//			con = DBManager.getConnection();
//			pstmt = con.prepareStatement(selectSql);
//			
//			pstmt.setInt(1, orderId);
//			
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				int userId = rs.getInt("user_id");
//				int phoneId = rs.getInt("phone_id");
//				int saleprice = rs.getInt("saleprice");
//				int ordercount = rs.getInt("ordercount");
//				Timestamp orderdate = rs.getTimestamp("orderdate");
//				
//				
//				orderDto = new OrderDto(orderId, userId, phoneId,
//						saleprice, ordercount, orderdate);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBManager.releaseConnection(rs, pstmt, con);
//		}
//		
//		return orderDto;
	}
}
