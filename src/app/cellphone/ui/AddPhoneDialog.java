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

public class AddPhoneDialog extends JDialog {
	private JTextField phoneIdField, brandField, phoneNameField, priceField, countField;
	private JButton addButton;
	
	public AddPhoneDialog(PhoneManager parent, DefaultTableModel tableModel) {
		setTitle("Phone Add Dialog");
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent); 
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(5, 2));
		
		phoneIdField = new JTextField();
		brandField = new JTextField();
		phoneNameField = new JTextField();
		priceField = new JTextField();
		countField = new JTextField();
		
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
		
		addButton = new JButton("Add");
		
		buttonPanel.add(addButton);
		
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		addButton.addActionListener(e -> {
			int phoneId = Integer.parseInt(phoneIdField.getText());
			String brand = brandField.getText();
			String phoneName = phoneNameField.getText();
			int price = Integer.parseInt(priceField.getText());
			int count = Integer.parseInt(countField.getText());
			
			parent.insertPhone(new PhoneDto(phoneId, brand, phoneName, price, count));
			
			dispose();
		});
	}
}
