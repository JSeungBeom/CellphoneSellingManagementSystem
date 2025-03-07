package app.cellphone.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import app.cellphone.common.HashManager;
import app.cellphone.dao.LoginDao;
import app.cellphone.dto.AdminDto;
import app.cellphone.dto.UserDto;

// 로그인 화면
public class LoginManager extends JFrame {
	
	private CardLayout cardLayout; // 어드민, 유저 로그인을 구분하기 위해 CardLayout 사용
	private JPanel cardPanel;
	private LoginDao loginDao = new LoginDao();
	
	public LoginManager() {
		setTitle("Login Manager");
		setSize(250, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		JButton adminButton = new JButton("Admin");
		JButton userButton = new JButton("User");
		
		buttonPanel.add(adminButton);
		buttonPanel.add(userButton);
		add(buttonPanel, BorderLayout.NORTH);
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		
		JPanel adminPanel = AdminLoginPanel();
		JPanel userPanel = UserLoginPanel();
		
		cardPanel.add(adminPanel, "Admin");
		cardPanel.add(userPanel, "User");
		
		add(cardPanel, BorderLayout.CENTER);
		
		adminButton.addActionListener(e ->
			cardLayout.show(cardPanel, "Admin")
		);
		
		userButton.addActionListener(e ->
			cardLayout.show(cardPanel, "User")
		);
		
		setVisible(true);
	}
	
	
	private JPanel AdminLoginPanel() {
		JPanel panel = new JPanel();
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 2));
		
		JTextField adminUsername = new JTextField();
		JTextField adminPassword = new JPasswordField();
		
		JPanel buttonPanel = new JPanel();
		JButton loginButton = new JButton("로그인");
		
		buttonPanel.add(loginButton);
		
		inputPanel.add(new JLabel("Admin Username"));
		inputPanel.add(adminUsername);
		inputPanel.add(new JLabel("Admin Password"));
		inputPanel.add(adminPassword);
		
		panel.add(inputPanel, BorderLayout.NORTH);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		loginButton.addActionListener(e -> {
			AdminDto adminDto = adminLogin(adminUsername.getText(), adminPassword.getText());
			
			if(adminDto != null) {
				PhoneManager phoneManager = new PhoneManager();
				phoneManager.setVisible(true);
				
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "아이디 혹은 패스워드가 일치하지 않습니다.");
			}
		});
		
		return panel;
	}
	
	private JPanel UserLoginPanel() {
		JPanel panel = new JPanel();
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 2));
		
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		
		JPanel buttonPanel = new JPanel();
		JButton loginButton = new JButton("로그인");
		JButton signUpButton = new JButton("회원가입");
		
		buttonPanel.add(loginButton);
		buttonPanel.add(signUpButton);
		
		inputPanel.add(new JLabel("User Username"));
		inputPanel.add(username);
		inputPanel.add(new JLabel("User Password"));
		inputPanel.add(password);
		
		panel.add(inputPanel);
		panel.add(buttonPanel);
		
		loginButton.addActionListener(e -> {
			String userName = username.getText();
			String passWord = password.getText();
			String salt = findSalt(userName);
			
			UserDto loginInfo = userLogin(userName, passWord, salt);
			
			if(loginInfo != null) {
				JOptionPane.showMessageDialog(this, "로그인 되었습니다.");
				PhoneBuyingManager phoneBuyingManager = new PhoneBuyingManager(loginInfo.getUserId());
				phoneBuyingManager.setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "아이디 혹은 패스워드가 일치하지 않습니다.");
			}
		});
		
		signUpButton.addActionListener(e -> {
			SignUpDialog signUpDialog = new SignUpDialog(this);
			signUpDialog.setVisible(true);
		});
		
		return panel;
	}
	
	private AdminDto adminLogin(String username, String password) {
		return loginDao.detailAdmin(username, password);
	}
	
	private UserDto userLogin(String username, String password, String salt) {
		return loginDao.detailUser(username, password, salt);
	}
	
	private String findSalt(String username) {
		return loginDao.detailSalt(username);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater( () -> {
			new LoginManager().setVisible(true);
		});
	}
}
