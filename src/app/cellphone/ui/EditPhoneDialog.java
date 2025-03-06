package app.cellphone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.cellphone.dto.PhoneDto;

public class EditPhoneDialog extends JDialog {
	private JTextField phoneIdField, brandField, phoneNameField, priceField, countField;
	private JButton editButton, deleteButton;
	
	public EditPhoneDialog(PhoneManager parent, DefaultTableModel tableModel, int rowIndex) {
		setTitle("Phone Edit Dialog");
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);
		
		Integer phoneId = (Integer) tableModel.getValueAt(rowIndex, 0);
		PhoneDto phoneDto = parent.detailPhone(phoneId);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(5, 2));
		
		phoneIdField = new JTextField(String.valueOf(phoneId));
		phoneIdField.setEditable(false);
		brandField = new JTextField(phoneDto.getBrand());
		phoneNameField = new JTextField(phoneDto.getName());
		priceField = new JTextField(String.valueOf(phoneDto.getPrice()));
		countField = new JTextField(String.valueOf(phoneDto.getCount()));
		
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
		
		JPanel buttonPanel = new JPanel();
		
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		editButton.addActionListener(e -> {
			String brand = brandField.getText();
			String phoneName = phoneNameField.getText();
			int price = Integer.parseInt(priceField.getText());
			int count = Integer.parseInt(countField.getText());
			
			parent.updatePhone(new PhoneDto(phoneId, brand, phoneName, price, count));
			
			dispose();
		});
		
		deleteButton.addActionListener(e -> {
			parent.deletePhone(phoneId);
			
			dispose();
		});
	}
}
