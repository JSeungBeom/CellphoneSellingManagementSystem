package app.cellphone.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.cellphone.dao.OrderDao;
import app.cellphone.dto.OrderDto;

public class AdminViewOrderDialog extends JDialog {
	
	private JTable table;
	private DefaultTableModel tableModel;
	private OrderDao orderDao = new OrderDao();
	
	public AdminViewOrderDialog(PhoneManager parent) {
		setTitle("Order View Dialog");
		setSize(800, 400);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);
		
		tableModel = new DefaultTableModel(new Object[] {"Order ID", "User_ID", "Phone_ID", "Saleprice", "Ordercount", "Orderdate"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		
		listOrder();
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	private void listOrder() {
		List<OrderDto> orderList = orderDao.listOrder();
		JOptionPane.showMessageDialog(this, "조회된 주문 개수: " + orderList.size());
		
		for(OrderDto orderDto : orderList) {
			tableModel.addRow(new Object[] {orderDto.getOrderId(), orderDto.getUserId(), orderDto.getPhoneId(),
					orderDto.getSaleprice(), orderDto.getOrdercount(), orderDto.getOrderdate()});
		}
	}
}
