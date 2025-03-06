package app.cellphone.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import app.cellphone.dao.PhoneDao;
import app.cellphone.dto.PhoneDto;

public class PhoneManager extends JFrame {
	
	private JTable table;
	private DefaultTableModel tableModel; 
	private JButton addButton, editButton, listButton;
	private PhoneDao phoneDao = new PhoneDao();
	
	public PhoneManager() {
		setTitle("Phone Manager");
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
		
		addButton = new JButton("등록");
		editButton = new JButton("수정 / 삭제");
		listButton = new JButton("목록");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(listButton);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		addButton.addActionListener(e -> {
			AddPhoneDialog addDialog = new AddPhoneDialog(this, this.tableModel);
			addDialog.setVisible(true);
		});
		
		editButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if(selectedRow >= 0) {
				EditPhoneDialog editDialog = new EditPhoneDialog(this, this.tableModel, selectedRow);
				editDialog.setVisible(true);
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
	
	void insertPhone(PhoneDto phoneDto) {
		int ret = phoneDao.insertPhone(phoneDto);
		
		if(ret > 0) {
			listPhone();
		}
	}
	
	void updatePhone(PhoneDto phoneDto) {
		int ret = phoneDao.updatePhone(phoneDto);
		
		if(ret > 0) {
			listPhone();
		}
	}
	
	void deletePhone(int phoneId) {
		int ret = phoneDao.deletePhone(phoneId);
		
		if(ret > 0) {
			listPhone();
		}
	}
	
	PhoneDto detailPhone(int phoneId) {
		return phoneDao.detailPhone(phoneId);
	}
}
