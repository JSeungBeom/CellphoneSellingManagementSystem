package app.cellphone.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.cellphone.dao.OrderDao;
import app.cellphone.dto.OrderDto;

public class UserViewOrderDialog extends JDialog {
	
	private JTable table;
	private DefaultTableModel tableModel;
	private OrderDao orderDao = new OrderDao();
	private JButton cancelOrderButton;
	
	public UserViewOrderDialog(PhoneBuyingManager parent, int userId) {
		setTitle("Order View Dialog");
		setSize(800, 400);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);
		
		tableModel = new DefaultTableModel(new Object[] {"Order ID", "Phone_ID", "Saleprice", "Ordercount", "Orderdate"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		cancelOrderButton = new JButton("주문 취소");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cancelOrderButton);
		
		table = new JTable(tableModel);
		
		listOrder(userId);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		cancelOrderButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if(selectedRow >= 0) {
				int ret = JOptionPane.showConfirmDialog(this, "주문을 취소하시겠습니까", "확인", JOptionPane.YES_NO_OPTION);
				
				if(ret == JOptionPane.YES_OPTION) { 
					Integer orderId = (Integer) tableModel.getValueAt(selectedRow, 0);
					OrderDto orderDto = detailOrder(orderId);
					
					cancelOrder(orderDto);
					parent.listPhone();
					refreshOrder(userId);
				}
			} else {
				JOptionPane.showMessageDialog(this, "주문을 선택해주세요");
			}
		});
	}
	
	private void clearTable() {
		tableModel.setRowCount(0);
	}
	
	private void listOrder(int userId) {
		clearTable();
		
		List<OrderDto> orderList = orderDao.listOrder(userId);
		JOptionPane.showMessageDialog(this, "조회된 주문 개수: " + orderList.size());
		
		for(OrderDto orderDto : orderList) {
			tableModel.addRow(new Object[] {orderDto.getOrderId(), orderDto.getPhoneId(),
					orderDto.getSaleprice(), orderDto.getOrdercount(), orderDto.getOrderdate()});
		}
	}
	
	private void refreshOrder(int userId) {
		clearTable();
		
		List<OrderDto> orderList = orderDao.listOrder(userId);
		
		for(OrderDto orderDto : orderList) {
			tableModel.addRow(new Object[] {orderDto.getOrderId(), orderDto.getPhoneId(),
					orderDto.getSaleprice(), orderDto.getOrdercount(), orderDto.getOrderdate()});
		}
	}
	
	private void cancelOrder(OrderDto orderDto) {
		orderDao.cancelOrder(orderDto);
	}
	
	private OrderDto detailOrder(int orderId) {
		return orderDao.detailOrder(orderId);
	}
}
