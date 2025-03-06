package app.cellphone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import app.cellphone.dto.OrderDto;
import app.cellphone.dto.PhoneDto;

public class BuyPhoneDialog extends JDialog {
	
	private JTextField phoneIdField, brandField, phoneNameField, priceField, countField;
	private JButton buyButton, cancelButton;
	
	public BuyPhoneDialog(PhoneBuyingManager parent, DefaultTableModel tableModel, int rowIndex, int userId) {
		setTitle("Phone Buy Dialog");
		setSize(300, 500);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);
		
		Integer phoneId = (Integer) tableModel.getValueAt(rowIndex, 0);
		PhoneDto phoneDto = parent.detailPhone(phoneId);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(6, 2));
		
		phoneIdField = new JTextField(String.valueOf(phoneId));
		phoneIdField.setEditable(false);
		brandField = new JTextField(phoneDto.getBrand());
		brandField.setEditable(false);
		phoneNameField = new JTextField(phoneDto.getName());
		phoneNameField.setEditable(false);
		priceField = new JTextField(String.valueOf(phoneDto.getPrice()));
		priceField.setEditable(false);
		countField = new JTextField(String.valueOf(phoneDto.getCount()));
		countField.setEditable(false);
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
		
		inputPanel.add(new JLabel("Phone Id"));
		inputPanel.add(phoneIdField);
		inputPanel.add(new JLabel("Brand"));
		inputPanel.add(brandField);
		inputPanel.add(new JLabel("Phone Name"));
		inputPanel.add(phoneNameField);
		inputPanel.add(new JLabel("Price"));
		inputPanel.add(priceField);
		inputPanel.add(new JLabel("Count"));
		inputPanel.add(countField);
		inputPanel.add(new JLabel("Choose Count"));
		inputPanel.add(spinner);
		
		JPanel buttonPanel = new JPanel();
		
		buyButton = new JButton("Buy");
		cancelButton = new JButton("Cancel");
		
		buttonPanel.add(buyButton);
		buttonPanel.add(cancelButton);
		
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
				
		buyButton.addActionListener(e -> {
			int saleprice = Integer.parseInt(priceField.getText());
			int ordercount = (int) spinner.getValue();
			Timestamp orderdate = new Timestamp(new Date().getTime());

			OrderDto orderDto = new OrderDto();
			
			orderDto.setUserId(userId);
			orderDto.setPhoneId(phoneId);
			orderDto.setSaleprice(saleprice);
			orderDto.setOrdercount(ordercount);
			orderDto.setOrderdate(orderdate);
			
			int ret = parent.orderPhone(orderDto);
			
			if(ret == -1) {
				JOptionPane.showMessageDialog(this, "재고 수량을 확인해주세요!");
			} else {
				JOptionPane.showMessageDialog(this, "주문이 성공적으로 처리되었습니다.");
			}
			dispose();
		});
		
		cancelButton.addActionListener(e -> {
			dispose();
		});
	}
}
