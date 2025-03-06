package app.cellphone.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.cellphone.dao.OrderDao;
import app.cellphone.dao.PhoneDao;
import app.cellphone.dto.OrderDto;
import app.cellphone.dto.PhoneDto;

public class PhoneBuyingManager extends JFrame {
	
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField searchWordField;
	private PhoneDao phoneDao = new PhoneDao();
	private OrderDao orderDao = new OrderDao();
	private JButton searchButton, buyButton, listButton, viewOrderButton;
	
	public PhoneBuyingManager(int userId) {
		setTitle("Phone Buying Manager");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		tableModel = new DefaultTableModel(new Object[] {"Phone ID", "Brand", "Phone Name", "Price", "Count"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		
		listPhone();
		
		// Search
		Dimension textFieldSize = new Dimension(400, 30);
		searchWordField = new JTextField();
		searchWordField.setPreferredSize(textFieldSize);
		
		searchButton = new JButton("검색");
		
		JPanel searchPanel = new JPanel();
		searchPanel.add(new JLabel("기종 검색"));
		searchPanel.add(searchWordField);
		searchPanel.add(searchButton);
		
		buyButton = new JButton("구매");
		listButton = new JButton("목록");
		viewOrderButton = new JButton("주문 현황");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buyButton);
		buttonPanel.add(listButton);
		buttonPanel.add(viewOrderButton);
		
		setLayout(new BorderLayout());
		add(searchPanel, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		searchButton.addActionListener(e -> {
			String word = searchWordField.getText();
			if(!word.isBlank()) {
				listPhone(word);
			} else {
				listPhone();
			}
		});
		
		buyButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if(selectedRow >= 0) {
				BuyPhoneDialog buyDialog = new BuyPhoneDialog(this, this.tableModel, selectedRow, userId);
				buyDialog.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "기종을 선택하세요");
			}
		});
		
		listButton.addActionListener( e -> listPhone() );
	}
	
	private void clearTable() {
		tableModel.setRowCount(0);
	}
	
	private void listPhone() {
		clearTable();
		
		List<PhoneDto> phoneList = phoneDao.listPhone();
		 
		for (PhoneDto phoneDto : phoneList) {
			tableModel.addRow(new Object[] {phoneDto.getPhoneId(), phoneDto.getBrand(),
					phoneDto.getName(), phoneDto.getPrice(), phoneDto.getCount()});
		}
	}
	
	private void listPhone(String word) {
		clearTable();
		
		List<PhoneDto> phoneList = phoneDao.detailPhone(word);
		
		for(PhoneDto phoneDto : phoneList) {
			tableModel.addRow(new Object[] {phoneDto.getPhoneId(), phoneDto.getBrand(),
					phoneDto.getName(), phoneDto.getPrice(), phoneDto.getCount()});
		}
	}
	
	PhoneDto detailPhone(int phoneId) {
		return phoneDao.detailPhone(phoneId);
	}
	
	int OrderPhone(OrderDto orderDto) {
		int ret = orderDao.insertOrder(orderDto);
		
		if(ret > 0) {
			listPhone();
		}
		
		return ret;
	}
}
