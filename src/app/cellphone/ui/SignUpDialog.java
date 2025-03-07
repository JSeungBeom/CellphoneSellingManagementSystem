package app.cellphone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.cellphone.common.HashManager;
import app.cellphone.dao.LoginDao;
import app.cellphone.dto.UserDto;

// 회원가입 화면
public class SignUpDialog extends JDialog {

	private JTextField usernameField, passwordField;
	private JButton signUpButton, cancelButton;
	private LoginDao loginDao = new LoginDao();
	
	public SignUpDialog(LoginManager parent) {
		setTitle("Sign Up Dialog");
		setSize(200, 150);
		setLayout(new GridLayout(2, 2));
		setLocationRelativeTo(parent);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 2));
		
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		
		inputPanel.add(new JLabel("Username"));
		inputPanel.add(usernameField);
		inputPanel.add(new JLabel("Password"));
		inputPanel.add(passwordField);
		
		JPanel buttonPanel = new JPanel();
		
		signUpButton = new JButton("회원 가입");
		cancelButton = new JButton("취소");
		
		buttonPanel.add(signUpButton);
		buttonPanel.add(cancelButton);
		
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		signUpButton.addActionListener(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			
			if(username.isBlank()) { // 사용자명 입력 X
				JOptionPane.showMessageDialog(this, "사용자명을 입력해주세요.");
			} else if(password.isBlank()) { // 패스워드 입력 X
				JOptionPane.showMessageDialog(this, "패스워드를 입력해주세요.");
			}
			else {
				int ret = signUp(username, password);
				
				if(ret == -1) { // 사용자명 중복
					JOptionPane.showMessageDialog(this, "중복된 사용자명이 존재합니다.");
				} else {
					JOptionPane.showMessageDialog(this, "회원가입 성공!");
					dispose();
				}
				
			}
		});
		
		cancelButton.addActionListener(e -> dispose());
	}
	
	private int signUp(String username, String password) {
		return loginDao.insertUser(username, password);
	}
}
